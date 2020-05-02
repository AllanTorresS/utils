
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;


@WebServiceClient(name = "SasIntegraWSService", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", wsdlLocation = "file:/C:/Users/pedro.silva/IdeaProjects/boleia-master/boleia-backend/src/main/resources/wsdl/sascar.wsdl")
public class SasIntegraWSService
    extends Service
{

    private final static URL SASINTEGRAWSSERVICE_WSDL_LOCATION;
    private final static WebServiceException SASINTEGRAWSSERVICE_EXCEPTION;
    private final static QName SASINTEGRAWSSERVICE_QNAME = new QName("http://webservice.web.integracao.sascar.com.br/", "SasIntegraWSService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/Users/pedro.silva/IdeaProjects/boleia-master/boleia-backend/src/main/resources/wsdl/sascar.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SASINTEGRAWSSERVICE_WSDL_LOCATION = url;
        SASINTEGRAWSSERVICE_EXCEPTION = e;
    }

    public SasIntegraWSService() {
        super(__getWsdlLocation(), SASINTEGRAWSSERVICE_QNAME);
    }

    public SasIntegraWSService(WebServiceFeature... features) {
        super(__getWsdlLocation(), SASINTEGRAWSSERVICE_QNAME, features);
    }

    public SasIntegraWSService(URL wsdlLocation) {
        super(wsdlLocation, SASINTEGRAWSSERVICE_QNAME);
    }

    public SasIntegraWSService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SASINTEGRAWSSERVICE_QNAME, features);
    }

    public SasIntegraWSService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SasIntegraWSService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    
    @WebEndpoint(name = "SasIntegraWSPort")
    public SasIntegraWS getSasIntegraWSPort() {
        return super.getPort(new QName("http://webservice.web.integracao.sascar.com.br/", "SasIntegraWSPort"), SasIntegraWS.class);
    }

    
    @WebEndpoint(name = "SasIntegraWSPort")
    public SasIntegraWS getSasIntegraWSPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://webservice.web.integracao.sascar.com.br/", "SasIntegraWSPort"), SasIntegraWS.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SASINTEGRAWSSERVICE_EXCEPTION!= null) {
            throw SASINTEGRAWSSERVICE_EXCEPTION;
        }
        return SASINTEGRAWSSERVICE_WSDL_LOCATION;
    }

}
