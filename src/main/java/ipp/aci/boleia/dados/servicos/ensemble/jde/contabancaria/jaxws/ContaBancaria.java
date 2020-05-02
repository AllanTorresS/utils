
package ipp.aci.boleia.dados.servicos.ensemble.jde.contabancaria.jaxws;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;


@WebServiceClient(name = "contaBancaria", targetNamespace = "http://ipiranga.com.br/dadosContaBancaria", wsdlLocation = "file:/C:/Projetos/boleia/boleia/fonte/boleia/boleia-master/boleia-backend/src/main/resources/wsdl/jde/contaBancaria.wsdl")
public class ContaBancaria
    extends Service
{

    private final static URL CONTABANCARIA_WSDL_LOCATION;
    private final static WebServiceException CONTABANCARIA_EXCEPTION;
    private final static QName CONTABANCARIA_QNAME = new QName("http://ipiranga.com.br/dadosContaBancaria", "contaBancaria");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/Projetos/boleia/boleia/fonte/boleia/boleia-master/boleia-backend/src/main/resources/wsdl/jde/contaBancaria.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CONTABANCARIA_WSDL_LOCATION = url;
        CONTABANCARIA_EXCEPTION = e;
    }

    public ContaBancaria() {
        super(__getWsdlLocation(), CONTABANCARIA_QNAME);
    }

    public ContaBancaria(WebServiceFeature... features) {
        super(__getWsdlLocation(), CONTABANCARIA_QNAME, features);
    }

    public ContaBancaria(URL wsdlLocation) {
        super(wsdlLocation, CONTABANCARIA_QNAME);
    }

    public ContaBancaria(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CONTABANCARIA_QNAME, features);
    }

    public ContaBancaria(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ContaBancaria(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    
    @WebEndpoint(name = "contaBancariaSoap")
    public ContaBancariaSoap getContaBancariaSoap() {
        return super.getPort(new QName("http://ipiranga.com.br/dadosContaBancaria", "contaBancariaSoap"), ContaBancariaSoap.class);
    }

    
    @WebEndpoint(name = "contaBancariaSoap")
    public ContaBancariaSoap getContaBancariaSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://ipiranga.com.br/dadosContaBancaria", "contaBancariaSoap"), ContaBancariaSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CONTABANCARIA_EXCEPTION!= null) {
            throw CONTABANCARIA_EXCEPTION;
        }
        return CONTABANCARIA_WSDL_LOCATION;
    }

}
