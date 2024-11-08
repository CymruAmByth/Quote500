package com.quote500.quiz.dataClasses;

import java.util.List;

public record Answer(long playerId, List<CachedQuote> quotes){}
