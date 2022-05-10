package chat.server;

import chat.threads.ThreadCpu;
import chat.threads.ThreadJogadorVsJogador;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    static ArrayList<ThreadCpu> threads = new ArrayList<>(); //Thread para jogador vs CPU
    static ArrayList<ThreadJogadorVsJogador> threadJogadores = new ArrayList<>(); //Thread para jogador vs JOGADOR

    public static void main(String[] args) {
        //VARIAVEIS
        final int PORT = 12345; //n° da porta do servidor

        ServerSocket serverSocket; //responsável por esperar a conexão do cliente e criar o socket
        Socket clientSocket = null; //mantem a comunicação entre o cliente e o servidor, null -> para não dar erro no try

        //CRIACAO DO SOCKET
        try {
            //SOCKET CRIADO
            serverSocket = new ServerSocket(PORT); //instanciei e passei a porta desejada para criação do meu server

        } catch(Exception e) {
            System.out.println("A porta " + PORT + " já está em uso: " + e.getMessage());
            return; //deu erro já para por aqui
        }


        //AGUARDANDO PEDIDO DE CONEXÃO DO SERVIDOR
        try {
            while(true) { //para garantir que o servidor não feche após o primeiro pedido de conexão, e passe a aceitar vários pedidos
                System.out.println("Sistema aguardando pedido de conexão para iniciar o jogo...");
                clientSocket = serverSocket.accept(); //faz com que fique aguardando um pedido de conexão

                //APÓS ACEITAR UM PEDIDO DE CONEXÃO
                System.out.println("O jogo se conectou com: " + clientSocket.getInetAddress().getHostAddress()); //pego o ip de quem se conectou
                Scanner input = new Scanner(clientSocket.getInputStream());
                String msg;
                msg = input.nextLine();
                //System.out.println("Mostrando na tela a opção escolhida: " + msg);

                //CRIAÇÃO DA THREAD - AtendeCli.java
                    //APÓS ISSO ENVIAR O CLIENT PARA ESSA THREAD E ELA IRÁ ATENDE-LO
                if(msg.equalsIgnoreCase("1")) {
                    System.out.println("teste1");
                    ThreadCpu threadCpu = new ThreadCpu(clientSocket, threads);

                    //adicionando a lista de Cliente vs CPU
                    threads.add(threadCpu);

                    //startando a Thread
                    threadCpu.start();//thread não entra em execução se não for iniciada, por isso o Start
                }else if(msg.equals("2")){
                    System.out.println("Entrou na opção 2");
                    ThreadJogadorVsJogador threadJogadorVsJogador = new ThreadJogadorVsJogador(clientSocket, threadJogadores);

                    //adicionando para a lista da Thread de Jogador vs Jogador
                    threadJogadores.add(threadJogadorVsJogador);

                    //startando a Thread
                    threadJogadorVsJogador.start();
                } else {
                    //tem uma validação no CLIENTE que não permite colocar opções diferentes de 1 ou 2, mas essa se trata de uma segunda validação por segurança, parando o servidor
                    System.out.println("Erro, a opção desejada é inválida");
                    return;
                }
            }
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
