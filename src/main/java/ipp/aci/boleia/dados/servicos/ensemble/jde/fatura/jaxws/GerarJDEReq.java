
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "gerarJDEReq", propOrder = {
    "cenario"
})
public class GerarJDEReq
    extends EnsRequest
{

    protected BigDecimal cenario;

    
    public BigDecimal getCenario() {
        return cenario;
    }

    
    public void setCenario(BigDecimal value) {
        this.cenario = value;
    }

}
