package chat.util;

import java.io.Serializable;

public class EscolhaJogador implements Serializable {

    private int escolha1;
    private int escolha2;

    public EscolhaJogador(int escolha1, int escolha2) {
        this.escolha1 = escolha1;
        this.escolha2 = escolha2;
    }

    public int getEscolha1() {
        return escolha1;
    }

    public int getEscolha2() {
        return escolha2;
    }

}
