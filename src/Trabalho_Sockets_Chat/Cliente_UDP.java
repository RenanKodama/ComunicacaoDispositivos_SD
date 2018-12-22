package Trabalho_Sockets_Chat;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente_UDP extends Thread {

    DatagramSocket aSocket;
    
    public Cliente_UDP(int port) {
        System.out.println("Cliente...");
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
                System.out.println("Cliente: " + new String(request.getData()));
            } catch (IOException ex) {
                Logger.getLogger(Cliente_UDP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public synchronized void enviar_msg(String msg, int porta) {
        try {
            DatagramPacket reply = new DatagramPacket(msg.getBytes(), msg.getBytes().length, InetAddress.getByName("127.0.0.1"), porta);
            aSocket.send(reply); 
        } catch (IOException ex) {
            Logger.getLogger(Cliente_UDP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }  
}
