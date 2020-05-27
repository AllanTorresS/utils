
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recuperarEventosCaixaPreta", propOrder = {
    "usuario",
    "senha",
    "placa",
    "idVeiculo",
    "dataPosicao"
})
public class RecuperarEventosCaixaPreta {

    protected String usuario;
    protected String senha;
    protected String placa;
    protected String idVeiculo;
    protected String dataPosicao;

    
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

    
    public String getPlaca() {
        return placa;
    }

    
    public void setPlaca(String value) {
        this.placa = value;
    }

    
    public String getIdVeiculo() {
        return idVeiculo;
    }

    
    public void setIdVeiculo(String value) {
        this.idVeiculo = value;
    }

    
    public String getDataPosicao() {
        return dataPosicao;
    }

    
    public void setDataPosicao(String value) {
        this.dataPosicao = value;
    }

}
