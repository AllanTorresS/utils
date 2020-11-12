package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.vo.apco.ClienteProFrotaVo;
import ipp.aci.boleia.dominio.vo.apco.FrotaPostoVo;
import ipp.aci.boleia.dominio.vo.apco.VolumeVendasClienteProFrotaVo;
import ipp.aci.boleia.util.excecao.ExcecaoIntegracaoAPCO;

import java.util.List;

/**
 * Interface responsável pela integração com o serviço de exportação de dados para a APCO.
 */
public interface IApcoExportacaoDados {


	/**
	 * Realiza a integração exportando as frotas Clientes atualizadas.
	 * @param frotasCadastro as frotas a serem cadastradas na integração
	 * @throws ExcecaoIntegracaoAPCO caso haja excecao na integracao com apco
	 */
	void exportarClientesProfrotas(List<ClienteProFrotaVo> frotasCadastro) throws ExcecaoIntegracaoAPCO;

	/**
	 * Realiza a integração exportando os vinculos(frota e posto) de um determinado período de integração.
	 * @param associacoes os registros de vinculo
	 * @throws ExcecaoIntegracaoAPCO caso haja excecao na integracao com apco
	 */
	void exportarFrotaPosto(List<FrotaPostoVo> associacoes) throws ExcecaoIntegracaoAPCO;

	/**
	 * Realiza a integração exportando as vendas(abastecimentos) de um determinado período de integração.
	 * Além disso, são passados informações do lote para um melhor controle dos dados enviado para o ensemble
	 * @param abastecimentos os consolidados do período de integração
	 * @param  indiceInicioLote o indice inicial do lote
	 * @param  indiceFimLote o indice final do lote
	 * @throws ExcecaoIntegracaoAPCO caso haja excecao na integracao com apco
	 */
	void exportarVendas(List<VolumeVendasClienteProFrotaVo> abastecimentos, Integer indiceInicioLote, Integer indiceFimLote) throws ExcecaoIntegracaoAPCO;
}
