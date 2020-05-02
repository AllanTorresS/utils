package ipp.aci.boleia.dados.servicos.ensemble.jde.boleto;

import ipp.aci.boleia.dados.IBoletoDados;
import ipp.aci.boleia.dados.servicos.ensemble.ServicoSoapDados;
import ipp.aci.boleia.dados.servicos.ensemble.WsSecurityUsernameTokenHandler;
import ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws.BoletoSoap;
import ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws.Boleto_Service;
import ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws.RecuperarBoletoReq;
import ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws.RecuperarBoletoResp;
import ipp.aci.boleia.dominio.Cobranca;
import ipp.aci.boleia.dominio.vo.BoletoVo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.UtilitarioIntegracao;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import static ipp.aci.boleia.util.jde.ConstantesJde.BOLETO_NUMERO_PARCELA;

/**
 * Implementação do integração com JDE para {@link ipp.aci.boleia.dados.IBoletoDados}
 *
 */

@Repository
@Scope("singleton")
public class JdeBoletoDados extends ServicoSoapDados<BoletoSoap> implements IBoletoDados {

    private static final String BOLETO_GERADO = "1";
    private static final String NOME_SERVICO = "boletoJDE";
    private static final String ARQUIVO_JDE_WSDL = "/wsdl/jde/boleto.wsdl";

    @Value("${jde.web.service.login}")
    private String jdeLogin;

    @Value("${jde.web.service.senha}")
    private String jdeSenha;

	@Override
	public BoletoVo recuperar(Cobranca cobranca) {
        RecuperarBoletoReq req = obterRequisicaoIncluir(cobranca);
        RecuperarBoletoResp resp = executarRequisicaoRecuperar(req);
        if (!resp.getStatusIntegracao().isStatus()) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, resp.getStatusIntegracao().getMensagem());
        }
        return obterBoletoVo(resp);
	}

    @Override
	protected WsSecurityUsernameTokenHandler instanciarHeadersHandler() {
		return new WsSecurityUsernameTokenHandler(jdeLogin, jdeSenha, NOME_SERVICO);
	}

	@Override
	protected BoletoSoap instanciarServico() throws MalformedURLException {
        URL url = new URL(obterUrlJdeCompleta());
        Boleto_Service boletoService =  new Boleto_Service(url);
		return boletoService.getBoletoSoap();
    }
    
    /**
     * Dado o endereco do wsdl, converte-o em um caminho absoluto
     *
     * @return url JDE completa
     */
    private String obterUrlJdeCompleta() {
        return this.getClass().getResource(ARQUIVO_JDE_WSDL).toString();
    }

    /**
     * Cria uma requisição para recuperar um boleto no JDE
     *
     * @param cobranca A combrança em questão.
     * @return  Um objeto de requisicao conforme o contrato de uso do JDE
     */
    private RecuperarBoletoReq obterRequisicaoIncluir(Cobranca cobranca) {
        RecuperarBoletoReq req = new RecuperarBoletoReq();
        req.setCompanhia(cobranca.getCiaDocumento());
        req.setTipoDocumento(cobranca.getTipoDocumento());
        req.setDocumento(new BigDecimal(cobranca.getNumeroDocumento()));
        req.setParcela(BOLETO_NUMERO_PARCELA);
        return req;
    }

    /**
     * Método que transforma a respsota da integração no Vo de boleto.
     *
     * @param resp Reposta do serviço JDE.
     * @return Boleto preenchido com os dados.
     */
    private BoletoVo obterBoletoVo(RecuperarBoletoResp resp) {
        BoletoVo boletoVo = new BoletoVo();
        boletoVo.setBoletoGerado(resp.getEncontrouBoleto() != null && resp.getEncontrouBoleto().equals(BOLETO_GERADO));
        boletoVo.setAceite(resp.getAceite());
        boletoVo.setCarteira(resp.getCarteira());
        boletoVo.setCedente(resp.getCedente());
        boletoVo.setCodigoAgencia(resp.getCodigoAgencia());
        boletoVo.setCodigoBanco(resp.getCodigoBanco());
        boletoVo.setCodigoBarras(resp.getCodigoBarras());
        boletoVo.setCompanhia(resp.getCompanhia());
        boletoVo.setCpfCnpj(formtarCpfCnpj(resp.getCpfCnpj()));
        boletoVo.setDataDeVencimento(UtilitarioFormatacaoData.formatarDataCurta(resp.getDataDeVencimento()));
        boletoVo.setDataDocumento(UtilitarioFormatacaoData.formatarDataCurta(resp.getDataCocumento()));
        boletoVo.setDataProcessamento(UtilitarioFormatacaoData.formatarDataCurta(resp.getDataProcessamento()));
        boletoVo.setDocumento(resp.getDocumento().toString());
        boletoVo.setInstrucoesLinha1(resp.getInstrucoesLinha1());
        boletoVo.setInstrucoesLinha2(resp.getInstrucoesLinha2());
        boletoVo.setInstrucoesLinha3(resp.getInstrucoesLinha3());
        boletoVo.setInstrucoesLinha4(resp.getInstrucoesLinha4());
        boletoVo.setInstrucoesLinha5(resp.getInstrucoesLinha5());
        boletoVo.setInstrucoesLinha6(resp.getInstrucoesLinha6());
        boletoVo.setInstrucoesLinha7(resp.getInstrucoesLinha7());
        boletoVo.setInstrucoesLinha8(resp.getInstrucoesLinha8());
        boletoVo.setLinhaDigitavel(resp.getLinhaDigitavel());
        boletoVo.setLocalPagamento(resp.getLocalPagamento());
        boletoVo.setMoeda(resp.getMoeda());
        boletoVo.setNomeBanco(resp.getNomeBanco());
        boletoVo.setNossoNumeroFormatado(resp.getNossoNumeroFormatado());
        boletoVo.setNumeroBoleto(resp.getNumeroBoleto());
        boletoVo.setNumeroDocumento(resp.getNumeroDocumento());
        boletoVo.setParcela(resp.getParcela());
        boletoVo.setQuantidade(tratarValorZeroParaNulo(resp.getQuantidade()));
        boletoVo.setSacado(resp.getSacado());
        boletoVo.setSacador(resp.getSacador());
        boletoVo.setTipoDocumento(resp.getTipoDocumento());
        boletoVo.setTipoDocumentoAbadi(resp.getTipoDocumentoAbadi());
        boletoVo.setUsoDoBanco(resp.getUsoDoBanco());
        boletoVo.setValorAcrescimos(tratarValorZeroParaNulo(resp.getValorAcrescimos()));
        boletoVo.setValorCobrado(tratarValorZeroParaNulo(resp.getValorCobrado()));
        boletoVo.setValorDeducoes(tratarValorZeroParaNulo(resp.getValorDeducoes()));
        boletoVo.setValorDesconto(tratarValorZeroParaNulo(resp.getValorDesconto()));
        boletoVo.setValorDocumento(tratarValorZeroParaNulo(resp.getValorDocumento()));
        boletoVo.setValorMoeda(tratarValorZeroParaNulo(resp.getValorMoeda()));
        boletoVo.setValorMulta(tratarValorZeroParaNulo(resp.getValorMulta()));
        return boletoVo;
    }

    /**
     * Executa a requisicao "recuperar" do web service
     * @param req A requisicao
     * @return A resposta da requisicao
     */
    private RecuperarBoletoResp executarRequisicaoRecuperar(RecuperarBoletoReq req) {
        return UtilitarioIntegracao.invocarIntegracao(req, NOME_SERVICO + ".recuperar", getServico()::recuperar);
    }

    /**
     * Se o valor for zero, retorna nulo
     * @param valor O valor
     * @return Nulo se zero, ou o proprio valor caso contrario
     */
    private BigDecimal tratarValorZeroParaNulo(BigDecimal valor) {
        return valor == null || valor.compareTo(BigDecimal.ZERO) == 0 ? null : valor;
    }

    /**
     * Formata um cpf ou um cnpj
     * @param cpfCnpj valor do cpf/cpnj
     * @return valor formatado
     */
    private String formtarCpfCnpj(String cpfCnpj) {
        return UtilitarioFormatacao.formatarCpjCnpjApresentacao(cpfCnpj);
    }
}