package ipp.aci.boleia.dados.servicos.ensemble.jde.contabancaria;

import ipp.aci.boleia.dados.IContaBancariaIntegracaoDados;
import ipp.aci.boleia.dados.servicos.ensemble.ServicoSoapDados;
import ipp.aci.boleia.dados.servicos.ensemble.WsSecurityUsernameTokenHandler;
import ipp.aci.boleia.dados.servicos.ensemble.jde.contabancaria.jaxws.ContaBancaria;
import ipp.aci.boleia.dados.servicos.ensemble.jde.contabancaria.jaxws.ContaBancariaSoap;
import ipp.aci.boleia.dados.servicos.ensemble.jde.contabancaria.jaxws.DadosContaBancariaReq;
import ipp.aci.boleia.dados.servicos.ensemble.jde.contabancaria.jaxws.DadosContaBancariaResp;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import static ipp.aci.boleia.util.jde.ConstantesJde.COD_PAIS;
import static ipp.aci.boleia.util.jde.ConstantesJde.CONTA_BANCARIA_ACAO;
import static ipp.aci.boleia.util.jde.ConstantesJde.CONTA_BANCARIA_CODIGO_CAD_BANCO;
import static ipp.aci.boleia.util.jde.ConstantesJde.CONTA_BANCARIA_TIPO_CONTA;
import static ipp.aci.boleia.util.jde.ConstantesJde.CONTA_BANCARIA_TIPO_REGISTRO;

/**
 * Implementação do integração com JDE para {@link IContaBancariaIntegracaoDados}
 *
 */
@Repository
@Scope("singleton")
public class JdeContaBancariaDados extends ServicoSoapDados<ContaBancariaSoap> implements IContaBancariaIntegracaoDados {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdeContaBancariaDados.class);
    private static final String NOME_SERVICO = "contaBancariaJDE";
    private static final String ARQUIVO_JDE_WSDL = "/wsdl/jde/contaBancaria.wsdl";

    @Value("${jde.web.service.login}")
    private String jdeLogin;

    @Value("${jde.web.service.senha}")
    private String jdeSenha;

    @Autowired
    private Mensagens mensagens;

    @Override
    protected WsSecurityUsernameTokenHandler instanciarHeadersHandler() {
        return new WsSecurityUsernameTokenHandler(jdeLogin, jdeSenha, NOME_SERVICO);
    }

    @Override
    protected ContaBancariaSoap instanciarServico() throws MalformedURLException {
        URL url = new URL(obterUrlJdeCompleta());
        ContaBancaria contaBancaria = new ContaBancaria(url);
        return contaBancaria.getContaBancariaSoap();
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
     * Valida a resposta enviada pelo serviço.
     *
     * @param response Objeto de resposta.
     * @throws ExcecaoValidacao Caso o status de integração não seja de sucesso.
     */
    private void validarResposta(DadosContaBancariaResp response) throws ExcecaoValidacao {
        if (!response.getStatusIntegracao().isStatus()) {
            ExcecaoValidacao excecaoValidacao = new ExcecaoValidacao(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("contabancaria.jde.servico.validacao.excecaoValidacao"));
            LOGGER.error(response.getMensagemRetorno() + " - " + response.getStatusIntegracao().getMensagem(), excecaoValidacao);
            throw excecaoValidacao;
        }
    }

    @Override
    public void cadastrarContaBancaria(ipp.aci.boleia.dominio.ContaBancaria contaBancaria, Integer numeroJdeInterno) throws ExcecaoValidacao {
        if(contaBancaria != null) {
            DadosContaBancariaReq req = obterRequisicaoCadastroContaBancaria(contaBancaria, numeroJdeInterno);
            DadosContaBancariaResp response;
            try {
                response = getServico().dadosContaBancaria(req);
            } catch (RuntimeException ex) {
                throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, ex, ex.getMessage());
            }
            validarResposta(response);
        }
    }

    /**
     * Monta a requisicao para a integração do cadastro de conta corrente
     * no JDE
     *
     * @param contaBancaria Conta Bancaria a ser cadastrada
     * @param numeroJdeInterno Identificador do Ponto de Venda no JDE
     * @return Requisicao montada
     */
    private DadosContaBancariaReq obterRequisicaoCadastroContaBancaria(ipp.aci.boleia.dominio.ContaBancaria contaBancaria, Integer numeroJdeInterno) {
        DadosContaBancariaReq req = preencherValoresFixos();
        req.setCodigoCadastroCliente(new BigDecimal(numeroJdeInterno));
        req.setBancoAgencia(montarBancoAgencia(contaBancaria));
        req.setNumeroContaBancaria(contaBancaria.getNumeroConta().toUpperCase());
        if(contaBancaria.getDigitoConta() != null){
            req.setDigitoControle(contaBancaria.getDigitoConta().toUpperCase());
        }
        return req;
    }

    /**
     * Formata uma string com o padrão BANCO/AGÊNCIA
     *
     * @param contaBancaria Conta Bancaria
     * @return String formatada
     */
    private String montarBancoAgencia(ipp.aci.boleia.dominio.ContaBancaria contaBancaria) {
        if (contaBancaria != null) {
            return (contaBancaria.getBanco().getCodigoInstituicao() + "/" +
                    contaBancaria.getNumeroAgencia());
        }
        return null;
    }

    /**
     * Preenche a requisicao com os valores constantes comuns a todos os casos
     * @return A requisicao preenchida
     */
    private DadosContaBancariaReq preencherValoresFixos() {
        DadosContaBancariaReq req = new DadosContaBancariaReq();
        req.setAcao(CONTA_BANCARIA_ACAO);
        req.setPaisDoBanco(COD_PAIS);
        req.setTipoRegistro(CONTA_BANCARIA_TIPO_REGISTRO);
        req.setCodigoCasdastroBanco(new BigDecimal(CONTA_BANCARIA_CODIGO_CAD_BANCO));
        req.setTipoConta(CONTA_BANCARIA_TIPO_CONTA);
        return req;
    }
}