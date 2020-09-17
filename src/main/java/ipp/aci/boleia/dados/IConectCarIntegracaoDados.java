package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.vo.AtivacaoLoteIntegracaoVo;
import ipp.aci.boleia.dominio.vo.ConectCarBloqueioResponseVo;
import ipp.aci.boleia.dominio.vo.TagDadosIntegracaoVo;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;

/**
 * Contrato para implementação de repositório de integração dos dados com a conectcar
 */
public interface IConectCarIntegracaoDados {
	
	
	boolean bloquearTag(TagDadosIntegracaoVo corpo) throws ExcecaoValidacao;
	boolean desbloquearTag(TagDadosIntegracaoVo corpo) throws ExcecaoValidacao;
	ConectCarBloqueioResponseVo ativarEmLote(AtivacaoLoteIntegracaoVo corpo) throws ExcecaoValidacao;
	
}