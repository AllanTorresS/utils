
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaDetalhesKeyDetalhe")
public class PairOflistaDetalhesKeyDetalhe
    extends Detalhe
{

    @XmlAttribute(name = "listaDetalhesKey", required = true)
    protected String listaDetalhesKey;

    
    public String getListaDetalhesKey() {
        return listaDetalhesKey;
    }

    
    public void setListaDetalhesKey(String value) {
        this.listaDetalhesKey = value;
    }

}
