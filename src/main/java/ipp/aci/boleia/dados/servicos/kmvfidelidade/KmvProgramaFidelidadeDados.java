package ipp.aci.boleia.dados.servicos.kmvfidelidade;

import ipp.aci.boleia.dados.IAutorizacaoPagamentoDados;
import ipp.aci.boleia.dados.IClienteHttpDados;
import ipp.aci.boleia.dados.IProgramaFidelidadeDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.enums.StatusAcumuloKmv;
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

	private static final Integer TIPO_ACUMULO = 178;
	private static final Logger LOGGER = LoggerFactory.getLogger(KmvProgramaFidelidadeDados.class);
	private static final String USER_CREDENTIALS = "user_credentials";
	private static final String BASIC_USER_CREDENTIALS_PREFIX = "Basic ";

	@Autowired
	private IClienteHttpDados clienteRest;

	@Autowired
	private IAutorizacaoPagamentoDados repositorioAutorizacaoPagamento;

	@Value("${kmv.api.acumulo.url}")
	private String kmvAcumuloUrl;

	@Value("${kmv.api.saldo.url}")
	private String kmvMostrarSaldoUrl;

	@Value("${kmv.api.endereco.url}")
	private String kmvEnderecoUrl;


	@Override
	public KmvVo acumularPontos(KmvAcumuloVo acumulo) {
		return clienteRest.doPostJson(kmvAcumuloUrl, acumulo, null, resp -> {
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
                return new KmvVo(StatusAcumuloKmv.NAO_ACUMULADO, acumulo.getPedido().getKm());
            }else{
                LOGGER.error(prefixoErroLog + mensagem);
                return new KmvVo(StatusAcumuloKmv.PENDENTE, acumulo.getPedido().getKm());
            }
        });

	}

	@Override
	public List<KmvAcumuloVo> obterAcumulosPendentes(Integer numeroDeRegistros) {
		List<KmvAcumuloVo> acumulosPendentes = new ArrayList<>();
		List<AutorizacaoPagamento> notas = repositorioAutorizacaoPagamento.obterNotasSemAcumuloKmv(numeroDeRegistros);
		notas.forEach(n -> {
			KmvAcumuloVo acumulo = new KmvAcumuloVo();
			KmvAcumuloPedidoVo acumuladoPedido = new KmvAcumuloPedidoVo();
			acumuladoPedido.setCodigoPedido(n.getId().toString());
			acumuladoPedido.setCpf(n.getCpfMotorista().toString());
			acumuladoPedido.setDataPedido(UtilitarioFormatacaoData.formatarDataIso8601SemTimezone(n.getDataProcessamento()));
			acumuladoPedido.setTipoAcumulo(TIPO_ACUMULO);
			BigDecimal valorTotalAbastecimento = n.getTotalLitrosAbastecimento().multiply(n.getValorUnitarioAbastecimento());
			acumuladoPedido.setValorCompra(valorTotalAbastecimento);
			acumuladoPedido.setValorPago(valorTotalAbastecimento);
			acumuladoPedido.setKm(valorTotalAbastecimento.intValue());

			acumulo.setPedido(acumuladoPedido);
			acumulosPendentes.add(acumulo);
		});
		return acumulosPendentes;
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
}
