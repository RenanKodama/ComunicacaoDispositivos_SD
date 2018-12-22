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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {

    InfoClient player01;
    InfoClient player02;
    String[][] tabuleiro;
    Integer[][] padroesVitoria;
    boolean turn;

    public Game() {
        this.player01 = null;
        this.player02 = null;
        this.turn = true;
        this.tabuleiro = new String[3][3];
        initTab();
    }

    public void initTab() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.tabuleiro[i][j] = "~";
            }
        }
        this.padroesVitoria = new Integer[][]{
            {0, 1, 2},
            {0, 4, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {2, 4, 6},
            {3, 4, 5},
            {6, 7, 8}};
    }

    public void hit_TableServer(int i, int j, String sybol) {
        this.tabuleiro[i][j] = sybol;
    }

    public boolean posicaoNOcupada(int i, int j) {
        return this.tabuleiro[i][j].equals("~");
    }

    public boolean isEndGame(String symbol) {
        boolean countH = false;
        boolean countV = false;
        int countD = 0;
        int countDI = 0;

        for (int i=0;i<3;i++){
            if(this.tabuleiro[i][0].equals(symbol) && this.tabuleiro[i][1].equals(symbol) && this.tabuleiro[i][2].equals(symbol)){
                countH = true;
            }
        }
        
        for (int j=0;j<3;j++){
            if(this.tabuleiro[0][j].equals(symbol) && this.tabuleiro[1][j].equals(symbol) && this.tabuleiro[2][j].equals(symbol)){
                countV = true;
            }
        }
        
        for (int i = 0; i < 3; i++) {            
            for (int j = 0; j < 3; j++) {

                if (i == j && this.tabuleiro[i][j].equals(symbol)) {
                    countD++;
                }
                if ((i + j) == 2 && this.tabuleiro[i][j].equals(symbol)) {
                    countDI++;
                }
            }
        }
        
        System.out.println(countV);
        System.out.println(countH);
        System.out.println(countD);
        System.out.println(countDI);
        
        return (countH || countV || countD == 3 || countDI == 3);
    }

    //turn = true -> vez do player1
    //turn = false -> vez do player2
    //FIRE i j symbol
    public void comando_Fire(String comando) {
        int i = Integer.parseInt(comando.split(" ")[1]);
        int j = Integer.parseInt(comando.split(" ")[2]);
        String symbol_player = comando.split(" ")[3];
        
        if (posicaoNOcupada(i, j)) {
            if (this.turn == true) {    //hit player 2
                hit_TableServer(i, j, this.player01.getSybol());

                try {
                    this.player02.getOut().writeUTF("HITYOU " + i + " " + j + " " + symbol_player);

                    if (!isEndGame(this.player01.getSybol())) {
                        change_Turn();
                        this.player02.getOut().writeUTF("YOURTURN");
                    } else {
                        this.player02.getOut().writeUTF("ENDGAME " + this.player01.getName());
                        this.player01.getOut().writeUTF("ENDGAME VOCÊ_GANHOU");
                    }

                } catch (IOException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {                       //hit player 1
                hit_TableServer(i, j, this.player02.getSybol());
                try {
                    this.player01.getOut().writeUTF("HITYOU " + i + " " + j + " " + symbol_player);

                    if (!isEndGame(this.player02.getSybol())) {
                        change_Turn();
                        this.player01.getOut().writeUTF("YOURTURN");
                    } else {
                        this.player01.getOut().writeUTF("ENDGAME " + this.player02.getName());
                        this.player02.getOut().writeUTF("ENDGAME VOCÊ_GANHOU");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            if (this.turn == true) {
                try {
                    this.player01.getOut().writeUTF("ERRORSELECT");
                } catch (IOException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    this.player02.getOut().writeUTF("ERRORSELECT");
                } catch (IOException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void change_Turn() {
        this.turn = !this.turn;
        verTabuleiro();
    }

    public void comando_Join(String comando, DataInputStream in, DataOutputStream out) {
        String nick = comando.split(" ")[1];

        if (this.player01 == null) {
            this.player01 = new InfoClient();
            this.player01.setName(nick);
            this.player01.setIn(in);
            this.player01.setOut(out);
            this.player01.setSybol("X");
            try {
                this.player01.getOut().writeUTF("SYMB " + this.player01.getSybol());
            } catch (IOException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (this.player02 == null) {
                this.player02 = new InfoClient();
                this.player02.setName(nick);
                this.player02.setIn(in);
                this.player02.setOut(out);
                this.player02.setSybol("O");
                try {
                    this.player02.getOut().writeUTF("SYMB " + this.player02.getSybol());
                    this.player01.getOut().writeUTF("PAREAD 1");
                    this.player02.getOut().writeUTF("PAREAD 1");

                    this.player01.getOut().writeUTF("YOURTURN");
                } catch (IOException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void verTabuleiro() {
        System.out.println("Begin Tabuleiro\n");

        for (int i = 0; i < 3; i++) {
            System.out.print("\t");
            for (int j = 0; j < 3; j++) {
                System.out.print(this.tabuleiro[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println("\nEnd Tabuleiro");
    }

}
