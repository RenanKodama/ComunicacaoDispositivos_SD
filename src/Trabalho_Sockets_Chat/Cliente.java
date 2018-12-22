package Trabalho_Sockets_Chat;

import javax.swing.JOptionPane;

public class Cliente extends Thread {

    String nick;
    Cliente_UDP udp;
    Cliente_Multicast multiCast;
    
    public Cliente(int porta) {
        this.udp = new Cliente_UDP(porta);
        this.multiCast = new Cliente_Multicast();
        this.nick = JOptionPane.showInputDialog("Nick: ");
    }

    @Override
    public void run() {
        this.udp.start();
        this.multiCast.start();
        
        while(true){
            String comando = JOptionPane.showInputDialog("("+this.nick+")"+" Comando: ");
            
            switch(comando.split(" ")[0]){
                case "JOIN":
                    comando_JOIN(comando);
                    break;
            
                case "MSG":
                    comando_MSG(comando);
                    break;
                    
                case "MSGIDV":
                    comando_MSGIDV(comando);
                    break;
                    
                default:
                    System.out.println("Comando Inválido");
            }
        }
    }
    
    public void comando_JOIN(String comando){
        this.multiCast.enviar_msg(comando+" "+this.nick);        
    }
    
    public void comando_MSG(String comando){
        this.multiCast.enviar_msg(this.nick+" "+comando);
    }
    
    public void comando_MSGIDV(String comando){
        this.multiCast.enviar_msg(this.nick+" "+comando);
             
    }
    
    
    
    public static void main(String[] args) {
        Cliente cl = new Cliente(6060);
        cl.start();
    }
    

}
