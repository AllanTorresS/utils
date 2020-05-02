package ipp.aci.boleia.dados.servicos.ensemble;

import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Set;

/**
 * Adiciona os cabecalhos WS-Security  Username Token Profile no pacote SOAP a ser enviado
 * para o consumode um web service.
 */
public class WsSecurityUsernameTokenHandler implements SOAPHandler<SOAPMessageContext> {

    private static final String SOAP_ELEMENT_PASS = "Password";
    private static final String SOAP_ELEMENT_USERNAME = "Username";
    private static final String SOAP_ELEMENT_USERNAME_TOKEN = "UsernameToken";
    private static final String SOAP_ELEMENT_SECURITY = "Security";
    private static final String PREFIX_SECURITY = "wsse";
    private static final String SOAP_ELEMENT_PASS_TYPE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText";
    private static final String NAMESPACE_SECURITY = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";

    private final String usernameText;
    private final String passwordText;
    private final String nomeServico;

    /**
     * Constroi uma instancia do handler de seguranca para invocacao de um web service
     *
     * @param usernameText O login para autenticacao e consumo do servico
     * @param passwordText A senha para autenticacao e consumo do servico
     * @param nomeServico O nome do servico para impressao no log, em caso de erro
     */
    public WsSecurityUsernameTokenHandler(String usernameText, String passwordText, String nomeServico) {
        this.usernameText = usernameText;
        this.passwordText = passwordText;
        this.nomeServico = nomeServico;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext soapMessageContext) {
        Boolean outboundProperty = (Boolean) soapMessageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (outboundProperty) {
            try {
                SOAPEnvelope soapEnvelope = soapMessageContext.getMessage().getSOAPPart().getEnvelope();
                SOAPHeader header = soapEnvelope.getHeader();
                if (header == null) {
                    header = soapEnvelope.addHeader();
                }
                SOAPElement soapElementSecurityHeader = header.addChildElement(SOAP_ELEMENT_SECURITY, PREFIX_SECURITY, NAMESPACE_SECURITY);
                SOAPElement soapElementUsernameToken = soapElementSecurityHeader.addChildElement(SOAP_ELEMENT_USERNAME_TOKEN, PREFIX_SECURITY);
                SOAPElement soapElementUsername = soapElementUsernameToken.addChildElement(SOAP_ELEMENT_USERNAME, PREFIX_SECURITY);
                soapElementUsername.addTextNode(this.usernameText);
                SOAPElement soapElementPassword = soapElementUsernameToken.addChildElement(SOAP_ELEMENT_PASS, PREFIX_SECURITY);
                soapElementPassword.addAttribute(new QName("Type"), SOAP_ELEMENT_PASS_TYPE);
                soapElementPassword.addTextNode(this.passwordText);
            } catch (Exception e) {
                throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, e, nomeServico);
            }
        }
        return true;
    }

    @Override
    public void close(MessageContext context) {
        // nada a fazer
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        // nada a fazer
        return true;
    }

    @Override
    public Set<QName> getHeaders() {
        // nada a fazer
        return null;
    }

    public String getNomeServico() {
        return nomeServico;
    }
}
