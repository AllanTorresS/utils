
package ipp.aci.boleia.dados.servicos.ensemble.jde.vinculocreditoboleto.jaxws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


@WebService(name = "vinculoCreditoBoletoSoap", targetNamespace = "http://ipiranga.com.br/vinculoCreditoBoleto")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface VinculoCreditoBoletoSoap {


    
    @WebMethod(action = "http://ipiranga.com.br/vinculoCreditoBoleto/ws.vinculoCreditoBoleto.Service.vincular")
    @WebResult(name = "vincularResult", targetNamespace = "http://ipiranga.com.br/vinculoCreditoBoleto")
    @RequestWrapper(localName = "vincular", targetNamespace = "http://ipiranga.com.br/vinculoCreditoBoleto", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.vinculocreditoboleto.jaxws.Vincular")
    @ResponseWrapper(localName = "vincularResponse", targetNamespace = "http://ipiranga.com.br/vinculoCreditoBoleto", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.vinculocreditoboleto.jaxws.VincularResponse")
    public Response vincular(
            @WebParam(name = "pRequest", targetNamespace = "http://ipiranga.com.br/vinculoCreditoBoleto")
                    Request pRequest);

}
