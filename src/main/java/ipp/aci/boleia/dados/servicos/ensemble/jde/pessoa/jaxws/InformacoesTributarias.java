
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "informacoesTributarias", propOrder = {
    "inscricaoMunicipal",
    "classeContribuinte",
    "repasseICMS",
    "indicadorICMS",
    "indicadorISS",
    "indicadorZonaFranca",
    "identificadorComplementar",
    "identidadePF",
    "orgaoExpedidorPF",
    "ufExpedidora",
    "dataExpedicao",
    "inscISSProfAutonomo",
    "cidadeProfAutonomo",
    "ufProfAutonomo",
    "listaImposto",
    "numRegistroINSS",
    "substituicaoICMS",
    "codigoApuracaoIPI",
    "cpf"
})
public class InformacoesTributarias {

    protected String inscricaoMunicipal;
    protected String classeContribuinte;
    protected boolean repasseICMS;
    protected String indicadorICMS;
    protected String indicadorISS;
    protected String indicadorZonaFranca;
    protected String identificadorComplementar;
    protected String identidadePF;
    protected String orgaoExpedidorPF;
    protected String ufExpedidora;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataExpedicao;
    protected String inscISSProfAutonomo;
    protected String cidadeProfAutonomo;
    protected String ufProfAutonomo;
    protected ArrayOfimpostoPairOflistaImpostoKeyimposto listaImposto;
    protected String numRegistroINSS;
    protected String substituicaoICMS;
    protected String codigoApuracaoIPI;
    protected String cpf;

    
    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    
    public void setInscricaoMunicipal(String value) {
        this.inscricaoMunicipal = value;
    }

    
    public String getClasseContribuinte() {
        return classeContribuinte;
    }

    
    public void setClasseContribuinte(String value) {
        this.classeContribuinte = value;
    }

    
    public boolean isRepasseICMS() {
        return repasseICMS;
    }

    
    public void setRepasseICMS(boolean value) {
        this.repasseICMS = value;
    }

    
    public String getIndicadorICMS() {
        return indicadorICMS;
    }

    
    public void setIndicadorICMS(String value) {
        this.indicadorICMS = value;
    }

    
    public String getIndicadorISS() {
        return indicadorISS;
    }

    
    public void setIndicadorISS(String value) {
        this.indicadorISS = value;
    }

    
    public String getIndicadorZonaFranca() {
        return indicadorZonaFranca;
    }

    
    public void setIndicadorZonaFranca(String value) {
        this.indicadorZonaFranca = value;
    }

    
    public String getIdentificadorComplementar() {
        return identificadorComplementar;
    }

    
    public void setIdentificadorComplementar(String value) {
        this.identificadorComplementar = value;
    }

    
    public String getIdentidadePF() {
        return identidadePF;
    }

    
    public void setIdentidadePF(String value) {
        this.identidadePF = value;
    }

    
    public String getOrgaoExpedidorPF() {
        return orgaoExpedidorPF;
    }

    
    public void setOrgaoExpedidorPF(String value) {
        this.orgaoExpedidorPF = value;
    }

    
    public String getUfExpedidora() {
        return ufExpedidora;
    }

    
    public void setUfExpedidora(String value) {
        this.ufExpedidora = value;
    }

    
    public XMLGregorianCalendar getDataExpedicao() {
        return dataExpedicao;
    }

    
    public void setDataExpedicao(XMLGregorianCalendar value) {
        this.dataExpedicao = value;
    }

    
    public String getInscISSProfAutonomo() {
        return inscISSProfAutonomo;
    }

    
    public void setInscISSProfAutonomo(String value) {
        this.inscISSProfAutonomo = value;
    }

    
    public String getCidadeProfAutonomo() {
        return cidadeProfAutonomo;
    }

    
    public void setCidadeProfAutonomo(String value) {
        this.cidadeProfAutonomo = value;
    }

    
    public String getUfProfAutonomo() {
        return ufProfAutonomo;
    }

    
    public void setUfProfAutonomo(String value) {
        this.ufProfAutonomo = value;
    }

    
    public ArrayOfimpostoPairOflistaImpostoKeyimposto getListaImposto() {
        return listaImposto;
    }

    
    public void setListaImposto(ArrayOfimpostoPairOflistaImpostoKeyimposto value) {
        this.listaImposto = value;
    }

    
    public String getNumRegistroINSS() {
        return numRegistroINSS;
    }

    
    public void setNumRegistroINSS(String value) {
        this.numRegistroINSS = value;
    }

    
    public String getSubstituicaoICMS() {
        return substituicaoICMS;
    }

    
    public void setSubstituicaoICMS(String value) {
        this.substituicaoICMS = value;
    }

    
    public String getCodigoApuracaoIPI() {
        return codigoApuracaoIPI;
    }

    
    public void setCodigoApuracaoIPI(String value) {
        this.codigoApuracaoIPI = value;
    }

    
    public String getCpf() {
        return cpf;
    }

    
    public void setCpf(String value) {
        this.cpf = value;
    }

}
