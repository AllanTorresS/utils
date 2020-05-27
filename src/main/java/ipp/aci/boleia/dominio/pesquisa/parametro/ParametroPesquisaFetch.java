package ipp.aci.boleia.dominio.pesquisa.parametro;

import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;

/**
 * Evita a execução de novas consultas para campos lazy da entidade
 */
public class ParametroPesquisaFetch extends ParametroPesquisa{

    /**
     * Construtor
     *
     * @param nome  O relacionamento lazy
     */
    public ParametroPesquisaFetch(String nome) {
        super(nome, null);
    }
}
