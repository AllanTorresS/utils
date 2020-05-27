package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.FrotaParametroSistemaPostoAutorizadoAbastecimento;
import ipp.aci.boleia.dominio.HistoricoParametroPostosPermitidos;
import ipp.aci.boleia.dominio.HistoricoParametroUso;
import ipp.aci.boleia.dominio.PontoDeVenda;

import java.util.Date;

/**
 * Repositório de dados da classe {@link HistoricoParametroPostosPermitidos}.
 *
 * @author pedro.silva
 */
public interface IHistoricoParametroPostosPermitidosDados extends IRepositorioBoleiaDados<HistoricoParametroPostosPermitidos> {

    /**
     * Retornar o último histórico de autorização de um posto.
     *
     * @param parametroPostoPermitido Parametro utilizado na consulta.
     * @param historicoParametroUso O histórico do parametro de uso vinculado.
     * @return o histórico encontrado.
     */
    HistoricoParametroPostosPermitidos obterUltimoHistoricoAutorizacao(FrotaParametroSistemaPostoAutorizadoAbastecimento parametroPostoPermitido, HistoricoParametroUso historicoParametroUso);

    /**
     * Busca um histórico de permissão de abastecimento de postos para uma determinada frota em uma determinada data.
     *
     * @param pv Ponto de venda usado na consulta do histórico.
     * @param historicoParametroUso Histórico do parametro de uso.
     * @param data Data da consulta do histórico.
     * @return Registro histórico encontrado.
     */
    HistoricoParametroPostosPermitidos obterHistorico(PontoDeVenda pv, HistoricoParametroUso historicoParametroUso, Date data);
}
