
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoDocumento", propOrder = {
    "codigoTipoDocumento",
    "descricaoTipoDocumento"
})
@XmlSeeAlso({
    PairOflistaTipoDocumentoPFKeytipoDocumento.class
})
public class TipoDocumento {

    protected Long codigoTipoDocumento;
    protected String descricaoTipoDocumento;

    
    public Long getCodigoTipoDocumento() {
        return codigoTipoDocumento;
    }

    
    public void setCodigoTipoDocumento(Long value) {
        this.codigoTipoDocumento = value;
    }

    
    public String getDescricaoTipoDocumento() {
        return descricaoTipoDocumento;
    }

    
    public void setDescricaoTipoDocumento(String value) {
        this.descricaoTipoDocumento = value;
    }

}
