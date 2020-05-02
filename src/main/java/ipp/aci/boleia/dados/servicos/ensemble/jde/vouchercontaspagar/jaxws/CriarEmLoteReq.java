
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "criarEmLoteReq", propOrder = {
    "cenario",
    "companhia",
    "filial",
    "listaVoucher"
})
public class CriarEmLoteReq
    extends EnsRequest
{

    protected long cenario;
    @XmlElement(required = true)
    protected String companhia;
    @XmlElement(required = true)
    protected String filial;
    protected ArrayOfvoucherPairOflistaVoucherKeyvoucher listaVoucher;

    
    public long getCenario() {
        return cenario;
    }

    
    public void setCenario(long value) {
        this.cenario = value;
    }

    
    public String getCompanhia() {
        return companhia;
    }

    
    public void setCompanhia(String value) {
        this.companhia = value;
    }

    
    public String getFilial() {
        return filial;
    }

    
    public void setFilial(String value) {
        this.filial = value;
    }

    
    public ArrayOfvoucherPairOflistaVoucherKeyvoucher getListaVoucher() {
        return listaVoucher;
    }

    
    public void setListaVoucher(ArrayOfvoucherPairOflistaVoucherKeyvoucher value) {
        this.listaVoucher = value;
    }

}
