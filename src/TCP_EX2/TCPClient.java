package TCP_EX2;

/**
 * TCPClient: Cliente para conexao TCP Descricao: Envia uma informacao ao
 * servidor e recebe confirmações ECHO Ao enviar "PARAR", a conexão é
 * finalizada.
 */
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class TCPClient {

    public static void main(String args[]) {
        Socket clientSocket = null; // socket do cliente
        //Scanner reader = new Scanner(System.in); // ler mensagens via teclado
        
        ArrayList<String> mensagens = new ArrayList();
        
        try { 
            /* Endereço e porta do servidor */
            int serverPort = 6666;
            InetAddress serverAddr = InetAddress.getByName("127.0.0.1");

            /* conecta com o servidor */
            clientSocket = new Socket(serverAddr, serverPort);

            /* cria objetos de leitura e escrita */
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            Interface face = new Interface(out);
            face.setVisible(true);

            /* protocolo de comunicação */
            String buffer;
            while (true) {
                buffer = in.readUTF();      // aguarda resposta do servidor
                if (buffer.equals("PARAR")) {
                    break;
                }

                System.out.println(">" + buffer);
                mensagens.add(buffer+"\n");
                
                face.getTextArea().setText(mensagens.toString());
                
            }

        } catch (UnknownHostException ue) {
            System.out.println("Socket:" + ue.getMessage());
        } catch (EOFException eofe) {
            System.out.println("EOF:" + eofe.getMessage());
        } catch (IOException ioe) {
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
