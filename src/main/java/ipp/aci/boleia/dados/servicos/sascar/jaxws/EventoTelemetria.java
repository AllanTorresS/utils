
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eventoTelemetria", propOrder = {
    "dataPosicao",
    "horimetro",
    "idEvento",
    "idMotorista",
    "idVeiculo",
    "latitude",
    "loginMotorista",
    "longitude",
    "nomeMotorista",
    "odometro",
    "tempoDuracao",
    "velocidadeMaximaEvento",
    "velocidadeReferencia"
})
public class EventoTelemetria {

    protected String dataPosicao;
    protected Integer horimetro;
    protected Integer idEvento;
    protected Integer idMotorista;
    protected Integer idVeiculo;
    protected Double latitude;
    protected Integer loginMotorista;
    protected Double longitude;
    protected String nomeMotorista;
    protected String odometro;
    protected Integer tempoDuracao;
    protected Integer velocidadeMaximaEvento;
    protected Integer velocidadeReferencia;

    
    public String getDataPosicao() {
        return dataPosicao;
    }

    
    public void setDataPosicao(String value) {
        this.dataPosicao = value;
    }

    
    public Integer getHorimetro() {
        return horimetro;
    }

    
    public void setHorimetro(Integer value) {
        this.horimetro = value;
    }

    
    public Integer getIdEvento() {
        return idEvento;
    }

    
    public void setIdEvento(Integer value) {
        this.idEvento = value;
    }

    
    public Integer getIdMotorista() {
        return idMotorista;
    }

    
    public void setIdMotorista(Integer value) {
        this.idMotorista = value;
    }

    
    public Integer getIdVeiculo() {
        return idVeiculo;
    }

    
    public void setIdVeiculo(Integer value) {
        this.idVeiculo = value;
    }

    
    public Double getLatitude() {
        return latitude;
    }

    
    public void setLatitude(Double value) {
        this.latitude = value;
    }

    
    public Integer getLoginMotorista() {
        return loginMotorista;
    }

    
    public void setLoginMotorista(Integer value) {
        this.loginMotorista = value;
    }

    
    public Double getLongitude() {
        return longitude;
    }

    
    public void setLongitude(Double value) {
        this.longitude = value;
    }

    
    public String getNomeMotorista() {
        return nomeMotorista;
    }

    
    public void setNomeMotorista(String value) {
        this.nomeMotorista = value;
    }

    
    public String getOdometro() {
        return odometro;
    }

    
    public void setOdometro(String value) {
        this.odometro = value;
    }

    
    public Integer getTempoDuracao() {
        return tempoDuracao;
    }

    
    public void setTempoDuracao(Integer value) {
        this.tempoDuracao = value;
    }

    
    public Integer getVelocidadeMaximaEvento() {
        return velocidadeMaximaEvento;
    }

    
    public void setVelocidadeMaximaEvento(Integer value) {
        this.velocidadeMaximaEvento = value;
    }

    
    public Integer getVelocidadeReferencia() {
        return velocidadeReferencia;
    }

    
    public void setVelocidadeReferencia(Integer value) {
        this.velocidadeReferencia = value;
    }

}
