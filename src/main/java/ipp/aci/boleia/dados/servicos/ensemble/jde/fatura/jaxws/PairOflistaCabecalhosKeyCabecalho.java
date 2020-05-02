
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaCabecalhosKeyCabecalho")
public class PairOflistaCabecalhosKeyCabecalho
    extends Cabecalho
{

    @XmlAttribute(name = "listaCabecalhosKey", required = true)
    protected String listaCabecalhosKey;

    
    public String getListaCabecalhosKey() {
        return listaCabecalhosKey;
    }

    
    public void setListaCabecalhosKey(String value) {
        this.listaCabecalhosKey = value;
    }

}
