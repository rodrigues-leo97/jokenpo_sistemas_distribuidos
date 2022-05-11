package chat.client;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        //getOutput-> para escrever no canal de comunicação / getInputStream -> para entrada de dados

        //VARIÁVEIS
        PrintStream output = null;  //system.OUT.println -> ou seja, para exibir algo na tela
        PrintStream outputJogo = null;

        //Scanner´s
        Scanner input = null; //getInputStream()
        Scanner teclado = null;
        Scanner tecladoEscolhaJogo = null; //Scanner para identificar qual tipo de jogo o User irá querer jogar
        Scanner sc = new Scanner(System.in);

        final String IP = "127.0.0.1"; //ip do host pessoal
        final int PORT = 12345; //porta do SERVER que quer se conectar
        Socket socket; //usado do lado do cliente para se comunicar com o servidor

        //CRIAÇÃO DO SOCKET E PEDIDO DE CONEXÃO
            //PRECISA DE DUAS INFORMAÇÕES:
                //1->PORTA
                //2->IP
        try {
            socket = new Socket(IP, PORT); //passando IP e PORTA para o socket fazer a conexão com o SERVER
            outputJogo = new PrintStream(socket.getOutputStream()); //para dizer que essa variável vai enviar mensagens para o SERVER

            System.out.println("Escolha 1 caso queira jogar contra a CPU \nEscolha 2 caso vc queria jogar contra um adversário");
            tecladoEscolhaJogo = new Scanner(System.in); //instanciando um novo Scanner para o usuário escolher

            String escolha = tecladoEscolhaJogo.nextLine(); //escolha do tipo de jogo
            while(!(escolha.equalsIgnoreCase("1") || escolha.equalsIgnoreCase("2"))){
                System.out.println("Essa opção está indisponível, digite qualquer coisa para continuar: ");
                escolha = tecladoEscolhaJogo.nextLine();
            }
            outputJogo.println(escolha); //para printar na tela do servidor quando capturar oq foi enviado a escolha do user


        }catch (Exception e) {
            System.out.println("Não foi possível se conectar ao servidor desejado :(");
            System.out.println("possível causa do erro: " + e.getMessage());
            return; //Não achou o servidor ai a aplicação para por aqui
        }

        //FASE DE COMUNICAÇÃO
            try {
                //getOutput-> para escrever no canal de comunicação(enviando para o SERVER) / getInputStream -> para entrada de dados
                output = new PrintStream(socket.getOutputStream()); //para escrever no canal de comunicação do SOCKET(SERVER)
                input = new Scanner(socket.getInputStream()); // retorna exatamente o que o cliente está enviando “do outro lado”
                teclado = new Scanner(System.in);

                System.out.println("Digite seu nome: ");
                String nome = sc.next();

                //deixando as mensagens de forma dinâmica
                String msg;
                    System.out.println("Escolha Pedra(1), Papel(2) ou Tesoura(3): ");
                    msg = teclado.nextLine(); //ao invés de receber mensagem mandada pelo input eu crio um Scanner para ler oq foi digitado
                    output.println(msg); //system.out.println -> para exibir na tela do servidor
                    output.println(nome);
                        //while (!msg.equalsIgnoreCase("exit")); //para caso a mensagem seja === exit(desconsiderando maiusculas ou minusculas) ele finaliza a aplicação
                    System.out.println(input.nextLine()); //mensagem de VENCER, PERDER ou EMPATAR

            }catch (Exception e) {
                System.out.println(e.getMessage());
            }

            //FASE DE ENCERRAMENTO DA CONEXÃO
            try {
                output.close();
                socket.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

    }

}
