package com.quote500.quiz.repositories;

import org.springframework.data.repository.CrudRepository;

import com.quote500.quiz.dataClasses.CachedQuote;

public interface QuoteRepository  extends CrudRepository<CachedQuote, Long>{
}
