package ipp.aci.boleia.dominio.pesquisa.comum;

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

}
