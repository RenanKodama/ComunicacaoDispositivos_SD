/*
*    Universidade Tecnológica Federal do Paraná
*    Sistemas Distribuídos
*
*    Renan Kodama Rodrigues 1602098
 */
package Serializacao;

import java.net.*;
import java.io.*;

public class Servidor {

    public static void main(String args[]) {
        Livro p1, p2;
        ServerSocket serverSocket;
        Socket clientSocket;

        ObjectInputStream objIn;

        try {
            System.out.println("Mapeando porta ...");
            serverSocket = new ServerSocket(6666);

            System.out.println("Servidor aguardando conex�es ...");
            clientSocket = serverSocket.accept();

            System.out.println("Criando objetos de leitura/escrita ...");
            objIn = new ObjectInputStream(clientSocket.getInputStream());

            System.out.println("Aguardando objetos serializados ...");
            p1 = (Livro) objIn.readObject();
            p2 = (Livro) objIn.readObject();

            System.out.println("\nObjetos Recebidos\n");

            System.out.println("\nPessoa 1: "
                    + "\n Titulo: " + p1.getTitulo()
                    + "\n Autor: " + p1.getAutor()
                    + "\n Ano: " + p1.getAno()
                    + "\n Numero de Paginas: " + p1.getNum_paginas()
                    + "\n Peso: " + p1.getPeso());

            System.out.println("\nPessoa 2: "
                    + "\n Titulo: " + p2.getTitulo()
                    + "\n Autor: " + p2.getAutor()
                    + "\n Ano: " + p2.getAno()
                    + "\n Numero de Paginas: " + p2.getNum_paginas()
                    + "\n Peso: " + p2.getPeso());

            System.out.println("\nSistema finalizado!");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        } 
    } 

}
