package Trabalho_Sockets_Chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor_Multicast extends Thread {

    InetAddress group;
    MulticastSocket s;
    ArrayList<Informacoes> onLine;
    Servidor_UDP server_UDP;

    public Servidor_Multicast(int porta, ArrayList<Informacoes> onLine, Servidor_UDP server_UDP) {
        this.onLine = onLine;
        this.server_UDP = server_UDP;

        try {
            this.group = InetAddress.getByName("225.1.2.3");
            this.s = new MulticastSocket(porta);
            this.s.joinGroup(group);
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

                for (int i = 0; i <= this.onLine.size(); i++) {

                    DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                    this.s.receive(messageIn);
                    String mensagem = new String(messageIn.getData());

                    switch (mensagem.split(" ")[0]) {
                        
                        //[JOIN]+[nick]
                        case ("JOIN"):
                            Informacoes cl = new Informacoes();
                            cl.setNick(mensagem.split(" ")[1]);
                            cl.setPorta(messageIn.getPort());

                            this.onLine.add(cl);
                            this.server_UDP.enviar_msg("JOINACK " + cl.getNick(), messageIn.getPort());
                            break;

                        //[nick]+[MSG]+[texto]    
                        case ("MSG"):
                            String nick = mensagem.split(" ")[0];
                            mensagem = mensagem.replaceAll("MSG", "");
                            mensagem = mensagem.replaceAll(nick, "");

                            enviar_msg("<" + nick + ">:" + mensagem);
                            break;

                        //[nick] [MSGIDV] [TO] [apelido] [texto]
                        case ("MSGIDV"):

                            nick = mensagem.split(" ")[0];
                            String sendToNick = mensagem.split(" ")[4];
                            int portaToSend = -1;

                            for (Informacoes clientes : this.onLine) {
                                if (clientes.getNick().equals(sendToNick)) {
                                    portaToSend = clientes.getPorta();
                                }
                            }
                            
                            mensagem = mensagem.replaceAll(nick, "");
                            mensagem = mensagem.replaceAll("MSGIDV TO", "");
                            mensagem = mensagem.replaceAll(sendToNick, "");
                            
                            if(portaToSend != -1){
                                this.server_UDP.enviar_msg(mensagem, portaToSend);
                            } else{
                                this.server_UDP.enviar_msg("Nick Inválido", messageIn.getPort());
                            }
                            
                            break;
                    }

                }
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
