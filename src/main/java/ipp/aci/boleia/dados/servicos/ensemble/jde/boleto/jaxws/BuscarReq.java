
package ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "buscarReq", propOrder = {
    "codigoPontoVenda",
    "codigoCompanhia",
    "tipoDocumento",
    "limite",
    "ultimoApenas"
})
public class BuscarReq
    extends EnsRequest
{

    protected long codigoPontoVenda;
    protected long codigoCompanhia;
    @XmlElement(required = true)
    protected String tipoDocumento;
    protected Long limite;
    protected Boolean ultimoApenas;

    
    public long getCodigoPontoVenda() {
        return codigoPontoVenda;
    }

    
    public void setCodigoPontoVenda(long value) {
        this.codigoPontoVenda = value;
    }

    
    public long getCodigoCompanhia() {
        return codigoCompanhia;
    }

    
    public void setCodigoCompanhia(long value) {
        this.codigoCompanhia = value;
    }

    
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

    
    public Long getLimite() {
        return limite;
    }

    
    public void setLimite(Long value) {
        this.limite = value;
    }

    
    public Boolean isUltimoApenas() {
        return ultimoApenas;
    }

    
    public void setUltimoApenas(Boolean value) {
        this.ultimoApenas = value;
    }

}
