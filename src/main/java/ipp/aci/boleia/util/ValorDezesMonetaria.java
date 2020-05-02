package ipp.aci.boleia.util;

import ipp.aci.boleia.util.i18n.Mensagens;

/**
 * Unidades em extenso valor monetario
 */
public enum ValorDezesMonetaria {

    ZERO        (""),
    ONZE        ("valormonetario.enum.dezes.onze"),
    DOZE        ("valormonetario.enum.dezes.doze"),
    TREZE       ("valormonetario.enum.dezes.treze"),
    QUATORZE    ("valormonetario.enum.dezes.quatorze"),
    QUINZE      ("valormonetario.enum.dezes.quinze"),
    DEZESSEIS   ("valormonetario.enum.dezes.dezesseis"),
    DEZESETE    ("valormonetario.enum.dezes.dezesete"),
    DEZOITO     ("valormonetario.enum.dezes.dezoito"),
    DEZENOVE    ("valormonetario.enum.dezes.dezenove");

    private final String nome;

    ValorDezesMonetaria(String nome) {
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
        ValorDezesMonetaria[] values = ValorDezesMonetaria.values();
        String[] nomes = new String[values.length];
        for(int i = 0; i < values.length; i++) {
            if(values[i] != ValorDezesMonetaria.ZERO) {
                nomes[i] = " " +  mensagens.obterMensagem(values[i].getNome());
            } else  {
                nomes[i] = mensagens.obterMensagem(values[i].getNome());
            }
        }
        return nomes;
    }
}
