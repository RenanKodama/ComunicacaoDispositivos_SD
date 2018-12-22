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

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Cliente {

    public static void main(String args[]) throws ClassNotFoundException {
        Music musicOne, musicTwo;
        Socket socket;

        ObjectOutputStream object;

        try {

            System.out.println("Making objects ...\n");

            musicOne = new Music("In the End", "Laplace Link2000", 2018, "RockMetak", "Samba");
            musicTwo = new Music("Cirando", "Cirandinha", 1500, "Vamos", "Cirandar");

            System.out.println("Connecting to server ...\n");
            socket = new Socket("localhost", 6666);

            object = new ObjectOutputStream(socket.getOutputStream());

            System.out.println("Sending objects ...\n");
            object.writeObject(musicOne);
            object.writeObject(musicTwo);
            object.flush();

            System.out.println("Transfer Finished!");

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Solicitando Listagem ...");
            out.writeUTF("Listar");

            ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
            ArrayList<Music> playlist = (ArrayList<Music>) objIn.readObject();

            System.out.println("Playlis: \n" + playlist.get(0).toString() + "\n" + playlist.get(1).toString());
            System.out.println("Finalizado!");

        } catch (IOException e) {
            System.out.println(e);
        } //catch
    } //main

}
