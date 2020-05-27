
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detalhe", propOrder = {
    "resultadoVoucher"
})
public class Detalhe {

    protected ResultadoVoucher resultadoVoucher;

    
    public ResultadoVoucher getResultadoVoucher() {
        return resultadoVoucher;
    }

    
    public void setResultadoVoucher(ResultadoVoucher value) {
        this.resultadoVoucher = value;
    }

}
