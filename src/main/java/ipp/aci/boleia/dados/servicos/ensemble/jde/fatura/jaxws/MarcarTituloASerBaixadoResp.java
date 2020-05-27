
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "marcarTituloASerBaixadoResp", propOrder = {
    "status",
    "msgErro",
    "listaParcelasBaixadas"
})
public class MarcarTituloASerBaixadoResp
    extends EnsResponse
{

    protected Boolean status;
    protected String msgErro;
    protected ArrayOfparcelaBaixadaPairOflistaParcelasBaixadasKeyparcelaBaixada listaParcelasBaixadas;

    
    public Boolean isStatus() {
        return status;
    }

    
    public void setStatus(Boolean value) {
        this.status = value;
    }

    
    public String getMsgErro() {
        return msgErro;
    }

    
    public void setMsgErro(String value) {
        this.msgErro = value;
    }

    
    public ArrayOfparcelaBaixadaPairOflistaParcelasBaixadasKeyparcelaBaixada getListaParcelasBaixadas() {
        return listaParcelasBaixadas;
    }

    
    public void setListaParcelasBaixadas(ArrayOfparcelaBaixadaPairOflistaParcelasBaixadasKeyparcelaBaixada value) {
        this.listaParcelasBaixadas = value;
    }

}
