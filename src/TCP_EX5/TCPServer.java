package TCP_EX5;

import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class TCPServer {

    public static void main(String args[]) {
        try {
            /* porta do servidor */
            int serverPort = 6666; 

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
            FileWriter writer = new FileWriter("src/Log/log.txt", true);
            //writer.write("texto que você quer salvar");
            ArrayList<String> log = new ArrayList<>();

            //date
            //DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            //LocalDateTime now = LocalDateTime.now();
            //formato.format(now);
            
            String buffer;

            File folder = new File("src/Files/");
            File[] listOfFiles;

            Path fileLocation;
            byte[] data = new byte[1];

            while (true) {
                String name_files;

                buffer = in.readUTF();
                log.add("1");
                
                String[] split = buffer.split(" ");
                boolean hasFile = false;
                
                if (buffer.equals("EXIT")) {
                    break;
                }

                switch (buffer.split(" ")[0]) {

                    case "DELETE":
                        log.add("2");
                        
                        listOfFiles = folder.listFiles();
                        name_files = buffer.replaceAll("DELETE ", "");
                        
                        log.add(Integer.toString(name_files.getBytes().length));
                        log.add(name_files);
                       
                        for (File f : listOfFiles) {
                            if (f.isFile() && f.getName().equals(name_files)) {
                                hasFile = true;
                                f.delete();
                            }
                        }
                        
                        log.add("2");
                        log.add("2");
                        if (hasFile) {
                            log.add("1");
                            buffer = "Removido com Sucesso" + name_files;
                        } else {
                            log.add("2");
                            buffer = "Arquivo não encontrado";
                        }

                        break;

                    case "GETFILESLIST":
                        log.add("3");
                        log.add("-1");
                        log.add("-1");
                        
                        name_files = "";
                        listOfFiles = folder.listFiles();

                        for (File file : listOfFiles) {
                            if (file.isFile()) {
                                name_files += " ||| " + file.getName();
                            }
                        }
                        name_files += "  <Files: " + listOfFiles.length + "> ";
                        
                        
                        log.add("2");
                        log.add("3");
                        log.add("1");
                        
                        buffer = name_files;

                        break;

                    case "GETFILE":
                        log.add("4");
                        
                        listOfFiles = folder.listFiles();

                        if (split.length < 2) {
                            break;
                        }

                        name_files = buffer.replaceAll("GETFILE ", "");
                        log.add(Integer.toString(name_files.getBytes().length));
                        log.add(name_files);
                               
                        for (File file : listOfFiles) {
                            if (file.isFile() && file.getName().equals(name_files)) {
                                fileLocation = Paths.get(file.getPath());
                                data = Files.readAllBytes(fileLocation);
                                hasFile = true;
                            }
                        }
                        
                        log.add("2");
                        log.add("4");
                        
                        if (hasFile) {
                            log.add("1");
                            buffer = Arrays.toString(data) + "\n" + "Size: " + data.length;
                        } else {
                            log.add("2");
                            buffer = "Arquivo Inexistente";
                        }

                        break;

                    default:
                        buffer = "Comando Invalido";
                        break;
                }
                
                for(String s : log){
                    writer.write(s+"\n");
                }
                log.clear();
                out.writeUTF(buffer);
            }
            writer.close();
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
