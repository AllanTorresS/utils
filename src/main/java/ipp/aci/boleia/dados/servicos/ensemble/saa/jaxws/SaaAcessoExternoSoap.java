
package ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


@WebService(name = "saaAcessoExternoSoap", targetNamespace = "http://tempuri.org")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SaaAcessoExternoSoap {


    
    @WebMethod(action = "http://tempuri.org/cbpi.bs.saa.acessoExterno.Service.autenticacaoUsuario")
    @WebResult(name = "autenticacaoUsuarioResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "autenticacaoUsuario", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws.AutenticacaoUsuario")
    @ResponseWrapper(localName = "autenticacaoUsuarioResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws.AutenticacaoUsuarioResponse")
    public AutenticacaoUsuarioResp autenticacaoUsuario(
            @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
                    AutenticacaoUsuarioReq pRequest);


    @WebMethod(action = "http://tempuri.org/cbpi.bs.saa.acessoExterno.Service.autorizacaoUsuario")
    @WebResult(name = "autorizacaoUsuarioResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "autorizacaoUsuario", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws.AutorizacaoUsuario")
    @ResponseWrapper(localName = "autorizacaoUsuarioResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws.AutorizacaoUsuarioResponse")
    public AutorizacaoResp autorizacaoUsuario(
            @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
                    AutorizacaoReq pRequest);

}
