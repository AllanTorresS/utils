
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;



@WebServiceClient(name = "pessoa", targetNamespace = "http://tempuri.org", wsdlLocation = "file:/C:/Projetos/ProFrotas/boleia/boleia-master/boleia-backend/src/main/resources/wsdl/jde/pessoa.wsdl")
public class Pessoa
    extends Service
{

    private final static URL PESSOA_WSDL_LOCATION;
    private final static WebServiceException PESSOA_EXCEPTION;
    private final static QName PESSOA_QNAME = new QName("http://tempuri.org", "pessoa");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/Projetos/ProFrotas/boleia/boleia-master/boleia-backend/src/main/resources/wsdl/jde/pessoa.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        PESSOA_WSDL_LOCATION = url;
        PESSOA_EXCEPTION = e;
    }

    public Pessoa() {
        super(__getWsdlLocation(), PESSOA_QNAME);
    }

    public Pessoa(WebServiceFeature... features) {
        super(__getWsdlLocation(), PESSOA_QNAME, features);
    }

    public Pessoa(URL wsdlLocation) {
        super(wsdlLocation, PESSOA_QNAME);
    }

    public Pessoa(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, PESSOA_QNAME, features);
    }

    public Pessoa(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Pessoa(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    
    @WebEndpoint(name = "pessoaSoap")
    public PessoaSoap getPessoaSoap() {
        return super.getPort(new QName("http://tempuri.org", "pessoaSoap"), PessoaSoap.class);
    }

    
    @WebEndpoint(name = "pessoaSoap")
    public PessoaSoap getPessoaSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org", "pessoaSoap"), PessoaSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (PESSOA_EXCEPTION!= null) {
            throw PESSOA_EXCEPTION;
        }
        return PESSOA_WSDL_LOCATION;
    }

}
