
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "demostrativoAbatimentoCreditoCombResp", propOrder = {
    "listaCabecalhos",
    "status",
    "msgErro"
})
public class DemostrativoAbatimentoCreditoCombResp
    extends EnsResponse
{

    protected ArrayOfCabecalhoPairOflistaCabecalhosKeyCabecalho listaCabecalhos;
    protected byte[] status;
    protected String msgErro;

    
    public ArrayOfCabecalhoPairOflistaCabecalhosKeyCabecalho getListaCabecalhos() {
        return listaCabecalhos;
    }

    
    public void setListaCabecalhos(ArrayOfCabecalhoPairOflistaCabecalhosKeyCabecalho value) {
        this.listaCabecalhos = value;
    }

    
    public byte[] getStatus() {
        return status;
    }

    
    public void setStatus(byte[] value) {
        this.status = value;
    }

    
    public String getMsgErro() {
        return msgErro;
    }

    
    public void setMsgErro(String value) {
        this.msgErro = value;
    }

}
