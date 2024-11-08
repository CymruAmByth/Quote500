package com.quote500.quiz.dataClasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ZenQuote(@JsonProperty("q") String quote,
                       @JsonProperty("a") String author){}
