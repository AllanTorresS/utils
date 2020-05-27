
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "item", propOrder = {
    "itemPagamento",
    "itemPagamentoNumExtensao"
})
public class Item {

    protected String itemPagamento;
    protected Long itemPagamentoNumExtensao;

    
    public String getItemPagamento() {
        return itemPagamento;
    }

    
    public void setItemPagamento(String value) {
        this.itemPagamento = value;
    }

    
    public Long getItemPagamentoNumExtensao() {
        return itemPagamentoNumExtensao;
    }

    
    public void setItemPagamentoNumExtensao(Long value) {
        this.itemPagamentoNumExtensao = value;
    }

}
