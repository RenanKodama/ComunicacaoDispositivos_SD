/*
    1) Fazer um código para o Cliente e Servidor se comunicarem. O cliente envia e 
    recebe mensagens. O servidor envia e recebe mensagens. Quando algum dos dois enviar 
    'SAIR', a comunicação entre eles deve ser finalizada. Use o TCP.
*/

package TCP_EX1;


import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TCPClient {
	public static void main (String args[]) {
	    Socket clientSocket = null; // socket do cliente
            Scanner reader = new Scanner(System.in); // ler mensagens via teclado
            
            try{
                /* Endereço e porta do servidor */
                int serverPort = 6666;   
                InetAddress serverAddr = InetAddress.getByName("127.0.0.1");
                
                /* conecta com o servidor */  
                clientSocket = new Socket(serverAddr, serverPort);  
                
                /* cria objetos de leitura e escrita */
                DataInputStream in = new DataInputStream( clientSocket.getInputStream());
                DataOutputStream out =new DataOutputStream( clientSocket.getOutputStream());
                
                /* protocolo de comunicação */
                String buffer;
                while (true) {
                    System.out.print("Mensagem: ");
                    buffer = reader.nextLine(); // lê mensagem via teclado
                
                    out.writeUTF(buffer);      	// envia a mensagem para o servidor
		
                    if (buffer.equals("SAIR")) break;
                    
                    buffer = in.readUTF();      // aguarda resposta do servidor
                    System.out.println("Server disse: " + buffer);
                } 
	    } catch (UnknownHostException ue){
		System.out.println("Socket:" + ue.getMessage());
            } catch (EOFException eofe){
		System.out.println("EOF:" + eofe.getMessage());
            } catch (IOException ioe){
		System.out.println("IO:" + ioe.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException ioe) {
                    System.out.println("IO: " + ioe);;
                }
            }
     } 
} 