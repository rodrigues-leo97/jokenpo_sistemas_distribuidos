package chat.client;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        //VARIÁVEIS
        PrintStream output = null;  //system.OUT.print -> ou seja, para exibir algo na tela

        //PARA CAPTURAR DADOS, APÓS ISSO IDENTIFICAR SE É DO TECLADO OU NÃO
        Scanner input = null;
        Scanner teclado = null;

        final String IP = "127.0.0.1"; //ip do host pessoal
        final int PORT = 12345; //porta do SERVER que quer se conectar
        Socket socket; //usado do lado do cliente para se comunicar com o servidor

        //CRIAÇÃO DO SOCKET E PEDIDO DE CONEXÃO
            //PRECISA DE DUAS INFORMAÇÕES:
                //1->PORTA
                //2->IP
        try {
            socket = new Socket(IP, PORT); //passando IP e PORTA para o socket fazer a conexão com o SERVER
        }catch (Exception e) {
            System.out.println("Não foi possível se conectar ao servidor desejado :(");
            System.out.println("possível causa do erro: " + e.getMessage());
            return; //Não achou o servidor ai a aplicação para por aqui
        }

        //FASE DE COMUNICAÇÃO
            try {
                //getOutput-> para escrever no canal de comunicação / getInputStream -> para entrada de dados
                output = new PrintStream(socket.getOutputStream()); //para escrever no canal de comunicação do SOCKET(SERVER)
                input = new Scanner(socket.getInputStream()); // retorna exatamente o que o cliente está enviando “do outro lado”
                teclado = new Scanner(System.in);

                //deixando as mensagens de forma dinâmica
                String msg;
                        do {
                            System.out.println("Digite a mensagem: ");
                            msg = teclado.nextLine(); //ao invés de receber mensagem mandada pelo input eu crio um Scanner para ler oq foi digitado
                            output.println(msg); //system.out.println -> para exibir na tela do servidor
                        }while (!msg.equalsIgnoreCase("exit")); //para caso a mensagem seja === exit(desconsiderando maiusculas ou minusculas) ele finaliza a aplicação

            }catch (Exception e) {
                System.out.println(e.getMessage());
            }

            //FASE DE ENCERRAMENTO DA CONEXÃO

    }
}
