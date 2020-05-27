
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfCabecalhoPairOflistaCabecalhosKeyCabecalho", propOrder = {
    "cabecalho"
})
public class ArrayOfCabecalhoPairOflistaCabecalhosKeyCabecalho {

    @XmlElement(name = "Cabecalho", nillable = true)
    protected List<PairOflistaCabecalhosKeyCabecalho> cabecalho;

    
    public List<PairOflistaCabecalhosKeyCabecalho> getCabecalho() {
        if (cabecalho == null) {
            cabecalho = new ArrayList<PairOflistaCabecalhosKeyCabecalho>();
        }
        return this.cabecalho;
    }

}
