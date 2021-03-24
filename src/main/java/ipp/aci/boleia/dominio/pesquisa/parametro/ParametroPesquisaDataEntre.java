package ipp.aci.boleia.dominio.pesquisa.parametro;

import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;

import java.util.Date;

/**
 * Representa um parametro de pesquisa para clausula SQL BETWEEN, a ser usado
 * para pesquisar por datas o banco de dados, encontrando registros
 * que possuam valor entre as datas informadas.
 */
public class ParametroPesquisaDataEntre extends ParametroPesquisa {

    private Date valorSecundario;

    /**
     * Construtor
     *
     * @param nome  O nome do parametro
     * @param valor O início do intervalo
     * @param valorSecundario O fim do intervalo
     */
    public ParametroPesquisaDataEntre(String nome, Date valor, Date valorSecundario) {
        super(nome, valor);
        this.valorSecundario = valorSecundario;
    }

    public Date getValorSecundario() {
        return valorSecundario;
    }

    public void setValorSecundario(Date valorSecundario) {
        this.valorSecundario = valorSecundario;
    }
}