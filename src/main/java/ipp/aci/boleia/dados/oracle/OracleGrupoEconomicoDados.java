package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IGrupoEconomicoDados;
import ipp.aci.boleia.dominio.GrupoEconomico;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaParcialVo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Respositorio de entidades GrupoEconomico
 */
@Repository
public class OracleGrupoEconomicoDados extends OracleRepositorioBoleiaDados<GrupoEconomico> implements IGrupoEconomicoDados {

    /**
     * Instancia o repositorio
     */
    public OracleGrupoEconomicoDados() {
        super(GrupoEconomico.class);
    }

    @Override
    public List<GrupoEconomico> pesquisarPorNome(FiltroPesquisaParcialVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        povoarParametrosParaBuscaParcial(filtro, parametros);
        return pesquisar(new ParametroOrdenacaoColuna("descricao"), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    /**
     * Povoa os parametros de pesquisa para uma busca parcial
     *
     * @param filtro     O filtro de pesquisa
     * @param parametros Os parametros da consulta
     */
    private void povoarParametrosParaBuscaParcial(FiltroPesquisaParcialVo filtro, List<ParametroPesquisa> parametros) {
        parametros.add(new ParametroPesquisaLike("descricao", filtro.getTermo()));
    }
}
