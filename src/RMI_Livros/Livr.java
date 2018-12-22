/*
    Universidade Tecnológica Federal do Paraná
    Programação Concorrente
    
    Mateus Yonemoto         1602055
    Renan Kodama Rodrigues  1602098
*/

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Livr extends Remote {
    public String include_book (String nome, String autor, int ano, int pages) throws RemoteException;
    public String remove_book (String name) throws RemoteException;
    public String list_book () throws RemoteException;
} 
