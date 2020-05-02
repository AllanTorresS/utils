package ipp.aci.boleia.util;

import ipp.aci.boleia.util.i18n.Mensagens;

/**
 * Unidades em extenso valor monetario
 */
public enum ValorUnidadeMonetaria{

    ZERO    (""),
    UM      ("valormonetario.enum.unidade.um"),
    DOIS    ("valormonetario.enum.unidade.dois"),
    TRES    ("valormonetario.enum.unidade.tres"),
    QUATRO  ("valormonetario.enum.unidade.quatro"),
    CINCO   ("valormonetario.enum.unidade.cinco"),
    SEIS    ("valormonetario.enum.unidade.seis"),
    SETE    ("valormonetario.enum.unidade.sete"),
    OITO    ("valormonetario.enum.unidade.oito"),
    NOVE    ("valormonetario.enum.unidade.nove");
    private final String nome;

    ValorUnidadeMonetaria(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    /**
     * Obtem uma lista dos nomes dos itens enumerados
     * @param mensagens Um objeto da classe Mensagens
     * @return lista de nomes
     */
    public static String[] obterArrayNomes(Mensagens mensagens) {
        ValorUnidadeMonetaria[] values = ValorUnidadeMonetaria.values();
        String[] nomes = new String[values.length];
        for(int i = 0; i < values.length; i++) {
            if(values[i] != ValorUnidadeMonetaria.ZERO){
                nomes[i] = " " + mensagens.obterMensagem(values[i].getNome());
            } else {
                nomes[i] = mensagens.obterMensagem(values[i].getNome());
            }
        }
        return nomes;
    }

}
