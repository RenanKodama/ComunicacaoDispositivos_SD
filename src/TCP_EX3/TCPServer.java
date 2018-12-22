package TCP_EX3;

import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

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
            } //while

        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        } //catch
    } //main
} //class

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

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            String buffer;
            LocalDateTime now = LocalDateTime.now();
            String[] now_Str = formato.format(now).split(" ");

            File folder = new File("src/Files/");
            File[] listOfFiles;

            Path fileLocation;
            byte[] data = new byte[1];

            while (true) {
                String name_files;
                buffer = in.readUTF();
                String[] split = buffer.split(" ");
                boolean hasFile = false;

                if (buffer.equals("EXIT")) {
                    break;
                }

                switch (buffer.split(" ")[0]) {
                    case "TIME":
                        buffer = now_Str[1];
                        break;

                    case "DATE":
                        buffer = now_Str[0];
                        break;

                    case "FILES":
                        name_files = "";
                        listOfFiles = folder.listFiles();

                        for (File file : listOfFiles) {
                            if (file.isFile()) {
                                name_files += " ||| " + file.getName();
                            }
                        }
                        name_files += "  <Files: " + listOfFiles.length + "> ";
                        buffer = name_files;

                        break;

                    case "DOWN":
                        listOfFiles = folder.listFiles();

                        if (split.length < 2) {
                            break;
                        }

                        name_files = buffer.replaceAll("DOWN ","");
                        System.out.println(name_files);
                       
                        for (File file : listOfFiles) {
                            if (file.isFile() && file.getName().equals(name_files)) {
                                fileLocation = Paths.get(file.getPath());
                                data = Files.readAllBytes(fileLocation);
                                hasFile = true;
                            }
                        }
                        
                        if (hasFile){
                            buffer = Arrays.toString(data)+"\n"+"Size: "+data.length;
                        }else{
                            buffer = "Arquivo Inexistente";
                        }
                        
                        break;

                    default:
                        break;
                }

                out.writeUTF(buffer);
                
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
    }
}
