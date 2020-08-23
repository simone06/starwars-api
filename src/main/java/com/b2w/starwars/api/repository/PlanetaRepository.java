package com.b2w.starwars.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.b2w.starwars.api.model.Planeta;


public interface PlanetaRepository extends MongoRepository<Planeta, String> {

    public Planeta findByNome(String nome);

    


}
