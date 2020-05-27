
package ipp.aci.boleia.dados.servicos.ensemble.jde.contabancaria.jaxws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;



@WebService(name = "contaBancariaSoap", targetNamespace = "http://ipiranga.com.br/dadosContaBancaria")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ContaBancariaSoap {


    
    @WebMethod(action = "http://ipiranga.com.br/dadosContaBancaria/ws.retornoDadosContaBancaria.Service.dadosContaBancaria")
    @WebResult(name = "dadosContaBancariaResult", targetNamespace = "http://ipiranga.com.br/dadosContaBancaria")
    @RequestWrapper(localName = "dadosContaBancaria", targetNamespace = "http://ipiranga.com.br/dadosContaBancaria", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.contabancaria.jaxws.DadosContaBancaria")
    @ResponseWrapper(localName = "dadosContaBancariaResponse", targetNamespace = "http://ipiranga.com.br/dadosContaBancaria", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.contabancaria.jaxws.DadosContaBancariaResponse")
    public DadosContaBancariaResp dadosContaBancaria(
        @WebParam(name = "pRequest", targetNamespace = "http://ipiranga.com.br/dadosContaBancaria")
        DadosContaBancariaReq pRequest);

}
