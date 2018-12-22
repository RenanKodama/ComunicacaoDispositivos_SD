/*
*    Universidade Tecnológica Federal do Paraná
*    Sistemas Distribuídos
*
*    Renan Kodama Rodrigues 1602098
*/

package Serializacao;

import java.io.Serializable;


public class Livro implements Serializable {
    
    String titulo;
    String autor;
    int ano;
    int num_paginas;
    float peso;

    public Livro(String titulo, String autor, int ano, int num_paginas, float peso) {
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.num_paginas = num_paginas;
        this.peso = peso;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getNum_paginas() {
        return num_paginas;
    }

    public void setNum_paginas(int num_paginas) {
        this.num_paginas = num_paginas;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }
    
    
    
} 
