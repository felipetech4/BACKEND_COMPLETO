package com.api.cadastro.cadastroms.service;

import java.util.List;
import java.util.Optional;

import com.api.cadastro.cadastroms.shared.ProdutoDto;

public interface CadastroProdutoService {
    
    Optional<List<ProdutoDto>> listAll ();
    Optional<ProdutoDto> getUnique (String id);
    Optional<ProdutoDto> getUniqueByCodigo (String codigo);
    Optional<ProdutoDto> postUnique (ProdutoDto produto);
    Optional<Boolean> putStock (String codigo,boolean adicionar,int novaQuantidade);
    Optional<ProdutoDto> putProduto(String id, ProdutoDto produtoDto);
    Optional <String> deleteById(String id);
}
