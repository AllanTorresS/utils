package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPrecoFreteDados;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.TipoCombustivel;
import ipp.aci.boleia.dominio.agenciadorfrete.PrecoFrete;
import ipp.aci.boleia.dominio.enums.StatusPedidoCredito;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaFetch;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.util.Ordenacao;
import org.springframework.stereotype.Repository;

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
        return pesquisarSemIsolamentoDados(new ParametroOrdenacaoColuna("id", Ordenacao.DECRESCENTE),
                new ParametroPesquisaNulo("dataExclusao"),
                new ParametroPesquisaIgual("posto.id", pontoDeVenda.getId()),
                new ParametroPesquisaIgual("combustivel.id", combustivel.getId())
        ).stream().findFirst().orElse(null);
    }
}