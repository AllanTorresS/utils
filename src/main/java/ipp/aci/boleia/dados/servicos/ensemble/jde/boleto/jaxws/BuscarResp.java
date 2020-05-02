
package ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "buscarResp", propOrder = {
    "totalRegistros",
    "boletos"
})
public class BuscarResp
    extends V2
{

    protected Long totalRegistros;
    protected ArrayOfboletoPairOfboletosKeyboleto boletos;

    
    public Long getTotalRegistros() {
        return totalRegistros;
    }

    
    public void setTotalRegistros(Long value) {
        this.totalRegistros = value;
    }

    
    public ArrayOfboletoPairOfboletosKeyboleto getBoletos() {
        return boletos;
    }

    
    public void setBoletos(ArrayOfboletoPairOfboletosKeyboleto value) {
        this.boletos = value;
    }

}
