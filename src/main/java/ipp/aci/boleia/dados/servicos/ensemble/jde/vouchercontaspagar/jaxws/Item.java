
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "item", propOrder = {
    "codigoPagamento",
    "valorItem",
    "dataVencimento",
    "observacao",
    "listaDistribuicaoContabil"
})
@XmlSeeAlso({
    PairOflistaItensKeyitem.class
})
public class Item {

    protected String codigoPagamento;
    @XmlElement(required = true)
    protected BigDecimal valorItem;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataVencimento;
    protected String observacao;
    protected ArrayOfdistribuicaoContabilPairOflistaDistribuicaoContabilKeydistribuicaoContabil listaDistribuicaoContabil;

    
    public String getCodigoPagamento() {
        return codigoPagamento;
    }

    
    public void setCodigoPagamento(String value) {
        this.codigoPagamento = value;
    }

    
    public BigDecimal getValorItem() {
        return valorItem;
    }

    
    public void setValorItem(BigDecimal value) {
        this.valorItem = value;
    }

    
    public XMLGregorianCalendar getDataVencimento() {
        return dataVencimento;
    }

    
    public void setDataVencimento(XMLGregorianCalendar value) {
        this.dataVencimento = value;
    }

    
    public String getObservacao() {
        return observacao;
    }

    
    public void setObservacao(String value) {
        this.observacao = value;
    }

    
    public ArrayOfdistribuicaoContabilPairOflistaDistribuicaoContabilKeydistribuicaoContabil getListaDistribuicaoContabil() {
        return listaDistribuicaoContabil;
    }

    
    public void setListaDistribuicaoContabil(ArrayOfdistribuicaoContabilPairOflistaDistribuicaoContabilKeydistribuicaoContabil value) {
        this.listaDistribuicaoContabil = value;
    }

}
