
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "marcarTituloASerBaixadoReq", propOrder = {
    "cenario",
    "numeroDocumento",
    "tipoDocumento",
    "companhiaDocumento",
    "listaParcelas"
})
public class MarcarTituloASerBaixadoReq
    extends EnsRequest
{

    protected long cenario;
    protected long numeroDocumento;
    @XmlElement(required = true)
    protected String tipoDocumento;
    @XmlElement(required = true)
    protected String companhiaDocumento;
    @XmlElement(required = true)
    protected ArrayOfparcelaPairOflistaParcelasKeyparcela listaParcelas;

    
    public long getCenario() {
        return cenario;
    }

    
    public void setCenario(long value) {
        this.cenario = value;
    }

    
    public long getNumeroDocumento() {
        return numeroDocumento;
    }

    
    public void setNumeroDocumento(long value) {
        this.numeroDocumento = value;
    }

    
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

    
    public String getCompanhiaDocumento() {
        return companhiaDocumento;
    }

    
    public void setCompanhiaDocumento(String value) {
        this.companhiaDocumento = value;
    }

    
    public ArrayOfparcelaPairOflistaParcelasKeyparcela getListaParcelas() {
        return listaParcelas;
    }

    
    public void setListaParcelas(ArrayOfparcelaPairOflistaParcelasKeyparcela value) {
        this.listaParcelas = value;
    }

}
