package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar;

import ipp.aci.boleia.dados.IVoucherContasPagarDados;
import ipp.aci.boleia.dados.servicos.ensemble.WsSecurityUsernameTokenHandler;
import ipp.aci.boleia.dados.servicos.ensemble.jde.JdeBaseDados;
import ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws.LiberarReq;
import ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws.LiberarResp;
import ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws.VoucherContasPagar;
import ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws.VoucherContasPagarSoap;
import ipp.aci.boleia.dominio.Reembolso;
import ipp.aci.boleia.dominio.enums.StatusIntegracaoReembolsoJde;
import ipp.aci.boleia.dominio.enums.StatusLiberacaoReembolsoJde;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import static ipp.aci.boleia.util.jde.ConstantesJde.VOUCHER_CONTAS_PAGAR_CENARIO;
import static ipp.aci.boleia.util.jde.ConstantesJde.VOUCHER_CONTAS_PAGAR_CIA_DOCUMENTO;
import static ipp.aci.boleia.util.jde.ConstantesJde.VOUCHER_CONTAS_PAGAR_LINHA_DOCUMENTO;
import static ipp.aci.boleia.util.jde.ConstantesJde.VOUCHER_CONTAS_PAGAR_TIPO_DOCUMENTO;

/**
 * Implementação de acesso para as operações do serviço Voucher Contas a Pagar do JDE.
 *
 * @author pedro.silva
 */
@Repository
@Scope("singleton")
public class JdeVoucherContasPagarDados extends JdeBaseDados<VoucherContasPagarSoap> implements IVoucherContasPagarDados {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdeVoucherContasPagarDados.class);
    private static final String NOME_SERVICO = "voucherContasPagarJDE";
    private static final String ARQUIVO_JDE_WSDL = "/wsdl/jde/voucherContasPagar.wsdl";

    private static final String CODIGO_LIBERACAO_SUCESSO = "0";

    @Override
    public Reembolso liberar(Reembolso reembolso) {
        try {
            LiberarReq requisicao = criarRequisicaoLiberar(reembolso);
            LiberarResp resposta = enviarRequisicaoLiberar(requisicao);
            if (reembolsoLiberadoComSucesso(resposta)) {
                reembolso.setStatusLiberacaoPagamento(StatusLiberacaoReembolsoJde.APROVADO_PAGAMENTO.getValue());
                reembolso.setStatusIntegracao(StatusIntegracaoReembolsoJde.REALIZADO.getValue());
                reembolso.setMensagemErroLiberacaoPagamento(null);
            } else {
                reembolso.setStatusLiberacaoPagamento(StatusLiberacaoReembolsoJde.SUSPENSO_PAGAMENTO.getValue());
                reembolso.setStatusIntegracao(StatusIntegracaoReembolsoJde.ERRO_LIBERACAO.getValue());
                reembolso.setMensagemErroLiberacaoPagamento(obterMensagemErro(resposta));
            }
        } catch (ExcecaoBoleiaRuntime e) {
            String mensagem = getMensagens().obterMensagem(e.getErro().getChaveMensagem(), e.getArgs());
            LOGGER.error(mensagem, e);

            reembolso.setStatusLiberacaoPagamento(StatusLiberacaoReembolsoJde.SUSPENSO_PAGAMENTO.getValue());
            reembolso.setStatusIntegracao(StatusIntegracaoReembolsoJde.ERRO_LIBERACAO.getValue());
            reembolso.setMensagemErroLiberacaoPagamento(mensagem);
        }
        return reembolso;
    }

    @Override
    protected WsSecurityUsernameTokenHandler instanciarHeadersHandler() {
        return new WsSecurityUsernameTokenHandler(getJdeLogin(), getJdeSenha(), NOME_SERVICO);
    }

    @Override
    protected VoucherContasPagarSoap instanciarServico() throws MalformedURLException {
        String urlWsdl = getClass().getResource(ARQUIVO_JDE_WSDL).toString();
        URL url = new URL(urlWsdl);
        VoucherContasPagar voucher = new VoucherContasPagar(url);
        return voucher.getVoucherContasPagarSoap();
    }

    /**
     * Obtém a mensagem de erro retornada no response da integração de liberação de voucher.
     *
     * @param resposta Response da integração.
     * @return Mensagem de erro.
     */
    private String obterMensagemErro(LiberarResp resposta) {
        String mensagemErro = "";
        if (resposta.getMsgErro() != null) {
            mensagemErro = resposta.getMsgErro();
        }
        if (resposta.getErrorDescription() != null) {
            mensagemErro = mensagemErro + " - " + resposta.getErrorDescription();
        }
        return mensagemErro;
    }

    /**
     * Cria o corpo da requisição da operação de liberar.
     *
     * @param reembolso
     * @return Requisição do liberar
     */
    private LiberarReq criarRequisicaoLiberar(Reembolso reembolso) {
        LiberarReq requisicao = new LiberarReq();
        requisicao.setCenario(VOUCHER_CONTAS_PAGAR_CENARIO);
        requisicao.setCiaDocumento(VOUCHER_CONTAS_PAGAR_CIA_DOCUMENTO);
        requisicao.setTipoDocumento(VOUCHER_CONTAS_PAGAR_TIPO_DOCUMENTO);
        requisicao.setNumeroDocumento(new BigDecimal(reembolso.getNumeroDocumento()));
        requisicao.setLinhaDocumento(VOUCHER_CONTAS_PAGAR_LINHA_DOCUMENTO);
        return requisicao;
    }

    /**
     * Chama o serviço de liberação do reembolso para pagamento.
     *
     * @param requisicao Corpo da requisição
     * @return Resposta da requisição
     */
    private LiberarResp enviarRequisicaoLiberar(LiberarReq requisicao) {
        LiberarResp resposta;
        try {
            resposta = getServico().liberar(requisicao);
        } catch (Exception e) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, e, NOME_SERVICO + ".liberar" + e.getMessage());
        }
        return resposta;
    }

    /**
     * Informa se um reembolso foi liberado para pagamento no JDE com sucesso.
     *
     * @param respostaLiberar Resposta do serviço de liberação do JDE.
     * @return true caso tenha sido liberado
     */
    private boolean reembolsoLiberadoComSucesso(LiberarResp respostaLiberar) {
        return CODIGO_LIBERACAO_SUCESSO.equals(respostaLiberar.getErrorCode());
    }
}
