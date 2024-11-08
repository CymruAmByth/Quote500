package com.quote500.quiz.dataClasses;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CachedQuote{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String quote;
    private String author;
    
    protected CachedQuote(){}

    public CachedQuote(String quote, String author) {
        this.quote = quote;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getQuote() {
        return quote;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((quote == null) ? 0 : quote.hashCode());
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CachedQuote other = (CachedQuote) obj;
        if (quote == null) {
            if (other.quote != null)
                return false;
        } else if (!quote.equals(other.quote))
            return false;
        if (author == null) {
            if (other.author != null)
                return false;
        } else if (!author.equals(other.author))
            return false;
        return true;
    }    
    
}
