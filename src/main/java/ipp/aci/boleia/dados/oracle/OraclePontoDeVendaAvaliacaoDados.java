package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPontoDeVendaAvaliacaoDados;
import ipp.aci.boleia.dominio.PontoDeVendaAvaliacao;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.vo.FiltroPontoDeVendaAvaliacaoVo;
import org.springframework.stereotype.Repository;

/**
 * Repositório de entidades de Avaliação de Ponto de Venda
 */
@Repository
public class OraclePontoDeVendaAvaliacaoDados extends OracleRepositorioBoleiaDados<PontoDeVendaAvaliacao> implements IPontoDeVendaAvaliacaoDados {

    /**
     * Instancia o repositório
     */
    public OraclePontoDeVendaAvaliacaoDados() { super(PontoDeVendaAvaliacao.class); }

    @Override
    public PontoDeVendaAvaliacao pesquisarPorPontoDeVenda(FiltroPontoDeVendaAvaliacaoVo filtro) {

        return pesquisarUnico(new ParametroPesquisaIgual("pontoDeVenda.id", filtro.getPontoDeVenda().getId()));
    }
}
