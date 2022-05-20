package br.com.tech4me.av1befelipe.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

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

import br.com.tech4me.av1befelipe.service.MusicaService;
import br.com.tech4me.av1befelipe.shared.MusicaDto;
import br.com.tech4me.av1befelipe.view.model.MusicaRequest;
import br.com.tech4me.av1befelipe.view.model.MusicaResponse;

@RestController
@RequestMapping("api/musicas")
public class MusicaController 
{
    @Autowired
    private MusicaService service;
    ModelMapper mapper = new ModelMapper();

    @PostMapping
    public ResponseEntity <MusicaResponse> criarMusica(@RequestBody @Valid MusicaRequest musicaRequest) 
    {
        MusicaDto musicaDto = mapper.map(musicaRequest, MusicaDto.class);
        musicaDto = service.criarMusica(musicaDto);
        MusicaResponse musicaResponse = mapper.map(musicaDto, MusicaResponse.class);
        return new ResponseEntity<>(musicaResponse, HttpStatus.CREATED) ;
    }

    @GetMapping
    public ResponseEntity <List<MusicaResponse>> obterMusicas() 
    {
        List<MusicaDto> musicasDto = service.obterMusicas();
        List<MusicaResponse> musicasResponse = musicasDto.stream()
        .map(m -> mapper.map(m, MusicaResponse.class))
        .collect(Collectors.toList());
        return new ResponseEntity<>(musicasResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity <MusicaResponse> obterMusicaPorId(@PathVariable String id)
    {
        Optional <MusicaDto> musicaDto = service.obterMusicaPorId(id);

        if(musicaDto.isPresent())
        {
            MusicaResponse musicaResponse = mapper.map(musicaDto.get(), MusicaResponse.class);
            return new ResponseEntity<>(musicaResponse, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/contagem")
    public ResponseEntity <Optional<String>> quantidadeMusicas()
    {
        return new ResponseEntity<>(service.quantidadeMusicas(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity <MusicaResponse> atualizarMusica(@PathVariable String id, @RequestBody @Valid MusicaRequest musicaRequest)
    {
        MusicaDto musicaDto = mapper.map(musicaRequest, MusicaDto.class);
        musicaDto = service.atualizarMusica(id, musicaDto);
        MusicaResponse musicaResponse = mapper.map(musicaDto, MusicaResponse.class);
        return new ResponseEntity<>(musicaResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity <Optional<String>> deletarMusicaPorId(@PathVariable String id)
    {
        return new ResponseEntity<>(service.deletarMusicaPorId(id), HttpStatus.OK);
    }
}
