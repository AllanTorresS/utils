
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


@WebService(name = "voucherContasPagarSoap", targetNamespace = "http://tempuri.org")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface VoucherContasPagarSoap {


    
    @WebMethod(action = "http://tempuri.org/cbpi.bs.voucherContasPagar.Service.consultar")
    @WebResult(name = "consultarResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "consultar", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws.Consultar")
    @ResponseWrapper(localName = "consultarResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws.ConsultarResponse")
    public ConsultarResp consultar(
            @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
                    ConsultarReq pRequest);


    @WebMethod(action = "http://tempuri.org/cbpi.bs.voucherContasPagar.Service.criarEmLote")
    @WebResult(name = "criarEmLoteResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "criarEmLote", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws.CriarEmLote")
    @ResponseWrapper(localName = "criarEmLoteResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws.CriarEmLoteResponse")
    public CriarEmLoteResp criarEmLote(
            @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
                    CriarEmLoteReq pRequest);


    @WebMethod(action = "http://tempuri.org/cbpi.bs.voucherContasPagar.Service.liberar")
    @WebResult(name = "liberarResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "liberar", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws.Liberar")
    @ResponseWrapper(localName = "liberarResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws.LiberarResponse")
    public LiberarResp liberar(
            @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
                    LiberarReq pRequest);

}
