package com.quote500.quiz.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.quote500.quiz.dataClasses.Highscore;
import com.quote500.quiz.dataClasses.Player;
import com.quote500.quiz.repositories.PlayerRepository;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HighScoreController {
    @Autowired
    PlayerRepository repository;

    @GetMapping("/highscore")
    public List<Highscore> getHighScores() {
        List<Highscore> highscores = new ArrayList<Highscore>();

        //Load scores into result
        for(Player player: repository.findByHighscoreGreaterThanOrderByHighscoreDesc(0)){
            highscores.add(new Highscore(player.getName(), player.getHighscore()));
        }

        return highscores;
    }
}
