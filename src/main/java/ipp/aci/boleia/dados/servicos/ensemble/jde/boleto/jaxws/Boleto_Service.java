
package ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;


@WebServiceClient(name = "Boleto", targetNamespace = "http://tempuri.org", wsdlLocation = "file:/C:/Projetos/boleia/boleia/fonte/boleia/boleia-master/boleia-backend/src/main/resources/wsdl/jde/boleto.wsdl")
public class Boleto_Service
    extends Service
{

    private final static URL BOLETO_WSDL_LOCATION;
    private final static WebServiceException BOLETO_EXCEPTION;
    private final static QName BOLETO_QNAME = new QName("http://tempuri.org", "Boleto");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/Projetos/boleia/boleia/fonte/boleia/boleia-master/boleia-backend/src/main/resources/wsdl/jde/boleto.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        BOLETO_WSDL_LOCATION = url;
        BOLETO_EXCEPTION = e;
    }

    public Boleto_Service() {
        super(__getWsdlLocation(), BOLETO_QNAME);
    }

    public Boleto_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), BOLETO_QNAME, features);
    }

    public Boleto_Service(URL wsdlLocation) {
        super(wsdlLocation, BOLETO_QNAME);
    }

    public Boleto_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, BOLETO_QNAME, features);
    }

    public Boleto_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Boleto_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    
    @WebEndpoint(name = "BoletoSoap")
    public BoletoSoap getBoletoSoap() {
        return super.getPort(new QName("http://tempuri.org", "BoletoSoap"), BoletoSoap.class);
    }

    
    @WebEndpoint(name = "BoletoSoap")
    public BoletoSoap getBoletoSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org", "BoletoSoap"), BoletoSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (BOLETO_EXCEPTION!= null) {
            throw BOLETO_EXCEPTION;
        }
        return BOLETO_WSDL_LOCATION;
    }

}
