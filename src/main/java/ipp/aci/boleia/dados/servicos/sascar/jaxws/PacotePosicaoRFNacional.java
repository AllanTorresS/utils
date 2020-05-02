
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pacotePosicaoRFNacional", propOrder = {
    "ccid",
    "cidade",
    "dataPacote",
    "dataPosicao",
    "idPacote",
    "idVeiculo",
    "integradoraId",
    "jamming",
    "latitude",
    "longitude",
    "rf",
    "rua",
    "uf"
})
public class PacotePosicaoRFNacional {

    protected String ccid;
    protected String cidade;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPacote;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPosicao;
    protected long idPacote;
    protected Integer idVeiculo;
    protected Integer integradoraId;
    protected Integer jamming;
    protected Double latitude;
    protected Double longitude;
    protected Long rf;
    protected String rua;
    protected String uf;

    
    public String getCcid() {
        return ccid;
    }

    
    public void setCcid(String value) {
        this.ccid = value;
    }

    
    public String getCidade() {
        return cidade;
    }

    
    public void setCidade(String value) {
        this.cidade = value;
    }

    
    public XMLGregorianCalendar getDataPacote() {
        return dataPacote;
    }

    
    public void setDataPacote(XMLGregorianCalendar value) {
        this.dataPacote = value;
    }

    
    public XMLGregorianCalendar getDataPosicao() {
        return dataPosicao;
    }

    
    public void setDataPosicao(XMLGregorianCalendar value) {
        this.dataPosicao = value;
    }

    
    public long getIdPacote() {
        return idPacote;
    }

    
    public void setIdPacote(long value) {
        this.idPacote = value;
    }

    
    public Integer getIdVeiculo() {
        return idVeiculo;
    }

    
    public void setIdVeiculo(Integer value) {
        this.idVeiculo = value;
    }

    
    public Integer getIntegradoraId() {
        return integradoraId;
    }

    
    public void setIntegradoraId(Integer value) {
        this.integradoraId = value;
    }

    
    public Integer getJamming() {
        return jamming;
    }

    
    public void setJamming(Integer value) {
        this.jamming = value;
    }

    
    public Double getLatitude() {
        return latitude;
    }

    
    public void setLatitude(Double value) {
        this.latitude = value;
    }

    
    public Double getLongitude() {
        return longitude;
    }

    
    public void setLongitude(Double value) {
        this.longitude = value;
    }

    
    public Long getRf() {
        return rf;
    }

    
    public void setRf(Long value) {
        this.rf = value;
    }

    
    public String getRua() {
        return rua;
    }

    
    public void setRua(String value) {
        this.rua = value;
    }

    
    public String getUf() {
        return uf;
    }

    
    public void setUf(String value) {
        this.uf = value;
    }

}
