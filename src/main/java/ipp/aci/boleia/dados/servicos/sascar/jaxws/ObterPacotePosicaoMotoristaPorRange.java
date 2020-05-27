
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterPacotePosicaoMotoristaPorRange", propOrder = {
    "usuario",
    "senha",
    "idInicio",
    "idFinal",
    "quantidade"
})
public class ObterPacotePosicaoMotoristaPorRange {

    protected String usuario;
    protected String senha;
    protected Long idInicio;
    protected Long idFinal;
    protected Integer quantidade;

    
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

    
    public Long getIdInicio() {
        return idInicio;
    }

    
    public void setIdInicio(Long value) {
        this.idInicio = value;
    }

    
    public Long getIdFinal() {
        return idFinal;
    }

    
    public void setIdFinal(Long value) {
        this.idFinal = value;
    }

    
    public Integer getQuantidade() {
        return quantidade;
    }

    
    public void setQuantidade(Integer value) {
        this.quantidade = value;
    }

}
