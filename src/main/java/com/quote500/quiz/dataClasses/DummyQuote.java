package com.quote500.quiz.dataClasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DummyQuote(String quote, String author){

}
