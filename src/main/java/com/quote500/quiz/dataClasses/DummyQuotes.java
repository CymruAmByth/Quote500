package com.quote500.quiz.dataClasses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DummyQuotes(List<DummyQuote> quotes) {}
