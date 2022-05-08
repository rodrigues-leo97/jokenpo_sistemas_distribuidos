package chat.server;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class AtendeCli extends Thread{
    //Tem a tarefa de conversar com o cliente
        //1-> saber o socket(ip)
            //2-> receber mensagens do cliente(Scanner)
                //3-> print para poder enviar mensagens para o cliente quando for o caso

    private Socket cliente; //serve para armazenar o retorno do accept() -> pedido de conexão
    private Scanner input = null;
    private PrintStream output = null;

    //CONSTRUCTOR para saber com quem a Thread ira conversar(cliente)
    public AtendeCli(Socket cliente) {
        this.cliente = cliente;
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
            do {
                msg = input.nextLine(); //uso scanner para ler um texto e guardo dentro dessa variavel
                System.out.println("Mensagem recebida: " + msg);
            }while (!msg.equalsIgnoreCase("exit"));

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
