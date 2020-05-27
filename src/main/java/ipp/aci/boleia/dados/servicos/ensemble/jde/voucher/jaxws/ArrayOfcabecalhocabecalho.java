
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfcabecalhocabecalho", propOrder = {
    "cabecalho"
})
public class ArrayOfcabecalhocabecalho {

    @XmlElement(nillable = true)
    protected List<Cabecalho> cabecalho;

    
    public List<Cabecalho> getCabecalho() {
        if (cabecalho == null) {
            cabecalho = new ArrayList<Cabecalho>();
        }
        return this.cabecalho;
    }

}
