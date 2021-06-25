package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.vo.conectcar.AtivacaoLoteIntegracaoVo;
import ipp.aci.boleia.dominio.vo.conectcar.AtivacaoTagIntegracaoVo;
import ipp.aci.boleia.dominio.vo.conectcar.ConectCarBloquearContratoRequestVo;
import ipp.aci.boleia.dominio.vo.conectcar.ConectCarBloqueioResponseVo;
import ipp.aci.boleia.dominio.vo.conectcar.ConectCarConsultaPedidoTagResponseVo;
import ipp.aci.boleia.dominio.vo.conectcar.ConectCarDesbloquearContratoRequestVo;
import ipp.aci.boleia.dominio.vo.conectcar.ConectCarPedidoResponseVo;
import ipp.aci.boleia.dominio.vo.conectcar.ConectcarCriarPedidoEntregaVo;
import ipp.aci.boleia.dominio.vo.conectcar.LoteAdesivosVo;
import ipp.aci.boleia.dominio.vo.conectcar.TagDadosIntegracaoVo;
import ipp.aci.boleia.dominio.vo.conectcar.TagSubstituicaoIntegracaoVo;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;

/**
 * Contrato para implementação de repositório de integração dos dados com a conectcar
 */
public interface IConectCarIntegracaoDados {
	
	boolean bloquearTag(TagDadosIntegracaoVo corpo) throws ExcecaoValidacao;
	boolean desbloquearTag(AtivacaoTagIntegracaoVo corpo) throws ExcecaoValidacao;
	boolean desvincularTag(TagDadosIntegracaoVo corpo) throws ExcecaoValidacao;
	ConectCarBloqueioResponseVo ativarEmLote(AtivacaoLoteIntegracaoVo corpo) throws ExcecaoValidacao;
	ConectCarBloqueioResponseVo bloquearEmLote(LoteAdesivosVo lote) throws ExcecaoValidacao;
	ConectCarBloqueioResponseVo desbloquearEmLote(LoteAdesivosVo lote) throws ExcecaoValidacao;
	ConectCarPedidoResponseVo criarPedidoEntrega(ConectcarCriarPedidoEntregaVo corpo) throws ExcecaoValidacao;
	boolean bloquearContrato(ConectCarBloquearContratoRequestVo corpo) throws ExcecaoValidacao;
	boolean desbloquearContrato(ConectCarDesbloquearContratoRequestVo corpo) throws ExcecaoValidacao;
	boolean substituirTag(TagSubstituicaoIntegracaoVo corpo) throws ExcecaoValidacao;
	ConectCarConsultaPedidoTagResponseVo consultarPedidoTag(Integer pedidoId) throws ExcecaoValidacao;

}