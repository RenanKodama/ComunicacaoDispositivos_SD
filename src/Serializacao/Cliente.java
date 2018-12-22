/*
*    Universidade Tecnológica Federal do Paraná
*    Sistemas Distribuídos
*
*    Renan Kodama Rodrigues 1602098
*/

package Serializacao;


import java.net.*;
import java.io.*;


public class Cliente {
    
    public static void main (String args[]) {
        Livro a, b;
        Socket s;
        
        ObjectOutputStream objOut;
        
        try {
            System.out.println ("Criando instâncias da classe Pessoa ...\n");
            a = new Livro ("Life is Sucks", "Laplace", 2018, 60, 0.5f);
            b = new Livro ("Life is Good", "Renan", 2016, 420, 0.452f);
            
            System.out.println ("Conectando ao servidor ...\n");
            s = new Socket ("localhost",6666);
            
            System.out.println ("Criando objetos de leitura/escrita ...\n");
            objOut = new ObjectOutputStream (s.getOutputStream());
            
            System.out.println ("Enviando objetos serializados ...\n");
            objOut.writeObject(a);
            objOut.writeObject(b);
            objOut.flush();

            System.out.println("Print ");
            
            
            
            System.out.println ("Finalizado.");
            
        } catch (IOException e) {
            System.out.println(e);
        } //catch
    } //main
    
    
}
