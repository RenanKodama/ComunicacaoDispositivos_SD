/*
    Universidade Tecnológica Federal do Paraná
    Programação Concorrente
    
    Mateus Yonemoto         1602055
    Renan Kodama Rodrigues  1602098
 */

package rmi.livro;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Cadastro extends UnicastRemoteObject implements LivroRemote {

    ArrayList<Livro> biblioteca;

    public Cadastro() throws RemoteException {
        super();
        this.biblioteca = new ArrayList<>();
        System.out.println("Objeto remoto instanciado");
    }

    @Override
    public String include_book(String nome, String autor, int ano, int pages) throws RemoteException {
        Livro livro = new Livro();
        livro.setName(nome);
        livro.setAutor(autor);
        livro.setAno(ano);
        livro.setPages(pages);

        this.biblioteca.add(livro);

        String result = "Livro Adicionado: " + nome;
        System.out.println(result);
        return result;
    }

    @Override
    public String remove_book(String nome) throws RemoteException {
        boolean remove = false;

        for (int i = 0; i < this.biblioteca.size(); i++) {
            if (this.biblioteca.get(i).getName().equals(nome)) {
                this.biblioteca.remove(i);
                remove = true;
            }
        }

        String result;
        if (remove) {
            result = "Livro Removido: " + nome;
        } else {
            result = "Livro não encontrado: " + nome;
        }

        System.out.println(result);
        return result;
    }

    @Override
    public String list_book() throws RemoteException {
        String result = "";
        for (int i = 0; i < this.biblioteca.size(); i++) {

            result = "Nome: " + this.biblioteca.get(i).getName() + "\n"
                    + "Autor: " + this.biblioteca.get(i).getAutor() + "\n"
                    + "Ano: " + this.biblioteca.get(i).getAno() + "\n"
                    + "Pages: " + this.biblioteca.get(i).getPages() + "\n\n";
            System.out.println(result);
        }
        return result;
    }

}
