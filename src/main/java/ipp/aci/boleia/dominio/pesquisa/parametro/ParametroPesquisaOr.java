package ipp.aci.boleia.dominio.pesquisa.parametro;

import ipp.aci.boleia.dominio.pesquisa.comum.GrupoParametrosPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;

/**
 * Representa um parametro de pesquisa que consiste em um
 * conjunto de parametros a serem concatenados com clausulas OR. Exemplo:
 * <code>x.a = 1 OR x.b != 2 OR x.c &gt; 3</code>
 */
public class ParametroPesquisaOr extends GrupoParametrosPesquisa {

    /**
     * Construtor
     * @param parametros A lista de parametros a serem concatenados por clausulas OR
     */
    public ParametroPesquisaOr(ParametroPesquisa... parametros) {
        super(parametros);
    }
}
