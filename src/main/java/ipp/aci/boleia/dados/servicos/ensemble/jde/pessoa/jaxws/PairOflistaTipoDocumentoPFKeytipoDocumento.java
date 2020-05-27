
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaTipoDocumentoPFKeytipoDocumento")
public class PairOflistaTipoDocumentoPFKeytipoDocumento
    extends TipoDocumento
{

    @XmlAttribute(name = "listaTipoDocumentoPFKey", required = true)
    protected String listaTipoDocumentoPFKey;

    
    public String getListaTipoDocumentoPFKey() {
        return listaTipoDocumentoPFKey;
    }

    
    public void setListaTipoDocumentoPFKey(String value) {
        this.listaTipoDocumentoPFKey = value;
    }

}
