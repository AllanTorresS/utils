
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;



@WebService(name = "SasIntegraWS", targetNamespace = "http://webservice.web.integracao.sascar.com.br/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SasIntegraWS {


    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "atualizarSenha", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.AtualizarSenha")
    @ResponseWrapper(localName = "atualizarSenhaResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.AtualizarSenhaResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/atualizarSenhaRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/atualizarSenhaResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/atualizarSenha/Fault/SasIntegraNotification")
    })
    public String atualizarSenha(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senhaAtual", targetNamespace = "")
        String senhaAtual,
        @WebParam(name = "novaSenha", targetNamespace = "")
        String novaSenha)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterClientes", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterClientes")
    @ResponseWrapper(localName = "obterClientesResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterClientesResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterClientesRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterClientesResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterClientes/Fault/SasIntegraNotification")
    })
    public List<Cliente> obterClientes(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "quantidade", targetNamespace = "")
        Integer quantidade,
        @WebParam(name = "idCliente", targetNamespace = "")
        Integer idCliente)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterMotoristas", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterMotoristas")
    @ResponseWrapper(localName = "obterMotoristasResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterMotoristasResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMotoristasRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMotoristasResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMotoristas/Fault/SasIntegraNotification")
    })
    public List<Motorista> obterMotoristas(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "quantidade", targetNamespace = "")
        Integer quantidade,
        @WebParam(name = "idMotorista", targetNamespace = "")
        Integer idMotorista)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterMotoristasVeiculos", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterMotoristasVeiculos")
    @ResponseWrapper(localName = "obterMotoristasVeiculosResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterMotoristasVeiculosResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMotoristasVeiculosRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMotoristasVeiculosResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMotoristasVeiculos/Fault/SasIntegraNotification")
    })
    public List<MotoristaVeiculo> obterMotoristasVeiculos(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "quantidade", targetNamespace = "")
        Integer quantidade,
        @WebParam(name = "idMotoristaVeiculo", targetNamespace = "")
        Long idMotoristaVeiculo)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterLayoutTecladoVeiculos", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterLayoutTecladoVeiculos")
    @ResponseWrapper(localName = "obterLayoutTecladoVeiculosResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterLayoutTecladoVeiculosResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterLayoutTecladoVeiculosRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterLayoutTecladoVeiculosResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterLayoutTecladoVeiculos/Fault/SasIntegraNotification")
    })
    public List<LayoutTecladoVeiculos> obterLayoutTecladoVeiculos(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterLayoutGrupoPontos", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterLayoutGrupoPontos")
    @ResponseWrapper(localName = "obterLayoutGrupoPontosResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterLayoutGrupoPontosResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterLayoutGrupoPontosRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterLayoutGrupoPontosResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterLayoutGrupoPontos/Fault/SasIntegraNotification")
    })
    public List<LayoutGrupoPontos> obterLayoutGrupoPontos(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterGrupoAtuadores", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterGrupoAtuadores")
    @ResponseWrapper(localName = "obterGrupoAtuadoresResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterGrupoAtuadoresResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterGrupoAtuadoresRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterGrupoAtuadoresResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterGrupoAtuadores/Fault/SasIntegraNotification")
    })
    public List<GrupoAtuador> obterGrupoAtuadores(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterStatusComando", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterStatusComando")
    @ResponseWrapper(localName = "obterStatusComandoResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterStatusComandoResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterStatusComandoRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterStatusComandoResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterStatusComando/Fault/SasIntegraNotification")
    })
    public List<StatusComando> obterStatusComando(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "ticket", targetNamespace = "")
        Integer ticket)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterStatusComandoTicketSascar", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterStatusComandoTicketSascar")
    @ResponseWrapper(localName = "obterStatusComandoTicketSascarResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterStatusComandoTicketSascarResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterStatusComandoTicketSascarRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterStatusComandoTicketSascarResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterStatusComandoTicketSascar/Fault/SasIntegraNotification")
    })
    public List<StatusComando> obterStatusComandoTicketSascar(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "ticket", targetNamespace = "")
        Integer ticket)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterTipoComando", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterTipoComando")
    @ResponseWrapper(localName = "obterTipoComandoResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterTipoComandoResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterTipoComandoRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterTipoComandoResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterTipoComando/Fault/SasIntegraNotification")
    })
    public List<TipoComando> obterTipoComando(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterPacotePosicoes", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicoes")
    @ResponseWrapper(localName = "obterPacotePosicoesResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicoesResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicoesRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicoesResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicoes/Fault/SasIntegraNotification")
    })
    public List<PacotePosicao> obterPacotePosicoes(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "quantidade", targetNamespace = "")
        Integer quantidade)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterPacotePosicoesMotorista", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicoesMotorista")
    @ResponseWrapper(localName = "obterPacotePosicoesMotoristaResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicoesMotoristaResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicoesMotoristaRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicoesMotoristaResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicoesMotorista/Fault/SasIntegraNotification")
    })
    public List<PacotePosicao> obterPacotePosicoesMotorista(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "quantidade", targetNamespace = "")
        Integer quantidade)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterPacotePosicoesRestricao", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicoesRestricao")
    @ResponseWrapper(localName = "obterPacotePosicoesRestricaoResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicoesRestricaoResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicoesRestricaoRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicoesRestricaoResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicoesRestricao/Fault/SasIntegraNotification")
    })
    public List<PacotePosicao> obterPacotePosicoesRestricao(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "quantidade", targetNamespace = "")
        Integer quantidade,
        @WebParam(name = "idVeiculo", targetNamespace = "")
        Integer idVeiculo)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterPacotePosicoesMotoristaRestricao", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicoesMotoristaRestricao")
    @ResponseWrapper(localName = "obterPacotePosicoesMotoristaRestricaoResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicoesMotoristaRestricaoResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicoesMotoristaRestricaoRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicoesMotoristaRestricaoResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicoesMotoristaRestricao/Fault/SasIntegraNotification")
    })
    public List<PacotePosicao> obterPacotePosicoesMotoristaRestricao(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "quantidade", targetNamespace = "")
        Integer quantidade,
        @WebParam(name = "idVeiculo", targetNamespace = "")
        Integer idVeiculo)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterPacotePosicoesRFNacional", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicoesRFNacional")
    @ResponseWrapper(localName = "obterPacotePosicoesRFNacionalResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicoesRFNacionalResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicoesRFNacionalRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicoesRFNacionalResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicoesRFNacional/Fault/SasIntegraNotification")
    })
    public List<PacotePosicaoRFNacional> obterPacotePosicoesRFNacional(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "quantidade", targetNamespace = "")
        Integer quantidade)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterPacotePosicoesJSON", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicoesJSON")
    @ResponseWrapper(localName = "obterPacotePosicoesJSONResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicoesJSONResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicoesJSONRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicoesJSONResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicoesJSON/Fault/SasIntegraNotification")
    })
    public List<String> obterPacotePosicoesJSON(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "quantidade", targetNamespace = "")
        Integer quantidade)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterPacotePosicoesMotoristaJSON", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicoesMotoristaJSON")
    @ResponseWrapper(localName = "obterPacotePosicoesMotoristaJSONResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicoesMotoristaJSONResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicoesMotoristaJSONRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicoesMotoristaJSONResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicoesMotoristaJSON/Fault/SasIntegraNotification")
    })
    public List<String> obterPacotePosicoesMotoristaJSON(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "quantidade", targetNamespace = "")
        Integer quantidade)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterPacotePosicaoPorRangeJSON", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicaoPorRangeJSON")
    @ResponseWrapper(localName = "obterPacotePosicaoPorRangeJSONResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicaoPorRangeJSONResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicaoPorRangeJSONRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicaoPorRangeJSONResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicaoPorRangeJSON/Fault/SasIntegraNotification")
    })
    public List<String> obterPacotePosicaoPorRangeJSON(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "idInicio", targetNamespace = "")
        Long idInicio,
        @WebParam(name = "idFinal", targetNamespace = "")
        Long idFinal,
        @WebParam(name = "quantidade", targetNamespace = "")
        Integer quantidade)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterPacotePosicaoMotoristaPorRangeJSON", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicaoMotoristaPorRangeJSON")
    @ResponseWrapper(localName = "obterPacotePosicaoMotoristaPorRangeJSONResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicaoMotoristaPorRangeJSONResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicaoMotoristaPorRangeJSONRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicaoMotoristaPorRangeJSONResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicaoMotoristaPorRangeJSON/Fault/SasIntegraNotification")
    })
    public List<String> obterPacotePosicaoMotoristaPorRangeJSON(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "idInicio", targetNamespace = "")
        Long idInicio,
        @WebParam(name = "idFinal", targetNamespace = "")
        Long idFinal,
        @WebParam(name = "quantidade", targetNamespace = "")
        Integer quantidade)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterPacotePosicaoPorRange", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicaoPorRange")
    @ResponseWrapper(localName = "obterPacotePosicaoPorRangeResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicaoPorRangeResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicaoPorRangeRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicaoPorRangeResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicaoPorRange/Fault/SasIntegraNotification")
    })
    public List<PacotePosicao> obterPacotePosicaoPorRange(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "idInicio", targetNamespace = "")
        Long idInicio,
        @WebParam(name = "idFinal", targetNamespace = "")
        Long idFinal,
        @WebParam(name = "quantidade", targetNamespace = "")
        Integer quantidade)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterPacotePosicaoMotoristaPorRange", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicaoMotoristaPorRange")
    @ResponseWrapper(localName = "obterPacotePosicaoMotoristaPorRangeResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicaoMotoristaPorRangeResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicaoMotoristaPorRangeRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicaoMotoristaPorRangeResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicaoMotoristaPorRange/Fault/SasIntegraNotification")
    })
    public List<PacotePosicao> obterPacotePosicaoMotoristaPorRange(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "idInicio", targetNamespace = "")
        Long idInicio,
        @WebParam(name = "idFinal", targetNamespace = "")
        Long idFinal,
        @WebParam(name = "quantidade", targetNamespace = "")
        Integer quantidade)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterPacotePosicaoHistorico", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicaoHistorico")
    @ResponseWrapper(localName = "obterPacotePosicaoHistoricoResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicaoHistoricoResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicaoHistoricoRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicaoHistoricoResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicaoHistorico/Fault/SasIntegraNotification")
    })
    public List<PacotePosicao> obterPacotePosicaoHistorico(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "dataInicio", targetNamespace = "")
        String dataInicio,
        @WebParam(name = "dataFinal", targetNamespace = "")
        String dataFinal,
        @WebParam(name = "idVeiculo", targetNamespace = "")
        Integer idVeiculo)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterPacotePosicaoMotoristaHistorico", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicaoMotoristaHistorico")
    @ResponseWrapper(localName = "obterPacotePosicaoMotoristaHistoricoResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacotePosicaoMotoristaHistoricoResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicaoMotoristaHistoricoRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicaoMotoristaHistoricoResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacotePosicaoMotoristaHistorico/Fault/SasIntegraNotification")
    })
    public List<PacotePosicao> obterPacotePosicaoMotoristaHistorico(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "dataInicio", targetNamespace = "")
        String dataInicio,
        @WebParam(name = "dataFinal", targetNamespace = "")
        String dataFinal,
        @WebParam(name = "idVeiculo", targetNamespace = "")
        Integer idVeiculo)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterPacoteLocalizacao", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacoteLocalizacao")
    @ResponseWrapper(localName = "obterPacoteLocalizacaoResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPacoteLocalizacaoResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacoteLocalizacaoRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacoteLocalizacaoResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPacoteLocalizacao/Fault/SasIntegraNotification")
    })
    public List<PacoteLocalizacao> obterPacoteLocalizacao(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "quantidade", targetNamespace = "")
        Integer quantidade)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterMacroTd50Tmcd", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterMacroTd50Tmcd")
    @ResponseWrapper(localName = "obterMacroTd50TmcdResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterMacroTd50TmcdResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMacroTd50TmcdRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMacroTd50TmcdResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMacroTd50Tmcd/Fault/SasIntegraNotification")
    })
    public List<MacroTd50Tmcd> obterMacroTd50Tmcd(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "tipoTeclado", targetNamespace = "")
        TipoTeclado tipoTeclado)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterMacroTd50TmcdDetalhado", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterMacroTd50TmcdDetalhado")
    @ResponseWrapper(localName = "obterMacroTd50TmcdDetalhadoResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterMacroTd50TmcdDetalhadoResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMacroTd50TmcdDetalhadoRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMacroTd50TmcdDetalhadoResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMacroTd50TmcdDetalhado/Fault/SasIntegraNotification")
    })
    public List<MacroTd50TmcdDetalhado> obterMacroTd50TmcdDetalhado(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "tipoTeclado", targetNamespace = "")
        TipoTeclado tipoTeclado)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterMacroTd40", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterMacroTd40")
    @ResponseWrapper(localName = "obterMacroTd40Response", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterMacroTd40Response")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMacroTd40Request", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMacroTd40Response", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMacroTd40/Fault/SasIntegraNotification")
    })
    public List<MacroTd40> obterMacroTd40(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "satelital", targetNamespace = "")
        boolean satelital)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterMacroTms3", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterMacroTms3")
    @ResponseWrapper(localName = "obterMacroTms3Response", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterMacroTms3Response")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMacroTms3Request", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMacroTms3Response", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMacroTms3/Fault/SasIntegraNotification")
    })
    public List<MacroTms3> obterMacroTms3(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterMascaraDispositivos", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterMascaraDispositivos")
    @ResponseWrapper(localName = "obterMascaraDispositivosResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterMascaraDispositivosResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMascaraDispositivosRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMascaraDispositivosResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMascaraDispositivos/Fault/SasIntegraNotification")
    })
    public List<MascaraDispositivo> obterMascaraDispositivos(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "idVeiculo", targetNamespace = "")
        Integer idVeiculo)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterLayout", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterLayout")
    @ResponseWrapper(localName = "obterLayoutResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterLayoutResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterLayoutRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterLayoutResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterLayout/Fault/SasIntegraNotification")
    })
    public List<Layout> obterLayout(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "layout", targetNamespace = "")
        TipoLayout layout)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterLayoutDetalhado", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterLayoutDetalhado")
    @ResponseWrapper(localName = "obterLayoutDetalhadoResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterLayoutDetalhadoResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterLayoutDetalhadoRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterLayoutDetalhadoResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterLayoutDetalhado/Fault/SasIntegraNotification")
    })
    public List<LayoutDetalhado> obterLayoutDetalhado(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "layout", targetNamespace = "")
        TipoLayout layout,
        @WebParam(name = "idLayout", targetNamespace = "")
        Integer idLayout,
        @WebParam(name = "dataReferencia", targetNamespace = "")
        String dataReferencia)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterVeiculos", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterVeiculos")
    @ResponseWrapper(localName = "obterVeiculosResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterVeiculosResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterVeiculosRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterVeiculosResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterVeiculos/Fault/SasIntegraNotification")
    })
    public List<Veiculo> obterVeiculos(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "quantidade", targetNamespace = "")
        Integer quantidade,
        @WebParam(name = "idVeiculo", targetNamespace = "")
        Integer idVeiculo)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterPontosReferencia", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPontosReferencia")
    @ResponseWrapper(localName = "obterPontosReferenciaResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterPontosReferenciaResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPontosReferenciaRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPontosReferenciaResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterPontosReferencia/Fault/SasIntegraNotification")
    })
    public List<PontoReferencia> obterPontosReferencia(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterSequenciamentoEvento", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterSequenciamentoEvento")
    @ResponseWrapper(localName = "obterSequenciamentoEventoResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterSequenciamentoEventoResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterSequenciamentoEventoRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterSequenciamentoEventoResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterSequenciamentoEvento/Fault/SasIntegraNotification")
    })
    public List<SequenciamentoEvento> obterSequenciamentoEvento(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterMensagemPortal", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterMensagemPortal")
    @ResponseWrapper(localName = "obterMensagemPortalResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterMensagemPortalResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMensagemPortalRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMensagemPortalResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterMensagemPortal/Fault/SasIntegraNotification")
    })
    public Mensagem obterMensagemPortal(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "idVeiculo", targetNamespace = "")
        Integer idVeiculo)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterTelemetriaPortal", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterTelemetriaPortal")
    @ResponseWrapper(localName = "obterTelemetriaPortalResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterTelemetriaPortalResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterTelemetriaPortalRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterTelemetriaPortalResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterTelemetriaPortal/Fault/SasIntegraNotification")
    })
    public Telemetria obterTelemetriaPortal(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "idVeiculo", targetNamespace = "")
        Integer idVeiculo)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterEnderecoPosicao", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterEnderecoPosicao")
    @ResponseWrapper(localName = "obterEnderecoPosicaoResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterEnderecoPosicaoResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterEnderecoPosicaoRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterEnderecoPosicaoResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterEnderecoPosicao/Fault/SasIntegraNotification")
    })
    public EnderecoPosicao obterEnderecoPosicao(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "latitude", targetNamespace = "")
        String latitude,
        @WebParam(name = "longitude", targetNamespace = "")
        String longitude)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "verificarVeiculoIntegrado", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.VerificarVeiculoIntegrado")
    @ResponseWrapper(localName = "verificarVeiculoIntegradoResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.VerificarVeiculoIntegradoResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/verificarVeiculoIntegradoRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/verificarVeiculoIntegradoResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/verificarVeiculoIntegrado/Fault/SasIntegraNotification")
    })
    public boolean verificarVeiculoIntegrado(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "idVeiculo", targetNamespace = "")
        Integer idVeiculo)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterLayoutAcaoEmbarcadaAVD", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterLayoutAcaoEmbarcadaAVD")
    @ResponseWrapper(localName = "obterLayoutAcaoEmbarcadaAVDResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterLayoutAcaoEmbarcadaAVDResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterLayoutAcaoEmbarcadaAVDRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterLayoutAcaoEmbarcadaAVDResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterLayoutAcaoEmbarcadaAVD/Fault/SasIntegraNotification")
    })
    public List<LayoutAcaoEmbarcadaAVD> obterLayoutAcaoEmbarcadaAVD(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "comandoEmbarquePontoDiario", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ComandoEmbarquePontoDiario")
    @ResponseWrapper(localName = "comandoEmbarquePontoDiarioResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ComandoEmbarquePontoDiarioResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/comandoEmbarquePontoDiarioRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/comandoEmbarquePontoDiarioResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/comandoEmbarquePontoDiario/Fault/SasIntegraNotification")
    })
    public LogComando comandoEmbarquePontoDiario(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "idVeiculo", targetNamespace = "")
        Integer idVeiculo,
        @WebParam(name = "pontosRef", targetNamespace = "")
        String pontosRef)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterEventoTelemetriaDescricao", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterEventoTelemetriaDescricao")
    @ResponseWrapper(localName = "obterEventoTelemetriaDescricaoResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterEventoTelemetriaDescricaoResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterEventoTelemetriaDescricaoRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterEventoTelemetriaDescricaoResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterEventoTelemetriaDescricao/Fault/SasIntegraNotification")
    })
    public List<TipoEventoTelemetriaDescricao> obterEventoTelemetriaDescricao(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterEventoTelemetriaIntegracao", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterEventoTelemetriaIntegracao")
    @ResponseWrapper(localName = "obterEventoTelemetriaIntegracaoResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterEventoTelemetriaIntegracaoResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterEventoTelemetriaIntegracaoRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterEventoTelemetriaIntegracaoResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterEventoTelemetriaIntegracao/Fault/SasIntegraNotification")
    })
    public List<EventoTelemetria> obterEventoTelemetriaIntegracao(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "dataInicio", targetNamespace = "")
        String dataInicio,
        @WebParam(name = "dataFinal", targetNamespace = "")
        String dataFinal,
        @WebParam(name = "idVeiculo", targetNamespace = "")
        Integer idVeiculo,
        @WebParam(name = "idEventoList", targetNamespace = "")
        List<Integer> idEventoList)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterDeltaTelemetriaIntegracao", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterDeltaTelemetriaIntegracao")
    @ResponseWrapper(localName = "obterDeltaTelemetriaIntegracaoResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterDeltaTelemetriaIntegracaoResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterDeltaTelemetriaIntegracaoRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterDeltaTelemetriaIntegracaoResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterDeltaTelemetriaIntegracao/Fault/SasIntegraNotification")
    })
    public List<DeltaTelemetria> obterDeltaTelemetriaIntegracao(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "dataInicio", targetNamespace = "")
        String dataInicio,
        @WebParam(name = "dataFinal", targetNamespace = "")
        String dataFinal,
        @WebParam(name = "idVeiculo", targetNamespace = "")
        Integer idVeiculo)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "enviarParametrizacaoTelemetria", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.EnviarParametrizacaoTelemetria")
    @ResponseWrapper(localName = "enviarParametrizacaoTelemetriaResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.EnviarParametrizacaoTelemetriaResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/enviarParametrizacaoTelemetriaRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/enviarParametrizacaoTelemetriaResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/enviarParametrizacaoTelemetria/Fault/SasIntegraNotification")
    })
    public List<ComandoTelemetriaRetorno> enviarParametrizacaoTelemetria(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "idVeiculo", targetNamespace = "")
        Integer idVeiculo,
        @WebParam(name = "telemetriaParametrizacao", targetNamespace = "")
        TelemetriaConfiguracao telemetriaParametrizacao)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterDadosAdicionais", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterDadosAdicionais")
    @ResponseWrapper(localName = "obterDadosAdicionaisResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.ObterDadosAdicionaisResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterDadosAdicionaisRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterDadosAdicionaisResponse", fault = {
        @FaultAction(className = SasIntegraNotification.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/obterDadosAdicionais/Fault/SasIntegraNotification")
    })
    public List<DadosAdicionais> obterDadosAdicionais(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "idVeiculo", targetNamespace = "")
        Integer idVeiculo)
        throws SasIntegraNotification
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "solicitarEventosCaixaPreta", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.SolicitarEventosCaixaPreta")
    @ResponseWrapper(localName = "solicitarEventosCaixaPretaResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.SolicitarEventosCaixaPretaResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/solicitarEventosCaixaPretaRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/solicitarEventosCaixaPretaResponse", fault = {
        @FaultAction(className = Exception_Exception.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/solicitarEventosCaixaPreta/Fault/Exception")
    })
    public String solicitarEventosCaixaPreta(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "idVeiculo", targetNamespace = "")
        String idVeiculo,
        @WebParam(name = "placa", targetNamespace = "")
        String placa,
        @WebParam(name = "dataPosicaoInicial", targetNamespace = "")
        String dataPosicaoInicial,
        @WebParam(name = "dataPosicaoFinal", targetNamespace = "")
        String dataPosicaoFinal,
        @WebParam(name = "ticket", targetNamespace = "")
        String ticket)
        throws Exception_Exception
    ;

    
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "recuperarEventosCaixaPreta", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.RecuperarEventosCaixaPreta")
    @ResponseWrapper(localName = "recuperarEventosCaixaPretaResponse", targetNamespace = "http://webservice.web.integracao.sascar.com.br/", className = "ipp.aci.boleia.dados.servicos.sascar.jaxws.RecuperarEventosCaixaPretaResponse")
    @Action(input = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/recuperarEventosCaixaPretaRequest", output = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/recuperarEventosCaixaPretaResponse", fault = {
        @FaultAction(className = Exception_Exception.class, value = "http://webservice.web.integracao.sascar.com.br/SasIntegraWS/recuperarEventosCaixaPreta/Fault/Exception")
    })
    public CaixaPretaList recuperarEventosCaixaPreta(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "placa", targetNamespace = "")
        String placa,
        @WebParam(name = "idVeiculo", targetNamespace = "")
        String idVeiculo,
        @WebParam(name = "dataPosicao", targetNamespace = "")
        String dataPosicao)
        throws Exception_Exception
    ;

}
