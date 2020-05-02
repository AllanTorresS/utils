package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.TipoRastreador;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.vo.DadosTelemetriaOnixSatVo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

import static ipp.aci.boleia.util.UtilitarioFormatacao.obterDecimalMascara;

/**
 * Classe que representa os dados de telemetria de um veículo obtidos do 
 * Serviço de Telemetria
 * 
 */
@Entity
@Table(name = "TELEMETRIA_VEICULO")
public class TelemetriaVeiculo implements IPersistente {

    private static final long serialVersionUID = -4777420415545261674L;


    @Id
    @Column(name = "CD_TELEMETRIA_VEICULO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TELEMETRIA_VEICULO")
    @SequenceGenerator(name = "SEQ_TELEMETRIA_VEICULO", sequenceName = "SEQ_TELEMETRIA_VEICULO", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max = 7)
    @Column(name = "DS_PLACA_VEICULO")
    private String placa;

    @NotNull
    @Column(name = "DT_POSICAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPosicao;

    @NotNull
    @Column(name = "DT_PACOTE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPacote;

    @NotNull
    @Column(name = "QT_GRAU_LATIT")
    private BigDecimal latitude;

    @NotNull
    @Column(name = "QT_GRAU_LONGIT")
    private BigDecimal longitude;

    @Column(name = "QT_VELOCIDADE")
    private Integer velocidade;

    @Column(name = "NO_HODOMETRO")
    private Long hodometro;

    @Column(name = "NO_HORIMETRO")
    private Long horimetro;

    @Column(name = "ID_IGNICAO")
    private Integer statusIgnicao;

    @Column(name = "ID_SATELITE")
    private Integer statusSatelite;

    @Column(name = "ID_GPS")
    private Integer statusGps;

    @NotNull
    @Column(name = "ID_TIPO_RASTREADOR")
    private Integer tipoRastreador;

    @Column(name = "CD_REQUISICAO")
    private Long codigoRequisicao;


    /**
     * Converte os dados de telemetria do veículo retornados pelo serviço da OnixSat
     * para a entidade persistida
     * @param vo O VO com os dados de telemetria do veículo     *
     */
    public TelemetriaVeiculo(DadosTelemetriaOnixSatVo vo){
        this.setCodigoRequisicao(vo.getIdMensagem());
        this.setDataPacote(vo.getDataPacote());
        this.setDataPosicao(vo.getDataPosicao());
        this.setHodometro(vo.getHodometro());
        this.setLatitude(obterDecimalMascara(vo.getLatitude()));
        this.setLongitude(obterDecimalMascara(vo.getLongitude()));
        this.setPlaca(vo.getPlaca());
        this.setStatusIgnicao(vo.getIgnicao());
        this.setVelocidade(vo.getVelocidade());
        this.setTipoRastreador(TipoRastreador.ONIXSAT.getValue());
    }

    /**
     * Construtor padrão de Telemetria Veiculo
     */
    public TelemetriaVeiculo(){

    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return A placa do veículo sem o '-'
     */
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     *
     * @return A data de gravação da posição do veículo no servidor da Integradora.
     */
    public Date getDataPosicao() {
        return dataPosicao;
    }


    public void setDataPosicao(Date dataPosicao) {
        this.dataPosicao = dataPosicao;
    }

    /**
     *
     * @return A data o GPS no momento do envio da posição
     */
    public Date getDataPacote() {
        return dataPacote;
    }

    public void setDataPacote(Date dataPacote) {
        this.dataPacote = dataPacote;
    }

    /**
     *
     * @return A latitude em formato decimal.
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return A longitude em formato decimal
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return A velocidade obtida do GPS
     */
    public Integer getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(Integer velocidade) {
        this.velocidade = velocidade;
    }

    /**
     *
     * @return O hodometro do veículo.
     */

    public Long getHodometro() {
        return hodometro;
    }

    public void setHodometro(Long hodometro) {
        this.hodometro = hodometro;
    }

    /**
     *
     * @return O horímetro do veículo.
     */
    public Long getHorimetro() {
        return horimetro;
    }

    public void setHorimetro(Long horimetro) {
        this.horimetro = horimetro;
    }

    /**
     *
     * @return O status da ignição. 1 - ligada, 0 - Desligada
     */
    public Integer getStatusIgnicao() {
        return statusIgnicao;
    }

    public void setStatusIgnicao(Integer statusIgnicao) {
        this.statusIgnicao = statusIgnicao;
    }

    /**
     *
     * @return O status do satélite. 0 - GPRS, 1 - Satelital
     */
    public Integer getStatusSatelite() {
        return statusSatelite;
    }

    public void setStatusSatelite(Integer statusSatelite) {
        this.statusSatelite = statusSatelite;
    }

    /**
     *
     * @return O status do GPS. 0 - Inválido, 1 - Válido
     */
    public Integer getStatusGps() {
        return statusGps;
    }

    public void setStatusGps(Integer statusGps) {
        this.statusGps = statusGps;
    }

    /**
     *
     * @return O tipo do rastreador usado para capturar os dados de telemetria
     */
    public Integer getTipoRastreador() {
        return tipoRastreador;
    }

    public void setTipoRastreador(Integer tipoRastreador) {
        this.tipoRastreador = tipoRastreador;
    }

    /**
     *
     * @return O código identificador da requisição enviada para capturar os dados
     */
    public Long getCodigoRequisicao() {
        return codigoRequisicao;
    }

    public void setCodigoRequisicao(Long codigoRequisicao) {
        this.codigoRequisicao = codigoRequisicao;
    }
}