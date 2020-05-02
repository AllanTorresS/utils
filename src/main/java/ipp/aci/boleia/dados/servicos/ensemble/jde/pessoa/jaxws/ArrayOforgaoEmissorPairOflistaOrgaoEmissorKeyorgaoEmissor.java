
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOforgaoEmissorPairOflistaOrgaoEmissorKeyorgaoEmissor", propOrder = {
    "orgaoEmissor"
})
public class ArrayOforgaoEmissorPairOflistaOrgaoEmissorKeyorgaoEmissor {

    @XmlElement(nillable = true)
    protected List<PairOflistaOrgaoEmissorKeyorgaoEmissor> orgaoEmissor;

    
    public List<PairOflistaOrgaoEmissorKeyorgaoEmissor> getOrgaoEmissor() {
        if (orgaoEmissor == null) {
            orgaoEmissor = new ArrayList<PairOflistaOrgaoEmissorKeyorgaoEmissor>();
        }
        return this.orgaoEmissor;
    }

}
