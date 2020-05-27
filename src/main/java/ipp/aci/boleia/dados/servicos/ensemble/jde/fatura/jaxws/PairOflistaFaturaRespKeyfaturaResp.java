
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaFaturaRespKeyfaturaResp")
public class PairOflistaFaturaRespKeyfaturaResp
    extends FaturaResp
{

    @XmlAttribute(name = "listaFaturaRespKey", required = true)
    protected String listaFaturaRespKey;

    
    public String getListaFaturaRespKey() {
        return listaFaturaRespKey;
    }

    
    public void setListaFaturaRespKey(String value) {
        this.listaFaturaRespKey = value;
    }

}
