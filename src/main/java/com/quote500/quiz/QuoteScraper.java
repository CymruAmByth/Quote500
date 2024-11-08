package com.quote500.quiz;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.quote500.quiz.dataClasses.CachedQuote;
import com.quote500.quiz.dataClasses.DummyQuote;
import com.quote500.quiz.dataClasses.DummyQuotes;
import com.quote500.quiz.dataClasses.ZenQuote;
import com.quote500.quiz.repositories.QuoteRepository;

@Component
public class QuoteScraper {

  @Autowired
  QuoteRepository repository;

  @EventListener
  public void cacheSomeQuotes(ApplicationReadyEvent event) {

    //Load quotes from dummyJson to cache
    RestTemplate restTemplate = new RestTemplate();
    try {
      DummyQuotes dQuotes = restTemplate.getForObject("https://dummyjson.com/quotes", DummyQuotes.class);
      if (!Objects.isNull(dQuotes)) {
        for (DummyQuote quote : dQuotes.quotes()) {
          repository.save(new CachedQuote(quote.quote(), quote.author()));
        }
        System.out.println("Cached quotes from DummyJson: " + dQuotes.quotes().size());
      }
    } catch (Exception e) {
      System.out.println("Error on trying to load cache quotes from dummyjson.com: " + e.getMessage());
    }

    //Add some custom quotes in case dummyjson is unavailable
    repository.save(new CachedQuote("Fietsbel!", "Joey van Gentevoort"));
    repository.save(new CachedQuote("Daar heb ik geen actieve herinnering aan", "Mark Rutte"));
    repository.save(new CachedQuote("Stories of imagination tend to upset those without one", "Terry Pratchett"));
    System.out.println("Cached custom quotes: 3");
  }

  @SuppressWarnings("null") //warning caught and thrown if need be
  public CachedQuote scrapeZenQuote() throws Exception{
    RestTemplate restTemplate = new RestTemplate();  
    ResponseEntity<ZenQuote[]> response = restTemplate.getForEntity("https://zenquotes.io/api/random", ZenQuote[].class);
      ZenQuote[] quotes = response.getBody();
      //ZenQuote returns an 'error quote' if the quota is exceeded, recognisable by the author parameter.
      if(quotes[0].author().equals("zenquotes.io")){
        throw new Exception("Quote quota exceeded!");
      }
      return new CachedQuote(quotes[0].quote(), quotes[0].author());
  }

}
