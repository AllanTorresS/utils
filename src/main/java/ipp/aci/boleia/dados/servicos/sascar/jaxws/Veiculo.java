
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "veiculo", propOrder = {
    "esn",
    "idAtuador1",
    "idAtuador2",
    "idAtuador3",
    "idAtuador4",
    "idAtuador5",
    "idAtuador6",
    "idAtuador7",
    "idAtuador8",
    "idCliente",
    "idContratoCliente",
    "idEquipamento",
    "idLayout",
    "idProjeto",
    "idSensor1",
    "idSensor2",
    "idSensor3",
    "idSensor4",
    "idSensor5",
    "idSensor6",
    "idSensor7",
    "idSensor8",
    "idSerial0",
    "idSerial1",
    "idVeiculo",
    "placa",
    "portaBloqueio",
    "portaPanico",
    "satelital",
    "telemetria"
})
public class Veiculo {

    protected String esn;
    protected Integer idAtuador1;
    protected Integer idAtuador2;
    protected Integer idAtuador3;
    protected Integer idAtuador4;
    protected Integer idAtuador5;
    protected Integer idAtuador6;
    protected Integer idAtuador7;
    protected Integer idAtuador8;
    protected Integer idCliente;
    protected Integer idContratoCliente;
    protected Long idEquipamento;
    protected Integer idLayout;
    protected Integer idProjeto;
    protected Integer idSensor1;
    protected Integer idSensor2;
    protected Integer idSensor3;
    protected Integer idSensor4;
    protected Integer idSensor5;
    protected Integer idSensor6;
    protected Integer idSensor7;
    protected Integer idSensor8;
    protected Integer idSerial0;
    protected Integer idSerial1;
    protected Integer idVeiculo;
    protected String placa;
    protected Integer portaBloqueio;
    protected Integer portaPanico;
    protected boolean satelital;
    protected boolean telemetria;
    @XmlAttribute(name = "descricao")
    protected String descricao;
    @XmlAttribute(name = "idEquipamentoDesc")
    protected String idEquipamentoDesc;

    
    public String getEsn() {
        return esn;
    }

    
    public void setEsn(String value) {
        this.esn = value;
    }

    
    public Integer getIdAtuador1() {
        return idAtuador1;
    }

    
    public void setIdAtuador1(Integer value) {
        this.idAtuador1 = value;
    }

    
    public Integer getIdAtuador2() {
        return idAtuador2;
    }

    
    public void setIdAtuador2(Integer value) {
        this.idAtuador2 = value;
    }

    
    public Integer getIdAtuador3() {
        return idAtuador3;
    }

    
    public void setIdAtuador3(Integer value) {
        this.idAtuador3 = value;
    }

    
    public Integer getIdAtuador4() {
        return idAtuador4;
    }

    
    public void setIdAtuador4(Integer value) {
        this.idAtuador4 = value;
    }

    
    public Integer getIdAtuador5() {
        return idAtuador5;
    }

    
    public void setIdAtuador5(Integer value) {
        this.idAtuador5 = value;
    }

    
    public Integer getIdAtuador6() {
        return idAtuador6;
    }

    
    public void setIdAtuador6(Integer value) {
        this.idAtuador6 = value;
    }

    
    public Integer getIdAtuador7() {
        return idAtuador7;
    }

    
    public void setIdAtuador7(Integer value) {
        this.idAtuador7 = value;
    }

    
    public Integer getIdAtuador8() {
        return idAtuador8;
    }

    
    public void setIdAtuador8(Integer value) {
        this.idAtuador8 = value;
    }

    
    public Integer getIdCliente() {
        return idCliente;
    }

    
    public void setIdCliente(Integer value) {
        this.idCliente = value;
    }

    
    public Integer getIdContratoCliente() {
        return idContratoCliente;
    }

    
    public void setIdContratoCliente(Integer value) {
        this.idContratoCliente = value;
    }

    
    public Long getIdEquipamento() {
        return idEquipamento;
    }

    
    public void setIdEquipamento(Long value) {
        this.idEquipamento = value;
    }

    
    public Integer getIdLayout() {
        return idLayout;
    }

    
    public void setIdLayout(Integer value) {
        this.idLayout = value;
    }

    
    public Integer getIdProjeto() {
        return idProjeto;
    }

    
    public void setIdProjeto(Integer value) {
        this.idProjeto = value;
    }

    
    public Integer getIdSensor1() {
        return idSensor1;
    }

    
    public void setIdSensor1(Integer value) {
        this.idSensor1 = value;
    }

    
    public Integer getIdSensor2() {
        return idSensor2;
    }

    
    public void setIdSensor2(Integer value) {
        this.idSensor2 = value;
    }

    
    public Integer getIdSensor3() {
        return idSensor3;
    }

    
    public void setIdSensor3(Integer value) {
        this.idSensor3 = value;
    }

    
    public Integer getIdSensor4() {
        return idSensor4;
    }

    
    public void setIdSensor4(Integer value) {
        this.idSensor4 = value;
    }

    
    public Integer getIdSensor5() {
        return idSensor5;
    }

    
    public void setIdSensor5(Integer value) {
        this.idSensor5 = value;
    }

    
    public Integer getIdSensor6() {
        return idSensor6;
    }

    
    public void setIdSensor6(Integer value) {
        this.idSensor6 = value;
    }

    
    public Integer getIdSensor7() {
        return idSensor7;
    }

    
    public void setIdSensor7(Integer value) {
        this.idSensor7 = value;
    }

    
    public Integer getIdSensor8() {
        return idSensor8;
    }

    
    public void setIdSensor8(Integer value) {
        this.idSensor8 = value;
    }

    
    public Integer getIdSerial0() {
        return idSerial0;
    }

    
    public void setIdSerial0(Integer value) {
        this.idSerial0 = value;
    }

    
    public Integer getIdSerial1() {
        return idSerial1;
    }

    
    public void setIdSerial1(Integer value) {
        this.idSerial1 = value;
    }

    
    public Integer getIdVeiculo() {
        return idVeiculo;
    }

    
    public void setIdVeiculo(Integer value) {
        this.idVeiculo = value;
    }

    
    public String getPlaca() {
        return placa;
    }

    
    public void setPlaca(String value) {
        this.placa = value;
    }

    
    public Integer getPortaBloqueio() {
        return portaBloqueio;
    }

    
    public void setPortaBloqueio(Integer value) {
        this.portaBloqueio = value;
    }

    
    public Integer getPortaPanico() {
        return portaPanico;
    }

    
    public void setPortaPanico(Integer value) {
        this.portaPanico = value;
    }

    
    public boolean isSatelital() {
        return satelital;
    }

    
    public void setSatelital(boolean value) {
        this.satelital = value;
    }

    
    public boolean isTelemetria() {
        return telemetria;
    }

    
    public void setTelemetria(boolean value) {
        this.telemetria = value;
    }

    
    public String getDescricao() {
        return descricao;
    }

    
    public void setDescricao(String value) {
        this.descricao = value;
    }

    
    public String getIdEquipamentoDesc() {
        return idEquipamentoDesc;
    }

    
    public void setIdEquipamentoDesc(String value) {
        this.idEquipamentoDesc = value;
    }

}
