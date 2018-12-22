/*
*    Universidade Tecnológica Federal do Paraná
*    Sistemas Distribuídos
*
*    Renan Kodama Rodrigues 1602098
 */
package UDP_EX1;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPServer extends Thread {

    DatagramSocket dgramSocket;
    ArrayList<UsersList> usersList;
    ArrayList<String> blockList;

    public UDPServer(int port) {
        this.usersList = new ArrayList<>();
        this.blockList = new ArrayList<>();
        try {
            this.dgramSocket = new DatagramSocket(port); // cria um socket datagrama em uma porta especifica
        } catch (SocketException ex) {
            System.out.println("Sevidor já está criado");
            //Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void run() {
        try {
            while (true) {
                byte[] buffer = new byte[1000]; // cria um buffer para receber requisições
                DatagramPacket dgramPacket = new DatagramPacket(buffer, buffer.length);

                this.dgramSocket.receive(dgramPacket); // aguarda a chegada de datagramas

                //nick,cmd,data
                String[] msg = (new String(dgramPacket.getData(), 0, dgramPacket.getLength())).split("-");
                DatagramPacket reply;

                String mensagem_toSend;
                byte[] m;

                switch (msg[1]) {
                    case "ACCEPT":
                        String nk = msg[2];
                        if ((!this.blockList.isEmpty()) && this.blockList.remove(msg[2])) {
                            System.out.println(nk + " Desblocked");
                        } else {
                            System.out.println("User não encontrado!");
                        }
                        break;

                    case "BLOCK":
                        for (UsersList usl : this.usersList) {
                            if (usl.getNick().equals(msg[2])) {
                                if (!this.blockList.contains(msg[2])) {
                                    this.blockList.add(msg[2]);
                                } else {
                                    System.out.println("User já bloqueado!");
                                }
                            } else {
                                System.out.println("User " + msg[2] + " não encontrado!");
                            }
                        }
                        break;

                    case "ADDNICK":
                        UsersList bl = new UsersList(msg[2], dgramPacket.getPort(), dgramPacket.getAddress());

                        if (!this.usersList.isEmpty()) {
                            if (!this.usersList.contains(bl)) {
                                this.usersList.add(bl);
                                mensagem_toSend = "Nick Cadastrado " +"<"+msg[2]+">";
                                m = mensagem_toSend.getBytes();
                                reply = new DatagramPacket(m, m.length, dgramPacket.getAddress(), dgramPacket.getPort());
                                this.dgramSocket.send(reply);
                            }
                        } else {
                            this.usersList.add(bl);
                            mensagem_toSend = "Nick Cadastrado " +"<"+msg[2]+">";
                            m = mensagem_toSend.getBytes();
                            reply = new DatagramPacket(m, m.length, dgramPacket.getAddress(), dgramPacket.getPort());
                            this.dgramSocket.send(reply);
                        }
                        break;

                    case "MSG":
                        mensagem_toSend = "@" + msg[0] + ": " + msg[2];
                        m = mensagem_toSend.getBytes();

                        if (!this.usersList.isEmpty()) {
                            for (UsersList allUser : this.usersList) {
                                if (!this.blockList.contains(allUser.getNick())){
                                    reply = new DatagramPacket(m, m.length, allUser.getAdress(), allUser.getPort());
                                    this.dgramSocket.send(reply);
                                } else {
                                    System.out.println("User " + allUser.getNick() + " blocked!");
                                }
                            }
                        }
                        break;

                    default:
                        mensagem_toSend = "Comando Inválido!";
                        m = mensagem_toSend.getBytes();

                        reply = new DatagramPacket(m, m.length, dgramPacket.getAddress(), dgramPacket.getPort());
                        this.dgramSocket.send(reply);
                        break;
                }

//                System.out.println("Cliente: " + new String(dgramPacket.getData(), 0, dgramPacket.getLength()));
            }
        } catch (IOException ex) {
            Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.dgramSocket.close();
        }
    }
}
