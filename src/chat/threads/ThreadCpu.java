package chat.threads;

import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ThreadCpu extends Thread{
    //Tem a tarefa de conversar com o cliente
        //1-> saber o socket(ip)
            //2-> receber mensagens do cliente(Scanner)
                //3-> print para poder enviar mensagens para o cliente quando for o caso

    private Socket cliente; //serve para armazenar o retorno do accept() -> pedido de conexão
    private Scanner input = null;
    private PrintStream output = null;
    private ArrayList<ThreadCpu> threads;

    //CONSTRUCTOR para saber com quem a Thread ira conversar(cliente)
    public ThreadCpu(Socket cliente, ArrayList<ThreadCpu> threads) {
        this.cliente = cliente;
        this.threads = threads; //para ter o controle certo das Threads se conectando na lista
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
            while(true) {
                msg = input.nextLine(); //uso scanner para ler um texto e guardo dentro dessa variavel

                Random random = new Random();
                int numero = (random.nextInt(3)) + 1; //+1 pq se inicia em zero
                String CPU = String.valueOf(numero); //convertendo um inteiro para String

                if(msg.equalsIgnoreCase("1") && CPU.equalsIgnoreCase("2") || msg.equalsIgnoreCase("2") && CPU.equalsIgnoreCase("3") || msg.equalsIgnoreCase("3") && CPU.equalsIgnoreCase("1")) {
                    //CPU vence
                    output.println("CPU venceu >8===D");
                }else if(CPU.equalsIgnoreCase("1") && msg.equalsIgnoreCase("2") || CPU.equalsIgnoreCase("2") && msg.equalsIgnoreCase("3") || CPU.equalsIgnoreCase("3") && msg.equalsIgnoreCase("1")) {
                    //Usuário VENCEU da CPU
                    output.println("Você venceu");
                }else {
                    output.println("Empate");
                }

                //System.out.println("Mensagem recebida: " + msg); seria a escolha do Cliente para o Jokenpo
            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //FASE DE ENCERRAMENTO DA CONEXÃO
        try {
            input.close();
            cliente.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //quando o construtor criar a thread, ele ira passar como param um Socket
    }
}
