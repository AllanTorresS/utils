
package ipp.aci.boleia.dados.servicos.ensemble.jde.vincularjurosboleto.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfE1Message", namespace = "java:oracle.e1.bssvfoundation.util", propOrder = {
    "e1Message"
})
public class ArrayOfE1Message {

    @XmlElement(name = "E1Message", nillable = true)
    protected List<E1Message> e1Message;

    
    public List<E1Message> getE1Message() {
        if (e1Message == null) {
            e1Message = new ArrayList<E1Message>();
        }
        return this.e1Message;
    }

}
