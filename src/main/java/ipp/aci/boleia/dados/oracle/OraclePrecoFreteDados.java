package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPrecoFreteDados;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.TipoCombustivel;
import ipp.aci.boleia.dominio.agenciadorfrete.PrecoFrete;
import ipp.aci.boleia.dominio.enums.StatusPrecoFrete;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPrecoFreteVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


/**
 * Respositorio de entidades Preco Frete
 */
@Repository
public class OraclePrecoFreteDados extends OracleRepositorioBoleiaDados<PrecoFrete> implements IPrecoFreteDados {

    /**
     * Instancia o repositorio
     */
    public OraclePrecoFreteDados() {
        super(PrecoFrete.class);
    }

    @Override
    public PrecoFrete obterPrecoFreteVigente(PontoDeVenda pontoDeVenda, TipoCombustivel combustivel) {
        if ((pontoDeVenda == null) || (combustivel == null)) return null;

        return pesquisarSemIsolamentoDados(new ParametroOrdenacaoColuna("id", Ordenacao.DECRESCENTE),
                new ParametroPesquisaNulo("dataExclusao"),
                new ParametroPesquisaIgual("status", StatusPrecoFrete.VIGENTE.getValue()),
                new ParametroPesquisaIgual("posto.id", pontoDeVenda.getId()),
                new ParametroPesquisaIgual("combustivel.id", combustivel.getId())
        ).stream().findFirst().orElse(null);
    }

    @Override
    public ResultadoPaginado<PrecoFrete> pesquisar(FiltroPesquisaPrecoFreteVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        filtro.getPaginacao().getParametrosOrdenacaoColuna().add( new ParametroOrdenacaoColuna("dataVigencia", Ordenacao.DECRESCENTE));
        filtro.getPaginacao().getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("posto.id"));

        if (filtro.getTipoCombustivel() != null && filtro.getTipoCombustivel().getId() != null) {
            parametros.add(new ParametroPesquisaIgual("combustivel.id", filtro.getTipoCombustivel().getId()));
        }

        if (filtro.getPontoVenda() != null && filtro.getPontoVenda().getId() != null) {
            parametros.add(new ParametroPesquisaIgual("posto.id", filtro.getPontoVenda().getId()));
        }

        if (filtro.getDataVigencia() != null) {
            parametros.add(new ParametroPesquisaDataMenorOuIgual("dataVigencia",
                    UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getDataVigencia())));
        }

        if (filtro.getStatus() != null && filtro.getStatus().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("status", StatusPrecoFrete.valueOf(filtro.getStatus().getName()).getValue()));
        }

        return pesquisar(
                filtro.getPaginacao(),
                parametros.toArray(new ParametroPesquisa[parametros.size()])
        );
    }
}