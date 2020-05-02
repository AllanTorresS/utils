package ipp.aci.boleia.dominio.pesquisa.parametro;

import ipp.aci.boleia.dominio.pesquisa.comum.GrupoParametrosPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;

/**
 * Representa um parametro de pesquisa que consiste em um
 * conjunto de parametros a serem concatenados com clausulas AND. Exemplo:
 * <code>x.a = 1 AND x.b != 2 AND x.c &gt; 3</code>
 */
public class ParametroPesquisaAnd extends GrupoParametrosPesquisa {

    /**
     * Construtor
     * @param parametros A lista de parametros a serem concatenados por clausulas AND
     */
    public ParametroPesquisaAnd(ParametroPesquisa... parametros) {
        super(parametros);
    }
}
