
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterMensagemPortalResponse", propOrder = {
    "_return"
})
public class ObterMensagemPortalResponse {

    @XmlElement(name = "return")
    protected Mensagem _return;

    
    public Mensagem getReturn() {
        return _return;
    }

    
    public void setReturn(Mensagem value) {
        this._return = value;
    }

}
