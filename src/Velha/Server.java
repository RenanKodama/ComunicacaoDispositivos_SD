/*
    Universidade Tecnológica Federal do Paraná
    Programação Concorrente
    
    Renan Kodama Rodrigues 1602098
    
    Sistemas Distribuidos: Jogo da Velha com TCP
*/

package Velha;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main (String args[]) {
        Game game = new Game();
        
        try{
            int serverPort = 7896;
            
            /* cria um socket e mapeia a porta para aguardar conexão */
            ServerSocket listenSocket = new ServerSocket(serverPort);
            
            while(true) {
                System.out.println ("Servidor aguardando conexão ...");
		
		/* aguarda conexões */
                Socket clientSocket = listenSocket.accept();
                
                System.out.println ("Cliente conectado ... Criando thread ...");
                
                /* cria um thread para atender a conexão */
                Connection c = new Connection(clientSocket,game);
            }
            
        } catch(IOException e) {
	    System.out.println("Listen socket:"+e.getMessage());
	} 
    } 
} 