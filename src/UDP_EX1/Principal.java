/*
*    Universidade Tecnológica Federal do Paraná
*    Sistemas Distribuídos
*
*    Renan Kodama Rodrigues 1602098
*    
*    1) Fazer o chat P2P  usando UDP com a porta 5555. O chat deve possuir as 
*    seguintes funcionalidades: Aceitar ou bloquear usuários via nick (apelido), 
*    Enviar e receber mensagens via nick e via IP. O formato das mensagens deve 
*    seguir o seguinte padrão: NICKorigem, CMD, DADOS
*    Se CMD:
*    - ADDNICK: solicita o recebimento de mensagens com origem de NICKorigem. DADOS = 
*    "Mensagem qualquer de solicitação".
*    - MSG: mensagem enviada por NICKorigem. DADOS = "conteúdo da mensagem". 
*    Se ADDNICK não foi enviado ou está na lista de bloqueio, recuse a mensagem.
*/

package UDP_EX1;

public class Principal {

    public static void main(String[] args) {
        UDPServer servidor = new UDPServer(6667);
        UDPClient cliente = new UDPClient();
        
        
        servidor.start();
        cliente.start();
    }

}
