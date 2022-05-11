package chat.util;

import java.net.Socket;
import java.util.ArrayList;

public class ListaDeConexao {

    public ArrayList<Socket> listaJogadoresConectados = new ArrayList<>();

    public void adicionarLista(Socket socket) {
        this.listaJogadoresConectados.add(socket);
    }

    public ArrayList<Socket> pegarLista() {
        return listaJogadoresConectados;
    }

}
