
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;



@WebService(name = "FaturaSoap", targetNamespace = "http://tempuri.org")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface FaturaSoap {


    
    @WebMethod(action = "http://tempuri.org/cbpi.bs.fatura.autenticado.Service.consultarJDE")
    @WebResult(name = "consultarJDEResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "consultarJDE", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.ConsultarJDE")
    @ResponseWrapper(localName = "consultarJDEResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.ConsultarJDEResponse")
    public ConsultarJDEResp consultarJDE(
        @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
        ConsultarJDEReq pRequest);

    
    @WebMethod(action = "http://tempuri.org/cbpi.bs.fatura.autenticado.Service.demostrativoAbatimentoCreditoComb")
    @WebResult(name = "demostrativoAbatimentoCreditoCombResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "demostrativoAbatimentoCreditoComb", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.DemostrativoAbatimentoCreditoComb")
    @ResponseWrapper(localName = "demostrativoAbatimentoCreditoCombResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.DemostrativoAbatimentoCreditoCombResponse")
    public DemostrativoAbatimentoCreditoCombResp demostrativoAbatimentoCreditoComb(
        @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
        DemostrativoAbatimentoCreditoCombReq pRequest);

    
    @WebMethod(action = "http://tempuri.org/cbpi.bs.fatura.autenticado.Service.gerarJDE")
    @WebResult(name = "gerarJDEResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "gerarJDE", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.GerarJDE")
    @ResponseWrapper(localName = "gerarJDEResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.GerarJDEResponse")
    public GerarJDEResp gerarJDE(
        @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
        GerarJDEReq pRequest);

    
    @WebMethod(action = "http://tempuri.org/cbpi.bs.fatura.autenticado.Service.incluirJDE")
    @WebResult(name = "incluirJDEResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "incluirJDE", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.IncluirJDE")
    @ResponseWrapper(localName = "incluirJDEResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.IncluirJDEResponse")
    public IncluirJDEResp incluirJDE(
        @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
        IncluirJDEReq pRequest);

    
    @WebMethod(action = "http://tempuri.org/cbpi.bs.fatura.autenticado.Service.inserirEmLoteJDE")
    @WebResult(name = "inserirEmLoteJDEResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "inserirEmLoteJDE", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.InserirEmLoteJDE")
    @ResponseWrapper(localName = "inserirEmLoteJDEResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.InserirEmLoteJDEResponse")
    public InserirEmLoteJDEResp inserirEmLoteJDE(
        @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
        InserirEmLoteJDEReq pRequest);

    
    @WebMethod(action = "http://tempuri.org/cbpi.bs.fatura.autenticado.Service.marcarTituloASerBaixado")
    @WebResult(name = "marcarTituloASerBaixadoResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "marcarTituloASerBaixado", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.MarcarTituloASerBaixado")
    @ResponseWrapper(localName = "marcarTituloASerBaixadoResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.MarcarTituloASerBaixadoResponse")
    public MarcarTituloASerBaixadoResp marcarTituloASerBaixado(
        @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
        MarcarTituloASerBaixadoReq pRequest);

    
    @WebMethod(action = "http://tempuri.org/cbpi.bs.fatura.autenticado.Service.prorrogarVencimento")
    @WebResult(name = "prorrogarVencimentoResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "prorrogarVencimento", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.ProrrogarVencimento")
    @ResponseWrapper(localName = "prorrogarVencimentoResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.ProrrogarVencimentoResponse")
    public ProrrogarVencimentoResp prorrogarVencimento(
        @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
        ProrrogarVencimentoReq pRequest);

    
    @WebMethod(action = "http://tempuri.org/cbpi.bs.fatura.autenticado.Service.verificarTituloPago")
    @WebResult(name = "verificarTituloPagoResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "verificarTituloPago", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.VerificarTituloPago")
    @ResponseWrapper(localName = "verificarTituloPagoResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.VerificarTituloPagoResponse")
    public VerificarTituloPagoResp verificarTituloPago(
        @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
        VerificarTituloPagoReq pRequest);

}
