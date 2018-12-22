/*
    Universidade Tecnológica Federal do Paraná
    Programação Concorrente
    
    Renan Kodama Rodrigues 1602098
    
    Sistemas Distribuidos: Jogo da Velha com TCP
*/

package Velha;

public class TabuleiroCliente {

    String[][] tabuleiro;
    boolean isEndGame;

    public TabuleiroCliente() {
        this.tabuleiro = new String[3][3];
        this.isEndGame = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.tabuleiro[i][j] = "~";
            }
        }
    }

    public boolean isIsEndGame() {
        return isEndGame;
    }

    public void setIsEndGame(boolean isEndGame) {
        this.isEndGame = isEndGame;
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

    public boolean setValue(int i, int j, String symbol) {
        if (posicaoNOcupada(i, j)) {
            this.tabuleiro[i][j] = symbol;
            return true;
        } else {
            System.out.println("Posição Ocupada!");
            return false;
        }
    }

    public boolean posicaoNOcupada(int i, int j) {
        return this.tabuleiro[i][j].equals("~");
    }

}
