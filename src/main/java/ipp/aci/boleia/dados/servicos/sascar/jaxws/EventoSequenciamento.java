
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eventoSequenciamento", propOrder = {
    "idEvento",
    "tempo"
})
public class EventoSequenciamento {

    protected Integer idEvento;
    protected Integer tempo;

    
    public Integer getIdEvento() {
        return idEvento;
    }

    
    public void setIdEvento(Integer value) {
        this.idEvento = value;
    }

    
    public Integer getTempo() {
        return tempo;
    }

    
    public void setTempo(Integer value) {
        this.tempo = value;
    }

}
