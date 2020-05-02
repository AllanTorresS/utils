package ipp.aci.boleia.dominio.pesquisa.parametro;

import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;

/**
 * Representa um parametro de pesquisa para clausula SQL NOT EQUAL (&lt;&gt;),
 * a ser usado pelos repositorios do sistema
 */
public class ParametroPesquisaDiferente extends ParametroPesquisa {

    /**
     * Construtor
     *
     * @param nome  O nome do parametro
     * @param valor A valor esperado
     */
    public ParametroPesquisaDiferente(String nome, Object valor) {
        super(nome, valor);
    }
}
