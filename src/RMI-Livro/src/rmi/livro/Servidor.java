/*
    Universidade Tecnológica Federal do Paraná
    Programação Concorrente
    
    Mateus Yonemoto         1602055
    Renan Kodama Rodrigues  1602098
 */
package rmi.livro;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;

public class Servidor {

    public static void main(String args[]) {

        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            System.out.println("RMI Registry Pronto!");
        } catch (Exception e) {
            System.out.println("RMI Registry já está rodando!");
        }

        try {

            /* inicializa um objeto remoto */
            Cadastro livro = new Cadastro();

            /* registra o objeto remoto no Binder */
//            Registry registry = LocateRegistry.getRegistry("localhost");
            Naming.rebind("ServicoBiblioteca", livro);

            /* aguardando invocacoes remotas */
            System.out.println("Servidor pronto ...");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
