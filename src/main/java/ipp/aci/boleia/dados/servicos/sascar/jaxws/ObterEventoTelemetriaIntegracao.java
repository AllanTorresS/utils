
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterEventoTelemetriaIntegracao", propOrder = {
    "usuario",
    "senha",
    "dataInicio",
    "dataFinal",
    "idVeiculo",
    "idEventoList"
})
public class ObterEventoTelemetriaIntegracao {

    protected String usuario;
    protected String senha;
    protected String dataInicio;
    protected String dataFinal;
    protected Integer idVeiculo;
    @XmlElement(type = Integer.class)
    protected List<Integer> idEventoList;

    
    public String getUsuario() {
        return usuario;
    }

    
    public void setUsuario(String value) {
        this.usuario = value;
    }

    
    public String getSenha() {
        return senha;
    }

    
    public void setSenha(String value) {
        this.senha = value;
    }

    
    public String getDataInicio() {
        return dataInicio;
    }

    
    public void setDataInicio(String value) {
        this.dataInicio = value;
    }

    
    public String getDataFinal() {
        return dataFinal;
    }

    
    public void setDataFinal(String value) {
        this.dataFinal = value;
    }

    
    public Integer getIdVeiculo() {
        return idVeiculo;
    }

    
    public void setIdVeiculo(Integer value) {
        this.idVeiculo = value;
    }

    
    public List<Integer> getIdEventoList() {
        if (idEventoList == null) {
            idEventoList = new ArrayList<Integer>();
        }
        return this.idEventoList;
    }

}
