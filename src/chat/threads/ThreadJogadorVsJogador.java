package chat.threads;

import chat.util.Comunicacao;
import chat.util.EscolhaJogador;

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
    private ArrayList<ThreadJogadorVsJogador> threadsJogadores;
    EscolhaJogador escolhaJogador;

    private ArrayList<Socket> todosConectados = new ArrayList<>();
    Comunicacao comunicacao;

    //CONSTRUCTOR para saber com quem a Thread ira conversar(cliente)
    public ThreadJogadorVsJogador(Socket cliente) {
        this.cliente = cliente;
        //this.threadsJogadores = threadsJogadores; //para ter o controle certo das Threads se conectando na lista
        this.comunicacao = new Comunicacao(cliente);
    }

    public void listaDeConectados(ArrayList<Socket> todosConectados) {
        this.todosConectados = todosConectados;
    }



    @Override
    public void run() {
        //FASE DE COMUNICAÇÃO

        this.escolhaJogador = (EscolhaJogador) comunicacao.receive();

        try {
            //preciso de 2 obj, um para poder ler e outro para poder escrever
            //system.in(system.out.println) -> para ler o teclado / getInputStream -> ler as mensagens de determinado canal de comunicação
            input = new Scanner(cliente.getInputStream()); //para ler as mensagens que virão dentro desse canal de comunicação

            output = new PrintStream(cliente.getOutputStream()); //para escrever no canal de comunicação do cliente

            String msg;
            String nome;
            do {
                msg = input.nextLine(); //uso scanner para ler um texto e guardo dentro dessa variavel
                nome = input.nextLine();
                System.out.println("Recebido: " + msg);
                System.out.println("Nome do jogador: " + nome);


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

    public boolean procuraJogador() {
        for(int i = 0; i < todosConectados.size(); i++) {
            if(this.cliente != todosConectados.get(i)) {
                if(this.escolhaJogador.getEscolha1() != this.escolhaJogador.getEscolha2()) return false;
            }
        }
        return true;
    }

}
