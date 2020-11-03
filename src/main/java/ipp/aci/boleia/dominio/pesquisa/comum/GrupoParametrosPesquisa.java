package ipp.aci.boleia.dominio.pesquisa.comum;

import java.util.Arrays;

/**
 * Representa um parametro de pesquisa que agrupa outros parametros de pesquisa
 */
public abstract class GrupoParametrosPesquisa extends ParametroPesquisa {

    private ParametroPesquisa[] parametros;

    /**
     * Construtor
     * @param parametros A lista de parametros a serem agrupados
     */
    public GrupoParametrosPesquisa(ParametroPesquisa... parametros) {
        super(null, null);
        this.parametros = parametros;
    }

    public ParametroPesquisa[] getParametros() {
        return parametros;
    }

    public void setParametros(ParametroPesquisa[] parametros) {
        this.parametros = parametros;
    }

    /**
     * Adiciona um parametro a lista de parametros.
     *
     * @param parametro parametro a ser incluso na lista.
     */
    public void addParametro(ParametroPesquisa parametro) {
        if(parametro != null) {
            if(this.parametros == null) {
                this.parametros = new ParametroPesquisa[]{parametro};
            } else {
                ParametroPesquisa[] parametros = Arrays.copyOf(this.parametros, this.parametros.length + 1);
                parametros[parametros.length - 1] = parametro;
                this.parametros = parametros;
            }
        }
    }

}
