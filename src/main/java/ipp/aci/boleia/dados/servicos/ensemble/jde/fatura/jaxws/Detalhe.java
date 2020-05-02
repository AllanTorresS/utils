
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Detalhe", propOrder = {
    "tipoProduto",
    "descProduto",
    "valorBruto",
    "dataVinculacaoDoc"
})
@XmlSeeAlso({
    PairOflistaDetalhesKeyDetalhe.class
})
public class Detalhe {

    protected String tipoProduto;
    protected String descProduto;
    protected BigDecimal valorBruto;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataVinculacaoDoc;

    
    public String getTipoProduto() {
        return tipoProduto;
    }

    
    public void setTipoProduto(String value) {
        this.tipoProduto = value;
    }

    
    public String getDescProduto() {
        return descProduto;
    }

    
    public void setDescProduto(String value) {
        this.descProduto = value;
    }

    
    public BigDecimal getValorBruto() {
        return valorBruto;
    }

    
    public void setValorBruto(BigDecimal value) {
        this.valorBruto = value;
    }

    
    public XMLGregorianCalendar getDataVinculacaoDoc() {
        return dataVinculacaoDoc;
    }

    
    public void setDataVinculacaoDoc(XMLGregorianCalendar value) {
        this.dataVinculacaoDoc = value;
    }

}
