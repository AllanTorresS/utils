
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pacoteLocalizacao", propOrder = {
    "dataPacote",
    "direcao",
    "gps",
    "idVeiculo",
    "ignicao",
    "latitude",
    "longitude",
    "velocidade"
})
public class PacoteLocalizacao {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPacote;
    protected Integer direcao;
    protected Integer gps;
    protected Integer idVeiculo;
    protected Integer ignicao;
    protected Double latitude;
    protected Double longitude;
    protected Integer velocidade;

    
    public XMLGregorianCalendar getDataPacote() {
        return dataPacote;
    }

    
    public void setDataPacote(XMLGregorianCalendar value) {
        this.dataPacote = value;
    }

    
    public Integer getDirecao() {
        return direcao;
    }

    
    public void setDirecao(Integer value) {
        this.direcao = value;
    }

    
    public Integer getGps() {
        return gps;
    }

    
    public void setGps(Integer value) {
        this.gps = value;
    }

    
    public Integer getIdVeiculo() {
        return idVeiculo;
    }

    
    public void setIdVeiculo(Integer value) {
        this.idVeiculo = value;
    }

    
    public Integer getIgnicao() {
        return ignicao;
    }

    
    public void setIgnicao(Integer value) {
        this.ignicao = value;
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

    
    public Integer getVelocidade() {
        return velocidade;
    }

    
    public void setVelocidade(Integer value) {
        this.velocidade = value;
    }

}
