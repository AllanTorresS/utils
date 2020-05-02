
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterPacotePosicaoMotoristaHistorico", propOrder = {
    "usuario",
    "senha",
    "dataInicio",
    "dataFinal",
    "idVeiculo"
})
public class ObterPacotePosicaoMotoristaHistorico {

    protected String usuario;
    protected String senha;
    protected String dataInicio;
    protected String dataFinal;
    protected Integer idVeiculo;

    
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

}
