/*
    Universidade Tecnológica Federal do Paraná
    Programação Concorrente
    
    Renan Kodama Rodrigues 1602098
    
    Sistemas Distribuidos: Jogo da Velha com TCP
*/

package Velha;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Cliente extends Thread {

    String nick;
    String symbol;
    Socket s;
    DataInputStream in;
    DataOutputStream out;
    TabuleiroCliente tabCli;

    public Cliente() {
        this.tabCli = new TabuleiroCliente();
        try {
            this.s = new Socket("127.0.0.1", 7896);
            this.in = new DataInputStream(s.getInputStream());
            this.out = new DataOutputStream(s.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        this.nick = JOptionPane.showInputDialog("Nick: ");
        comando_JOIN("JOIN " + this.nick);

        System.out.println("Aguardando Adversário...");
        receber_MSG();
        boolean respostaClienteOk = true;

        while (true) {
            if (respostaClienteOk) {
                wait_Turn();
            }

            String comando = JOptionPane.showInputDialog("<" + this.nick + ">" + "Comando: ");

            switch (comando.split(" ")[0]) {

                case ("SELECT"):                    
                    respostaClienteOk = true;
                    comando_SELECT(comando);
                    break;

                case ("EXIT"):
                    respostaClienteOk = true;
                    comando_EXIT(comando);
                    break;

                default:
                    respostaClienteOk = false;
                    System.out.println("Comando Inválido!");
                    break;
            }
            
            if(respostaClienteOk == true){
                respostaClienteOk = receber_MSG();
            }
            
        }
    }

    public void comando_JOIN(String comando) {
        try {
            this.out.writeUTF(comando);
            receber_MSG();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void comando_SELECT(String comando) {
        if(!this.tabCli.isIsEndGame()) {
            hitMyTable(comando + " " + this.symbol);
            this.tabCli.verTabuleiro();
            enviar_MSG(comando + " " + this.symbol);
        }else{
            System.out.println("O jogo acabou!");
        }
    }

    public void comando_EXIT(String comadno) {
        try {
            this.out.writeUTF("EXIT");
            this.in.close();
            this.out.close();
            this.s.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void wait_Turn() {
        String mensagem = " ";

        while (!mensagem.equals("YOURTURN")) {
            try {
                this.tabCli.verTabuleiro();
                mensagem = this.in.readUTF();

                if (mensagem.split(" ")[0].equals("HITYOU")) {
                    hitMyTable(mensagem);
                }
                
                if(mensagem.split(" ")[0].equals("ENDGAME")){
                    this.tabCli.setIsEndGame(true);
                    System.out.println("FIM DE JOGO! "+mensagem);
                }

            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void hitMyTable(String comando) {
        int i = Integer.parseInt(comando.split(" ")[1]);
        int j = Integer.parseInt(comando.split(" ")[2]);
        String symbol_player = comando.split(" ")[3];

        this.tabCli.setValue(i, j, symbol_player);
        this.tabCli.verTabuleiro();
    }

    public void enviar_MSG(String mensagem) {
        try {
            this.out.writeUTF(mensagem);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean receber_MSG() {
        try {
            String mensagem = this.in.readUTF();
            switch (mensagem.split(" ")[0]) {
                case ("SYMB"):
                    this.symbol = mensagem.split(" ")[1];
                    System.out.println("Your Symbol: " + this.symbol);
                    break;

                case ("PAREAD"):
                    System.out.println("Adversário Conectado!");
                    break;

                case ("HITYOU"):
                    hitMyTable(mensagem);
                    break;

                case ("ERRORSELECT"):
                    System.out.println("Posição Inválida");
                    return false;

                case ("ENDGAME"):
                    System.out.println("FIM DE JOGO! " + mensagem);
                    this.tabCli.setIsEndGame(true);

                    break;

                default:
                    System.out.println("Error 0x01");
                    break;
            }

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public static void main(String[] args) {
        Cliente cl = new Cliente();
        cl.start();
    }
}
