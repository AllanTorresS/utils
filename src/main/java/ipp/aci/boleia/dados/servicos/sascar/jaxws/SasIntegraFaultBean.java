
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sasIntegraFaultBean", propOrder = {
    "message"
})
public class SasIntegraFaultBean {

    protected String message;

    
    public String getMessage() {
        return message;
    }

    
    public void setMessage(String value) {
        this.message = value;
    }

}
