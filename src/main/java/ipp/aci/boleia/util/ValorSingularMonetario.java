package ipp.aci.boleia.util;

import ipp.aci.boleia.util.i18n.Mensagens;

/**
 * Unidades em extenso valor monetario
 */
public enum ValorSingularMonetario {


    BILHAO    ("valormonetario.enum.singular.bilhao"),
    MILHAO    ("valormonetario.enum.singular.milhao"),
    MIL       ("valormonetario.enum.singular.mil"),
    CEM       ("valormonetario.enum.singular.cem"),
    ZERO      ("");


    private final String nome;

    ValorSingularMonetario(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    /**
     * Obtem o valor singular monetario correspondente ao nome cem
     * @param mensagens Um objeto da classe Mensagens
     * @return O valor monetario formatado
     */
    public String getNomeCemEspecial(Mensagens mensagens) {
        return mensagens.obterMensagem(ValorSingularMonetario.CEM.getNome());
    }

    /**
     * Obtem uma lista dos nomes dos itens enumerados
     * @param mensagens Um objeto da classe Mensagens
     * @return lista de nomes
     */
    public static String[] obterArrayNomes(Mensagens mensagens) {
        ValorSingularMonetario[] values = ValorSingularMonetario.values();
        String[] nomes = new String[values.length];
        for(int i = 0; i < values.length; i++) {
            if(values[i] != ValorSingularMonetario.ZERO) {
                nomes[i] = " " + mensagens.obterMensagem(values[i].getNome());
            } else {
                nomes[i] = mensagens.obterMensagem(values[i].getNome());
            }
        }
        return nomes;
    }
}
