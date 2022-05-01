package com.api.venda.vendams.clientHttp;

import java.util.Optional;

import com.api.venda.vendams.shared.Produto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cadastro-ms", fallback = ProdutoFeingClientFallback.class)
public interface ProdutoFeingClient {

    @GetMapping(path = "/api/cadastro/pesquisar-por-codigo/{codigo}")
    Optional<Produto> getProduto(@PathVariable String codigo);

    @PutMapping(path = "/api/cadastro/modificar-estoque/{codigo}/{adicionar}/{novaQuantidade}")
    public Optional<Boolean> putStock(@PathVariable String codigo, @PathVariable boolean adicionar,
            @PathVariable int novaQuantidade);

}

@Component
class ProdutoFeingClientFallback implements ProdutoFeingClient {

    @Override
    public Optional<Produto> getProduto(String codigo) {
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> putStock(String codigo, boolean adicionar, int novaQuantidade) {
        return Optional.empty();
    }

}
