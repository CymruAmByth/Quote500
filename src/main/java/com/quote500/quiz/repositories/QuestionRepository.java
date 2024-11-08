package com.quote500.quiz.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.quote500.quiz.dataClasses.DataBaseQuestion;
import com.quote500.quiz.dataClasses.Player;


public interface QuestionRepository  extends CrudRepository<DataBaseQuestion, Long>{
    Optional<DataBaseQuestion> findByPlayer(Player player);
    long countByPlayer(Player player);
}
