
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mensagem", propOrder = {
    "mensagem"
})
public class Mensagem {

    protected String mensagem;

    
    public String getMensagem() {
        return mensagem;
    }

    
    public void setMensagem(String value) {
        this.mensagem = value;
    }

}
