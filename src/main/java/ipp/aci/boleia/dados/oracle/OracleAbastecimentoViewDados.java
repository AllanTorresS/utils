package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAbastecimentoViewDados;
import ipp.aci.boleia.dominio.AbastecimentoView;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaUltimosAbastecimentosVo;
import ipp.aci.boleia.util.Ordenacao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


/**
 * Respositorio de entidades Agenciador de Frete
 */
@Repository
public class OracleAbastecimentoViewDados extends OracleRepositorioBoleiaDados<AbastecimentoView> implements IAbastecimentoViewDados {

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

    /**
     * Monta os parametros da pesquisa do filtro dos ultimos abasteicmentos
     * @param filtro O filtro dos ultimos abastecimentos
     * @return Lista de parametros de pesquisa
     */
    private List<ParametroPesquisa> montarParametroPesquisa(FiltroPesquisaUltimosAbastecimentosVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("dataProcessamento", filtro.getDe()));
        parametros.add(new ParametroPesquisaIgual("dataProcessamento", filtro.getAte()));
        if(filtro.getPlaca() != null && !filtro.getPlaca().isEmpty()) {
            parametros.add(new ParametroPesquisaLike("placaVeiculo", filtro.getPlaca()));
        }
        parametros.add(new ParametroPesquisaIgual("empresa.id", filtro.getEmpresa()));
        parametros.add(new ParametroPesquisaIgual("posto.id", filtro.getPontoDeVenda()));
        return parametros;
    }

}