
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "buscarResp", propOrder = {
    "cabecalho"
})
public class BuscarResp
    extends V2
{

    protected ArrayOfcabecalhocabecalho cabecalho;

    
    public ArrayOfcabecalhocabecalho getCabecalho() {
        return cabecalho;
    }

    
    public void setCabecalho(ArrayOfcabecalhocabecalho value) {
        this.cabecalho = value;
    }

}
