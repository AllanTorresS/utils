package ipp.aci.boleia.dados;

import java.util.List;

import ipp.aci.boleia.dominio.ReembolsoConectcar;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaReembolsoConectcarVo;

/**
 * Contrato para implementacao de repositorios de entidades Reembolso da conectcar
 */
public interface IReembolsoConectcarDados extends IRepositorioBoleiaDados<ReembolsoConectcar> {

    /**
     * Pesquisa Reembolsos a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return Uma lista de entidades localizadas
     */
    ResultadoPaginado<ReembolsoConectcar> pesquisar(FiltroPesquisaReembolsoConectcarVo filtro);
  
    /**
     * Obtém uma lista de reembolsos com status Suspenso para Pagamento.
     *
     * @return Lista de reembolsos elegíveis para liberação de pagamento.
     */
    List<ReembolsoConectcar> obterReembolsosSuspensosParaPagamento();

    /**
     * Retorna os reembolsos com erro de integração.
     *
     * @param numeroTentativas Número máximo de tentativas considerado na consulta
     * @return lista com os reembolsos
     */
    List<ReembolsoConectcar> obterReembolsosErroEnvio(Integer numeroTentativas);
}