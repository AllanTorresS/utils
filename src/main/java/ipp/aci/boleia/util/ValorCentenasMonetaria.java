package ipp.aci.boleia.util;

import ipp.aci.boleia.util.i18n.Mensagens;

/**
 * Unidades em extenso valor monetario
 */
public enum ValorCentenasMonetaria {

    ZERO            (""),
    CEM             ("valormonetario.enum.dezenas.cento"),
    DUZENTOS        ("valormonetario.enum.dezenas.duzentos"),
    TREZENTOS       ("valormonetario.enum.dezenas.trezentos"),
    QUATROCENTOS    ("valormonetario.enum.dezenas.quatrocentos"),
    QUINHENTOS      ("valormonetario.enum.dezenas.quinhentos"),
    SEISCENTOS      ("valormonetario.enum.dezenas.seiscentos"),
    SETECENTOS      ("valormonetario.enum.dezenas.setecentos"),
    OITOCENTOS      ("valormonetario.enum.dezenas.oitocentos"),
    NOVECENTOS      ("valormonetario.enum.dezenas.novecentos");

    private final String nome;

    ValorCentenasMonetaria(String nome) {
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
        ValorCentenasMonetaria[] values = ValorCentenasMonetaria.values();
        String[] nomes = new String[values.length];
        for(int i = 0; i < values.length; i++) {
            if(values[i] != ValorCentenasMonetaria.ZERO){
                nomes[i] = " " + mensagens.obterMensagem(values[i].getNome());
            } else {
                nomes[i] = mensagens.obterMensagem(values[i].getNome());
            }
        }
        return nomes;
    }
}
