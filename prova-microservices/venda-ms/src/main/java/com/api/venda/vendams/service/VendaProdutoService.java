package com.api.venda.vendams.service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import com.api.venda.vendams.shared.VendaDto;

public interface VendaProdutoService {
    
    Optional<List<VendaDto>> listAll ();
    Optional<List<VendaDto>> listAllByCodigo (String codigo);
    Optional<List<VendaDto>> listAllByDateInterval (String primeiraData, String segundaData) throws ParseException;
    Optional<VendaDto> listUnique (String id);
    Optional<VendaDto> postUnique (VendaDto venda);
    Optional<String> deleteById(String id);
}
