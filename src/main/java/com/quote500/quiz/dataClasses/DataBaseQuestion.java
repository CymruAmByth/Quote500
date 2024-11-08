package com.quote500.quiz.dataClasses;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity
public class DataBaseQuestion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Player player;    
    @ManyToMany
    private List<CachedQuote> quotes;
    
    protected DataBaseQuestion(){}

    public DataBaseQuestion(Player player, List<CachedQuote> quotes) {
        this.player = player;
        this.quotes = quotes;
    }

    public Long getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public List<CachedQuote> getQuotes() {
        return quotes;
    }

    

    
}
