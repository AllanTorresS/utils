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
	
	/**
	 * Realiza o bloqueio de uma Tag na ConectCar
	 * @param corpo TagDadosIntegracaoVo com os dados a serem enviados na requisição
	 * @return boolean indica se a requisição foi enviada com sucesso
	 * @throws ExcecaoValidacao caso o código de retorno da requisição seja diferente de 200
	 */
	boolean bloquearTag(TagDadosIntegracaoVo corpo) throws ExcecaoValidacao;
	
	/**
	 * Realiza o desbloqueio de uma Tag na ConectCar
	 * @param corpo AtivacaoTagIntegracaoVo com os dados a serem enviados na requisição
	 * @return boolean indica se a requisição foi enviada com sucesso
	 * @throws ExcecaoValidacao caso o código de retorno da requisição seja diferente de 200
	 */
	boolean desbloquearTag(AtivacaoTagIntegracaoVo corpo) throws ExcecaoValidacao;
	
	/**
	 * Realiza o desvinculo de uma Tag na ConectCar
	 * @param corpo TagDadosIntegracaoVo com os dados a serem enviados na requisição
	 * @return boolean indica se a requisição foi enviada com sucesso
	 * @throws ExcecaoValidacao caso o código de retorno da requisição seja diferente de 200 e 404
	 */
	boolean desvincularTag(TagDadosIntegracaoVo corpo) throws ExcecaoValidacao;
	
	/**
	 * Realiza a ativação em lote de Tags na ConectCar
	 * @param corpo AtivacaoLoteIntegracaoVo com os dados a serem enviados na requisição
	 * @return ConectCarBloqueioResponseVo com a resposta da requisição à ConectCar
	 * @throws ExcecaoValidacao caso não ocorra sucesso na requisição
	 */
	ConectCarBloqueioResponseVo ativarEmLote(AtivacaoLoteIntegracaoVo corpo) throws ExcecaoValidacao;
	
	/**
	 * Realiza o bloqueio em lote de Tags na ConectCar
	 * @param lote LoteAdesivosVo com os dados a serem enviados na requisição
	 * @return ConectCarBloqueioResponseVo com a resposta da requisição à ConectCar
	 * @throws ExcecaoValidacao caso não ocorra sucesso na requisição
	 */
	ConectCarBloqueioResponseVo bloquearEmLote(LoteAdesivosVo lote) throws ExcecaoValidacao;
	
	/**
	 * Realiza o desbloqueio em lote de Tags na ConectCar
	 * @param lote LoteAdesivosVo com os dados a serem enviados na requisição
	 * @return ConectCarBloqueioResponseVo com a resposta da requisição à ConectCar
	 * @throws ExcecaoValidacao caso não ocorra sucesso na requisição
	 */
	ConectCarBloqueioResponseVo desbloquearEmLote(LoteAdesivosVo lote) throws ExcecaoValidacao;
	
	/**
	 * Realiza um pedido de Tags na ConectCar
	 * @param corpo ConectcarCriarPedidoEntregaVo com os dados a serem enviados na requisição
	 * @return ConectCarPedidoResponseVo com a resposta da requisição à ConectCar
	 * @throws ExcecaoValidacao caso não ocorra sucesso na requisição
	 */
	ConectCarPedidoResponseVo criarPedidoEntrega(ConectcarCriarPedidoEntregaVo corpo) throws ExcecaoValidacao;
	
	/**
	 * Realiza o bloqueio de um contrato na ConectCar
	 * @param corpo ConectCarBloquearContratoRequestVo com os dados a serem enviados na requisição
	 * @return boolean indica se a requisição foi enviada com sucesso
	 * @throws ExcecaoValidacao caso não ocorra sucesso na requisição
	 */
	boolean bloquearContrato(ConectCarBloquearContratoRequestVo corpo) throws ExcecaoValidacao;
	
	/**
	 * Realiza o desbloqueio de um contrato na ConectCar
	 * @param corpo ConectCarDesbloquearContratoRequestVo com os dados a serem enviados na requisição
	 * @return boolean indica se a requisição foi enviada com sucesso
	 * @throws ExcecaoValidacao caso não ocorra sucesso na requisição
	 */
	boolean desbloquearContrato(ConectCarDesbloquearContratoRequestVo corpo) throws ExcecaoValidacao;
	
	/**
	 * Realiza a substituição de uma Tag na ConectCar
	 * @param corpo TagSubstituicaoIntegracaoVo com os dados a serem enviados na requisição
	 * @return boolean indica se a requisição foi enviada com sucesso
	 * @throws ExcecaoValidacao caso o código de retorno da requisição seja diferente de 200
	 */
	boolean substituirTag(TagSubstituicaoIntegracaoVo corpo) throws ExcecaoValidacao;
	
	/**
	 * Realiza a consulta de um pedido na ConectCar 
	 * @param pedidoId Identificador do pedido
	 * @return ConectCarConsultaPedidoTagResponseVo com a resposta da requisição à ConectCar
	 * @throws ExcecaoValidacao caso não ocorra sucesso na requisição
	 */
	ConectCarConsultaPedidoTagResponseVo consultarPedidoTag(Integer pedidoId) throws ExcecaoValidacao;

}