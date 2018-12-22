/*
*    Universidade Tecnológica Federal do Paraná
*    Sistemas Distribuídos
*
*    Renan Kodama Rodrigues 1602098
*       2-Fazer um sistema de upload de arquivos via UDP. Um servidor UDP deverá
*       receber as partes dos arquivos (1024 bytes), verificar ao final a 
*       integridade via um checksum (MD5) e armazenar o arquivo em uma pasta 
*       padrão.
 */
package UDP_EX2;

import java.net.*;
import java.io.*;


public class UDPClient extends Thread {

    DatagramSocket dgramSocket;
    int resp;

    public UDPClient() {
        this.resp = 0;
    }

    @Override
    public void run() {
        try {
            this.dgramSocket = new DatagramSocket(); //cria um socket datagrama

            String dstIP = "127.0.0.1"; //JOptionPane.showInputDialog("IP Destino?");
            int dstPort = 5555; //Integer.parseInt(JOptionPane.showInputDialog("Porta Destino?"));

            /* armazena o IP do destino */
            InetAddress serverAddr = InetAddress.getByName(dstIP);
            int serverPort = dstPort;

            File file = new File("src/Files/Conteudos Abordados.txt");
            InputStream is = new FileInputStream(file);
            int length = (int) file.length();
            int take = 1024;                      //size of chunk
            byte[] bytes = new byte[take];
            int offset = 0;
            int a;

            while (offset < length) {

                a = is.read(bytes, 0, take);


                /* cria um pacote datagrama */
                DatagramPacket request = new DatagramPacket(bytes, bytes.length, serverAddr, serverPort);

                /* envia o pacote */
                this.dgramSocket.send(request);

                /* cria um buffer vazio para receber datagramas */
                byte[] buffer = new byte[1000];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);

                /* aguarda datagramas */
                //this.dgramSocket.receive(reply);
                //System.out.println("Resposta: " + new String(reply.getData(), 0, reply.getLength()));
                offset += a;
            }
            /* libera o socket */

            DatagramPacket requestQuit = new DatagramPacket("SAIR".getBytes(), "SAIR".getBytes().length, serverAddr, serverPort);
            this.dgramSocket.send(requestQuit);
            this.dgramSocket.close();
            is.close();
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }

}
