package com.quote500.quiz.dataClasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record RestQuestion(Long id, Player player, List<String> quotes, List<String> authors) {
    public RestQuestion(DataBaseQuestion dataBaseQuestion){
        this(dataBaseQuestion.getId(), 
             dataBaseQuestion.getPlayer(),
             new ArrayList<String>(),
             new ArrayList<String>());

        for(CachedQuote quote: dataBaseQuestion.getQuotes()){
            this.quotes.add(quote.getQuote());
            this.authors.add(quote.getAuthor());
        }

        Collections.shuffle(this.authors);
    }
}
