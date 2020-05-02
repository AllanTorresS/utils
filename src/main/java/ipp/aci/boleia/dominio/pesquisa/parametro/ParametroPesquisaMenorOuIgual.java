package ipp.aci.boleia.dominio.pesquisa.parametro;

import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;

import java.math.BigDecimal;

/**
 * Representa um parametro de pesquisa para clausula SQL LESS_THAN_OR_EQUAL (&lt;),
 * a ser usado pelos repositorios do sistema
 */
public class ParametroPesquisaMenorOuIgual extends ParametroPesquisa {

    /**
     * Construtor
     *
     * @param nome  O nome do parametro
     * @param valor A valor esperado
     */
    public ParametroPesquisaMenorOuIgual(String nome, BigDecimal valor) {
        super(nome, valor);
    }
}