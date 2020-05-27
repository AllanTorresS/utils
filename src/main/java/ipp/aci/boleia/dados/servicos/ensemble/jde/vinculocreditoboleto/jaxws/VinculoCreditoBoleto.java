
package ipp.aci.boleia.dados.servicos.ensemble.jde.vinculocreditoboleto.jaxws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;



@WebServiceClient(name = "vinculoCreditoBoleto", targetNamespace = "http://ipiranga.com.br/vinculoCreditoBoleto", wsdlLocation = "file:/C:/Projetos/ProFrotas/boleia/boleia-master/boleia-backend/src/main/resources/wsdl/jde/vinculoCreditoBoleto.wsdl")
public class VinculoCreditoBoleto
    extends Service
{

    private final static URL VINCULOCREDITOBOLETO_WSDL_LOCATION;
    private final static WebServiceException VINCULOCREDITOBOLETO_EXCEPTION;
    private final static QName VINCULOCREDITOBOLETO_QNAME = new QName("http://ipiranga.com.br/vinculoCreditoBoleto", "vinculoCreditoBoleto");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/Projetos/ProFrotas/boleia/boleia-master/boleia-backend/src/main/resources/wsdl/jde/vinculoCreditoBoleto.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        VINCULOCREDITOBOLETO_WSDL_LOCATION = url;
        VINCULOCREDITOBOLETO_EXCEPTION = e;
    }

    public VinculoCreditoBoleto() {
        super(__getWsdlLocation(), VINCULOCREDITOBOLETO_QNAME);
    }

    public VinculoCreditoBoleto(WebServiceFeature... features) {
        super(__getWsdlLocation(), VINCULOCREDITOBOLETO_QNAME, features);
    }

    public VinculoCreditoBoleto(URL wsdlLocation) {
        super(wsdlLocation, VINCULOCREDITOBOLETO_QNAME);
    }

    public VinculoCreditoBoleto(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, VINCULOCREDITOBOLETO_QNAME, features);
    }

    public VinculoCreditoBoleto(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public VinculoCreditoBoleto(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    
    @WebEndpoint(name = "vinculoCreditoBoletoSoap")
    public VinculoCreditoBoletoSoap getVinculoCreditoBoletoSoap() {
        return super.getPort(new QName("http://ipiranga.com.br/vinculoCreditoBoleto", "vinculoCreditoBoletoSoap"), VinculoCreditoBoletoSoap.class);
    }

    
    @WebEndpoint(name = "vinculoCreditoBoletoSoap")
    public VinculoCreditoBoletoSoap getVinculoCreditoBoletoSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://ipiranga.com.br/vinculoCreditoBoleto", "vinculoCreditoBoletoSoap"), VinculoCreditoBoletoSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (VINCULOCREDITOBOLETO_EXCEPTION!= null) {
            throw VINCULOCREDITOBOLETO_EXCEPTION;
        }
        return VINCULOCREDITOBOLETO_WSDL_LOCATION;
    }

}
