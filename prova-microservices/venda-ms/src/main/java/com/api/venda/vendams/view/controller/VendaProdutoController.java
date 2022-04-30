package com.api.venda.vendams.view.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.api.venda.vendams.service.VendaProdutoService;
import com.api.venda.vendams.shared.VendaDto;
import com.api.venda.vendams.view.model.VendaDetails;
import com.api.venda.vendams.view.model.VendaRequest;
import com.api.venda.vendams.view.model.VendaResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/venda")
public class VendaProdutoController {
    
    private final ModelMapper MAPPER = new ModelMapper();
    @Autowired
    private VendaProdutoService service;

    @GetMapping
    public ResponseEntity<List<VendaResponse>> getAll () {
        Optional<List<VendaDto>> serviceResponse = service.listAll();
        
        if (serviceResponse.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<VendaResponse> vendaResponseList = serviceResponse.get().stream()
        .map(dat -> MAPPER.map(dat, VendaResponse.class)).collect(Collectors.toList());

        return new ResponseEntity<>(vendaResponseList, HttpStatus.OK);
    
    }

    @GetMapping("/pesquisar-por-codigo/{codigo}")
    public ResponseEntity<List<VendaResponse>> getAllByCodigo (@PathVariable String codigo) {
        Optional<List<VendaDto>> serviceResponse = service.listAllByCodigo(codigo);

        if (serviceResponse.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<VendaResponse> vendaResponseList = serviceResponse.get().stream()
        .map(dat -> MAPPER.map(dat, VendaResponse.class)).collect(Collectors.toList());

        return new ResponseEntity<>(vendaResponseList, HttpStatus.FOUND);
    }

    @GetMapping("/pesquisar-por-intervalo/{data1}/{data2}")
    public ResponseEntity<List<VendaResponse>> getAllByInterval ( @PathVariable String data1, @PathVariable String data2) throws ParseException {        
        String fixedDate1 = data1.replaceAll("-", "/");
        String fixedDate2 = data2.replaceAll("-", "/");

        boolean datesValidated = validarData(fixedDate1) && validarData(fixedDate2);
        
        if (!datesValidated) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<List<VendaDto>> serviceResponse = service.listAllByDateInterval(fixedDate1, fixedDate2);

        if (serviceResponse.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<VendaResponse> vendaResponseList = serviceResponse.get().stream()
        .map(dat -> MAPPER.map(dat, VendaResponse.class)).collect(Collectors.toList());

        return new ResponseEntity<>(vendaResponseList, HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaResponse> getUnique (@PathVariable String id) {
        Optional<VendaDto> serviceResponse = service.listUnique(id);

        if (serviceResponse.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        VendaResponse vendaResponseUnique = MAPPER.map(serviceResponse.get(), VendaResponse.class);
        
        return new ResponseEntity<>(vendaResponseUnique, HttpStatus.FOUND);
        
    }


    @PostMapping("/adicionar")
    public ResponseEntity<VendaDetails> postUnique (@RequestBody @Valid VendaRequest venda) {
        
        VendaDto vendaReq = MAPPER.map(venda, VendaDto.class);
        Optional<VendaDto> vendaRes = service.postUnique(vendaReq);

        if (vendaRes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        VendaDetails vendaDetailsRes = MAPPER.map(vendaRes.get(), VendaDetails.class);

        return new ResponseEntity<>(vendaDetailsRes, HttpStatus.CREATED);
        
    }
    
    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<Optional<String>> cancelUnique(@PathVariable String id) {
        return new ResponseEntity<>(service.cancelById(id), HttpStatus.OK);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Optional<String>> deleteUnique(@PathVariable String id) {
        return new ResponseEntity<>(service.cancelById(id), HttpStatus.OK);
    }

    private static boolean validarData (String data) {

        boolean dataValida = data.matches("^(\\d{2})[/](\\d{2})[/](\\d{4})$");
            
            if (!dataValida) { 
                return false;
            }
            
            String[] arrayData = data.split("/");
            
            int dia = Integer.parseInt(arrayData[0]);
            int mes = Integer.parseInt(arrayData[1]);
            int ano = Integer.parseInt(arrayData[2]);
            
            if ((dia > 0 && dia <= 31) &&
            (mes > 0 && mes <= 12) &&
            (ano > 1900 && ano <= 2050)) {
                return true;
            } else {
                return false;
            }
    }
    

}
