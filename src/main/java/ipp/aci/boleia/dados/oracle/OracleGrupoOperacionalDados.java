package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IGrupoOperacionalDados;
import ipp.aci.boleia.dominio.GrupoOperacional;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgualIgnoreCase;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaGrupoOperacionalVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaParcialVo;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Respositorio de entidades
 * GrupoOperacional
 */
@Repository
public class OracleGrupoOperacionalDados extends OracleRepositorioBoleiaDados<GrupoOperacional> implements IGrupoOperacionalDados {

    private static final String UNIDADE = "unidade";
    private static final String CODIGO_CC = "codigoCC";

    /**
     * Instancia o repositorio
     */
    public OracleGrupoOperacionalDados() {
        super(GrupoOperacional.class);
    }

    /**
     * Monta os parametros de pesquisa a partir do filtro de busca recebido
     *
     * @param filtro O filtro de busca
     * @return Uma lista de parametros de pesquisa
     */
    private List<ParametroPesquisa> montarParametroPesquisa(FiltroPesquisaGrupoOperacionalVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        if (filtro.getNome() != null) {
            parametros.add(new ParametroPesquisaLike("nome", filtro.getNome()));
        }
        if (filtro.getFrota() != null) {
            parametros.add(new ParametroPesquisaIgual("frota.id", filtro.getFrota().getId()));
        }
        if (filtro.getUnidade() != null && filtro.getUnidade().getId() != null) {
            if (filtro.getUnidade().getId() > 0) {
                parametros.add(new ParametroPesquisaIgual(UNIDADE, filtro.getUnidade().getId()));
            } else {
                parametros.add(new ParametroPesquisaNulo(UNIDADE));
            }
        }
        if (filtro.getCodigoCC() != null) {
            parametros.add(new ParametroPesquisaLike(CODIGO_CC, filtro.getCodigoCC()));
        }
        return parametros;
    }

    @Override
    public ResultadoPaginado<GrupoOperacional> pesquisaPaginada(FiltroPesquisaGrupoOperacionalVo filtro) {
        List<ParametroPesquisa> parametros = montarParametroPesquisa(filtro);
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public GrupoOperacional obterPorCodigo(String codigo) {
        return pesquisarUnico(new ParametroPesquisaIgualIgnoreCase(CODIGO_CC, codigo));
    }

    @Override
    public List<GrupoOperacional> pesquisarSemUnidade() {
        return pesquisar(new ParametroOrdenacaoColuna(CODIGO_CC), new ParametroPesquisaNulo(UNIDADE));
    }

    @Override
    public void desvincularUnidades(Long unidadeId) {
        String update = "update GrupoOperacional g set g.unidade = null where g.unidade.id = :unidadeId";
        Query query = getGerenciadorDeEntidade().createQuery(update);
        query.setParameter("unidadeId", unidadeId);
        query.executeUpdate();
    }

    @Override
    public List<GrupoOperacional> pesquisarPorNomeGrupoRazaoSocialFrota(FiltroPesquisaParcialVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        povoarParametrosParaAutocomplete(filtro, parametros);
        return pesquisar(new ParametroOrdenacaoColuna("nome"), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    /**
     * Povoa os parametros de pesquisa para a busca do componente de auto-complete
     *
     * @param filtro O filtro de pesquisa
     * @param parametros Os parametros da consulta
     */
    private void povoarParametrosParaAutocomplete(FiltroPesquisaParcialVo filtro, List<ParametroPesquisa> parametros) {
        parametros.add(new ParametroPesquisaLike("nome", filtro.getTermo()));
    }

}
