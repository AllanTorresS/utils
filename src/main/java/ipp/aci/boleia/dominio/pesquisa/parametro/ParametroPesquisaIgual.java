package ipp.aci.boleia.dominio.pesquisa.parametro;


import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;

/**
 * Representa um parametro de pesquisa para clausula SQL EQUAL (=),
 * a ser usado pelos repositorios do sistema
 */
public class ParametroPesquisaIgual extends ParametroPesquisa {

    /**
     * Construtor
     *
     * @param nome  O nome do parametro
     * @param valor A valor esperado
     */
    public ParametroPesquisaIgual(String nome, Object valor) {
        super(nome, valor);
    }
}
