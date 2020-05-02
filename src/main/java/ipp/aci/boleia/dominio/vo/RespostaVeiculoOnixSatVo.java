package ipp.aci.boleia.dominio.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;


/**
 * Classe que encapsula resposta da ONIXSAT
 */

@XmlRootElement(name = "ResponseVeiculo")
@XmlAccessorType(XmlAccessType.FIELD)
public class RespostaVeiculoOnixSatVo implements Serializable {

    private static final long serialVersionUID = 8358586412571356361L;

    @XmlElement(name = "Veiculo", required = true)
    private List<DadosVeiculoOnixSatVo> veiculo;

    /**
     * Construtor padrão
     */
    public RespostaVeiculoOnixSatVo() {

    }

    /**
     * Construtor
     * @param veiculo os dados do veículo
     */
    public RespostaVeiculoOnixSatVo(List<DadosVeiculoOnixSatVo> veiculo) {
        this.veiculo = veiculo;
    }

    public List<DadosVeiculoOnixSatVo> getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(List<DadosVeiculoOnixSatVo> veiculo) {
        this.veiculo = veiculo;
    }
}
