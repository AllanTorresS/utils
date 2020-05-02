
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "listarEstadoCivilResult"
})
@XmlRootElement(name = "listarEstadoCivilResponse")
public class ListarEstadoCivilResponse {

    @XmlElement(required = true)
    protected ListarEstadoCivilResp listarEstadoCivilResult;

    
    public ListarEstadoCivilResp getListarEstadoCivilResult() {
        return listarEstadoCivilResult;
    }

    
    public void setListarEstadoCivilResult(ListarEstadoCivilResp value) {
        this.listarEstadoCivilResult = value;
    }

}
