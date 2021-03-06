package com.api.cadastro.cadastroms.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.api.cadastro.cadastroms.service.CadastroProdutoService;
import com.api.cadastro.cadastroms.shared.ProdutoDto;
import com.api.cadastro.cadastroms.view.model.ProdutoRequest;
import com.api.cadastro.cadastroms.view.model.ProdutoResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cadastro")
public class CadastoProdutoController {

    private final ModelMapper MAPPER = new ModelMapper();
    @Autowired
    private CadastroProdutoService service;

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> getAll() {
        Optional<List<ProdutoDto>> serviceResponse = service.listAll();

        if (serviceResponse.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<ProdutoResponse> produtoResponseList = serviceResponse.get().stream()
                .map(dat -> MAPPER.map(dat, ProdutoResponse.class)).collect(Collectors.toList());

        return new ResponseEntity<>(produtoResponseList, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> getUnique(@PathVariable String id) {
        Optional<ProdutoDto> serviceResponse = service.getUnique(id);

        if (serviceResponse.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ProdutoResponse produtoResponse = MAPPER.map(serviceResponse.get(), ProdutoResponse.class);

        return new ResponseEntity<>(produtoResponse, HttpStatus.FOUND);
    }

    @GetMapping("/pesquisar-por-codigo/{codigo}")
    public Optional<ProdutoDto> getUniqueByCodigo(@PathVariable String codigo) {

        return Optional.of(service.getUniqueByCodigo(codigo).get());
    }

    @PostMapping("/adicionar")
    public ResponseEntity<ProdutoResponse> postUnique(@RequestBody @Valid ProdutoRequest produtoReq) {

        ProdutoDto produtoDtoReq = MAPPER.map(produtoReq, ProdutoDto.class);
        Optional<ProdutoDto> produtoDtoRes = service.postUnique(produtoDtoReq);

        ProdutoResponse produtoResponse = MAPPER.map(produtoDtoRes.get(), ProdutoResponse.class);

        return new ResponseEntity<>(produtoResponse, HttpStatus.CREATED);

    }

    @PutMapping("/modificar-estoque/{codigo}/{adicionar}/{novaQuantidade}")
    public Optional<Boolean> putStock(@PathVariable String codigo, @PathVariable boolean adicionar,
            @PathVariable int novaQuantidade) {
        Optional<Boolean> serviceResponse = service.putStock(codigo, adicionar, novaQuantidade);

        if (serviceResponse.isEmpty()) {
            return Optional.empty();
        }

        return serviceResponse;

    }

    @PutMapping("alterar/{id}")
    public ResponseEntity<ProdutoResponse> putProduto(@PathVariable String id,
            @RequestBody @Valid ProdutoRequest produtoRequest) {
        ProdutoDto produtoDto = MAPPER.map(produtoRequest, ProdutoDto.class);
        
        Optional<ProdutoDto> serviceResponse = service.putProduto(id, produtoDto);
        
        if (serviceResponse.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ProdutoResponse produtoResponse = MAPPER.map(serviceResponse.get(), ProdutoResponse.class);

        return new ResponseEntity<>(produtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Optional<String>> deleteProduto(@PathVariable String id) {
        return new ResponseEntity<>(service.deleteById(id), HttpStatus.OK);
    }
}
