package Trabalho_Sockets_Chat;

import java.util.ArrayList;


public class ServidorChat {
    ArrayList<Informacoes> onLine;
    Servidor_Multicast server_multi;
    Servidor_UDP server_UDP;

    public ServidorChat() {
        this.onLine = new ArrayList<>();
        this.server_UDP = new Servidor_UDP(5555);
        this.server_multi = new Servidor_Multicast(6789,this.onLine,server_UDP);    
    }
    
    
    public static void main(String[] args) {
        ServidorChat server = new ServidorChat();
        server.init();
    }
    
    
    public void init(){
        this.server_UDP.start();
        this.server_multi.start();
    }
}
