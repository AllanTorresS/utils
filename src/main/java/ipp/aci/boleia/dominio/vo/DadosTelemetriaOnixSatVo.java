package ipp.aci.boleia.dominio.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * Classe que encapsula resposta com os dados de telemetria da OnixSat
 */
@XmlRootElement(name="MensagemCB")
@XmlAccessorType(XmlAccessType.FIELD)
public class DadosTelemetriaOnixSatVo implements Serializable {

    private static final long serialVersionUID = -8444222978921028093L;

    @XmlElement(name = "mId", required=true)
    private Long idMensagem;

    @XmlElement(name = "veiID", required=true)
    private Integer idVeiculo;

    private String placa;

    @XmlElement(name = "dt", required=true)
    private Date dataPosicao;

    @XmlElement(name = "dtInc", required=true)
    private Date dataPacote;

    @XmlElement(name = "lat", required=true)
    private String latitude;

    @XmlElement(name = "lon", required=true)
    private String longitude;

    @XmlElement(name = "vel")
    private Integer velocidade;

    @XmlElement(name = "evt4")
    private Integer ignicao;

    @XmlElement(name = "odm")
    private Long hodometro;

    /**
     * Construtor padrão
     */
    public DadosTelemetriaOnixSatVo(){

    }

    /**
     * Construtor
     *
     * @param idMensagem O código da mensagem retornada
     * @param idVeiculo O código do veículo na OnixSat
     * @param placa A placa do veículo
     * @param dataPosicao A data da posição (GPS) capturada
     * @param dataPacote A data em que o pacote foi registrado
     * @param latitude A coordenada latitude do veículo
     * @param longitude A coordenada longitude do veículo
     * @param velocidade A velocidade do veículo no momento da captura
     * @param ignicao Indica se a ignição do veículo estava acionada no momento da captura
     * @param hodometro O hodômetro do veículo
     */
    public DadosTelemetriaOnixSatVo(Long idMensagem, Integer idVeiculo, String placa, Date dataPosicao, Date dataPacote, String latitude, String longitude, Integer velocidade, Integer ignicao, Long hodometro) {
        this.idMensagem = idMensagem;
        this.idVeiculo = idVeiculo;
        this.placa = placa;
        this.dataPosicao = dataPosicao;
        this.dataPacote = dataPacote;
        this.latitude = latitude;
        this.longitude = longitude;
        this.velocidade = velocidade;
        this.ignicao = ignicao;
        this.hodometro = hodometro;
    }

    public Long getIdMensagem() {
        return idMensagem;
    }

    public void setIdMensagem(Long idMensagem) {
        this.idMensagem = idMensagem;
    }

    public Integer getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Integer idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Date getDataPosicao() {
        return dataPosicao;
    }

    public void setDataPosicao(Date dataPosicao) {
        this.dataPosicao = dataPosicao;
    }

    public Date getDataPacote() {
        return dataPacote;
    }

    public void setDataPacote(Date dataPacote) {
        this.dataPacote = dataPacote;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(Integer velocidade) {
        this.velocidade = velocidade;
    }

    public Integer getIgnicao() {
        return ignicao;
    }

    public void setIgnicao(Integer ignicao) {
        this.ignicao = ignicao;
    }

    public Long getHodometro() {
        return hodometro;
    }

    public void setHodometro(Long hodometro) {
        this.hodometro = hodometro;
    }
}
