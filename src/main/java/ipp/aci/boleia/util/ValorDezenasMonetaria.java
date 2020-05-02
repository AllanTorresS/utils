package ipp.aci.boleia.util;

import ipp.aci.boleia.util.i18n.Mensagens;

/**
 * Dezenas em extenso valor monetario
 */
public enum ValorDezenasMonetaria {

    ZERO        (""),
    DEZ         ("valormonetario.enum.dezenas.dez"),
    VINTE       ("valormonetario.enum.dezenas.vinte"),
    TRINTA      ("valormonetario.enum.dezenas.trinta"),
    QUARENTA    ("valormonetario.enum.dezenas.quarenta"),
    CINQUENTA   ("valormonetario.enum.dezenas.cinquenta"),
    SESSENTA    ("valormonetario.enum.dezenas.sessenta"),
    SETENTA     ("valormonetario.enum.dezenas.setenta"),
    OITENTA     ("valormonetario.enum.dezenas.oitenta"),
    NOVENTA     ("valormonetario.enum.dezenas.noventa");

    private final String nome;

    ValorDezenasMonetaria(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    /**
     * Obtem uma lista dos nomes dos itens enumerados
     *
     * @param mensagens Um objeto da classe Mensagens
     * @return lista de nomes
     */
    public static String[] obterArrayNomes(Mensagens mensagens) {
        ValorDezenasMonetaria[] values = ValorDezenasMonetaria.values();
        String[] nomes = new String[values.length];
        for(int i = 0; i < values.length; i++) {
            if(values[i] != ValorDezenasMonetaria.ZERO) {
                nomes[i] = " " + mensagens.obterMensagem(values[i].getNome());
            } else {
                nomes[i] = mensagens.obterMensagem(values[i].getNome());
            }
        }
        return nomes;
    }
}
