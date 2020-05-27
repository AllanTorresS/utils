
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.ws.WebFault;



@WebFault(name = "Exception", targetNamespace = "http://webservice.web.integracao.sascar.com.br/")
public class Exception_Exception
    extends java.lang.Exception
{

    
    private ipp.aci.boleia.dados.servicos.sascar.jaxws.Exception faultInfo;

    
    public Exception_Exception(String message, ipp.aci.boleia.dados.servicos.sascar.jaxws.Exception faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    
    public Exception_Exception(String message, ipp.aci.boleia.dados.servicos.sascar.jaxws.Exception faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    
    public ipp.aci.boleia.dados.servicos.sascar.jaxws.Exception getFaultInfo() {
        return faultInfo;
    }

}
