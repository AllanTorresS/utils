package ipp.aci.boleia.dominio.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;


/**
 * Classe que encapsula resposta de telemetria da OnixSat
 */

@XmlRootElement(name = "ResponseMensagemCB")
@XmlAccessorType(XmlAccessType.FIELD)
public class RespostaTelemetriaOnixSatVo implements Serializable {

    private static final long serialVersionUID = -1251141369569422849L;

    @XmlElement(name = "MensagemCB", required = true)
    private List<DadosTelemetriaOnixSatVo> dadosTelemetria;

    /**
     * Construtor padrão
     */
    public RespostaTelemetriaOnixSatVo() {

    }

    /**
     * Construtor
     * @param dadosTelemetria os dados de telemetria capturados
     */
    public RespostaTelemetriaOnixSatVo(List<DadosTelemetriaOnixSatVo> dadosTelemetria) {
        this.dadosTelemetria = dadosTelemetria;
    }

    public List<DadosTelemetriaOnixSatVo> getDadosTelemetria() {
        return dadosTelemetria;
    }

    public void setDadosTelemetria(List<DadosTelemetriaOnixSatVo> dadosTelemetria) {
        this.dadosTelemetria = dadosTelemetria;
    }
}
