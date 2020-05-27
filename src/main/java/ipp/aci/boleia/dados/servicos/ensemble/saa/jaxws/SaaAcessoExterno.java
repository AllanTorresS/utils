
package ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;



@WebServiceClient(name = "saaAcessoExterno", targetNamespace = "http://tempuri.org", wsdlLocation = "file:/C:/Projetos/ProFrotas/boleia/boleia-master/boleia-backend/src/main/resources/wsdl/saa.wsdl")
public class SaaAcessoExterno
    extends Service
{

    private final static URL SAAACESSOEXTERNO_WSDL_LOCATION;
    private final static WebServiceException SAAACESSOEXTERNO_EXCEPTION;
    private final static QName SAAACESSOEXTERNO_QNAME = new QName("http://tempuri.org", "saaAcessoExterno");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/Projetos/ProFrotas/boleia/boleia-master/boleia-backend/src/main/resources/wsdl/saa.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SAAACESSOEXTERNO_WSDL_LOCATION = url;
        SAAACESSOEXTERNO_EXCEPTION = e;
    }

    public SaaAcessoExterno() {
        super(__getWsdlLocation(), SAAACESSOEXTERNO_QNAME);
    }

    public SaaAcessoExterno(WebServiceFeature... features) {
        super(__getWsdlLocation(), SAAACESSOEXTERNO_QNAME, features);
    }

    public SaaAcessoExterno(URL wsdlLocation) {
        super(wsdlLocation, SAAACESSOEXTERNO_QNAME);
    }

    public SaaAcessoExterno(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SAAACESSOEXTERNO_QNAME, features);
    }

    public SaaAcessoExterno(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SaaAcessoExterno(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    
    @WebEndpoint(name = "saaAcessoExternoSoap")
    public SaaAcessoExternoSoap getSaaAcessoExternoSoap() {
        return super.getPort(new QName("http://tempuri.org", "saaAcessoExternoSoap"), SaaAcessoExternoSoap.class);
    }

    
    @WebEndpoint(name = "saaAcessoExternoSoap")
    public SaaAcessoExternoSoap getSaaAcessoExternoSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org", "saaAcessoExternoSoap"), SaaAcessoExternoSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SAAACESSOEXTERNO_EXCEPTION!= null) {
            throw SAAACESSOEXTERNO_EXCEPTION;
        }
        return SAAACESSOEXTERNO_WSDL_LOCATION;
    }

}
