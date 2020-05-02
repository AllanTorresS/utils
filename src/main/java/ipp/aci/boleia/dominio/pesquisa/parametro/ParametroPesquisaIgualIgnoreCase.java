package ipp.aci.boleia.dominio.pesquisa.parametro;

import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;

/**
 * Representa um parametro de pesquisa para clausula SQL EQUAL, a ser usado
 * para confrontar valores textuais ignorando a caixa alta (uper case) ou baixa
 * (lower case).
 */
public class ParametroPesquisaIgualIgnoreCase extends ParametroPesquisa {

    /**
     * Construtor
     *
     * @param nome  O nome do parametro
     * @param valor O valor desejado
     */
    public ParametroPesquisaIgualIgnoreCase(String nome, String valor) {
        super(nome, valor);
    }
}