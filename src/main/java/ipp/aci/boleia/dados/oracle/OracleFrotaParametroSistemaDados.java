package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IFrotaParametroSistemaDados;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.enums.GrupoExecucaoParametroSistema;
import ipp.aci.boleia.dominio.enums.ParametroSistema;
import ipp.aci.boleia.dominio.enums.TipoRestritividade;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaFetch;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Respositorio de entidades FrotaParametroSistema
 */
@Repository
public class OracleFrotaParametroSistemaDados extends OracleRepositorioBoleiaDados<FrotaParametroSistema> implements IFrotaParametroSistemaDados {

    /**
     * Instancia o repositorio
     */
    public OracleFrotaParametroSistemaDados() {
        super(FrotaParametroSistema.class);
    }

    @Override
    public List<FrotaParametroSistema> buscarPorFrotaGrupoExecucaoOrdenadosPorRestritividade(Long idFrota, GrupoExecucaoParametroSistema grupo) {
        List<Integer> codsParams = Arrays.asList(grupo.getParametrosOrdenadosPorCriticidade()).stream().map(ParametroSistema::getCodigo).collect(Collectors.toList());
        List<FrotaParametroSistema> params = pesquisar((ParametroOrdenacaoColuna) null,
            new ParametroPesquisaIgual("frota.id", idFrota),
            new ParametroPesquisaIn("parametroSistema", codsParams));
        params.sort((p1,p2) -> compararParametros(p1,p2));
        return params;
    }

    @Override
    public List<FrotaParametroSistema> buscarPorFrota(Long idFrota) {
        List<FrotaParametroSistema> params = pesquisar((ParametroOrdenacaoColuna) null,
                new ParametroPesquisaFetch("frota"),
                new ParametroPesquisaIgual("frota.id", idFrota));
        params.sort((p1,p2) -> compararParametros(p1,p2));
        return params;
    }

    /**
     * Comparador para ordenacao de FrotaParametroSistema de acordo com a criticidade/prioridade da regra implementada
     * @param p1 objeto 1
     * @param p2 objeto 2
     * @return Comparacao inteira
     */
    private int compararParametros(FrotaParametroSistema p1, FrotaParametroSistema p2) {
        TipoRestritividade r1 = ParametroSistema.obterPorCodigo(p1.getParametroSistema()).getTipoRestritividade();
        TipoRestritividade r2 = ParametroSistema.obterPorCodigo(p2.getParametroSistema()).getTipoRestritividade();
        if(!r1.equals(r2)) {
            return TipoRestritividade.RESTRITIVA.equals(r1) ? -1 : 1;
        } else if(p1.isRestritivo() != p2.isRestritivo()){
            return p1.isRestritivo() ? -1 : 1;
        }
        return 0;
    }

    @Override
    public FrotaParametroSistema obterPorParametroSistema(Long idFrota, ParametroSistema parametroSistema) {
        return pesquisarUnico(new ParametroPesquisaIgual("frota.id", idFrota), new ParametroPesquisaIgual("parametroSistema", parametroSistema.getCodigo()));
    }
}
