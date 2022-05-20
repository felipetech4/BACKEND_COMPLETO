package br.com.tech4me.av1befelipe.service;

import java.util.List;
import java.util.Optional;

import br.com.tech4me.av1befelipe.shared.MusicaDto;

public interface MusicaService 
{
    MusicaDto criarMusica(MusicaDto musica);

    List<MusicaDto> obterMusicas();

    Optional <MusicaDto> obterMusicaPorId(String id);

    Optional <String> quantidadeMusicas();

    MusicaDto atualizarMusica(String id, MusicaDto musica);

    Optional <String> deletarMusicaPorId(String id);
}
