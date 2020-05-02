
package ipp.aci.boleia.dados.servicos.ensemble.jde.vinculocreditoboleto.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "E1MessageList", namespace = "java:oracle.e1.bssvfoundation.util", propOrder = {
    "e1Messages"
})
public class E1MessageList {

    @XmlElement(name = "E1Messages", nillable = true)
    protected List<E1Message> e1Messages;

    
    public List<E1Message> getE1Messages() {
        if (e1Messages == null) {
            e1Messages = new ArrayList<E1Message>();
        }
        return this.e1Messages;
    }

}
