
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaTituloKeymarcarTituloASerBaixadoLoteTituloReq")
public class PairOflistaTituloKeymarcarTituloASerBaixadoLoteTituloReq
    extends MarcarTituloASerBaixadoLoteTituloReq
{

    @XmlAttribute(name = "listaTituloKey", required = true)
    protected String listaTituloKey;

    
    public String getListaTituloKey() {
        return listaTituloKey;
    }

    
    public void setListaTituloKey(String value) {
        this.listaTituloKey = value;
    }

}
