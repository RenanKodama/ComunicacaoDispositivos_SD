package Trabalho_Sockets_Chat;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente_Multicast extends Thread {

    InetAddress group;
    MulticastSocket s;

    public Cliente_Multicast() {
        try {
            this.group = InetAddress.getByName("225.1.2.3");
            this.s = new MulticastSocket(6789);
            this.s.joinGroup(this.group);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Cliente_Multicast.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Cliente_Multicast.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                byte[] buffer = new byte[1000];

                //for (int i = 0; i < 3; i++) {
                DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                this.s.receive(messageIn);
                System.out.println("(MultiCast)" + new String(messageIn.getData()));
                
                //}
            }
        } catch (IOException ex) {
            Logger.getLogger(Cliente_Multicast.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void enviar_msg(String msg) {
        try {
            DatagramPacket messageOut = new DatagramPacket(msg.getBytes(), msg.getBytes().length, this.group, 6789);
            this.s.send(messageOut);
        } catch (IOException ex) {
            Logger.getLogger(Cliente_Multicast.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void sair() {
        try {
            this.s.leaveGroup(group);
        } catch (IOException ex) {
            Logger.getLogger(Cliente_Multicast.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (this.s != null) {
                this.s.close();
            }
        }
    }
}
