
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "marcarTituloASerBaixadoLoteLancamentoResp", propOrder = {
    "lancamento",
    "codigoErro",
    "mensagemErro"
})
@XmlSeeAlso({
    ParListasLancamentoRetorno.class
})
public class MarcarTituloASerBaixadoLoteLancamentoResp {

    protected Long lancamento;
    protected String codigoErro;
    protected String mensagemErro;

    
    public Long getLancamento() {
        return lancamento;
    }

    
    public void setLancamento(Long value) {
        this.lancamento = value;
    }

    
    public String getCodigoErro() {
        return codigoErro;
    }

    
    public void setCodigoErro(String value) {
        this.codigoErro = value;
    }

    
    public String getMensagemErro() {
        return mensagemErro;
    }

    
    public void setMensagemErro(String value) {
        this.mensagemErro = value;
    }

}
