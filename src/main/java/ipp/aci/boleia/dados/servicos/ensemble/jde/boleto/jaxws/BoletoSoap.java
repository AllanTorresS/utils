
package ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


@WebService(name = "BoletoSoap", targetNamespace = "http://tempuri.org")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface BoletoSoap {


    
    @WebMethod(action = "http://tempuri.org/ws.boleto.Service.buscar")
    @WebResult(name = "buscarResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "buscar", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws.Buscar")
    @ResponseWrapper(localName = "buscarResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws.BuscarResponse")
    public BuscarResp buscar(
            @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
                    BuscarReq pRequest);


    @WebMethod(action = "http://tempuri.org/ws.boleto.Service.gerarPDF")
    @WebResult(name = "gerarPDFResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "gerarPDF", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws.GerarPDF")
    @ResponseWrapper(localName = "gerarPDFResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws.GerarPDFResponse")
    public GerarPDFResp gerarPDF(
            @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
                    GerarPDFReq pRequest);


    @WebMethod(action = "http://tempuri.org/ws.boleto.Service.recuperar")
    @WebResult(name = "recuperarResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "recuperar", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws.Recuperar")
    @ResponseWrapper(localName = "recuperarResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws.RecuperarResponse")
    public RecuperarBoletoResp recuperar(
            @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
                    RecuperarBoletoReq pRequest);

}
