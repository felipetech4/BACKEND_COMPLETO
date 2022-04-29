package com.api.cadastro.cadastroms.service;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.api.cadastro.cadastroms.model.Produto;
import com.api.cadastro.cadastroms.repository.CadastroProdutoRepository;
import com.api.cadastro.cadastroms.shared.ProdutoDto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroProdutoServiceImpl implements CadastroProdutoService {

    
    private final ModelMapper MAPPER = new ModelMapper();
    @Autowired
    private CadastroProdutoRepository repository;
    @Override
    public Optional<List<ProdutoDto>> listAll() {
        
        if (repository.count() < 1)  {
            return Optional.empty();
        }

        List<ProdutoDto> ListProdutoDto = repository.findAll().stream()
        .map(dat -> MAPPER.map(dat, ProdutoDto.class)).collect(Collectors.toList());
        
        return Optional.of(ListProdutoDto);
    }
    
    
    @Override
    public Optional<ProdutoDto> getUnique (String id) {
        
        Optional<Produto> repositoryResponse = repository.findById(id);
        
        if (repositoryResponse.isEmpty()) {
            return Optional.empty();
        }
        
        Optional<ProdutoDto> produtoDtoResponse = Optional.of(MAPPER.map(repositoryResponse.get(), ProdutoDto.class));
        return produtoDtoResponse;
    }
    
    @Override
    public Optional<ProdutoDto> getUniqueByCodigo(String codigo) {
        
        Optional<Produto> repositoryResponse = repository.findByCodigo(codigo);

        if (repositoryResponse.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(MAPPER.map(repositoryResponse.get(), ProdutoDto.class));
    }
    
    @Override
    public Optional<ProdutoDto> postUnique(ProdutoDto produto) {
        
        Produto repositoryRequest = MAPPER.map(produto, Produto.class);
        Produto repositoryResponse = repository.save(repositoryRequest);
        ProdutoDto produtoResponse = MAPPER.map(repositoryResponse, ProdutoDto.class);
        
        return Optional.of(produtoResponse);
    }
    

    @Override
    public Optional<Boolean> putStock(String codigo, boolean adicionar, int novaQuantidade) {
        
        Optional<Produto> repositoryResponse = repository.findByCodigo(codigo);
        
        if (repositoryResponse.isEmpty()) {
            return Optional.of(false);
        }
        
        // se a opção adicionar estiver ativa ele adiciona e sai do metodo
        if(adicionar) {
            repositoryResponse.get().adicionarEstoque(novaQuantidade);
            repository.save(repositoryResponse.get());
            return Optional.empty();    
        }
        
        boolean modResult = repositoryResponse.get().removerEstoque(novaQuantidade);

        if (!modResult) {
            return Optional.of(false);
        }

        repository.save(repositoryResponse.get());

        return Optional.of(true);
    }



    
    
}
