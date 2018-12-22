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

public class Servidor {

    public static void main(String args[]) {
        Music music1, music2;
        ServerSocket serverSocket;
        Socket clientSocket;
        ArrayList<Music> list = new ArrayList();

        ObjectInputStream inputObject;

        try {

            serverSocket = new ServerSocket(6666);

            System.out.println("Waiting for connections ...");
            clientSocket = serverSocket.accept();

            System.out.println("Making objects ...");
            inputObject = new ObjectInputStream(clientSocket.getInputStream());

            System.out.println("Waiting processed objects ...");
            music1 = (Music) inputObject.readObject();
            music2 = (Music) inputObject.readObject();
            
            System.out.println("\n-------- Received Objects ------\n");
            System.out.println(music1.toString());
            System.out.println(music2.toString());

            list.add(music1);
            list.add(music2);

            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            ObjectOutputStream objOut = new ObjectOutputStream(clientSocket.getOutputStream());

            while (true) {
                if (in.readUTF().equals("Listar")) {
                    System.out.println("Enviando Músicas ...");
                    objOut.writeObject(list);
                    objOut.flush();
                    System.out.println("Lista enviada com sucesso!");
                    break;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

}
