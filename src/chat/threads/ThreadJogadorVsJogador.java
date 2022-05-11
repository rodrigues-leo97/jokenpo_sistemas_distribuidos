package chat.threads;

import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ThreadJogadorVsJogador extends Thread{

    //Tem a tarefa de conversar com o cliente
    //1-> saber o socket(ip)
    //2-> receber mensagens do cliente(Scanner)
    //3-> print para poder enviar mensagens para o cliente quando for o caso

    private Socket cliente; //serve para armazenar o retorno do accept() -> pedido de conexão
    private Scanner input = null;
    private PrintStream output = null;
    private ArrayList<ThreadJogadorVsJogador> threadsJogador;

    //CONSTRUCTOR para saber com quem a Thread ira conversar(cliente)
    public ThreadJogadorVsJogador(Socket cliente, ArrayList<ThreadJogadorVsJogador> threadsJogador) {
        this.cliente = cliente;
        this.threadsJogador = threadsJogador; //para ter o controle certo das Threads se conectando na lista
    }

    public ArrayList<ThreadJogadorVsJogador> getThreadsJogador() {
        return threadsJogador;
    }

    @Override
    public void run() {
        //FASE DE COMUNICAÇÃO
        try {
            //preciso de 2 obj, um para poder ler e outro para poder escrever
            //system.in(system.out.println) -> para ler o teclado / getInputStream -> ler as mensagens de determinado canal de comunicação
            input = new Scanner(cliente.getInputStream()); //para ler as mensagens que virão dentro desse canal de comunicação

            output = new PrintStream(cliente.getOutputStream()); //para escrever no canal de comunicação do cliente

            String msg;
            String nome;
            do {
                msg = input.nextLine(); //uso scanner para ler um texto e guardo dentro dessa variavel
                System.out.println("Recebido: " + msg);
                nome = input.nextLine();
                System.out.println(nome);

                //ArrayList<ThreadJogadorVsJogador> threadsJogadores;

                for(ThreadJogadorVsJogador threadJogadorVsJogador : threadsJogador) {
                    int cont = 0;
                    //
                    threadJogadorVsJogador.printResultado(msg);
                }


//                Random random = new Random();
//                int numero = (random.nextInt(2)) + 1; //+1 pq se inicia em zero
//                String CPU = String.valueOf(numero); //convertendo um inteiro para String
//
//                if (msg.equalsIgnoreCase("1") && CPU.equalsIgnoreCase("2") || msg.equalsIgnoreCase("2") && CPU.equalsIgnoreCase("3") || msg.equalsIgnoreCase("3") && CPU.equalsIgnoreCase("1")) {
//                    //CPU vence
//                    output.println("CPU venceu >8===D");
//                } else if (CPU.equalsIgnoreCase("1") && msg.equalsIgnoreCase("2") || CPU.equalsIgnoreCase("2") && msg.equalsIgnoreCase("3") || CPU.equalsIgnoreCase("3") && msg.equalsIgnoreCase("1")) {
//                    //Usuário VENCEU da CPU
//                    output.println("Você venceu");
//                } else {
//                    output.println("Empate");
//                }

            } while (!msg.equalsIgnoreCase("exit"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //FASE DE ENCERRAMENTO DA CONEXÃO
        try {
            input.close();
            cliente.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void printResultado(String msg) {
        output.println("> " + msg);
    }

}
