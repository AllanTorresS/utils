
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "processamento", propOrder = {
    "versaoProcessamento",
    "versaoProcessamentoContabil"
})
public class Processamento {

    protected String versaoProcessamento;
    protected String versaoProcessamentoContabil;

    
    public String getVersaoProcessamento() {
        return versaoProcessamento;
    }

    
    public void setVersaoProcessamento(String value) {
        this.versaoProcessamento = value;
    }

    
    public String getVersaoProcessamentoContabil() {
        return versaoProcessamentoContabil;
    }

    
    public void setVersaoProcessamentoContabil(String value) {
        this.versaoProcessamentoContabil = value;
    }

}
