package br.com.tech4me.produtosms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.tech4me.produtosms.model.Produto;

public interface ProdutoRepository extends MongoRepository <Produto, String>
{
    
}
