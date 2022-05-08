package chat.server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        //VARIAVEIS
        final int PORT = 12345; //n° da porta do servidor

        ServerSocket serverSocket; //responsável por esperar a conexão do cliente
        Socket clientSocket = null; //mantem a comunicação entre o cliente e o servidor, null -> para não dar erro no try

        //CRIACAO DO SOCKET
        try {
            //SOCKET CRIADO
            serverSocket = new ServerSocket(PORT); //instanciei e passei a porta desejada para criação do meu server

        } catch(Exception e) {
            System.out.println("A porta " + PORT + " já está em uso: " + e.getMessage());
            return;
        }


        //AGUARDANDO PEDIDO DE CONEXÃO DO SERVIDOR
        try {
            int cont = 0;
            do { //para garantir que o servidor não feche após o primeiro pedido de conexão, e passe a aceitar vários pedidos
                System.out.println("Sistema aguardando pedido de conexão para iniciar o jogo...");
                clientSocket = serverSocket.accept(); //faz com que fique aguardando um pedido de conexão

                //APÓS ACEITAR UM PEDIDO DE CONEXÃO
                System.out.println("O jogo se conectou com: " + clientSocket.getInetAddress().getHostAddress()); //pego o ip de quem se conectou

                //CRIAÇÃO DA THREAD - AtendeCli.java
                    //APÓS ISSO ENVIAR O CLIENT PARA ESSA THREAD E ELA IRÁ ATENDE-LO
                AtendeCli atendeCli = new AtendeCli(clientSocket);
                atendeCli.start();//thread não entra em execução se não for iniciada, por isso o Start
                cont+=1;
            }while (cont < 2); //para possibilitar apenas que 2 jogadores no máximo joguem
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //OBS: a fase de comunicação está em outra classe, função foi delegada para outra

        //FASE DE ENCERRAMENTO DA CONEXÃO
        try {
            serverSocket.close(); //para finalizar a comunicação
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}