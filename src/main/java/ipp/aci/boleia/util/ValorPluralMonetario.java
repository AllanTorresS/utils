package ipp.aci.boleia.util;

import ipp.aci.boleia.util.i18n.Mensagens;

/**
 * Unidades em extenso valor monetario
 */
public enum ValorPluralMonetario {

    BILHOES   ("valormonetario.enum.plural.bilhoes"),
    MILHOES   ("valormonetario.enum.plural.milhoes"),
    MIL       ("valormonetario.enum.plural.mil"),
    ZERO      ("");

    private final String nome;

    ValorPluralMonetario(String nome) {
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
        ValorPluralMonetario[] values = ValorPluralMonetario.values();
        String[] nomes = new String[values.length];
        for(int i = 0; i < values.length; i++) {
            if(values[i] != ValorPluralMonetario.ZERO) {
                nomes[i] = " " + mensagens.obterMensagem(values[i].getNome());
            } else  {
                nomes[i] = mensagens.obterMensagem(values[i].getNome());
            }
        }
        return nomes;
    }
}
