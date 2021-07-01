package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.salesforce.ChamadoVo;
import ipp.aci.boleia.dominio.vo.salesforce.CriacaoChamadoVo;
import ipp.aci.boleia.dominio.vo.salesforce.EdicaoChamadoVo;
import ipp.aci.boleia.dominio.vo.salesforce.FiltroConsultaChamadosVo;
import ipp.aci.boleia.dominio.vo.salesforce.ValorPicklistVo;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;

import java.util.List;

/**
 * Contrato para implementacao de repositorios
 * de entidades de chamado
 */
public interface IChamadoDados {

    /**
     * Obtém os dados de um chamado a partir do seu identificador interno do salesforce.
     *
     * @param idSalesforce Identificador interno do chamado no salesforce.
     * @return Objeto com as informações do chamado.
     */
    ChamadoVo obterPorIdSalesforce(String idSalesforce);

    /**
     * Realiza a consulta por uma lista de chamados no salesforce.
     *
     * @param filtro Filtro de pesquisa
     * @return Resultado da consulta
     */
    ResultadoPaginado<ChamadoVo> consultarChamados(FiltroConsultaChamadosVo filtro);


    /**
     * Busca uma lista de chamados ativos com as mesmas características de um chamado que será aberto.
     *
     * OBS.: 1- Será considerado na busca o tipo, sistema de origem, motivo e módulo do chamado.
     *       2- Será considerado um chamado ativo aquele que possui status diferente de "Concluído" e "Cancelado".
     *
     * @param criacaoChamadoVo Objeto com as informações do chamado que será aberto.
     * @return Lista com os chamados duplicados encontrados.
     */
    List<ChamadoVo> buscarChamadosDuplicados(CriacaoChamadoVo criacaoChamadoVo);

    /**
     * Abre chamado no SalesForce
     *
     * @param chamadoVo os dados do chamado
     * @throws ExcecaoBoleiaRuntime Caso ocorra algum erro
     */
    void criarChamado(CriacaoChamadoVo chamadoVo)  throws ExcecaoBoleiaRuntime;

    /**
     * Altera um chamado no SalesForce
     *
     * @param vo os dados do chamado
     * @param idSalesforce o id do chamado a ser alterado
     * @throws ExcecaoBoleiaRuntime Caso ocorra algum erro
     */
    void editarChamado(EdicaoChamadoVo vo, String idSalesforce)  throws ExcecaoBoleiaRuntime;

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

    /**
     * Retorna uma lista com os motivos de cancelamento de chamado.
     *
     * @return Lista com os motivos de cancelamento.
     */
    List<ValorPicklistVo> listarMotivosCancelamento();

    /**
     * Realiza o cancelamento de um chamado no salesforce.
     *
     * @param idSalesforce Identificador interno do chamado no salesforce.
     * @param motivoCancelamento Motivo do cancelamento.
     * @param descricaoCancelamento Descrição sobre o cancelamento que está sendo feito.
     */
    void cancelarChamado(String idSalesforce, String motivoCancelamento, String descricaoCancelamento);
}