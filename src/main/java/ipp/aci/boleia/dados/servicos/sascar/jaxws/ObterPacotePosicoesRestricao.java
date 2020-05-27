
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterPacotePosicoesRestricao", propOrder = {
    "usuario",
    "senha",
    "quantidade",
    "idVeiculo"
})
public class ObterPacotePosicoesRestricao {

    protected String usuario;
    protected String senha;
    protected Integer quantidade;
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

    
    public Integer getQuantidade() {
        return quantidade;
    }

    
    public void setQuantidade(Integer value) {
        this.quantidade = value;
    }

    
    public Integer getIdVeiculo() {
        return idVeiculo;
    }

    
    public void setIdVeiculo(Integer value) {
        this.idVeiculo = value;
    }

}
