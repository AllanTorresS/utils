
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "marcarTituloASerBaixadoLoteReq", propOrder = {
    "listaTitulo"
})
public class MarcarTituloASerBaixadoLoteReq
    extends EnsRequest
{

    protected ListaTitulo listaTitulo;

    
    public ListaTitulo getListaTitulo() {
        return listaTitulo;
    }

    
    public void setListaTitulo(ListaTitulo value) {
        this.listaTitulo = value;
    }

}
