package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.TelemetriaVeiculo;
import ipp.aci.boleia.dominio.VeiculoRastreador;
import ipp.aci.boleia.dominio.vo.RespostaSascarVo;

import java.util.function.Function;

/**
 * Repositório com informações de pacote de posição para Telemetria
 *
 */
public interface ISascarPacotePosicaoDados {

    /**
     * Método que obtém placas dos veiculos na Sascar
     *
     * @param ponteiroParaRequisicao Indica a partir de qual pacote devemos começar a ler. Usar null para primeira requisicao.
     * @return Lista de veículos obtidos com informação se tem próximo para consumir
     */
    RespostaSascarVo<VeiculoRastreador> obterPlacasVeiculos(Integer ponteiroParaRequisicao);

    /**
     * Método que realiza integração com finalidade de obter
     * pacotes de telemetria dos veículos
     *
     * @param obterPlaca Método que recebe um idVeículo na Sascar e retorna uma placa
     *
     * @return Lista de Telemetria Veículo
     */
    RespostaSascarVo<TelemetriaVeiculo> obterPacotes(Function<Integer, String> obterPlaca);


}
