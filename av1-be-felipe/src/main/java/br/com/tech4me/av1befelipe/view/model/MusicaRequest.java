package br.com.tech4me.av1befelipe.view.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class MusicaRequest 
{
    @NotBlank(message = "O título deve possuir caracteres(não brancos)")
    @NotEmpty(message = "O título deve ser preenchido")
    @Pattern(regexp = "^[A-Z]+(.)*", message = "O título deve começar com letra MAIÚSCULA")
    private String titulo;
    @NotBlank(message = "O artista deve possuir caracteres(não brancos)")
    @NotEmpty(message = "O artista deve ser preenchido")
    @Pattern(regexp = "^[A-Z]+(.)*", message = "O artista deve começar com letra MAIÚSCULA")
    private String artista;
    @NotBlank(message = "O álbum deve possuir caracteres(não brancos)")
    @NotEmpty(message = "O álbum deve ser preenchido")
    @Pattern(regexp = "^[A-Z]+(.)*", message = "O álbum deve começar com letra MAIÚSCULA")
    private String album;
    @NotBlank(message = "O gênero deve possuir caracteres(não brancos)")
    @NotEmpty(message = "O gênero deve ser preenchido")
    @Pattern(regexp = "^[A-Z]+(.)*", message = "O gênero deve começar com letra MAIÚSCULA")
    private String genero;
    @Min(value = 1702, message = "O ano de lançamento deve ser superior a 1701")
    @Max(value = 2022, message = "O ano de lançamento deve ser inferior a 2023")
    private int anoLancamento;
    @NotBlank(message = "O compositor deve possuir caracteres(não brancos)")
    @NotEmpty(message = "O compositor deve ser preenchido")
    @Pattern(regexp = "^[A-Z]+(.)*", message = "O compositor deve começar com letra MAIÚSCULA")
    private String compositor;

    public String getTitulo() 
    {
        return titulo;
    }
    public void setTitulo(String titulo) 
    {
        this.titulo = titulo;
    }


    public String getArtista() 
    {
        return artista;
    }
    public void setArtista(String artista) 
    {
        this.artista = artista;
    }


    public String getAlbum() 
    {
        return album;
    }
    public void setAlbum(String album) 
    {
        this.album = album;
    }


    public String getGenero() 
    {
        return genero;
    }
    public void setGenero(String genero) 
    {
        this.genero = genero;
    }

    
    public int getAnoLancamento() 
    {
        return anoLancamento;
    }
    public void setAnoLancamento(int anoLancamento) 
    {
        this.anoLancamento = anoLancamento;
    }


    public String getCompositor() 
    {
        return compositor;
    }
    public void setCompositor(String compositor) 
    {
        this.compositor = compositor;
    }    
}
