
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaParcelasBaixadasKeyparcelaBaixada")
public class PairOflistaParcelasBaixadasKeyparcelaBaixada
    extends ParcelaBaixada
{

    @XmlAttribute(name = "listaParcelasBaixadasKey", required = true)
    protected String listaParcelasBaixadasKey;

    
    public String getListaParcelasBaixadasKey() {
        return listaParcelasBaixadasKey;
    }

    
    public void setListaParcelasBaixadasKey(String value) {
        this.listaParcelasBaixadasKey = value;
    }

}
