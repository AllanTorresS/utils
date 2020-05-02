
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mascaraDispositivo", propOrder = {
    "atuadores"
})
public class MascaraDispositivo {

    @XmlElement(nillable = true)
    protected List<Integer> atuadores;

    
    public List<Integer> getAtuadores() {
        if (atuadores == null) {
            atuadores = new ArrayList<Integer>();
        }
        return this.atuadores;
    }

}
