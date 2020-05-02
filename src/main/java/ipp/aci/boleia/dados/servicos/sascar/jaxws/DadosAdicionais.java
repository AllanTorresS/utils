
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dadosAdicionais", propOrder = {
    "dataAlteracao",
    "idCliente",
    "idVeiculo",
    "notaDois",
    "notaUm",
    "placa"
})
public class DadosAdicionais {

    protected String dataAlteracao;
    protected Integer idCliente;
    protected Integer idVeiculo;
    protected String notaDois;
    protected String notaUm;
    protected String placa;

    
    public String getDataAlteracao() {
        return dataAlteracao;
    }

    
    public void setDataAlteracao(String value) {
        this.dataAlteracao = value;
    }

    
    public Integer getIdCliente() {
        return idCliente;
    }

    
    public void setIdCliente(Integer value) {
        this.idCliente = value;
    }

    
    public Integer getIdVeiculo() {
        return idVeiculo;
    }

    
    public void setIdVeiculo(Integer value) {
        this.idVeiculo = value;
    }

    
    public String getNotaDois() {
        return notaDois;
    }

    
    public void setNotaDois(String value) {
        this.notaDois = value;
    }

    
    public String getNotaUm() {
        return notaUm;
    }

    
    public void setNotaUm(String value) {
        this.notaUm = value;
    }

    
    public String getPlaca() {
        return placa;
    }

    
    public void setPlaca(String value) {
        this.placa = value;
    }

}
