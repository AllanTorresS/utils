
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cadastrarJDEReq", propOrder = {
    "somenteInformacoesTributarias",
    "codigoCliente",
    "cenario",
    "unidadeNegocio",
    "nomeCliente",
    "ramoAtividade",
    "observacao",
    "tipoContribuinte",
    "inscricaoEstadual",
    "isencaoImposto",
    "temRecebiveis",
    "pontoEntrega",
    "cpfCnpj",
    "codCatGerenciaVenda",
    "codCatCoordVenda",
    "codCatZonaVenda",
    "codCatGerenciaComercial",
    "codCatTipoNegocio",
    "codCatDistribuicaoLubes",
    "codCatLocalPagamento",
    "codCatGarantia",
    "codCatSegmentoMercadoria",
    "codCatMicromercado",
    "codCatPrevisaoLubes",
    "codCatTipoComponente",
    "codCatLimiteTolerancia",
    "codCatPerfilPosto",
    "codCatPropriedadeTerreno",
    "codCatLocalizacao",
    "codCatTipoImagem",
    "codCatEmpresa",
    "codCatBeneficioFiscal",
    "codCatAgenteEconomicoANP",
    "codCatGrupoSuspensao",
    "codCatSituacaoTributaria",
    "nomeCorrespondencia",
    "enderecoCompleto",
    "logradouro",
    "numero",
    "bairro",
    "cidade",
    "municipio",
    "estado",
    "cep",
    "pais",
    "codigoReservado",
    "dataReservada",
    "valorReservado",
    "numeroReservado",
    "textoReservado",
    "listaTelefone",
    "listaEmail",
    "companhiaCliente",
    "valorLimiteCredito",
    "mensagemCredito",
    "codUsuarioGerenteCredito",
    "mensagemCreditoTemporario",
    "codigoVendaABC",
    "codigoMargemABC",
    "codigoMediaDiasABC",
    "codigoReservadoCR",
    "dataReservadaCR",
    "valorReservadoCR",
    "numeroReservadoCR",
    "textoReservadoCR",
    "dataAberturaConta",
    "informacoesTributarias",
    "condicaoPagamento",
    "cadastroPai",
    "cadastroRelacionado1",
    "cadastroRelacionado2",
    "cadastroRelacionado3",
    "cadastroRelacionado4",
    "cadastroRelacionado5",
    "cadastroRelacionado6"
})
public class CadastrarJDEReq
    extends EnsRequest
{

    protected Boolean somenteInformacoesTributarias;
    protected Long codigoCliente;
    protected long cenario;
    @XmlElement(required = true)
    protected String unidadeNegocio;
    @XmlElement(required = true)
    protected String nomeCliente;
    protected String ramoAtividade;
    protected String observacao;
    @XmlElement(required = true)
    protected String tipoContribuinte;
    protected String inscricaoEstadual;
    protected String isencaoImposto;
    protected boolean temRecebiveis;
    @XmlElement(required = true)
    protected String pontoEntrega;
    @XmlElement(required = true)
    protected String cpfCnpj;
    protected String codCatGerenciaVenda;
    protected String codCatCoordVenda;
    protected String codCatZonaVenda;
    protected String codCatGerenciaComercial;
    protected String codCatTipoNegocio;
    protected String codCatDistribuicaoLubes;
    protected String codCatLocalPagamento;
    protected String codCatGarantia;
    protected String codCatSegmentoMercadoria;
    protected String codCatMicromercado;
    protected String codCatPrevisaoLubes;
    protected String codCatTipoComponente;
    protected String codCatLimiteTolerancia;
    protected String codCatPerfilPosto;
    protected String codCatPropriedadeTerreno;
    protected String codCatLocalizacao;
    protected String codCatTipoImagem;
    @XmlElement(required = true)
    protected String codCatEmpresa;
    protected String codCatBeneficioFiscal;
    protected String codCatAgenteEconomicoANP;
    protected String codCatGrupoSuspensao;
    protected String codCatSituacaoTributaria;
    @XmlElement(required = true)
    protected String nomeCorrespondencia;
    protected String enderecoCompleto;
    @XmlElement(required = true)
    protected String logradouro;
    @XmlElement(required = true)
    protected String numero;
    @XmlElement(required = true)
    protected String bairro;
    @XmlElement(required = true)
    protected String cidade;
    @XmlElement(required = true)
    protected String municipio;
    @XmlElement(required = true)
    protected String estado;
    @XmlElement(required = true)
    protected String cep;
    @XmlElement(required = true)
    protected String pais;
    protected String codigoReservado;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataReservada;
    protected BigDecimal valorReservado;
    protected Long numeroReservado;
    protected String textoReservado;
    protected ArrayOftelefonePairOflistaTelefoneKeytelefone listaTelefone;
    protected ArrayOfemailPairOflistaEmailKeyemail listaEmail;
    protected String companhiaCliente;
    protected Long valorLimiteCredito;
    protected String mensagemCredito;
    protected String codUsuarioGerenteCredito;
    protected String mensagemCreditoTemporario;
    protected String codigoVendaABC;
    protected String codigoMargemABC;
    protected String codigoMediaDiasABC;
    protected String codigoReservadoCR;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataReservadaCR;
    protected BigDecimal valorReservadoCR;
    protected Long numeroReservadoCR;
    protected String textoReservadoCR;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataAberturaConta;
    protected InformacoesTributarias informacoesTributarias;
    protected String condicaoPagamento;
    protected Long cadastroPai;
    protected Long cadastroRelacionado1;
    protected Long cadastroRelacionado2;
    protected Long cadastroRelacionado3;
    protected Long cadastroRelacionado4;
    protected Long cadastroRelacionado5;
    protected Long cadastroRelacionado6;

    
    public Boolean isSomenteInformacoesTributarias() {
        return somenteInformacoesTributarias;
    }

    
    public void setSomenteInformacoesTributarias(Boolean value) {
        this.somenteInformacoesTributarias = value;
    }

    
    public Long getCodigoCliente() {
        return codigoCliente;
    }

    
    public void setCodigoCliente(Long value) {
        this.codigoCliente = value;
    }

    
    public long getCenario() {
        return cenario;
    }

    
    public void setCenario(long value) {
        this.cenario = value;
    }

    
    public String getUnidadeNegocio() {
        return unidadeNegocio;
    }

    
    public void setUnidadeNegocio(String value) {
        this.unidadeNegocio = value;
    }

    
    public String getNomeCliente() {
        return nomeCliente;
    }

    
    public void setNomeCliente(String value) {
        this.nomeCliente = value;
    }

    
    public String getRamoAtividade() {
        return ramoAtividade;
    }

    
    public void setRamoAtividade(String value) {
        this.ramoAtividade = value;
    }

    
    public String getObservacao() {
        return observacao;
    }

    
    public void setObservacao(String value) {
        this.observacao = value;
    }

    
    public String getTipoContribuinte() {
        return tipoContribuinte;
    }

    
    public void setTipoContribuinte(String value) {
        this.tipoContribuinte = value;
    }

    
    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    
    public void setInscricaoEstadual(String value) {
        this.inscricaoEstadual = value;
    }

    
    public String getIsencaoImposto() {
        return isencaoImposto;
    }

    
    public void setIsencaoImposto(String value) {
        this.isencaoImposto = value;
    }

    
    public boolean isTemRecebiveis() {
        return temRecebiveis;
    }

    
    public void setTemRecebiveis(boolean value) {
        this.temRecebiveis = value;
    }

    
    public String getPontoEntrega() {
        return pontoEntrega;
    }

    
    public void setPontoEntrega(String value) {
        this.pontoEntrega = value;
    }

    
    public String getCpfCnpj() {
        return cpfCnpj;
    }

    
    public void setCpfCnpj(String value) {
        this.cpfCnpj = value;
    }

    
    public String getCodCatGerenciaVenda() {
        return codCatGerenciaVenda;
    }

    
    public void setCodCatGerenciaVenda(String value) {
        this.codCatGerenciaVenda = value;
    }

    
    public String getCodCatCoordVenda() {
        return codCatCoordVenda;
    }

    
    public void setCodCatCoordVenda(String value) {
        this.codCatCoordVenda = value;
    }

    
    public String getCodCatZonaVenda() {
        return codCatZonaVenda;
    }

    
    public void setCodCatZonaVenda(String value) {
        this.codCatZonaVenda = value;
    }

    
    public String getCodCatGerenciaComercial() {
        return codCatGerenciaComercial;
    }

    
    public void setCodCatGerenciaComercial(String value) {
        this.codCatGerenciaComercial = value;
    }

    
    public String getCodCatTipoNegocio() {
        return codCatTipoNegocio;
    }

    
    public void setCodCatTipoNegocio(String value) {
        this.codCatTipoNegocio = value;
    }

    
    public String getCodCatDistribuicaoLubes() {
        return codCatDistribuicaoLubes;
    }

    
    public void setCodCatDistribuicaoLubes(String value) {
        this.codCatDistribuicaoLubes = value;
    }

    
    public String getCodCatLocalPagamento() {
        return codCatLocalPagamento;
    }

    
    public void setCodCatLocalPagamento(String value) {
        this.codCatLocalPagamento = value;
    }

    
    public String getCodCatGarantia() {
        return codCatGarantia;
    }

    
    public void setCodCatGarantia(String value) {
        this.codCatGarantia = value;
    }

    
    public String getCodCatSegmentoMercadoria() {
        return codCatSegmentoMercadoria;
    }

    
    public void setCodCatSegmentoMercadoria(String value) {
        this.codCatSegmentoMercadoria = value;
    }

    
    public String getCodCatMicromercado() {
        return codCatMicromercado;
    }

    
    public void setCodCatMicromercado(String value) {
        this.codCatMicromercado = value;
    }

    
    public String getCodCatPrevisaoLubes() {
        return codCatPrevisaoLubes;
    }

    
    public void setCodCatPrevisaoLubes(String value) {
        this.codCatPrevisaoLubes = value;
    }

    
    public String getCodCatTipoComponente() {
        return codCatTipoComponente;
    }

    
    public void setCodCatTipoComponente(String value) {
        this.codCatTipoComponente = value;
    }

    
    public String getCodCatLimiteTolerancia() {
        return codCatLimiteTolerancia;
    }

    
    public void setCodCatLimiteTolerancia(String value) {
        this.codCatLimiteTolerancia = value;
    }

    
    public String getCodCatPerfilPosto() {
        return codCatPerfilPosto;
    }

    
    public void setCodCatPerfilPosto(String value) {
        this.codCatPerfilPosto = value;
    }

    
    public String getCodCatPropriedadeTerreno() {
        return codCatPropriedadeTerreno;
    }

    
    public void setCodCatPropriedadeTerreno(String value) {
        this.codCatPropriedadeTerreno = value;
    }

    
    public String getCodCatLocalizacao() {
        return codCatLocalizacao;
    }

    
    public void setCodCatLocalizacao(String value) {
        this.codCatLocalizacao = value;
    }

    
    public String getCodCatTipoImagem() {
        return codCatTipoImagem;
    }

    
    public void setCodCatTipoImagem(String value) {
        this.codCatTipoImagem = value;
    }

    
    public String getCodCatEmpresa() {
        return codCatEmpresa;
    }

    
    public void setCodCatEmpresa(String value) {
        this.codCatEmpresa = value;
    }

    
    public String getCodCatBeneficioFiscal() {
        return codCatBeneficioFiscal;
    }

    
    public void setCodCatBeneficioFiscal(String value) {
        this.codCatBeneficioFiscal = value;
    }

    
    public String getCodCatAgenteEconomicoANP() {
        return codCatAgenteEconomicoANP;
    }

    
    public void setCodCatAgenteEconomicoANP(String value) {
        this.codCatAgenteEconomicoANP = value;
    }

    
    public String getCodCatGrupoSuspensao() {
        return codCatGrupoSuspensao;
    }

    
    public void setCodCatGrupoSuspensao(String value) {
        this.codCatGrupoSuspensao = value;
    }

    
    public String getCodCatSituacaoTributaria() {
        return codCatSituacaoTributaria;
    }

    
    public void setCodCatSituacaoTributaria(String value) {
        this.codCatSituacaoTributaria = value;
    }

    
    public String getNomeCorrespondencia() {
        return nomeCorrespondencia;
    }

    
    public void setNomeCorrespondencia(String value) {
        this.nomeCorrespondencia = value;
    }

    
    public String getEnderecoCompleto() {
        return enderecoCompleto;
    }

    
    public void setEnderecoCompleto(String value) {
        this.enderecoCompleto = value;
    }

    
    public String getLogradouro() {
        return logradouro;
    }

    
    public void setLogradouro(String value) {
        this.logradouro = value;
    }

    
    public String getNumero() {
        return numero;
    }

    
    public void setNumero(String value) {
        this.numero = value;
    }

    
    public String getBairro() {
        return bairro;
    }

    
    public void setBairro(String value) {
        this.bairro = value;
    }

    
    public String getCidade() {
        return cidade;
    }

    
    public void setCidade(String value) {
        this.cidade = value;
    }

    
    public String getMunicipio() {
        return municipio;
    }

    
    public void setMunicipio(String value) {
        this.municipio = value;
    }

    
    public String getEstado() {
        return estado;
    }

    
    public void setEstado(String value) {
        this.estado = value;
    }

    
    public String getCep() {
        return cep;
    }

    
    public void setCep(String value) {
        this.cep = value;
    }

    
    public String getPais() {
        return pais;
    }

    
    public void setPais(String value) {
        this.pais = value;
    }

    
    public String getCodigoReservado() {
        return codigoReservado;
    }

    
    public void setCodigoReservado(String value) {
        this.codigoReservado = value;
    }

    
    public XMLGregorianCalendar getDataReservada() {
        return dataReservada;
    }

    
    public void setDataReservada(XMLGregorianCalendar value) {
        this.dataReservada = value;
    }

    
    public BigDecimal getValorReservado() {
        return valorReservado;
    }

    
    public void setValorReservado(BigDecimal value) {
        this.valorReservado = value;
    }

    
    public Long getNumeroReservado() {
        return numeroReservado;
    }

    
    public void setNumeroReservado(Long value) {
        this.numeroReservado = value;
    }

    
    public String getTextoReservado() {
        return textoReservado;
    }

    
    public void setTextoReservado(String value) {
        this.textoReservado = value;
    }

    
    public ArrayOftelefonePairOflistaTelefoneKeytelefone getListaTelefone() {
        return listaTelefone;
    }

    
    public void setListaTelefone(ArrayOftelefonePairOflistaTelefoneKeytelefone value) {
        this.listaTelefone = value;
    }

    
    public ArrayOfemailPairOflistaEmailKeyemail getListaEmail() {
        return listaEmail;
    }

    
    public void setListaEmail(ArrayOfemailPairOflistaEmailKeyemail value) {
        this.listaEmail = value;
    }

    
    public String getCompanhiaCliente() {
        return companhiaCliente;
    }

    
    public void setCompanhiaCliente(String value) {
        this.companhiaCliente = value;
    }

    
    public Long getValorLimiteCredito() {
        return valorLimiteCredito;
    }

    
    public void setValorLimiteCredito(Long value) {
        this.valorLimiteCredito = value;
    }

    
    public String getMensagemCredito() {
        return mensagemCredito;
    }

    
    public void setMensagemCredito(String value) {
        this.mensagemCredito = value;
    }

    
    public String getCodUsuarioGerenteCredito() {
        return codUsuarioGerenteCredito;
    }

    
    public void setCodUsuarioGerenteCredito(String value) {
        this.codUsuarioGerenteCredito = value;
    }

    
    public String getMensagemCreditoTemporario() {
        return mensagemCreditoTemporario;
    }

    
    public void setMensagemCreditoTemporario(String value) {
        this.mensagemCreditoTemporario = value;
    }

    
    public String getCodigoVendaABC() {
        return codigoVendaABC;
    }

    
    public void setCodigoVendaABC(String value) {
        this.codigoVendaABC = value;
    }

    
    public String getCodigoMargemABC() {
        return codigoMargemABC;
    }

    
    public void setCodigoMargemABC(String value) {
        this.codigoMargemABC = value;
    }

    
    public String getCodigoMediaDiasABC() {
        return codigoMediaDiasABC;
    }

    
    public void setCodigoMediaDiasABC(String value) {
        this.codigoMediaDiasABC = value;
    }

    
    public String getCodigoReservadoCR() {
        return codigoReservadoCR;
    }

    
    public void setCodigoReservadoCR(String value) {
        this.codigoReservadoCR = value;
    }

    
    public XMLGregorianCalendar getDataReservadaCR() {
        return dataReservadaCR;
    }

    
    public void setDataReservadaCR(XMLGregorianCalendar value) {
        this.dataReservadaCR = value;
    }

    
    public BigDecimal getValorReservadoCR() {
        return valorReservadoCR;
    }

    
    public void setValorReservadoCR(BigDecimal value) {
        this.valorReservadoCR = value;
    }

    
    public Long getNumeroReservadoCR() {
        return numeroReservadoCR;
    }

    
    public void setNumeroReservadoCR(Long value) {
        this.numeroReservadoCR = value;
    }

    
    public String getTextoReservadoCR() {
        return textoReservadoCR;
    }

    
    public void setTextoReservadoCR(String value) {
        this.textoReservadoCR = value;
    }

    
    public XMLGregorianCalendar getDataAberturaConta() {
        return dataAberturaConta;
    }

    
    public void setDataAberturaConta(XMLGregorianCalendar value) {
        this.dataAberturaConta = value;
    }

    
    public InformacoesTributarias getInformacoesTributarias() {
        return informacoesTributarias;
    }

    
    public void setInformacoesTributarias(InformacoesTributarias value) {
        this.informacoesTributarias = value;
    }

    
    public String getCondicaoPagamento() {
        return condicaoPagamento;
    }

    
    public void setCondicaoPagamento(String value) {
        this.condicaoPagamento = value;
    }

    
    public Long getCadastroPai() {
        return cadastroPai;
    }

    
    public void setCadastroPai(Long value) {
        this.cadastroPai = value;
    }

    
    public Long getCadastroRelacionado1() {
        return cadastroRelacionado1;
    }

    
    public void setCadastroRelacionado1(Long value) {
        this.cadastroRelacionado1 = value;
    }

    
    public Long getCadastroRelacionado2() {
        return cadastroRelacionado2;
    }

    
    public void setCadastroRelacionado2(Long value) {
        this.cadastroRelacionado2 = value;
    }

    
    public Long getCadastroRelacionado3() {
        return cadastroRelacionado3;
    }

    
    public void setCadastroRelacionado3(Long value) {
        this.cadastroRelacionado3 = value;
    }

    
    public Long getCadastroRelacionado4() {
        return cadastroRelacionado4;
    }

    
    public void setCadastroRelacionado4(Long value) {
        this.cadastroRelacionado4 = value;
    }

    
    public Long getCadastroRelacionado5() {
        return cadastroRelacionado5;
    }

    
    public void setCadastroRelacionado5(Long value) {
        this.cadastroRelacionado5 = value;
    }

    
    public Long getCadastroRelacionado6() {
        return cadastroRelacionado6;
    }

    
    public void setCadastroRelacionado6(Long value) {
        this.cadastroRelacionado6 = value;
    }

}
