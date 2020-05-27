
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultarJDEResp", propOrder = {
    "parcela",
    "dataFatura",
    "valorBruto",
    "valorEmAberto",
    "statusPgto",
    "dataVenc",
    "codigoCliente",
    "numLoteCont",
    "tipoLoteCont",
    "dataContabil",
    "codigoEstorno",
    "dataFechamento",
    "codigoErro",
    "descricaoErro",
    "status",
    "msgErro"
})
public class ConsultarJDEResp
    extends EnsResponse
{

    protected String parcela;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataFatura;
    protected BigDecimal valorBruto;
    protected BigDecimal valorEmAberto;
    protected String statusPgto;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataVenc;
    protected BigDecimal codigoCliente;
    protected BigDecimal numLoteCont;
    protected String tipoLoteCont;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataContabil;
    protected String codigoEstorno;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataFechamento;
    protected String codigoErro;
    protected String descricaoErro;
    protected Boolean status;
    protected String msgErro;

    
    public String getParcela() {
        return parcela;
    }

    
    public void setParcela(String value) {
        this.parcela = value;
    }

    
    public XMLGregorianCalendar getDataFatura() {
        return dataFatura;
    }

    
    public void setDataFatura(XMLGregorianCalendar value) {
        this.dataFatura = value;
    }

    
    public BigDecimal getValorBruto() {
        return valorBruto;
    }

    
    public void setValorBruto(BigDecimal value) {
        this.valorBruto = value;
    }

    
    public BigDecimal getValorEmAberto() {
        return valorEmAberto;
    }

    
    public void setValorEmAberto(BigDecimal value) {
        this.valorEmAberto = value;
    }

    
    public String getStatusPgto() {
        return statusPgto;
    }

    
    public void setStatusPgto(String value) {
        this.statusPgto = value;
    }

    
    public XMLGregorianCalendar getDataVenc() {
        return dataVenc;
    }

    
    public void setDataVenc(XMLGregorianCalendar value) {
        this.dataVenc = value;
    }

    
    public BigDecimal getCodigoCliente() {
        return codigoCliente;
    }

    
    public void setCodigoCliente(BigDecimal value) {
        this.codigoCliente = value;
    }

    
    public BigDecimal getNumLoteCont() {
        return numLoteCont;
    }

    
    public void setNumLoteCont(BigDecimal value) {
        this.numLoteCont = value;
    }

    
    public String getTipoLoteCont() {
        return tipoLoteCont;
    }

    
    public void setTipoLoteCont(String value) {
        this.tipoLoteCont = value;
    }

    
    public XMLGregorianCalendar getDataContabil() {
        return dataContabil;
    }

    
    public void setDataContabil(XMLGregorianCalendar value) {
        this.dataContabil = value;
    }

    
    public String getCodigoEstorno() {
        return codigoEstorno;
    }

    
    public void setCodigoEstorno(String value) {
        this.codigoEstorno = value;
    }

    
    public XMLGregorianCalendar getDataFechamento() {
        return dataFechamento;
    }

    
    public void setDataFechamento(XMLGregorianCalendar value) {
        this.dataFechamento = value;
    }

    
    public String getCodigoErro() {
        return codigoErro;
    }

    
    public void setCodigoErro(String value) {
        this.codigoErro = value;
    }

    
    public String getDescricaoErro() {
        return descricaoErro;
    }

    
    public void setDescricaoErro(String value) {
        this.descricaoErro = value;
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
