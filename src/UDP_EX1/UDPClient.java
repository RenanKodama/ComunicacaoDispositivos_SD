/*
*    Universidade Tecnológica Federal do Paraná
*    Sistemas Distribuídos
*
*    Renan Kodama Rodrigues 1602098
*/

package UDP_EX1;

import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;

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

            String nick = JOptionPane.showInputDialog("Nick: ");
            String dstIP = "127.0.0.1"; //JOptionPane.showInputDialog("IP Destino?");
            int dstPort = Integer.parseInt(JOptionPane.showInputDialog("Porta Destino?"));

            /* armazena o IP do destino */
            InetAddress serverAddr = InetAddress.getByName(dstIP);
            int serverPort = dstPort; // porta do servidor

            do {
                String cmd = JOptionPane.showInputDialog("CMD: ");
                String msg = JOptionPane.showInputDialog("Mensagem?");

                String msg_final = nick + "-" + cmd + "-" + msg;
                byte[] m = msg_final.getBytes(); // transforma a mensagem em bytes
                
                /* cria um pacote datagrama */
                DatagramPacket request
                        = new DatagramPacket(m, m.length, serverAddr, serverPort);

                /* envia o pacote */
                this.dgramSocket.send(request);

                /* cria um buffer vazio para receber datagramas */
                byte[] buffer = new byte[1000];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);

                /* aguarda datagramas */
                this.dgramSocket.receive(reply);
                System.out.println("Resposta: " + new String(reply.getData(), 0, reply.getLength()));

                resp = JOptionPane.showConfirmDialog(null, "Nova mensagem?","Continuar", JOptionPane.YES_NO_OPTION);

            } while (resp != JOptionPane.NO_OPTION);

            /* libera o socket */
            dgramSocket.close();
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } 
    }

} 
