package TCP_EX1;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TCPServer {

    public static void main(String args[]) {
        try {
            int serverPort = 6666; // porta do servidor

            /* cria um socket e mapeia a porta para aguardar conexao */
            ServerSocket listenSocket = new ServerSocket(serverPort);

            while (true) {
                System.out.println("Servidor aguardando conexao ...");

                /* aguarda conexoes */
                Socket clientSocket = listenSocket.accept();
                                
                System.out.println("Cliente conectado ... Criando thread ...");

                /* cria um thread para atender a conexao */
                ClientThread c = new ClientThread(clientSocket);

                /* inicializa a thread */
                c.start();
            } 

        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        } 
    } 
} 


class ClientThread extends Thread {

    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;

    public ClientThread(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException ioe) {
            System.out.println("Connection:" + ioe.getMessage());
        }
    } 

    /* metodo executado ao iniciar a thread - start() */
    @Override
    public void run() {
        try {
            Scanner reader = new Scanner(System.in);
            String buffer;
            while (true) {
                System.out.print("Mensagem: ");
                buffer = reader.nextLine(); // lê mensagem via teclado

                out.writeUTF(buffer);      	// envia a mensagem para o servidor

                if (buffer.equals("SAIR")) {
                    break;
                }

                buffer = in.readUTF();      // aguarda resposta do servidor
                System.out.println("Client disse: " + buffer);
            }
        } catch (EOFException eofe) {
            System.out.println("EOF: " + eofe.getMessage());
        } catch (IOException ioe) {
            System.out.println("IOE: " + ioe.getMessage());
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException ioe) {
                System.err.println("IOE: " + ioe);
            }
        }
        System.out.println("Thread comunicação cliente finalizada.");
    } //run
} //class
