
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inativarJDEReq", propOrder = {
    "codigoCliente",
    "cenario",
    "codCatMotivoInativacao",
    "codCatStatusFornecedor",
    "pontoEntrega",
    "cpfCnpj"
})
public class InativarJDEReq
    extends EnsRequest
{

    protected long codigoCliente;
    protected long cenario;
    @XmlElement(required = true)
    protected String codCatMotivoInativacao;
    @XmlElement(required = true)
    protected String codCatStatusFornecedor;
    @XmlElement(required = true)
    protected String pontoEntrega;
    @XmlElement(required = true)
    protected String cpfCnpj;

    
    public long getCodigoCliente() {
        return codigoCliente;
    }

    
    public void setCodigoCliente(long value) {
        this.codigoCliente = value;
    }

    
    public long getCenario() {
        return cenario;
    }

    
    public void setCenario(long value) {
        this.cenario = value;
    }

    
    public String getCodCatMotivoInativacao() {
        return codCatMotivoInativacao;
    }

    
    public void setCodCatMotivoInativacao(String value) {
        this.codCatMotivoInativacao = value;
    }

    
    public String getCodCatStatusFornecedor() {
        return codCatStatusFornecedor;
    }

    
    public void setCodCatStatusFornecedor(String value) {
        this.codCatStatusFornecedor = value;
    }

    
    public String getPontoEntrega() {
        return pontoEntrega;
    }

    
    public void setPontoEntrega(String value) {
        this.pontoEntrega = value;
    }

    
    public String getCpfCnpj() {
        return cpfCnpj;
    }

    
    public void setCpfCnpj(String value) {
        this.cpfCnpj = value;
    }

}
