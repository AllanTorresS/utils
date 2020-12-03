package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAbastecimentoViewDados;
import ipp.aci.boleia.dominio.AbastecimentoView;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.vo.EmpresaAbastecedoraVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaUltimosAbastecimentosVo;
import ipp.aci.boleia.dominio.vo.QuantidadeAbastecidaVeiculoVo;
import ipp.aci.boleia.util.Ordenacao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


/**
 * Respositorio de entidades Agenciador de Frete
 */
@Repository
public class OracleAbastecimentoViewDados extends OracleRepositorioBoleiaDados<AbastecimentoView> implements IAbastecimentoViewDados {

    private static final String QUERY_PESQUISAR_EMPRESA_ABASTECEDORA =
            "SELECT DISTINCT new ipp.aci.boleia.dominio.vo.EmpresaAbastecedoraVo(" +
                    "a.idEmpresa, a.tipoEmpresa,  a.cnpjEmpresa,  a.nomeEmpresa) " +
                    "FROM AbastecimentoView a " +
                    "WHERE a.nomeEmpresa LIKE '%%'||:termo||'%%' ";

    /**
     * Instancia o repositorio
     */
    public OracleAbastecimentoViewDados() {
        super(AbastecimentoView.class);
    }

    @Override
    public ResultadoPaginado<AbastecimentoView> pesquisaPaginada(FiltroPesquisaUltimosAbastecimentosVo filtro) {
        List<ParametroPesquisa> parametros = montarParametroPesquisa(filtro);

        if(filtro.getPaginacao() != null && filtro.getPaginacao().getParametrosOrdenacaoColuna().isEmpty()) {
            filtro.getPaginacao().getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("dataProcessamento", Ordenacao.DECRESCENTE));
        }
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<EmpresaAbastecedoraVo> obterEmpresasPorTermo(String termo) {
        return pesquisar(null, QUERY_PESQUISAR_EMPRESA_ABASTECEDORA, EmpresaAbastecedoraVo.class, new ParametroPesquisaIgual("termo", termo)).getRegistros();
    }

    /**
     * Monta os parametros da pesquisa do filtro dos ultimos abasteicmentos
     * @param filtro O filtro dos ultimos abastecimentos
     * @return Lista de parametros de pesquisa
     */
    private List<ParametroPesquisa> montarParametroPesquisa(FiltroPesquisaUltimosAbastecimentosVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataProcessamento", filtro.getDe()));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataProcessamento", filtro.getAte()));
        if(filtro.getPlaca() != null && !filtro.getPlaca().isEmpty()) {
            parametros.add(new ParametroPesquisaLike("placaVeiculo", filtro.getPlaca()));
        }
        if(filtro.getEmpresa() != null) {
            parametros.add(new ParametroPesquisaIgual("idEmpresa", filtro.getEmpresa()));
        }
        if(filtro.getTipo() != null) {
            parametros.add(new ParametroPesquisaIgual("tipoEmpresa", filtro.getTipo()));
        }
        if(filtro.getPontoDeVenda() != null){
            parametros.add(new ParametroPesquisaIgual("posto.id", filtro.getPontoDeVenda()));
        }
        return parametros;
    }

}