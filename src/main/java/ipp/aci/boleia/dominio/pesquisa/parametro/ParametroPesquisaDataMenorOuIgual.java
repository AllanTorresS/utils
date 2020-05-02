package ipp.aci.boleia.dominio.pesquisa.parametro;

import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;

import java.util.Date;

/**
 * Representa um parametro de pesquisa para clausula SQL &lt;=, a ser usado
 * para pesquisar por datas o banco de dados, encontrando registros
 * que possuam valor menor ou igual a data informada.
 */
public class ParametroPesquisaDataMenorOuIgual extends ParametroPesquisa {

    /**
     * Construtor
     *
     * @param nome  O nome do parametro
     * @param valor A data de corte
     */
    public ParametroPesquisaDataMenorOuIgual(String nome, Date valor) {
        super(nome, valor);
    }
}