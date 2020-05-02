package ipp.aci.boleia.dominio.pesquisa.parametro;

import ipp.aci.boleia.dominio.pesquisa.comum.GrupoParametrosPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;

/**
 * Quando uma entidade possui uma relacao *-to-many, permite que seja gerada uma consulta
 * contendo multiplos "joins", um para cada parametro informado. Exemplo de utilizacao:
 * <br>
 * "Encontrar os funcionarios que possuam mais de um dependente, sendo pelo menos um menor de 12 e outro maior de 18 anos."
 * <br>
 * <pre>
 * SELECT f from Funcionario f
 * where exists (
 *   select 1 from
 *     dependente d1
 *     dependente d2
 *   where
 *     (d1.funcionario.id = f.id and d1.idade &lt; 12)
 *     and (d2.funcionario.id = f.id and d2.idade &gt; 18)
 * )
 * </pre>
 */
public class ParametroPesquisaMultiJoin extends GrupoParametrosPesquisa {

    /**
     * Construtor
     * @param parametros A lista de parametros a serem avaliados para as entidades relacionadas
     */
    public ParametroPesquisaMultiJoin(ParametroPesquisa... parametros) {
        super(parametros);
    }
}
