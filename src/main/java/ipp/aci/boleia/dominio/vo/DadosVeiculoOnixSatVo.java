package ipp.aci.boleia.dominio.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Classe que encapsula resposta com os dados do veículo da OnixSat
 */
@XmlRootElement(name="Veiculo")
@XmlAccessorType(XmlAccessType.FIELD)
public class DadosVeiculoOnixSatVo implements Serializable {

    private static final long serialVersionUID = 680907265113006471L;

    @XmlElement(name = "veiID", required=true)
    private Integer veiID;

    @XmlElement(name = "placa", required=true)
    private String placa;


    /**
     * Construtor padrão
     */
    public DadosVeiculoOnixSatVo(){

    }

    /**
     * Construtor
     * @param veiID o ID do veículo na OnixSat
     * @param placa a placa do veículo
     */
    public DadosVeiculoOnixSatVo(Integer veiID, String placa){
        this.veiID = veiID;
        this.placa = placa;
    }

    public Integer getVeiID() {
        return veiID;
    }

    public void setVeiID(Integer veiID) {
        this.veiID = veiID;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
