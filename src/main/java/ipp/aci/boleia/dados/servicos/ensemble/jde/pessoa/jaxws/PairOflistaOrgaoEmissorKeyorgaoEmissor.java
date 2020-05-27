
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaOrgaoEmissorKeyorgaoEmissor")
public class PairOflistaOrgaoEmissorKeyorgaoEmissor
    extends OrgaoEmissor
{

    @XmlAttribute(name = "listaOrgaoEmissorKey", required = true)
    protected String listaOrgaoEmissorKey;

    
    public String getListaOrgaoEmissorKey() {
        return listaOrgaoEmissorKey;
    }

    
    public void setListaOrgaoEmissorKey(String value) {
        this.listaOrgaoEmissorKey = value;
    }

}
