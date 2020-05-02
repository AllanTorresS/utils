package ipp.aci.boleia.dominio.pesquisa.parametro;

import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;

import java.util.Date;

/**
 * Representa um parametro de pesquisa para clausula SQL &gt;, a ser usado
 * para pesquisar por datas o banco de dados, encontrando registros
 * que possuam valor maior a data informada.
 */
public class ParametroPesquisaDataMaior extends ParametroPesquisa {

    /**
     * Construtor
     *
     * @param nome  O nome do parametro
     * @param valor A data de corte
     */
    public ParametroPesquisaDataMaior(String nome, Date valor) {
        super(nome, valor);
    }
}