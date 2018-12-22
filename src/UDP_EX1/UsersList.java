/*
*    Universidade Tecnológica Federal do Paraná
*    Sistemas Distribuídos
*
*    Renan Kodama Rodrigues 1602098
*/

package UDP_EX1;

import java.net.InetAddress;


public class UsersList {
    String nick;
    int port;
    InetAddress adress;

    public UsersList(String nick, int port, InetAddress adress) {
        this.nick = nick;
        this.port = port;
        this.adress = adress;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getAdress() {
        return adress;
    }

    public void setAdress(InetAddress adress) {
        this.adress = adress;
    }
    
}
