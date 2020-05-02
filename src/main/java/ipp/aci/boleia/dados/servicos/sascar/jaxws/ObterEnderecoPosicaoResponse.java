
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterEnderecoPosicaoResponse", propOrder = {
    "_return"
})
public class ObterEnderecoPosicaoResponse {

    @XmlElement(name = "return")
    protected EnderecoPosicao _return;

    
    public EnderecoPosicao getReturn() {
        return _return;
    }

    
    public void setReturn(EnderecoPosicao value) {
        this._return = value;
    }

}
