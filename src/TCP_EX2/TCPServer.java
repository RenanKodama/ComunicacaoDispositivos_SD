package TCP_EX2;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class TCPServer {

    public static void main(String args[]) {
        try {
            int serverPort = 6666; // porta do servidor
 
            /* cria um socket e mapeia a porta para aguardar conexao */
            ServerSocket listenSocket = new ServerSocket(serverPort);
            ArrayList<DataOutputStream> clientes_conectados = new ArrayList<>();

            while (true) {
                System.out.println("Servidor aguardando conexao ...");

                /* aguarda conexoes */
                Socket clientSocket = listenSocket.accept();

                System.out.println("Cliente conectado ... Criando thread ...");

                /* cria um thread para atender a conexao */
                ClientThread c = new ClientThread(clientSocket, clientes_conectados);

                /* inicializa a thread */
                c.start();
            } //while

        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        } //catch
    } //main
} //class

/**
 * Classe ClientThread: Thread responsavel pela comunicacao Descricao: Rebebe um
 * socket, cria os objetos de leitura e escrita, aguarda msgs clientes e
 * responde com a msg + :OK
 */
class ClientThread extends Thread {

    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    ArrayList<DataOutputStream> clientes_conectados;

    public ClientThread(Socket clientSocket, ArrayList<DataOutputStream> clientes_conectados) {
        this.clientes_conectados = clientes_conectados;

        try {
            this.clientSocket = clientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

            if (!clientes_conectados.contains(out)) {
                clientes_conectados.add(out);
            }
            
        } catch (IOException ioe) {
            System.out.println("Connection:" + ioe.getMessage());
        } //catch
    } //construtor

    /* metodo executado ao iniciar a thread - start() */
    @Override
    public void run() {
        try {
            String buffer;

            while (true) {
                buffer = in.readUTF();
                /* aguarda o envio de dados */

                System.out.println("Cliente disse: " + buffer);

                if (buffer.equals("PARAR")) {
                    break;
                }
                for (DataOutputStream s : clientes_conectados) {
                    s.writeUTF(buffer);
                }
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
