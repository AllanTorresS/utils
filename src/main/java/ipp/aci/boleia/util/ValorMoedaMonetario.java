package ipp.aci.boleia.util;

import ipp.aci.boleia.util.i18n.Mensagens;

/**
 * Unidades em extenso valor monetario
 */
public enum ValorMoedaMonetario {

    REAL        ("valormonetario.enum.moeda.real"),
    REAIS       ("valormonetario.enum.moeda.reais"),
    CENTAVO     ("valormonetario.enum.moeda.centavo"),
    CENTAVOS    ("valormonetario.enum.moeda.centavos"),
    SIGLA_REAL  ("valormonetario.enum.moeda.siglareal");

    private final String nome;

    ValorMoedaMonetario(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    /**
     * Obtem uma mensagem internacionalizada a partir de sua chave
     * @param mensagens A mensagem a ser formatada
     * @return A mensagem formatada
     */
    public String getNomeEspecial(Mensagens mensagens) {
        return " " + mensagens.obterMensagem(nome);
    }

}
