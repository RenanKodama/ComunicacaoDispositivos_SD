package Trabalho_Sockets_Chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor_UDP extends Thread {

    DatagramSocket aSocket;

    public Servidor_UDP(int port) {
        System.out.println("Servidor...");
        try {
            this.aSocket = new DatagramSocket(port);
        } catch (SocketException ex) {
            Logger.getLogger(Cliente_UDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            byte[] buffer = new byte[1000];

            DatagramPacket request = new DatagramPacket(buffer, buffer.length);

            try {
                aSocket.receive(request);
                String message = new String(request.getData());
                System.out.println("(UDP) "+message);
                
            } catch (IOException ex) {
                Logger.getLogger(Cliente_UDP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public synchronized void enviar_msg(String msg, int porta) {
        try {
            DatagramPacket reply = new DatagramPacket(msg.getBytes(), msg.getBytes().length, InetAddress.getByName("127.0.0.1"), porta);
            this.aSocket.send(reply);
        } catch (IOException ex) {
            Logger.getLogger(Cliente_UDP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
