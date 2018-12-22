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

class Connection extends Thread {

    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    Game game;

    public Connection(Socket aClientSocket, Game game) {
        this.game = game;

        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String data = in.readUTF();

                System.out.println("Cliente disse: " + data);
                listComandos(data);

            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void listComandos(String data) {

        switch (data.split(" ")[0]) {
            //JOIN nick
            case ("JOIN"):
                this.game.comando_Join(data, this.in, this.out);
                break;

            //FIRE i j
            case ("SELECT"):
                this.game.comando_Fire(data);
                break;
        }
    }

    public void comando_Exit() {
        try {
            this.in.close();
            this.out.close();
            this.clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
