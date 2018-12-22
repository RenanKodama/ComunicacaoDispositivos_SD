/*
*    Universidade Tecnológica Federal do Paraná
*    Sistemas Distribuídos
*
*    Renan Kodama Rodrigues 1602098
*       2-Fazer um sistema de upload de arquivos via UDP. Um servidor UDP deverá
*       receber as partes dos arquivos (1024 bytes), verificar ao final a 
*       integridade via um checksum (MD5) e armazenar o arquivo em uma pasta 
*       padrão.
 */

package UDP_EX2;

public class Principal {

    public static void main(String[] args) {
        UDPServer servidor = new UDPServer(5555);
        UDPClient cliente = new UDPClient();
        
        
        servidor.start();
        cliente.start();
        
        
    }

}
