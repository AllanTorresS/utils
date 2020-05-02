
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "caixaPreta", propOrder = {
    "dataEvento",
    "latitude",
    "longitude",
    "operador",
    "velocidade",
    "rpm",
    "ignicao",
    "freio",
    "limpador",
    "descricaoEvento"
})
public class CaixaPreta {

    protected String dataEvento;
    protected String latitude;
    protected String longitude;
    protected String operador;
    protected String velocidade;
    protected String rpm;
    protected String ignicao;
    protected String freio;
    protected String limpador;
    protected String descricaoEvento;

    
    public String getDataEvento() {
        return dataEvento;
    }

    
    public void setDataEvento(String value) {
        this.dataEvento = value;
    }

    
    public String getLatitude() {
        return latitude;
    }

    
    public void setLatitude(String value) {
        this.latitude = value;
    }

    
    public String getLongitude() {
        return longitude;
    }

    
    public void setLongitude(String value) {
        this.longitude = value;
    }

    
    public String getOperador() {
        return operador;
    }

    
    public void setOperador(String value) {
        this.operador = value;
    }

    
    public String getVelocidade() {
        return velocidade;
    }

    
    public void setVelocidade(String value) {
        this.velocidade = value;
    }

    
    public String getRpm() {
        return rpm;
    }

    
    public void setRpm(String value) {
        this.rpm = value;
    }

    
    public String getIgnicao() {
        return ignicao;
    }

    
    public void setIgnicao(String value) {
        this.ignicao = value;
    }

    
    public String getFreio() {
        return freio;
    }

    
    public void setFreio(String value) {
        this.freio = value;
    }

    
    public String getLimpador() {
        return limpador;
    }

    
    public void setLimpador(String value) {
        this.limpador = value;
    }

    
    public String getDescricaoEvento() {
        return descricaoEvento;
    }

    
    public void setDescricaoEvento(String value) {
        this.descricaoEvento = value;
    }

}
