/*
    Universidade Tecnológica Federal do Paraná
    Programação Concorrente
    
    Mateus Yonemoto         1602055
    Renan Kodama Rodrigues  1602098
*/

package rmi.livro;


import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class Cliente {

    public static void main(String args[]) {
        try {
            System.out.println("Cliente iniciado ...");

//            if (System.getSecurityManager() == null) {
//                System.setSecurityManager(new SecurityManager());
//            } 

            /* obtem a referencia para o objeto remoto */
            Registry registry = LocateRegistry.getRegistry("localhost");
            LivroRemote c = (LivroRemote) registry.lookup("ServicoBiblioteca");
            
            System.out.println("Adicionar :");
            System.out.println(c.include_book("Como ser um bom aluno", "M.Y Laplace", 1990, 1810));
            System.out.println(c.include_book("Vivendo e Aprendendo", "R.K Kodama", 1602, 500));
            
            System.out.println();
            System.out.println("Listar :");
            System.out.println(c.list_book());
            
            System.out.println();
            System.out.println("Remover :");
            System.out.println(c.remove_book("Vivendo e Aprendendo"));
            
            
            
            System.out.println();
            System.out.println("Remover Inexistente:");
            System.out.println(c.remove_book("Teste Remove"));
            
            System.out.println();
            System.out.println("Listar após a remoção :");
            System.out.println(c.list_book());
            
        } catch (Exception e) {
            System.out.println(e);
        }

    } 
} 
