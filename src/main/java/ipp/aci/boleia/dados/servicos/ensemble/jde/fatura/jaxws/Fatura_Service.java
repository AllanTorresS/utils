
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;



@WebServiceClient(name = "Fatura", targetNamespace = "http://tempuri.org", wsdlLocation = "file:/C:/Projetos/ProFrotas/boleia/boleia-master/boleia-backend/src/main/resources/wsdl/jde/fatura.wsdl")
public class Fatura_Service
    extends Service
{

    private final static URL FATURA_WSDL_LOCATION;
    private final static WebServiceException FATURA_EXCEPTION;
    private final static QName FATURA_QNAME = new QName("http://tempuri.org", "Fatura");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/Projetos/ProFrotas/boleia/boleia-master/boleia-backend/src/main/resources/wsdl/jde/fatura.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        FATURA_WSDL_LOCATION = url;
        FATURA_EXCEPTION = e;
    }

    public Fatura_Service() {
        super(__getWsdlLocation(), FATURA_QNAME);
    }

    public Fatura_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), FATURA_QNAME, features);
    }

    public Fatura_Service(URL wsdlLocation) {
        super(wsdlLocation, FATURA_QNAME);
    }

    public Fatura_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, FATURA_QNAME, features);
    }

    public Fatura_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Fatura_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    
    @WebEndpoint(name = "FaturaSoap")
    public FaturaSoap getFaturaSoap() {
        return super.getPort(new QName("http://tempuri.org", "FaturaSoap"), FaturaSoap.class);
    }

    
    @WebEndpoint(name = "FaturaSoap")
    public FaturaSoap getFaturaSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org", "FaturaSoap"), FaturaSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (FATURA_EXCEPTION!= null) {
            throw FATURA_EXCEPTION;
        }
        return FATURA_WSDL_LOCATION;
    }

}
