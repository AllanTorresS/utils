package ipp.aci.boleia.dados.servicos.ibm;

import com.fasterxml.jackson.databind.ObjectMapper;
import ipp.aci.boleia.dados.IClienteHttpDados;
import ipp.aci.boleia.dados.IIbmMensagemDados;
import ipp.aci.boleia.dominio.ContingenciaSms;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.enums.AssuntoMensagem;
import ipp.aci.boleia.dominio.enums.CamposSms;
import ipp.aci.boleia.dominio.vo.AutenticacaoVo;
import ipp.aci.boleia.dominio.vo.IbmMensagemVo;
import ipp.aci.boleia.dominio.vo.TransacaoVo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;

/**
 * Respositorio de entidades do serviço de SMS da IBM
 */

@Repository
public class IbmMensagemDados implements IIbmMensagemDados {

	@Autowired
	private IClienteHttpDados clienteRest;

	@Autowired
	private UtilitarioAmbiente ambiente;

	@Value("${ibm.api.endereco.url}")
	private String ibmServicoSMSUrl;

	@Value("${ibm.api.login}")
    private String login;

	@Value("${ibm.api.senha}")
    private String senha;

    /**
     * Assunto a ser enviado na requisição para o IBM Marketing Cloud
     */
	private static final String ASSUNTO = "profrotas";

    /**
     * Logger para possíveis erros na requisição
     */
	private static final Logger LOGGER = LoggerFactory.getLogger(IbmMensagemDados.class);

	/**
	 * Dispara uma requisição para o serviço de envio de SMS do IBM Marketing Cloud
     *
	 * @param sms mensagem a ser enviada para api
	 * @return entidade contendo a resposta para ser armazenada na solução
	 */
	private ContingenciaSms enviarSMS(IbmMensagemVo sms) {
        sms.setAutenticacao(new AutenticacaoVo(login, senha));
		return clienteRest.doPostJson(ibmServicoSMSUrl, sms, null, this::tratarResposta);
	}

	/**
	 * Trata a resposta enviada pela api do IBM Marketing Cloud
	 * @param resp response da requisição
	 * @return resposta do serviço de sms
	 */
	private ContingenciaSms tratarResposta(CloseableHttpResponse resp) {
		ContingenciaSms response = new ContingenciaSms();
		String prefixoErroLog = "IBM Marketing Cloud - Error:";
		try {
			response = new ObjectMapper().readValue(EntityUtils.toString(resp.getEntity()), ContingenciaSms.class);
			response.setMensagemDeErro();
		} catch (IOException e) {
			LOGGER.error(prefixoErroLog, e);
		}
		return response;

	}

	@Override
	public ContingenciaSms enviarCodigoDesbloqueio(String telefone, String codigoAcesso, String urlBaseSistema) {
		IbmMensagemVo sms = new IbmMensagemVo();
		TransacaoVo transacao = new TransacaoVo();
		transacao.setAssunto(ASSUNTO);
		transacao.adicionarParametro(CamposSms.LINK_APP, urlBaseSistema)
				.adicionarParametro(CamposSms.CODIGO_ACESSO, codigoAcesso)
				.adicionarParametro(CamposSms.TELEFONE, telefone)
				.adicionarParametro(CamposSms.TRANSACAO_TIPO, AssuntoMensagem.CODIGO_DESBLOQUEIO.getCodigoAssunto())
				.adicionarParametro(CamposSms.DATA, UtilitarioFormatacaoData.formatarDataCurta(ambiente.buscarDataAmbiente()));
		sms.setTransacao(transacao);
		return enviarSMS(sms);
	}

	@Override
	public ContingenciaSms enviarCodigoRegerado(String telefone, String codigoAcesso) {
		IbmMensagemVo sms = new IbmMensagemVo();
		TransacaoVo transacao = new TransacaoVo();
		transacao.setAssunto(ASSUNTO);
		transacao.adicionarParametro(CamposSms.CODIGO_ACESSO, codigoAcesso)
				.adicionarParametro(CamposSms.TELEFONE, telefone)
				.adicionarParametro(CamposSms.TRANSACAO_TIPO, AssuntoMensagem.CODIGO_REGERADO.getCodigoAssunto())
				.adicionarParametro(CamposSms.DATA, UtilitarioFormatacaoData.formatarDataCurta(ambiente.buscarDataAmbiente()));
		sms.setTransacao(transacao);
		return enviarSMS(sms);
	}

	@Override
	public ContingenciaSms enviarCodigoAbastecimento(String telefone, String codigoAbastecimento) {
		IbmMensagemVo sms = new IbmMensagemVo();
		TransacaoVo transacao = new TransacaoVo();
		transacao.setAssunto(ASSUNTO);
		transacao.adicionarParametro(CamposSms.CODIGO_ABASTECIMENTO, codigoAbastecimento)
				.adicionarParametro(CamposSms.TELEFONE, telefone)
				.adicionarParametro(CamposSms.TRANSACAO_TIPO, AssuntoMensagem.CODIGO_ABASTECIMENTO.getCodigoAssunto())
				.adicionarParametro(CamposSms.DATA, UtilitarioFormatacaoData.formatarDataCurta(ambiente.buscarDataAmbiente()));
		sms.setTransacao(transacao);
		return enviarSMS(sms);
	}

	@Override
	public ContingenciaSms enviarSenhaContingencia(String telefone, String senhaContingencia, Motorista motorista) {
		IbmMensagemVo sms = new IbmMensagemVo();
		TransacaoVo transacao = new TransacaoVo();
		transacao.setAssunto(ASSUNTO);
		transacao.adicionarParametro(CamposSms.SENHA_CONTINGENCIA, senhaContingencia)
				.adicionarParametro(CamposSms.TELEFONE, telefone)
				.adicionarParametro(CamposSms.TRANSACAO_TIPO, AssuntoMensagem.SENHA_CONTINGENCIA.getCodigoAssunto())
				.adicionarParametro(CamposSms.NOME_MOTORISTA, UtilitarioFormatacao.removerAcentos(motorista.getNome()))
				.adicionarParametro(CamposSms.NOME_SOLUCAO, UtilitarioFormatacao.removerAcentos(ambiente.getNomeSistema()))
				.adicionarParametro(CamposSms.NOME_FROTA, UtilitarioFormatacao.removerAcentos( motorista.getFrota().getNomeFantasia()))
				.adicionarParametro(CamposSms.DATA, UtilitarioFormatacaoData.formatarDataCurta(ambiente.buscarDataAmbiente()));
		sms.setTransacao(transacao);
		return enviarSMS(sms);
	}
}
