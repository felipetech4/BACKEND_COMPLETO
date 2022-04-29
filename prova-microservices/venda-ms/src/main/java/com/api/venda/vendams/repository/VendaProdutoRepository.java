package com.api.venda.vendams.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.api.venda.vendams.model.Venda;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaProdutoRepository extends MongoRepository<Venda,String> { 
    Optional<List<Venda>> findAllByCodigo (String codigo);
}
