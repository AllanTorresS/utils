
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterStatusComandoTicketSascar", propOrder = {
    "usuario",
    "senha",
    "ticket"
})
public class ObterStatusComandoTicketSascar {

    protected String usuario;
    protected String senha;
    protected Integer ticket;

    
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

    
    public Integer getTicket() {
        return ticket;
    }

    
    public void setTicket(Integer value) {
        this.ticket = value;
    }

}
