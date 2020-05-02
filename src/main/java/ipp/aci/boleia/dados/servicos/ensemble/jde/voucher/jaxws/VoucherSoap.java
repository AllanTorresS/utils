
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


@WebService(name = "voucherSoap", targetNamespace = "http://ipiranga.com.br/voucher")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface VoucherSoap {


    
    @WebMethod(action = "http://ipiranga.com.br/voucher/ws.voucher.Service.buscar")
    @WebResult(name = "buscarResult", targetNamespace = "http://ipiranga.com.br/voucher")
    @RequestWrapper(localName = "buscar", targetNamespace = "http://ipiranga.com.br/voucher", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws.Buscar")
    @ResponseWrapper(localName = "buscarResponse", targetNamespace = "http://ipiranga.com.br/voucher", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws.BuscarResponse")
    public BuscarResp buscar(
            @WebParam(name = "pRequest", targetNamespace = "http://ipiranga.com.br/voucher")
                    BuscarReq pRequest);


    @WebMethod(action = "http://ipiranga.com.br/voucher/ws.voucher.Service.criar")
    @WebResult(name = "criarResult", targetNamespace = "http://ipiranga.com.br/voucher")
    @RequestWrapper(localName = "criar", targetNamespace = "http://ipiranga.com.br/voucher", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws.Criar")
    @ResponseWrapper(localName = "criarResponse", targetNamespace = "http://ipiranga.com.br/voucher", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws.CriarResponse")
    public CriarResp criar(
            @WebParam(name = "pRequest", targetNamespace = "http://ipiranga.com.br/voucher")
                    CriarReq pRequest);

}
