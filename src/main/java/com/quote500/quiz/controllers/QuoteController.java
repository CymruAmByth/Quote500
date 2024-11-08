package com.quote500.quiz.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quote500.quiz.QuoteScraper;
import com.quote500.quiz.dataClasses.CachedQuote;
import com.quote500.quiz.repositories.QuoteRepository;

@RestController
@RequestMapping("/cachedQuotes")
public class QuoteController {

    @Autowired
    QuoteRepository repository;

    @GetMapping("/all")
    public List<CachedQuote> quotes(){
        //Return all quotes from the DB
        List<CachedQuote> result = new ArrayList<CachedQuote>();
        repository.findAll().iterator().forEachRemaining(result::add);
        return result; 
    }

    @GetMapping("/random")
    public CachedQuote quote(){
        QuoteScraper scraper = new QuoteScraper();
        try {
            CachedQuote cQuote = scraper.scrapeZenQuote();
            repository.save(cQuote);
            return cQuote;                
        } catch (Exception e) {
            System.out.println("An error occured scraping zenquotes.io:"+e.getMessage());
            System.out.println("Quote from database returned");

            //Return random quote if ZenQuote fails
            Random random = new Random();
            return repository.findById(random.nextLong(repository.count()-1)).get();
        }
    }

}
