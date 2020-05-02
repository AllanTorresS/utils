
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inserirEmLoteJDEReq", propOrder = {
    "cenario",
    "listaFatura"
})
public class InserirEmLoteJDEReq
    extends EnsRequest
{

    @XmlElement(required = true)
    protected BigDecimal cenario;
    protected ArrayOffaturaPairOflistaFaturaKeyfatura listaFatura;

    
    public BigDecimal getCenario() {
        return cenario;
    }

    
    public void setCenario(BigDecimal value) {
        this.cenario = value;
    }

    
    public ArrayOffaturaPairOflistaFaturaKeyfatura getListaFatura() {
        return listaFatura;
    }

    
    public void setListaFatura(ArrayOffaturaPairOflistaFaturaKeyfatura value) {
        this.listaFatura = value;
    }

}
