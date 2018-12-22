/*
    Universidade Tecnológica Federal do Paraná
    Programação Concorrente
    
    Renan Kodama Rodrigues 1602098
    
    Sistemas Distribuidos: Jogo da Velha com TCP
*/

package Velha;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class InfoClient {

    String name;
    String sybol;
    DataInputStream in;
    DataOutputStream out;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSybol() {
        return sybol;
    }

    public void setSybol(String sybol) {
        this.sybol = sybol;
    }

    public DataInputStream getIn() {
        return in;
    }

    public void setIn(DataInputStream in) {
        this.in = in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public void setOut(DataOutputStream out) {
        this.out = out;
    }

}
