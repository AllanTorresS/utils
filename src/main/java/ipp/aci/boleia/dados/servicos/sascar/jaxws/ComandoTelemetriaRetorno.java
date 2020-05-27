
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "comandoTelemetriaRetorno", propOrder = {
    "descricaoComando",
    "ticket"
})
public class ComandoTelemetriaRetorno {

    protected String descricaoComando;
    protected String ticket;

    
    public String getDescricaoComando() {
        return descricaoComando;
    }

    
    public void setDescricaoComando(String value) {
        this.descricaoComando = value;
    }

    
    public String getTicket() {
        return ticket;
    }

    
    public void setTicket(String value) {
        this.ticket = value;
    }

}
