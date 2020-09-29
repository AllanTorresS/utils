package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.vo.conectcar.AtivacaoLoteIntegracaoVo;
import ipp.aci.boleia.dominio.vo.conectcar.ConectCarBloqueioResponseVo;
import ipp.aci.boleia.dominio.vo.conectcar.ConectCarPedidoResponseVo;
import ipp.aci.boleia.dominio.vo.conectcar.ConectcarCriarPedidoEntregaVo;
import ipp.aci.boleia.dominio.vo.conectcar.TagDadosIntegracaoVo;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;

/**
 * Contrato para implementação de repositório de integração dos dados com a conectcar
 */
public interface IConectCarIntegracaoDados {
	
	
	boolean bloquearTag(TagDadosIntegracaoVo corpo) throws ExcecaoValidacao;
	boolean desbloquearTag(TagDadosIntegracaoVo corpo) throws ExcecaoValidacao;
	ConectCarBloqueioResponseVo ativarEmLote(AtivacaoLoteIntegracaoVo corpo) throws ExcecaoValidacao;	
	ConectCarPedidoResponseVo criarPedidoEntrega(ConectcarCriarPedidoEntregaVo corpo) throws ExcecaoValidacao;
	
}