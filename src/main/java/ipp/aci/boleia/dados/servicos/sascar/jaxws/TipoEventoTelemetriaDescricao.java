
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoEventoTelemetriaDescricao", propOrder = {
    "eventoDescricao",
    "eventoTipo",
    "idEvento"
})
public class TipoEventoTelemetriaDescricao {

    protected String eventoDescricao;
    protected String eventoTipo;
    protected Integer idEvento;

    
    public String getEventoDescricao() {
        return eventoDescricao;
    }

    
    public void setEventoDescricao(String value) {
        this.eventoDescricao = value;
    }

    
    public String getEventoTipo() {
        return eventoTipo;
    }

    
    public void setEventoTipo(String value) {
        this.eventoTipo = value;
    }

    
    public Integer getIdEvento() {
        return idEvento;
    }

    
    public void setIdEvento(Integer value) {
        this.idEvento = value;
    }

}
