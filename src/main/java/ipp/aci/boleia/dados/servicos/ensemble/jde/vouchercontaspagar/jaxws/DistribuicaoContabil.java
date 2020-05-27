
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "distribuicaoContabil", propOrder = {
    "indiceContaContabil",
    "valor",
    "observacao"
})
@XmlSeeAlso({
    PairOflistaDistribuicaoContabilKeydistribuicaoContabil.class
})
public class DistribuicaoContabil {

    protected Long indiceContaContabil;
    protected BigDecimal valor;
    protected String observacao;

    
    public Long getIndiceContaContabil() {
        return indiceContaContabil;
    }

    
    public void setIndiceContaContabil(Long value) {
        this.indiceContaContabil = value;
    }

    
    public BigDecimal getValor() {
        return valor;
    }

    
    public void setValor(BigDecimal value) {
        this.valor = value;
    }

    
    public String getObservacao() {
        return observacao;
    }

    
    public void setObservacao(String value) {
        this.observacao = value;
    }

}
