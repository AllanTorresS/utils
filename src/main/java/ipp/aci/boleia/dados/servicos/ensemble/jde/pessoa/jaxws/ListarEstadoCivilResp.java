
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listarEstadoCivilResp", propOrder = {
    "listaEstadoCivil",
    "status",
    "msgErro"
})
public class ListarEstadoCivilResp
    extends EnsResponse
{

    protected ArrayOfestadoCivilPairOflistaEstadoCivilKeyestadoCivil listaEstadoCivil;
    protected Boolean status;
    protected String msgErro;

    
    public ArrayOfestadoCivilPairOflistaEstadoCivilKeyestadoCivil getListaEstadoCivil() {
        return listaEstadoCivil;
    }

    
    public void setListaEstadoCivil(ArrayOfestadoCivilPairOflistaEstadoCivilKeyestadoCivil value) {
        this.listaEstadoCivil = value;
    }

    
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

}
