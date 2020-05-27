
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cidade", propOrder = {
    "state",
    "name",
    "country"
})
public class Cidade {

    protected String state;
    protected String name;
    protected String country;

    
    public String getState() {
        return state;
    }

    
    public void setState(String value) {
        this.state = value;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String value) {
        this.name = value;
    }

    
    public String getCountry() {
        return country;
    }

    
    public void setCountry(String value) {
        this.country = value;
    }

}
