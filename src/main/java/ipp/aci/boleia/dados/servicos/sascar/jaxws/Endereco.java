
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "endereco", propOrder = {
    "city",
    "street"
})
public class Endereco {

    protected Cidade city;
    protected String street;

    
    public Cidade getCity() {
        return city;
    }

    
    public void setCity(Cidade value) {
        this.city = value;
    }

    
    public String getStreet() {
        return street;
    }

    
    public void setStreet(String value) {
        this.street = value;
    }

}
