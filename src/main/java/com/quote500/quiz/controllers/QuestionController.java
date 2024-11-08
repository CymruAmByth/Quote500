package com.quote500.quiz.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.quote500.quiz.QuoteScraper;
import com.quote500.quiz.dataClasses.Answer;
import com.quote500.quiz.dataClasses.CachedQuote;
import com.quote500.quiz.dataClasses.DataBaseQuestion;
import com.quote500.quiz.dataClasses.Player;
import com.quote500.quiz.dataClasses.RestQuestion;
import com.quote500.quiz.repositories.PlayerRepository;
import com.quote500.quiz.repositories.QuestionRepository;
import com.quote500.quiz.repositories.QuoteRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuoteRepository quoteRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @GetMapping("/{playerId}")
    public RestQuestion getQuestion(@PathVariable Long playerId) {
        Player player;
        List<CachedQuote> quotes = new ArrayList<CachedQuote>();
        QuoteScraper scraper = new QuoteScraper();

        //find player
        try {
            player = playerRepository.findById(playerId).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
        }

        //if the player already has a question open, remove it and set streak to 0
        if(questionRepository.countByPlayer(player)>0){
            player.setCurrentStreak(0);
            playerRepository.save(player);

            DataBaseQuestion exQuestion = questionRepository.findByPlayer(player).get();
            questionRepository.delete(exQuestion);
        }

        //collext quotes for the question
        for (int i = 0; i < 3; i++) {
            try {
                CachedQuote cQuote = scraper.scrapeZenQuote();
                quoteRepository.save(cQuote);
                quotes.add(cQuote);
            } catch (Exception e) {
                System.out.println("An error occured scraping zenquotes.io:"+e.getMessage());
                System.out.println("Quote from database returned");
                //Return random quote if ZenQuote fails
                Random random = new Random();
                quotes.add(quoteRepository.findById(random.nextLong(quoteRepository.count()-1)+1).get());
            }
        }

        //save question and return result
        DataBaseQuestion dataBaseQuestion = new DataBaseQuestion(player, quotes);
        questionRepository.save(dataBaseQuestion);
        return new RestQuestion(dataBaseQuestion);
    }

    @PostMapping("/answer")
    public Player answerQuestion(@RequestBody Answer answer) {
        Player player;
        //find player
        try {
            player = playerRepository.findById(answer.playerId()).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
        } 

        DataBaseQuestion question;
        //find question
        try {
            question = questionRepository.findByPlayer(player).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found");
        }

        //Check if the answer is correct (The quotes and answers in the answer correspond with the question)
        if(answer.quotes().containsAll(question.getQuotes()) && answer.quotes().size() == question.getQuotes().size()){
            //Raise current streak
            player.setCurrentStreak(player.getCurrentStreak() + 1);
            //If high score broken, raise high score
            if(player.getHighscore() < player.getCurrentStreak() ){
                player.setHighscore(player.getCurrentStreak());
            }
        } else {
            player.setCurrentStreak(0);
        }

        //Remove question, save player, return player
        questionRepository.delete(question);
        playerRepository.save(player);        
        return player;
    }
    

}
