
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;



@WebService(name = "pessoaSoap", targetNamespace = "http://tempuri.org")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface PessoaSoap {


    
    @WebMethod(action = "http://tempuri.org/cbpi.bs.pessoa.Service.atualizarClienteJDE")
    @WebResult(name = "atualizarClienteJDEResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "atualizarClienteJDE", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.AtualizarClienteJDE")
    @ResponseWrapper(localName = "atualizarClienteJDEResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.AtualizarClienteJDEResponse")
    public Response atualizarClienteJDE(
        @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
        AtualizarJDEReq pRequest);

    
    @WebMethod(action = "http://tempuri.org/cbpi.bs.pessoa.Service.cadastrarClienteJDE")
    @WebResult(name = "cadastrarClienteJDEResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "cadastrarClienteJDE", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.CadastrarClienteJDE")
    @ResponseWrapper(localName = "cadastrarClienteJDEResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.CadastrarClienteJDEResponse")
    public CadastrarJDEResp cadastrarClienteJDE(
        @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
        CadastrarJDEReq pRequest);

    
    @WebMethod(action = "http://tempuri.org/cbpi.bs.pessoa.Service.consultarCadastroGeralJDE")
    @WebResult(name = "consultarCadastroGeralJDEResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "consultarCadastroGeralJDE", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.ConsultarCadastroGeralJDE")
    @ResponseWrapper(localName = "consultarCadastroGeralJDEResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.ConsultarCadastroGeralJDEResponse")
    public ConsultarCadastroGeralJDEResp consultarCadastroGeralJDE(
        @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
        ConsultarCadastroGeralJDEReq pRequest);

    
    @WebMethod(action = "http://tempuri.org/cbpi.bs.pessoa.Service.consultarPessoaJuridica")
    @WebResult(name = "consultarPessoaJuridicaResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "consultarPessoaJuridica", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.ConsultarPessoaJuridica")
    @ResponseWrapper(localName = "consultarPessoaJuridicaResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.ConsultarPessoaJuridicaResponse")
    public ConsultarPessoaJuridicaeResp consultarPessoaJuridica(
        @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
        ConsultarPessoaJuridicaReq pRequest);

    
    @WebMethod(action = "http://tempuri.org/cbpi.bs.pessoa.Service.inativarClienteJDE")
    @WebResult(name = "inativarClienteJDEResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "inativarClienteJDE", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.InativarClienteJDE")
    @ResponseWrapper(localName = "inativarClienteJDEResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.InativarClienteJDEResponse")
    public Response inativarClienteJDE(
        @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
        InativarJDEReq pRequest);

    
    @WebMethod(action = "http://tempuri.org/cbpi.bs.pessoa.Service.listarEstadoCivil")
    @WebResult(name = "listarEstadoCivilResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "listarEstadoCivil", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.ListarEstadoCivil")
    @ResponseWrapper(localName = "listarEstadoCivilResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.ListarEstadoCivilResponse")
    public ListarEstadoCivilResp listarEstadoCivil(
        @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
        ListarEstadoCivilReq pRequest);

    
    @WebMethod(action = "http://tempuri.org/cbpi.bs.pessoa.Service.listarOrgEmisDocumentosPF")
    @WebResult(name = "listarOrgEmisDocumentosPFResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "listarOrgEmisDocumentosPF", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.ListarOrgEmisDocumentosPF")
    @ResponseWrapper(localName = "listarOrgEmisDocumentosPFResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.ListarOrgEmisDocumentosPFResponse")
    public ListarOrgEmisDocumentosPFResp listarOrgEmisDocumentosPF(
        @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
        ListarOrgEmisDocumentosPFReq pRequest);

    
    @WebMethod(action = "http://tempuri.org/cbpi.bs.pessoa.Service.listarOrgEmisPorDocumentoPF")
    @WebResult(name = "listarOrgEmisPorDocumentoPFResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "listarOrgEmisPorDocumentoPF", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.ListarOrgEmisPorDocumentoPF")
    @ResponseWrapper(localName = "listarOrgEmisPorDocumentoPFResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.ListarOrgEmisPorDocumentoPFResponse")
    public ListarOrgEmisPorDocPFResp listarOrgEmisPorDocumentoPF(
        @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
        ListarOrgEmisPorDocPFReq pRequest);

    
    @WebMethod(action = "http://tempuri.org/cbpi.bs.pessoa.Service.listarTipoDocumentoPF")
    @WebResult(name = "listarTipoDocumentoPFResult", targetNamespace = "http://tempuri.org")
    @RequestWrapper(localName = "listarTipoDocumentoPF", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.ListarTipoDocumentoPF")
    @ResponseWrapper(localName = "listarTipoDocumentoPFResponse", targetNamespace = "http://tempuri.org", className = "ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.ListarTipoDocumentoPFResponse")
    public ListarTipoDocumentoPFResp listarTipoDocumentoPF(
        @WebParam(name = "pRequest", targetNamespace = "http://tempuri.org")
        ListarTipoDocumentoPFReq pRequest);

}
