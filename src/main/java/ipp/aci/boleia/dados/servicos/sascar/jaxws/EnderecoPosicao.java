
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "enderecoPosicao", propOrder = {
    "address"
})
public class EnderecoPosicao {

    protected Endereco address;

    
    public Endereco getAddress() {
        return address;
    }

    
    public void setAddress(Endereco value) {
        this.address = value;
    }

}
