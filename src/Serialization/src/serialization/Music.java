/*
Atividade :

Comunicação entre dois processos usando:
- Serialização Java
- Protocol buffers (usar duas linguagens de programação diferentes)

Sistema de gerenciamento de livros ou músicas.
Exemplos de campos: título, autor, cantor, ano (int), número de páginas, ISBN, peso (float), dimensoes (struct, tupla, vetor[3]), album, data de lançamento. Use ao menos três tipos de dados diferentes

Mateus Yomemoto 1602055
Renan Kodama 1602098
 */

package serialization;

import java.io.Serializable;

public class Music implements Serializable {

    String titulo;
    String cantor;
    int ano;
    String album;
    String estilo;

    public Music(String titulo, String cantor, int ano, String album, String estilo) {
        this.titulo = titulo;
        this.cantor = cantor;
        this.ano = ano;
        this.album = album;
        this.estilo = estilo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCantor() {
        return cantor;
    }

    public void setCantor(String cantor) {
        this.cantor = cantor;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    @Override
    public String toString() {

        return "\n Titulo: " + this.titulo
                + "\n Cantor: " + this.cantor
                + "\n Ano: " + this.ano
                + "\n Album: " + this.album
                + "\n Estilo: " + this.estilo; //To change body of generated methods, choose Tools | Templates.
    }

}
