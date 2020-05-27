package ipp.aci.boleia.dados.servicos.kmvfidelidade;

import ipp.aci.boleia.dados.IAcumuloKmvDados;
import ipp.aci.boleia.dados.IClienteHttpDados;
import ipp.aci.boleia.dados.IProgramaFidelidadeDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.acumulo.AcumuloKmv;
import ipp.aci.boleia.dominio.enums.StatusAcumuloKmv;
import ipp.aci.boleia.dominio.enums.TipoAcumuloKmv;
import ipp.aci.boleia.dominio.vo.KmvAcumuloPedidoVo;
import ipp.aci.boleia.dominio.vo.KmvAcumuloVo;
import ipp.aci.boleia.dominio.vo.KmvVo;
import ipp.aci.boleia.dominio.vo.SaldoKmvVo;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.UtilitarioJson;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Respositorio de entidades do Km de Vantagens
 */
@Repository
public class KmvProgramaFidelidadeDados implements IProgramaFidelidadeDados {


	private static final BigDecimal UM_POR_CENTO_VALOR = new BigDecimal(0.01);
	private static final Logger LOGGER = LoggerFactory.getLogger(KmvProgramaFidelidadeDados.class);
	private static final String USER_CREDENTIALS = "user_credentials";
	private static final String BASIC_USER_CREDENTIALS_PREFIX = "Basic ";

	@Autowired
	private IClienteHttpDados clienteRest;

	@Autowired
	private IAcumuloKmvDados repositorioControleAcumulo;

	@Value("${kmv.api.acumulo.motorista.url}")
	private String kmvAcumuloMotoristaUrl;

	@Value("${kmv.api.acumulo.dono.url}")
	private String kmvAcumuloDonoUrl;

	@Value("${kmv.api.saldo.url}")
	private String kmvMostrarSaldoUrl;

	@Value("${kmv.api.endereco.url}")
	private String kmvEnderecoUrl;

	/**
	 * Registra o acúmulo de Km de vantagens
	 *
	 * @param acumulo Objeto que contêm os dados do acúmulo
	 * @param url a url representando os diferentes endpoints de acúmulo disponíveis.
	 * @return O resultado da operacao de acumulo de pontos
	 */

	private KmvVo acumularPontos(KmvAcumuloVo acumulo, String url) {
		return clienteRest.doPostJson(url, acumulo, null, resp -> {
			String prefixoErroLog = "Kmv - Erro: ";
			String mensagem = null;
			try {
				mensagem = EntityUtils.toString(resp.getEntity());
			} catch (IOException e) {
				LOGGER.error(prefixoErroLog, e);
			}
			if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED){
				return new KmvVo(StatusAcumuloKmv.ACUMULADO, acumulo.getPedido().getKm());
			}else if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND
					|| resp.getStatusLine().getStatusCode() == HttpStatus.SC_CONFLICT){
				LOGGER.error(prefixoErroLog + mensagem);
				return new KmvVo(StatusAcumuloKmv.NAO_ACUMULADO, acumulo.getPedido().getKm(),mensagem);
			}else{
				LOGGER.error(prefixoErroLog + mensagem);
				return new KmvVo(StatusAcumuloKmv.PENDENTE, acumulo.getPedido().getKm(),mensagem);
			}
		});

	}

	@Override
	public List<KmvAcumuloVo> obterAcumulosPendentes(Integer numeroDeRegistros) {
		List<KmvAcumuloVo> acumulosASeremProcessados = new ArrayList<>();
		List<AcumuloKmv> acumulosPendentes = repositorioControleAcumulo.obterAcumulosPendentes(numeroDeRegistros);
		acumulosPendentes.forEach(a -> montarAcumuloSimplesOuDividido(acumulosASeremProcessados, a));
		return acumulosASeremProcessados;
	}

	/**
	 * Monta os pedidos de acúmulo, verificando se o abastecimento do acúmulo pendente deve gerar
	 * dois acúmulos, sendo um para o motorista e outro para o  dono da frota, ou apenas um acúmulo individual.
	 * @param acumuloPendenteAtual o acúmulo pendente atual
	 * @param acumulosASeremProcessados a lista onde os acúmulos montados que irão ser salvos para processamento.
	 */
	private void montarAcumuloSimplesOuDividido(List<KmvAcumuloVo> acumulosASeremProcessados, AcumuloKmv acumuloPendenteAtual) {

		if(acumuloPendenteAtual.getFrota().getCpfDonoFrota() != null && acumuloPendenteAtual.getTipoAcumulo().equals(TipoAcumuloKmv.MOTORISTA.getValue())
		&& !existePedidoDonoDaFrotaParaOAbastecimento(acumuloPendenteAtual.getAutorizacaoPagamento())){

			acumulosASeremProcessados.add(montarAcumulosPedidos(acumuloPendenteAtual, TipoAcumuloKmv.DONO_FROTA.getValue()));
			acumulosASeremProcessados.add(montarAcumulosPedidos(acumuloPendenteAtual,TipoAcumuloKmv.MOTORISTA.getValue()));
		}
		else {
			acumulosASeremProcessados.add(montarAcumulosPedidos(acumuloPendenteAtual, acumuloPendenteAtual.getTipoAcumulo()));
		}
	}

	/**
	 * Verifica se para um abastecimento em comum já existe acúmulo de dono da frota para dividir  os pontos com o motorista.
	 * @param autorizacaoPagamento o abastecimento com acúmulo pendente atual
	 * @return true caso já exista um registro dividindo os pontos acúmulados para esse abastecimento
	 */
	private Boolean existePedidoDonoDaFrotaParaOAbastecimento(AutorizacaoPagamento autorizacaoPagamento) {
			return repositorioControleAcumulo.recuperarAcumuloDonoFrotaJaCriado(autorizacaoPagamento.getId()) != null;
	}

	/**
	 * Monta os objetos requisição de acúmulo pedido para diferentes casos de acúmulo
	 * @param acumuloPendente os acumulos com abastecimentos pendentes.
	 * @param tipoAcumulo o tipo do acúmulo para motorista ou Dono da frota, atualmente.
	 * @return o objeto montado
	 */
	private KmvAcumuloVo montarAcumulosPedidos(AcumuloKmv acumuloPendente, Integer tipoAcumulo){

		AutorizacaoPagamento abastecimentosDeNotas = acumuloPendente.getAutorizacaoPagamento();
		KmvAcumuloVo acumulo = new KmvAcumuloVo();
		KmvAcumuloPedidoVo acumuladoPedido = new KmvAcumuloPedidoVo();

		acumuladoPedido.setCodigoPedido(abastecimentosDeNotas.getId().toString());
		acumuladoPedido.setDataPedido(UtilitarioFormatacaoData.formatarDataIso8601SemTimezone(abastecimentosDeNotas.getDataProcessamento()));
		acumuladoPedido.setTipoAcumulo(tipoAcumulo);
		BigDecimal valorTotalAbastecimento = abastecimentosDeNotas.getTotalLitrosAbastecimento().multiply(abastecimentosDeNotas.getValorUnitarioAbastecimento());
		acumuladoPedido.setValorCompra(valorTotalAbastecimento);
		acumuladoPedido.setValorPago(valorTotalAbastecimento);

		if(tipoAcumulo.equals(TipoAcumuloKmv.DONO_FROTA.getValue())) {

			acumuladoPedido.setCpf(abastecimentosDeNotas.getFrota().getCpfDonoFrota().toString());
			acumuladoPedido.setKm(valorTotalAbastecimento.multiply(UM_POR_CENTO_VALOR).intValue());
		}
		else{

			acumuladoPedido.setCpf(abastecimentosDeNotas.getCpfMotorista().toString());
			acumuladoPedido.setKm(valorTotalAbastecimento.intValue());
		}
		acumulo.setPedido(acumuladoPedido);
		return acumulo;
	}



	@Override
	public String pesquisaCep(String cep) {
		return clienteRest.doGet(kmvEnderecoUrl + "&cep=" + cep, r -> EntityUtils.toString(r.getEntity()));
	}

	/**
	 * Cria o header com as user_credencials do usuario kmv
	 * @param cpf o cpf do usuario no kmv
	 * @param senha a senha do usuario no kmv
	 * @return header montado
	 */
	public Header[] montarHeader(String cpf, String senha) {
		return new Header[]{new BasicHeader(USER_CREDENTIALS, BASIC_USER_CREDENTIALS_PREFIX + Base64.getEncoder().encodeToString((cpf + ":" + senha).getBytes()))};
	}


	@Override
	public Integer mostrarSaldo(String cpf, String senha) {
		return clienteRest.doGet(kmvMostrarSaldoUrl.replace("{cpf}", cpf), montarHeader(cpf, senha), resp -> {
			String prefixoErroLog = "Kmv - Erro: ";
			SaldoKmvVo response;
			try {
				response = UtilitarioJson.toObjectWithConfigureFailOnUnknowProperties(resp, SaldoKmvVo.class, false);
				return response.getSaldo();
			} catch (Exception e) {
				LOGGER.error(prefixoErroLog, e);
			}
			return null;
		});
	}

	@Override
	public KmvVo acumularPontosParaDonoFrota(KmvAcumuloVo acumulo) {
		return acumularPontos(acumulo,kmvAcumuloDonoUrl);
	}

	@Override
	public KmvVo acumularPontosParaMotorista(KmvAcumuloVo acumulo) {
		return acumularPontos(acumulo, kmvAcumuloMotoristaUrl);
	}





}
