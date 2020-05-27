
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOflistaCodigoPessoaItemLong", propOrder = {
    "listaCodigoPessoaItem"
})
public class ArrayOflistaCodigoPessoaItemLong {

    @XmlElement(nillable = true)
    protected List<Long> listaCodigoPessoaItem;

    
    public List<Long> getListaCodigoPessoaItem() {
        if (listaCodigoPessoaItem == null) {
            listaCodigoPessoaItem = new ArrayList<Long>();
        }
        return this.listaCodigoPessoaItem;
    }

}
