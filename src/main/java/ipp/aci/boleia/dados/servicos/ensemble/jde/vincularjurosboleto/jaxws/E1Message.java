
package ipp.aci.boleia.dados.servicos.ensemble.jde.vincularjurosboleto.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "E1Message", namespace = "java:oracle.e1.bssvfoundation.util", propOrder = {
    "messagePrefix",
    "message"
})
public class E1Message {

    @XmlElement(name = "MessagePrefix")
    protected String messagePrefix;
    @XmlElement(name = "Message")
    protected String message;

    
    public String getMessagePrefix() {
        return messagePrefix;
    }

    
    public void setMessagePrefix(String value) {
        this.messagePrefix = value;
    }

    
    public String getMessage() {
        return message;
    }

    
    public void setMessage(String value) {
        this.message = value;
    }

}
