package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ITelemetriaVeiculoDados;
import ipp.aci.boleia.dominio.TelemetriaVeiculo;
import ipp.aci.boleia.dominio.enums.TipoRastreador;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.Ordenacao;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Implementação do repositório {@link ITelemetriaVeiculoDados}
 *
 */
@Repository
public class OracleTelemetriaVeiculoDados extends OracleRepositorioBoleiaDados<TelemetriaVeiculo> implements ITelemetriaVeiculoDados {

    private static final String PARAM_PLACA = "placa";
    private static final String PARAM_DATA_POSICAO = "dataPacote";
    private static final String PARAM_CODIGO_REQUISICAO = "codigoRequisicao";
    private static final String PARAM_TIPO_RASTREADOR = "tipoRastreador";

    /**
     * Instancia o repositorio
     *
     */
    public OracleTelemetriaVeiculoDados() {
        super(TelemetriaVeiculo.class);
    }

    @Override
    public Optional<TelemetriaVeiculo> obterMaisRecentePorPlaca(String placa) {
        InformacaoPaginacao paginacao = new InformacaoPaginacao(1, 1, PARAM_DATA_POSICAO, Ordenacao.DECRESCENTE);
        ResultadoPaginado<TelemetriaVeiculo> resultadoPaginado = pesquisar(paginacao, new ParametroPesquisaIgual(PARAM_PLACA, placa));
        if(!resultadoPaginado.getRegistros().isEmpty()) {
            return Optional.of(resultadoPaginado.getRegistros().get(0));
        }
        return Optional.empty();
    }

    @Override
    public Optional<TelemetriaVeiculo> obterMaisRecentePorTipoRastreadorERequisicao(TipoRastreador tipoRastreador) {
        InformacaoPaginacao paginacao = new InformacaoPaginacao(1, 1, PARAM_CODIGO_REQUISICAO, Ordenacao.DECRESCENTE);
        ResultadoPaginado<TelemetriaVeiculo> resultadoPaginado = pesquisar(paginacao, new ParametroPesquisaIgual(PARAM_TIPO_RASTREADOR, tipoRastreador.getValue()));
        if(!resultadoPaginado.getRegistros().isEmpty()) {
            return Optional.of(resultadoPaginado.getRegistros().get(0));
        }
        return Optional.empty();
    }

}
