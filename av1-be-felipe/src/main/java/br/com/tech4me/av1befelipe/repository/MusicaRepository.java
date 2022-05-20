package br.com.tech4me.av1befelipe.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.tech4me.av1befelipe.model.Musica;

public interface MusicaRepository extends MongoRepository<Musica, String> 
{
    
}
