
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfcontabilizacaoPairOflistaContabilizacaoKeycontabilizacao", propOrder = {
    "contabilizacao"
})
public class ArrayOfcontabilizacaoPairOflistaContabilizacaoKeycontabilizacao {

    @XmlElement(nillable = true)
    protected List<PairOflistaContabilizacaoKeycontabilizacao> contabilizacao;

    
    public List<PairOflistaContabilizacaoKeycontabilizacao> getContabilizacao() {
        if (contabilizacao == null) {
            contabilizacao = new ArrayList<PairOflistaContabilizacaoKeycontabilizacao>();
        }
        return this.contabilizacao;
    }

}
