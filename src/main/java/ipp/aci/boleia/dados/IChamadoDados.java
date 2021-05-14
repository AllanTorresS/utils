package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.vo.salesforce.ConsultaChamadosVo;
import ipp.aci.boleia.dominio.vo.salesforce.FiltroConsultaChamadosVo;

/**
 * Contrato para implementacao de repositorios
 * de entidades de chamado
 */
public interface IChamadoDados {

    /**
     * Realiza a consulta por uma lista de chamados no salesforce.
     *
     * @param filtro Filtro de pesquisa
     * @return Resultado da consulta
     */
    ConsultaChamadosVo consultarChamados(FiltroConsultaChamadosVo filtro);

    /**
     * Abre chamado no SalesForce
     *
     * @param company do chamado
     * @param name do chamado
     * @param email do chamado
     * @param phone do chamado
     * @param idReason do chamado
     * @param subject do chamado
     * @param description do chamado
     * @return true se o chamado for enviado para o SalesForce, false caso contrário
     */
    boolean abrirChamado(String company ,String name, String email, String phone, Long idReason, String subject, String description);
}