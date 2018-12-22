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
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPServer extends Thread {

    DatagramSocket dgramSocket;
    FileWriter writer;

    public UDPServer(int port) {
        try {
            this.writer = new FileWriter("src/Files/Arquivo_SaidaUDP.txt", true);
            this.dgramSocket = new DatagramSocket(port); // cria um socket datagrama em uma porta especifica
        } catch (SocketException ex) {
            System.out.println("Sevidor já está criado");
            //Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void run() {
        String sair="";
        
        try {
            while (!sair.equals("SAIR")) {
                byte[] buffer = new byte[1024]; // cria um buffer para receber requisições
                DatagramPacket dgramPacket = new DatagramPacket(buffer, buffer.length);

                this.dgramSocket.receive(dgramPacket); // aguarda a chegada de datagramas

                String msg = (new String(dgramPacket.getData(), 0, dgramPacket.getLength()));
                System.out.println(msg);
                writer.write(msg);
                sair = msg;
                //DatagramPacket reply;
                //reply = new DatagramPacket(m, m.length, dgramPacket.getAddress(), dgramPacket.getPort());
                //this.dgramSocket.send(reply);
            }
        } catch (IOException ex) {
            Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.dgramSocket.close();
            try {
                this.writer.close();
            } catch (IOException ex) {
                Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
