
package ipp.aci.boleia.dados.servicos.ensemble.jde.vincularjurosboleto.jaxws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;



@WebService(name = "vincularJurosBoletoSoap", targetNamespace = "http://ipiranga.com.br/vincularJurosBoleto")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface VincularJurosBoletoSoap {


    
    @WebMethod(action = "http://ipiranga.com.br/vincularJurosBoleto/ws.vincularJurosBoleto.Service.vincular")
    @WebResult(name = "vincularResult", targetNamespace = "http://ipiranga.com.br/vincularJurosBoleto")
    @RequestWrapper(localName = "vincular", targetNamespace = "http://ipiranga.com.br/vincularJurosBoleto", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.vincularjurosboleto.jaxws.Vincular")
    @ResponseWrapper(localName = "vincularResponse", targetNamespace = "http://ipiranga.com.br/vincularJurosBoleto", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.vincularjurosboleto.jaxws.VincularResponse")
    public Response vincular(
        @WebParam(name = "pRequest", targetNamespace = "http://ipiranga.com.br/vincularJurosBoleto")
        Request pRequest);

}
