
package ipp.aci.boleia.dados.servicos.ensemble.jde.contabancaria.jaxws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dadosContaBancariaReq", propOrder = {
    "acao",
    "tipoRegistro",
    "bancoAgencia",
    "numeroContaBancaria",
    "codigoCadastroCliente",
    "descricao",
    "digitoControle",
    "numeroReferencia",
    "codigoSwift",
    "tipoConta",
    "paisDoBanco",
    "iban",
    "codigoCasdastroBanco"
})
public class DadosContaBancariaReq
    extends EnsRequest
{

    @XmlElement(required = true)
    protected String acao;
    protected String tipoRegistro;
    protected String bancoAgencia;
    protected String numeroContaBancaria;
    protected BigDecimal codigoCadastroCliente;
    protected String descricao;
    protected String digitoControle;
    protected String numeroReferencia;
    protected String codigoSwift;
    protected String tipoConta;
    protected String paisDoBanco;
    protected String iban;
    protected BigDecimal codigoCasdastroBanco;

    
    public String getAcao() {
        return acao;
    }

    
    public void setAcao(String value) {
        this.acao = value;
    }

    
    public String getTipoRegistro() {
        return tipoRegistro;
    }

    
    public void setTipoRegistro(String value) {
        this.tipoRegistro = value;
    }

    
    public String getBancoAgencia() {
        return bancoAgencia;
    }

    
    public void setBancoAgencia(String value) {
        this.bancoAgencia = value;
    }

    
    public String getNumeroContaBancaria() {
        return numeroContaBancaria;
    }

    
    public void setNumeroContaBancaria(String value) {
        this.numeroContaBancaria = value;
    }

    
    public BigDecimal getCodigoCadastroCliente() {
        return codigoCadastroCliente;
    }

    
    public void setCodigoCadastroCliente(BigDecimal value) {
        this.codigoCadastroCliente = value;
    }

    
    public String getDescricao() {
        return descricao;
    }

    
    public void setDescricao(String value) {
        this.descricao = value;
    }

    
    public String getDigitoControle() {
        return digitoControle;
    }

    
    public void setDigitoControle(String value) {
        this.digitoControle = value;
    }

    
    public String getNumeroReferencia() {
        return numeroReferencia;
    }

    
    public void setNumeroReferencia(String value) {
        this.numeroReferencia = value;
    }

    
    public String getCodigoSwift() {
        return codigoSwift;
    }

    
    public void setCodigoSwift(String value) {
        this.codigoSwift = value;
    }

    
    public String getTipoConta() {
        return tipoConta;
    }

    
    public void setTipoConta(String value) {
        this.tipoConta = value;
    }

    
    public String getPaisDoBanco() {
        return paisDoBanco;
    }

    
    public void setPaisDoBanco(String value) {
        this.paisDoBanco = value;
    }

    
    public String getIban() {
        return iban;
    }

    
    public void setIban(String value) {
        this.iban = value;
    }

    
    public BigDecimal getCodigoCasdastroBanco() {
        return codigoCasdastroBanco;
    }

    
    public void setCodigoCasdastroBanco(BigDecimal value) {
        this.codigoCasdastroBanco = value;
    }

}
