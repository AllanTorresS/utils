
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.ws.WebFault;



@WebFault(name = "SasIntegraFault", targetNamespace = "http://webservice.web.integracao.sascar.com.br/")
public class SasIntegraNotification
    extends java.lang.Exception
{

    
    private SasIntegraFaultBean faultInfo;

    
    public SasIntegraNotification(String message, SasIntegraFaultBean faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    
    public SasIntegraNotification(String message, SasIntegraFaultBean faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    
    public SasIntegraFaultBean getFaultInfo() {
        return faultInfo;
    }

}
