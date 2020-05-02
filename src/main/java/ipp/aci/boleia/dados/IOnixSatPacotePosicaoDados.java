package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.vo.RespostaTelemetriaOnixSatVo;
import ipp.aci.boleia.dominio.vo.RespostaVeiculoOnixSatVo;

import java.util.List;

/**
 * Repositório com informações de pacote de posição para Telemetria
 *
 */
public interface IOnixSatPacotePosicaoDados {

    /**
     * Obtêm os dados dos veículos através da consulta ao serviço OnixSat.
     *
     * @return Uma lista com as respostas retornadas pelo serviço.
     */
    List<RespostaVeiculoOnixSatVo> obterDadosVeiculo();

    /**
     * Obtêm os dados de telemetria através da consulta ao serviço OnixSat.
     *
     * @param idUltimaMensagem O código da última mensagem enviada para o serviço
     * @return Uma lista com as respostas retornadas pelo serviço.
     */
    List<RespostaTelemetriaOnixSatVo> obterDadosTelemetria(Long idUltimaMensagem);

}
