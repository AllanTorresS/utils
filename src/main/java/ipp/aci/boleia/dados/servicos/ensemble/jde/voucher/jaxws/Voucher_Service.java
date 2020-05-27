
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;



@WebServiceClient(name = "voucher", targetNamespace = "http://ipiranga.com.br/voucher", wsdlLocation = "file:/C:/Projetos/ProFrotas/boleia/boleia-master/boleia-backend/src/main/resources/wsdl/jde/voucher.wsdl")
public class Voucher_Service
    extends Service
{

    private final static URL VOUCHER_WSDL_LOCATION;
    private final static WebServiceException VOUCHER_EXCEPTION;
    private final static QName VOUCHER_QNAME = new QName("http://ipiranga.com.br/voucher", "voucher");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/Projetos/ProFrotas/boleia/boleia-master/boleia-backend/src/main/resources/wsdl/jde/voucher.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        VOUCHER_WSDL_LOCATION = url;
        VOUCHER_EXCEPTION = e;
    }

    public Voucher_Service() {
        super(__getWsdlLocation(), VOUCHER_QNAME);
    }

    public Voucher_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), VOUCHER_QNAME, features);
    }

    public Voucher_Service(URL wsdlLocation) {
        super(wsdlLocation, VOUCHER_QNAME);
    }

    public Voucher_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, VOUCHER_QNAME, features);
    }

    public Voucher_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Voucher_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    
    @WebEndpoint(name = "voucherSoap")
    public VoucherSoap getVoucherSoap() {
        return super.getPort(new QName("http://ipiranga.com.br/voucher", "voucherSoap"), VoucherSoap.class);
    }

    
    @WebEndpoint(name = "voucherSoap")
    public VoucherSoap getVoucherSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://ipiranga.com.br/voucher", "voucherSoap"), VoucherSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (VOUCHER_EXCEPTION!= null) {
            throw VOUCHER_EXCEPTION;
        }
        return VOUCHER_WSDL_LOCATION;
    }

}
