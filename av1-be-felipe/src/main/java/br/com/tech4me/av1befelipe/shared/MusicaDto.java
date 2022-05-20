package br.com.tech4me.av1befelipe.shared;

public class MusicaDto 
{
    private String titulo;
    private String artista;
    private String album;
    private String genero;
    private int anoLancamento;
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
