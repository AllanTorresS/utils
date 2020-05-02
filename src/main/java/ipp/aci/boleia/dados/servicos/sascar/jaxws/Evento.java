
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "evento", propOrder = {
    "codigo"
})
public class Evento {

    protected Integer codigo;

    
    public Integer getCodigo() {
        return codigo;
    }

    
    public void setCodigo(Integer value) {
        this.codigo = value;
    }

}
