
package ipp.aci.boleia.dados.servicos.ensemble.jde.vinculocreditoboleto.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Response", namespace = "http://ipiranga.com.br/statusIntegracao", propOrder = {
    "saida"
})
public class Response
    extends V3
{

    protected VincularCreditoResponse saida;

    
    public VincularCreditoResponse getSaida() {
        return saida;
    }

    
    public void setSaida(VincularCreditoResponse value) {
        this.saida = value;
    }

}
