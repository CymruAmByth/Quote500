package com.quote500.quiz.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.quote500.quiz.dataClasses.Player;
import com.quote500.quiz.repositories.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    PlayerRepository repository;

    @GetMapping("/{id}")    
    public Player getPlayer(@PathVariable Long id){
        try {
            return repository.findById(id).get();            
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
        }
    }

    @PostMapping("/new")
    public Player newPlayer(@RequestBody Player newPlayer) {        
        try {
            return repository.save(newPlayer);    
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
        
    }
    
}
