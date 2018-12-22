package RMI_Livros;

/*
    Universidade Tecnológica Federal do Paraná
    Programação Concorrente
    
    Mateus Yonemoto         1602055
    Renan Kodama Rodrigues  1602098
*/


import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class Servidor {
     public static void main(String args[]) {
       try {
             if (System.getSecurityManager() == null) {
                 System.setSecurityManager(new SecurityManager());
             }

             /* inicializa um objeto remoto */
             Cadastro livro = new Cadastro();

             /* registra o objeto remoto no Binder */
             Registry registry = LocateRegistry.getRegistry("localhost");
	         registry.bind("ServicoCalculadora", livro);

	         /* aguardando invocacoes remotas */
	         System.out.println("Servidor pronto ...");
	     } catch (Exception e) {
	         System.out.println(e);
         } 
     } 
} 
