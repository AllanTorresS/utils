
package ipp.aci.boleia.dados.servicos.ensemble.jde.vinculocreditoboleto.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Request", propOrder = {
    "entrada"
})
public class Request
    extends EnsRequest
{

    protected VincularCreditoRequest entrada;

    
    public VincularCreditoRequest getEntrada() {
        return entrada;
    }

    
    public void setEntrada(VincularCreditoRequest value) {
        this.entrada = value;
    }

}
