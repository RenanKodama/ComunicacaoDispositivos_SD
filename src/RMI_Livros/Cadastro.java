/*
    Universidade Tecnológica Federal do Paraná
    Programação Concorrente
    
    Mateus Yonemoto         1602055
    Renan Kodama Rodrigues  1602098
 */

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Cadastro extends UnicastRemoteObject implements Livr {

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
        String result = null;
        for (Livro l : this.biblioteca) {
            result += "Nome: " + l.getName()+"\n"
                    + "Autor: " + l.getAutor()+"\n"
                    + "Ano: " + l.getAno()+"\n"
                    + "Pages: " + l.getPages()+"\n\n";
        }
        System.out.println(result);
        return result;
    }

}
