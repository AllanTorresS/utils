package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.salesforce.ChamadoVo;
import ipp.aci.boleia.dominio.vo.salesforce.ConsultaChamadosVo;
import ipp.aci.boleia.dominio.vo.salesforce.CriacaoChamadoVo;
import ipp.aci.boleia.dominio.vo.salesforce.FiltroConsultaChamadosVo;
import ipp.aci.boleia.dominio.vo.salesforce.ValorPicklistVo;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.excecao.ExcecaoServicoIndisponivel;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;

import java.util.List;

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
     * @throws ExcecaoBoleiaRuntime Caso ocorra algum erro
     */
    void criarChamado(CriacaoChamadoVo chamadoVo)  throws ExcecaoBoleiaRuntime;

    /**
     * Retorna uma lista com os status possíveis de um chamado.
     *
     * @return Lista com os status que um chamado pode ter.
     */
    List<ValorPicklistVo> listarStatusChamado();

    /**
     * Retorna uma lista com os tipos de chamado que podem ser abertos.
     *
     * @return Lista com os tipos de chamado.
     */
    List<ValorPicklistVo> listarTiposChamado();

    /**
     * Retorna a lista de sistemas de origem possíveis para um chamado.
     *
     * @return Lista com os sistemas de origem.
     */
    List<ValorPicklistVo> listarSistemasDeOrigem();

    /**
     * Retorna a lista de motivos com base no sistema de origem do chamado.
     *
     * @param sistemaDeOrigem Sistema de Origem do chamado.
     * @return Lista com os motivos.
     */
    List<ValorPicklistVo> listarMotivosPorSistemaDeOrigem(String sistemaDeOrigem);

    /**
     * Retorna a lista de módulos com base no sistema de origem do chamado.
     *
     * @param sistemaDeOrigem Sistema de Origem do chamado.
     * @return Lista com os módulos.
     */
    List<ValorPicklistVo> listarModulosPorSistemaDeOrigem(String sistemaDeOrigem);
}