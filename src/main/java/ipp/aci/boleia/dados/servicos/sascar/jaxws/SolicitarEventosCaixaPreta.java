
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitarEventosCaixaPreta", propOrder = {
    "usuario",
    "senha",
    "idVeiculo",
    "placa",
    "dataPosicaoInicial",
    "dataPosicaoFinal",
    "ticket"
})
public class SolicitarEventosCaixaPreta {

    protected String usuario;
    protected String senha;
    protected String idVeiculo;
    protected String placa;
    protected String dataPosicaoInicial;
    protected String dataPosicaoFinal;
    protected String ticket;

    
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

    
    public String getIdVeiculo() {
        return idVeiculo;
    }

    
    public void setIdVeiculo(String value) {
        this.idVeiculo = value;
    }

    
    public String getPlaca() {
        return placa;
    }

    
    public void setPlaca(String value) {
        this.placa = value;
    }

    
    public String getDataPosicaoInicial() {
        return dataPosicaoInicial;
    }

    
    public void setDataPosicaoInicial(String value) {
        this.dataPosicaoInicial = value;
    }

    
    public String getDataPosicaoFinal() {
        return dataPosicaoFinal;
    }

    
    public void setDataPosicaoFinal(String value) {
        this.dataPosicaoFinal = value;
    }

    
    public String getTicket() {
        return ticket;
    }

    
    public void setTicket(String value) {
        this.ticket = value;
    }

}
