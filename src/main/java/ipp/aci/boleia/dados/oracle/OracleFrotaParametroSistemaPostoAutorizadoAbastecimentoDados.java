package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IFrotaParametroSistemaPostoAutorizadoAbastecimentoDados;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.FrotaParametroSistemaPostoAutorizadoAbastecimento;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementação oracle para {@link IFrotaParametroSistemaPostoAutorizadoAbastecimentoDados}
 */
@Repository
public class OracleFrotaParametroSistemaPostoAutorizadoAbastecimentoDados extends OracleRepositorioBoleiaDados<FrotaParametroSistemaPostoAutorizadoAbastecimento> implements IFrotaParametroSistemaPostoAutorizadoAbastecimentoDados {

    /**
     * Instancia o repositorio
     */
    public OracleFrotaParametroSistemaPostoAutorizadoAbastecimentoDados() {
        super(FrotaParametroSistemaPostoAutorizadoAbastecimento.class);
    }

    @Override
    public List<FrotaParametroSistemaPostoAutorizadoAbastecimento> obterAutorizadosPorParametroUso(FrotaParametroSistema parametroUso) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("frotaParametroSistema.id", parametroUso.getId()));
        parametros.add(new ParametroPesquisaIgual("autorizado", true));
        return pesquisar((ParametroOrdenacaoColuna) null, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }
}
