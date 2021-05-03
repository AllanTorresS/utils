package ipp.aci.boleia.dominio.pesquisa.parametro;

import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;

public class ParametroPesquisaMaiorData extends ParametroPesquisa {

    private String nome;

    /**
     * Construtor
     *
     * @param nome  O nome do parametro
     */
    public ParametroPesquisaMaiorData(String nome) {
        super(nome, null);
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }
}
