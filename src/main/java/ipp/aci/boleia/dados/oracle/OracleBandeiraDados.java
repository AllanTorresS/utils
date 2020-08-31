package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IBandeiraDados;
import ipp.aci.boleia.dominio.Bandeira;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaParcialVo;
import ipp.aci.boleia.util.Ordenacao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


/**
 * Respositorio de entidades Bandeira
 */
@Repository
public class OracleBandeiraDados extends OracleRepositorioBoleiaDados<Bandeira> implements IBandeiraDados {

    /**
     * Instancia o repositorio
     */
    public OracleBandeiraDados() {
        super(Bandeira.class);
    }

    @Override
    public List<Bandeira> obterParaCredenciamentoPosto(List<Integer> codigosCorp) {
        return pesquisar(new ParametroOrdenacaoColuna("propria", Ordenacao.DECRESCENTE),
                new ParametroPesquisaIn("codigoCorporativo", codigosCorp));
    }

    @Override
    public List<Bandeira> pesquisarPorDescricao(FiltroPesquisaParcialVo filtro) {
        List<ParametroPesquisa> params = new ArrayList<>();
        povoarParametrosParaAutocomplete(filtro, params);
        return pesquisar(new ParametroOrdenacaoColuna("descricao"), params.toArray(new ParametroPesquisa[params.size()]));
    }

    @Override
    public Bandeira obterPorCodigoCorporativo(Long codigoCorporativo) {
        List<ParametroPesquisa> params = new ArrayList<>();
        params.add(new ParametroPesquisaIgual("codigoCorporativo", codigoCorporativo));
        return pesquisarUnico(params.toArray(new ParametroPesquisa[params.size()]));
    }

    /**
     * Povoa os parametros de pesquisa para a busca do componente de auto-complete
     *
     * @param filtro     O filtro de pesquisa
     * @param parametros Os parametros da consulta
     */
    private void povoarParametrosParaAutocomplete(FiltroPesquisaParcialVo filtro, List<ParametroPesquisa> parametros) {
        ParametroPesquisa param = new ParametroPesquisaLike("descricao", filtro.getTermo());
        parametros.add(param);
    }
}
