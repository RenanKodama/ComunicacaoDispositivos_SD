package RMI_Livros;

/*
    Universidade Tecnológica Federal do Paraná
    Programação Concorrente
    
    Mateus Yonemoto         1602055
    Renan Kodama Rodrigues  1602098
*/

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class Cliente {

    public static void main(String args[]) {
        try {
            System.out.println("Cliente iniciado ...");

            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            } 

            /* obtem a referencia para o objeto remoto */
            Registry registry = LocateRegistry.getRegistry("localhost");
            Livr c = (Livr) registry.lookup("ServicoCalculadora");

            System.out.println(c.include_book("Como ser um bom aluno", "M.Y Laplace", 1990, 1810));
            System.out.println(c.include_book("Vivendo e Aprendendo", "R.K Kodama", 1602, 500));
            
            System.out.println(c.list_book());
            
            System.out.println(c.remove_book("Vivendo e Aprendendo"));
            System.out.println(c.remove_book("livro nao inserido"));
            
            System.out.println(c.list_book());
            
        } catch (Exception e) {
            System.out.println(e);
        }

    } 
} 
