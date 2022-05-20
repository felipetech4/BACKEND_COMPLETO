package br.com.tech4me.av1befelipe.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tech4me.av1befelipe.model.Musica;
import br.com.tech4me.av1befelipe.repository.MusicaRepository;
import br.com.tech4me.av1befelipe.shared.MusicaDto;

@Service
public class MusicaServiceImpl implements MusicaService 
{
    @Autowired
    private MusicaRepository repository;
    ModelMapper mapper = new ModelMapper();

    @Override
    public MusicaDto criarMusica(MusicaDto musicaDto) 
    {
        Musica musica = mapper.map(musicaDto, Musica.class);
        musica = repository.save(musica);
        MusicaDto dto = mapper.map(musica, MusicaDto.class);
        return dto;
    }

    @Override
    public List<MusicaDto> obterMusicas() 
    {
        List<Musica> musicas = repository.findAll();
        List<MusicaDto> musicasDto = musicas.stream()
        .map(m -> mapper.map(m, MusicaDto.class))
        .collect(Collectors.toList());
        return musicasDto;
    }

    @Override
    public Optional <MusicaDto> obterMusicaPorId(String id) 
    {
        Optional <Musica> musica = repository.findById(id);
        if(musica.isPresent())
        {
            MusicaDto musicaDto = mapper.map(musica.get(), MusicaDto.class);
            return Optional.of(musicaDto);
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> quantidadeMusicas() 
    {
        Long quantidadeLong = repository.count();
        String quantidadeString = mapper.map(quantidadeLong, String.class);
        return Optional.of(String.format("ESSA COLLECTION POSSUI %s DOCUMENTOS", quantidadeString));
    }

    @Override
    public MusicaDto atualizarMusica(String id, MusicaDto musicaDto) 
    {
        Musica musica = mapper.map(musicaDto, Musica.class);
        musica.setId(id);
        repository.save(musica);
        MusicaDto dto = mapper.map(musica, MusicaDto.class);
        return dto;
    }

    @Override
    public Optional <String> deletarMusicaPorId(String id) 
    {
        Optional <Musica> musica = repository.findById(id);
        if (musica.isPresent())
        {
            repository.deleteById(id);
            return Optional.of(String.format("REGISTRO DA MÚSICA '%s' DELETADO COM SUCESSO!", 
            musica.get().getTitulo()));
        }
        return Optional.of("REGISTRO INFORMADO NÃO EXISTE");
    }
}
