
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;



@WebServiceClient(name = "voucherContasPagar", targetNamespace = "http://tempuri.org", wsdlLocation = "file:/C:/Projetos/ProFrotas/boleia/boleia-master/boleia-backend/src/main/resources/wsdl/jde/voucherContasPagar.wsdl")
public class VoucherContasPagar
    extends Service
{

    private final static URL VOUCHERCONTASPAGAR_WSDL_LOCATION;
    private final static WebServiceException VOUCHERCONTASPAGAR_EXCEPTION;
    private final static QName VOUCHERCONTASPAGAR_QNAME = new QName("http://tempuri.org", "voucherContasPagar");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/Projetos/ProFrotas/boleia/boleia-master/boleia-backend/src/main/resources/wsdl/jde/voucherContasPagar.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        VOUCHERCONTASPAGAR_WSDL_LOCATION = url;
        VOUCHERCONTASPAGAR_EXCEPTION = e;
    }

    public VoucherContasPagar() {
        super(__getWsdlLocation(), VOUCHERCONTASPAGAR_QNAME);
    }

    public VoucherContasPagar(WebServiceFeature... features) {
        super(__getWsdlLocation(), VOUCHERCONTASPAGAR_QNAME, features);
    }

    public VoucherContasPagar(URL wsdlLocation) {
        super(wsdlLocation, VOUCHERCONTASPAGAR_QNAME);
    }

    public VoucherContasPagar(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, VOUCHERCONTASPAGAR_QNAME, features);
    }

    public VoucherContasPagar(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public VoucherContasPagar(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    
    @WebEndpoint(name = "voucherContasPagarSoap")
    public VoucherContasPagarSoap getVoucherContasPagarSoap() {
        return super.getPort(new QName("http://tempuri.org", "voucherContasPagarSoap"), VoucherContasPagarSoap.class);
    }

    
    @WebEndpoint(name = "voucherContasPagarSoap")
    public VoucherContasPagarSoap getVoucherContasPagarSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org", "voucherContasPagarSoap"), VoucherContasPagarSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (VOUCHERCONTASPAGAR_EXCEPTION!= null) {
            throw VOUCHERCONTASPAGAR_EXCEPTION;
        }
        return VOUCHERCONTASPAGAR_WSDL_LOCATION;
    }

}
