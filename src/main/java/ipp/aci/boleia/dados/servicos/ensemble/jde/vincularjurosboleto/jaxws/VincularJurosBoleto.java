
package ipp.aci.boleia.dados.servicos.ensemble.jde.vincularjurosboleto.jaxws;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;


@WebServiceClient(name = "vincularJurosBoleto", targetNamespace = "http://ipiranga.com.br/vincularJurosBoleto", wsdlLocation = "file:/C:/Projetos/boleia/boleia/fonte/boleia/boleia-master/boleia-backend/src/main/resources/wsdl/jde/vincularJurosBoleto.wsdl")
public class VincularJurosBoleto
    extends Service
{

    private final static URL VINCULARJUROSBOLETO_WSDL_LOCATION;
    private final static WebServiceException VINCULARJUROSBOLETO_EXCEPTION;
    private final static QName VINCULARJUROSBOLETO_QNAME = new QName("http://ipiranga.com.br/vincularJurosBoleto", "vincularJurosBoleto");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/Projetos/boleia/boleia/fonte/boleia/boleia-master/boleia-backend/src/main/resources/wsdl/jde/vincularJurosBoleto.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        VINCULARJUROSBOLETO_WSDL_LOCATION = url;
        VINCULARJUROSBOLETO_EXCEPTION = e;
    }

    public VincularJurosBoleto() {
        super(__getWsdlLocation(), VINCULARJUROSBOLETO_QNAME);
    }

    public VincularJurosBoleto(WebServiceFeature... features) {
        super(__getWsdlLocation(), VINCULARJUROSBOLETO_QNAME, features);
    }

    public VincularJurosBoleto(URL wsdlLocation) {
        super(wsdlLocation, VINCULARJUROSBOLETO_QNAME);
    }

    public VincularJurosBoleto(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, VINCULARJUROSBOLETO_QNAME, features);
    }

    public VincularJurosBoleto(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public VincularJurosBoleto(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    
    @WebEndpoint(name = "vincularJurosBoletoSoap")
    public VincularJurosBoletoSoap getVincularJurosBoletoSoap() {
        return super.getPort(new QName("http://ipiranga.com.br/vincularJurosBoleto", "vincularJurosBoletoSoap"), VincularJurosBoletoSoap.class);
    }

    
    @WebEndpoint(name = "vincularJurosBoletoSoap")
    public VincularJurosBoletoSoap getVincularJurosBoletoSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://ipiranga.com.br/vincularJurosBoleto", "vincularJurosBoletoSoap"), VincularJurosBoletoSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (VINCULARJUROSBOLETO_EXCEPTION!= null) {
            throw VINCULARJUROSBOLETO_EXCEPTION;
        }
        return VINCULARJUROSBOLETO_WSDL_LOCATION;
    }

}
