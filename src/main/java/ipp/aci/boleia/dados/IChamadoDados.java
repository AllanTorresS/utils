package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.salesforce.ChamadoVo;
import ipp.aci.boleia.dominio.vo.salesforce.ConsultaChamadosVo;
import ipp.aci.boleia.dominio.vo.salesforce.CriacaoChamadoVo;
import ipp.aci.boleia.dominio.vo.salesforce.FiltroConsultaChamadosVo;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.excecao.ExcecaoServicoIndisponivel;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;

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
    ResultadoPaginado<ChamadoVo> consultarChamados(FiltroConsultaChamadosVo filtro);

    /**
     * Abre chamado no SalesForce por email
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
    boolean abrirChamadoEmail(String company ,String name, String email, String phone, Long idReason, String subject, String description);

    /**
     * Abre chamado no SalesForce
     *
     * @param chamadoVo os dados do chamado
     */
    void criarChamado(CriacaoChamadoVo chamadoVo)  throws ExcecaoBoleiaRuntime;
}