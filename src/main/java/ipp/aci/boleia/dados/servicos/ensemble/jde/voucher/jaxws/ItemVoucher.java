
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "itemVoucher", propOrder = {
    "itemPagamento",
    "itemPagamentoExtensao"
})
public class ItemVoucher {

    protected String itemPagamento;
    protected String itemPagamentoExtensao;

    
    public String getItemPagamento() {
        return itemPagamento;
    }

    
    public void setItemPagamento(String value) {
        this.itemPagamento = value;
    }

    
    public String getItemPagamentoExtensao() {
        return itemPagamentoExtensao;
    }

    
    public void setItemPagamentoExtensao(String value) {
        this.itemPagamentoExtensao = value;
    }

}
