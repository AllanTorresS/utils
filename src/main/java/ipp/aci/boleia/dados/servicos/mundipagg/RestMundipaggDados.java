package ipp.aci.boleia.dados.servicos.mundipagg;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ipp.aci.boleia.dados.IClienteHttpDados;
import ipp.aci.boleia.dados.IMundipaggDados;
import ipp.aci.boleia.dominio.PedidoCreditoFrota;
import ipp.aci.boleia.dominio.enums.StatusPedidoCredito;
import ipp.aci.boleia.dominio.vo.MundipaggPedidoVo;
import ipp.aci.boleia.dominio.vo.MundipaggRespostaPedidoVo;
import ipp.aci.boleia.dominio.vo.MundipaggStatusVo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.UtilitarioJson;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Base64;

/**
 * Respositorio de entidades do Km de Vantagens
 */
@Repository
public class RestMundipaggDados implements IMundipaggDados {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestMundipaggDados.class);

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String BASIC_AUTHORIZATION_PREFIX = "Basic ";

	private static final String ORDERS_API_ENDPOINT = "/orders";
	private static final String CHARGES_API_ENDPOINT = "/charges";
	private static final String CLOSE_ORDER_API_ENDPOINT = "/closed";
	private static final String CANCELED_STATUS = "canceled";
	private static final String CAMPO_STATUS = "status";
	private static final String CAMPO_CHARGES = "charges";

	@Autowired
	private IClienteHttpDados clienteRest;

	@Autowired
	private Mensagens mensagens;

	@Value("${mundipagg.api.key}")
	private String mundipaggKey;

	@Value("${mundipagg.api.endereco}")
	private String mundipaggEnderecoUrl;

	private Header[] authorizationHeaders;

	/**
	 * Cria o header de autenticacao para chamada do servico
	 */
	@PostConstruct
	public void init() {
		authorizationHeaders = new Header[]{new BasicHeader(AUTHORIZATION_HEADER, BASIC_AUTHORIZATION_PREFIX + Base64.getEncoder().encodeToString((mundipaggKey + ":").getBytes()))};
	}

	@Override
	public MundipaggRespostaPedidoVo criarPedido(MundipaggPedidoVo pedido) {
		if(LOGGER.isInfoEnabled()) {
			String requestString = UtilitarioJson.toJSON(pedido).replace("\n", "").replace("\r", "");
			LOGGER.info(requestString);
		}
		return clienteRest.doPostJson(mundipaggEnderecoUrl + ORDERS_API_ENDPOINT, pedido, authorizationHeaders, this::processarRespostaCriacaoPedido);
	}

	@Override
	public PedidoCreditoFrota atualizarPedido(PedidoCreditoFrota pedido) throws ExcecaoValidacao {
		StringBuilder mensagemErro = new StringBuilder();
		String url = mundipaggEnderecoUrl + ORDERS_API_ENDPOINT + "/" + pedido.getCodigoPedidoMundipagg();
		PedidoCreditoFrota pedidoAtualizado = clienteRest.doGet(url, authorizationHeaders, resp -> tratarRespostaAtualizacaoPedido(pedido, mensagemErro, resp));
		if(pedidoAtualizado == null) {
			throw new ExcecaoValidacao(mensagemErro.toString());
		}
		return pedidoAtualizado;
	}

	@Override
	public PedidoCreditoFrota cancelarPedido(PedidoCreditoFrota pedido) throws ExcecaoValidacao {
		PedidoCreditoFrota pedidoAtualizado = atualizarPedido(pedido);
		if(pedidoAtualizado.getIdCobranca() != null) {
			cancelarCobranca(pedidoAtualizado.getIdCobranca());
		}
		StringBuilder mensagemErro = new StringBuilder();
		String url = mundipaggEnderecoUrl + ORDERS_API_ENDPOINT + "/" + pedidoAtualizado.getCodigoPedidoMundipagg() + CLOSE_ORDER_API_ENDPOINT;
		MundipaggStatusVo novoStatusVo = new MundipaggStatusVo(CANCELED_STATUS);
		if(LOGGER.isInfoEnabled()) {
			String requestString = UtilitarioJson.toJSON(novoStatusVo).replace("\n", "").replace("\r", "");
			LOGGER.info(requestString);
		}
		PedidoCreditoFrota pedidoCancelado = clienteRest.doPatchJson(url, novoStatusVo, authorizationHeaders, resp -> tratarRespostaCancelamentoPedido(pedidoAtualizado, mensagemErro, resp));
		if(pedidoCancelado == null) {
			if(pedidoAtualizado.getIdCobranca() == null) {
				throw new ExcecaoValidacao(Erro.ERRO_GENERICO);
			}
			throw new ExcecaoValidacao(Erro.PEDIDO_MUNDIPAGG_JA_CANCELADO);
		}
		return pedidoCancelado;
	}

	/**
	 * Cancela a cobrança do pedido
	 * @param idCobranca a ser cancelada
	 */
	private void cancelarCobranca(String idCobranca) throws ExcecaoValidacao {
		StringBuilder mensagemErro = new StringBuilder();
		String url = mundipaggEnderecoUrl + CHARGES_API_ENDPOINT + "/" + idCobranca;
		boolean cancelada = clienteRest.doDelete(url, authorizationHeaders, resp -> tratarRespostaCancelamentoCobranca(mensagemErro, resp));
		if(!cancelada) {
			throw new ExcecaoValidacao(Erro.PEDIDO_MUNDIPAGG_NAO_CANCELADO);
		}
	}

	/**
	 * Trata a resposta recebida do servico MundiPagg durante o cancelamento de um pedido
	 *
	 * @param pedido O pedido
	 * @param mensagemErro As mensagens de erro concatenadas, caso ocorra algum problema
	 * @param resp A resposta recebida do MundiPagg
	 * @return true caso o cancelamento seja bem sucedido
	 */
	private PedidoCreditoFrota tratarRespostaCancelamentoPedido(PedidoCreditoFrota pedido, StringBuilder mensagemErro, CloseableHttpResponse resp) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode resposta = mapper.readTree(EntityUtils.toString(resp.getEntity()));
			if(verificarErroIntegracao(resp.getStatusLine().getStatusCode(), resposta, mensagemErro)) {
				return null;
			}
			StatusPedidoCredito statusNovo = StatusPedidoCredito.obterPorNomeMundiPagg(resposta.get(CAMPO_STATUS).asText());
			pedido.setStatus(statusNovo.getValue());
			return pedido;
		} catch(Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			mensagemErro.append(ex.getMessage());
			return null;
		}
	}

	/**
	 * Trata a resposta recebida do servico MundiPagg durante o cancelamento de uma cobranca
	 *
	 * @param mensagemErro As mensagens de erro concatenadas, caso ocorra algum problema
	 * @param resp A resposta recebida do MundiPagg
	 * @return true caso o cancelamento seja bem sucedido
	 */
	private Boolean tratarRespostaCancelamentoCobranca(StringBuilder mensagemErro, CloseableHttpResponse resp) {
		try {
            ObjectMapper mapper = new ObjectMapper();
			String respString = EntityUtils.toString(resp.getEntity());
			if(LOGGER.isInfoEnabled()) {
				LOGGER.info(respString.replace("\n", "").replace("\r", ""));
			}
            JsonNode resposta = mapper.readTree(respString);
            if(verificarErroIntegracao(resp.getStatusLine().getStatusCode(), resposta, mensagemErro)) {
                return false;
            }
            if (!resposta.get(CAMPO_STATUS).asText().equals(CANCELED_STATUS)) {
                mensagemErro.append(mensagens.obterMensagem("frota.servico.credito.prepago.erro.mundipagg"));
                return false;
            }
            return true;
        } catch(Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            mensagemErro.append(ex.getMessage());
        }
		return false;
	}

	/**
	 * Trata a resposta recebida do servico MundiPagg durante a atualizacao de status de um pedido
	 *
	 * @param pedido O pedido
	 * @param mensagemErro As mensagens de erro concatenadas, caso ocorra algum problema
	 * @param resp A resposta recebida do MundiPagg
	 * @return O pedido atualizado
	 */
	private PedidoCreditoFrota tratarRespostaAtualizacaoPedido(PedidoCreditoFrota pedido, StringBuilder mensagemErro, CloseableHttpResponse resp) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String respString = EntityUtils.toString(resp.getEntity());
			if(LOGGER.isInfoEnabled()) {
				LOGGER.info(respString.replace("\n", "").replace("\r", ""));
			}
			JsonNode resposta = mapper.readTree(respString);
			if(verificarErroIntegracao(resp.getStatusLine().getStatusCode(), resposta, mensagemErro)) {
				return null;
			}
			JsonNode charge = resposta.get(CAMPO_CHARGES) != null && resposta.get(CAMPO_CHARGES).elements().hasNext() ? resposta.get(CAMPO_CHARGES).elements().next() : null;
			if(charge != null) {
				JsonNode transaction = charge.get("last_transaction");
				if(transaction != null) {
					BigDecimal valorPago = new BigDecimal(transaction.get("amount").asText()).divide(new BigDecimal(100));
					pedido.setValorPago(valorPago);
				}
				StatusPedidoCredito statusNovo = StatusPedidoCredito.obterPorNomeMundiPagg(charge.get(CAMPO_STATUS).asText());
				pedido.setStatus(statusNovo.getValue());
				pedido.setIdCobranca(charge.get("id").asText());
			}
			return pedido;
		} catch(Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			mensagemErro.append(ex.getMessage());
			return null;
		}
	}

	/**
	 * Verifica se a reposta da integracao mundipagg contem erros
	 * @param statusHTTP codigo do statusHTTP retornado
	 * @param resposta retornada pela mundipagg
	 * @param mensagemErro onde serao armazenadas mensagens de erro
	 * @return true em caso de erros
	 */
	private boolean verificarErroIntegracao(Integer statusHTTP, JsonNode resposta, StringBuilder mensagemErro) {
		if(statusHTTP >= HttpStatus.SC_BAD_REQUEST) {
			if(resposta.get("message") != null) {
				mensagemErro.append(resposta.get("message").asText());
			}
			if(resposta.get("errors") != null) {
				mensagemErro.append(" (").append(resposta.get("errors").asText()).append(")");
			}
			if(mensagemErro.length()==0) {
				mensagemErro.append(mensagens.obterMensagem("frota.servico.credito.prepago.erro.mundipagg"));
			}
			return true;
		}
		return false;
	}

	/**
	 * Processa a resposta de criacao de pedido recebida da mundipagg
	 * @param resp A resposta
	 * @return Um vo contendo a resposta processada
	 */
	private MundipaggRespostaPedidoVo processarRespostaCriacaoPedido(CloseableHttpResponse resp) {
		MundipaggRespostaPedidoVo respostaPedido = new MundipaggRespostaPedidoVo();
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode resposta = mapper.readTree(EntityUtils.toString(resp.getEntity()));
			StringBuilder mensagemErro = new StringBuilder();
			if(verificarErroIntegracao(resp.getStatusLine().getStatusCode(), resposta, mensagemErro)) {
				respostaPedido.setErro(mensagemErro.toString());
			} else {
				respostaPedido.setId(resposta.get("id").asText());
				respostaPedido.setCodigo(resposta.get("code").asText());
				BigDecimal valorCompra = new BigDecimal(resposta.get("amount").asText()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_UNNECESSARY);
				respostaPedido.setValorCompra(UtilitarioFormatacao.formatarDecimal(valorCompra));
				respostaPedido.setToken(resposta.get("checkouts").elements().next().get("id").asText());
			}
		} catch(Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			respostaPedido.setErro(ex.getMessage());
		}
		return respostaPedido;
	}
}
