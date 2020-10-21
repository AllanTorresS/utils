package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.StatusExecucaoParametroSistema;
import ipp.aci.boleia.dominio.enums.TipoRastreador;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.UtilitarioCalculo;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Verifica se as informacoes de localizacao da autorizacao de pagamento estao consistentes
 */
@Component
public class LogicaParametroLocalizacaoAbastecimento implements ILogicaParametroSistema<AutorizacaoPagamento> {

    private static final int DISTANCIA_MAXIMA_PERMITIDA_RASTREADOR = 300;

    @Autowired
    private Mensagens mensagens;

    @Override
    public ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> executar(ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> contexto, FrotaParametroSistema frotaParam) {
        AutorizacaoPagamento autorizacao = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> resultado = new ResultadoExecucaoParametroSistemaVo<>(autorizacao);
        //Pedidos realizados offline, não serão considerados para a validação de localização.
        if(autorizacao.getPedido() != null) {
            executarValidacaoLocalizacao(autorizacao, resultado);
        }
        return resultado;
    }

    /**
     * Realiza as validações de localização para dispositivo e rastreador.
     *
     * @param autorizacao Autorização de pagamento.
     * @param resultado Resultado das validações.
     */
    private void executarValidacaoLocalizacao(AutorizacaoPagamento autorizacao, ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> resultado) {
        BigDecimal latitudeOrigem = autorizacao.getLatitudeOrigem();
        BigDecimal longitudeOrigem = autorizacao.getLongitudeOrigem();
        BigDecimal latitudePosto = autorizacao.getLatitudePosto();
        BigDecimal longitudePosto = autorizacao.getLongitudePosto();
        BigDecimal latitudeRastreador = autorizacao.getLatitudeRastreador();
        BigDecimal longitudeRastreador = autorizacao.getLongitudeRastreador();

        if(!autorizacao.possuiLocalizacaoOrigem()) {
            resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
            resultado.setMensagemErro(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.coordenadas.inconsistentes.posto", autorizacao.getPlacaVeiculo()));
        } else {
            BigDecimal distanciaPosto = UtilitarioCalculo.calcularDistanciaEntrePontos(latitudePosto, longitudePosto, latitudeOrigem, longitudeOrigem );
            if(distanciaPosto.doubleValue() > DISTANCIA_MAXIMA_PERMITIDA_RASTREADOR) {
                resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                resultado.setMensagemErro(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.coordenadas.inconsistentes.posto", autorizacao.getPlacaVeiculo()));
            } else {
                validarLocalizacaoRastreador(autorizacao, resultado, latitudeOrigem, longitudeOrigem, latitudeRastreador, longitudeRastreador);
            }
        }
    }

    /**
     * Valida as coordenadas do rastreador para a autorização de pagamento.
     *
     * @param autorizacao autorização de pagamento.
     * @param resultado Resultado da execução dos parâmetros do sistema.
     * @param latitudeOrigem latitude do dispostivo.
     * @param longitudeOrigem longitude do dispositivo.
     * @param latitudeRastreador latitude do rastreador.
     * @param longitudeRastreador longitude do rastreador.
     */
    private void validarLocalizacaoRastreador(AutorizacaoPagamento autorizacao, ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> resultado, BigDecimal latitudeOrigem, BigDecimal longitudeOrigem, BigDecimal latitudeRastreador, BigDecimal longitudeRastreador) {
        if(latitudeRastreador != null && longitudeRastreador != null) {
            Veiculo veiculo = autorizacao.getVeiculo();
            BigDecimal distanciaRastreador = UtilitarioCalculo.calcularDistanciaEntrePontos(latitudeRastreador, longitudeRastreador, latitudeOrigem, longitudeOrigem );
            boolean temRastreador = veiculo.getRastreador() != null && !veiculo.getRastreador().equals(TipoRastreador.NAO.getValue());
            if(temRastreador && distanciaRastreador.doubleValue() > DISTANCIA_MAXIMA_PERMITIDA_RASTREADOR) {
                resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                resultado.setMensagemErro(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.coordenadas.inconsistentes.rastreador", autorizacao.getPlacaVeiculo()));
            }
        }
    }

}
