package com.quote500.quiz.repositories;

import org.springframework.data.repository.CrudRepository;

import com.quote500.quiz.dataClasses.Player;
import java.util.List;



public interface PlayerRepository  extends CrudRepository<Player, Long>{
    List<Player> findByHighscoreGreaterThanOrderByHighscoreDesc(int highScore);
}

