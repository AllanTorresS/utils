package ipp.aci.boleia.dominio.pesquisa.parametro;

import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;

import java.math.BigDecimal;

/**
 * Representa um parametro de pesquisa para clausula SQL BETWEEN,
 * a ser usado pelos repositorios do sistema
 */
public class ParametroPesquisaEntre extends ParametroPesquisa {

    private BigDecimal valorSecundario;

    /**
     * Construtor
     *
     * @param nome  O nome do parametro
     * @param valor O valor esperado
     * @param valorSecundario O segundo valor esperado
     */
    public ParametroPesquisaEntre(String nome, BigDecimal valor, BigDecimal valorSecundario) {
        super(nome, valor);
        this.valorSecundario = valorSecundario;
    }

    public BigDecimal getValorSecundario() {
        return valorSecundario;
    }

    public void setValorSecundario(BigDecimal valorSecundario) {
        this.valorSecundario = valorSecundario;
    }
}
