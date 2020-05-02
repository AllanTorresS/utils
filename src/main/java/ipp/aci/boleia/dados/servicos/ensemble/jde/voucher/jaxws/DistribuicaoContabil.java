
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "distribuicaoContabil", propOrder = {
    "indiceContaContabil",
    "linhaDocumento",
    "valorMoedaNacional",
    "valorMoedaEstrangeira",
    "observacao",
    "referencia",
    "numeroSerie",
    "subConta",
    "chaveDaConta"
})
public class DistribuicaoContabil {

    protected Long indiceContaContabil;
    protected Long linhaDocumento;
    protected BigDecimal valorMoedaNacional;
    protected BigDecimal valorMoedaEstrangeira;
    protected String observacao;
    protected String referencia;
    protected String numeroSerie;
    protected SubConta subConta;
    protected ChaveConta chaveDaConta;

    
    public Long getIndiceContaContabil() {
        return indiceContaContabil;
    }

    
    public void setIndiceContaContabil(Long value) {
        this.indiceContaContabil = value;
    }

    
    public Long getLinhaDocumento() {
        return linhaDocumento;
    }

    
    public void setLinhaDocumento(Long value) {
        this.linhaDocumento = value;
    }

    
    public BigDecimal getValorMoedaNacional() {
        return valorMoedaNacional;
    }

    
    public void setValorMoedaNacional(BigDecimal value) {
        this.valorMoedaNacional = value;
    }

    
    public BigDecimal getValorMoedaEstrangeira() {
        return valorMoedaEstrangeira;
    }

    
    public void setValorMoedaEstrangeira(BigDecimal value) {
        this.valorMoedaEstrangeira = value;
    }

    
    public String getObservacao() {
        return observacao;
    }

    
    public void setObservacao(String value) {
        this.observacao = value;
    }

    
    public String getReferencia() {
        return referencia;
    }

    
    public void setReferencia(String value) {
        this.referencia = value;
    }

    
    public String getNumeroSerie() {
        return numeroSerie;
    }

    
    public void setNumeroSerie(String value) {
        this.numeroSerie = value;
    }

    
    public SubConta getSubConta() {
        return subConta;
    }

    
    public void setSubConta(SubConta value) {
        this.subConta = value;
    }

    
    public ChaveConta getChaveDaConta() {
        return chaveDaConta;
    }

    
    public void setChaveDaConta(ChaveConta value) {
        this.chaveDaConta = value;
    }

}
