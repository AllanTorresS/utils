
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resultadoVoucher", propOrder = {
    "tipoDocumentoAjuste",
    "codPagador",
    "codAprovador",
    "dataFatura",
    "dataServicoImposto",
    "dataVencimento",
    "dataVencimentoDesconto",
    "dataContabilizacao",
    "ano",
    "seculo",
    "periodo",
    "documentoBalanceado",
    "statusPagamento",
    "valorBruto",
    "valorEmAberto",
    "descontoDisponivel",
    "descontoObtido",
    "valorTribut\u00e1vel",
    "valorNaoTribut\u00e1vel",
    "valorImposto",
    "areaTaxaImposto",
    "codigoExplicacaoImposto1",
    "valorMoeda",
    "valorEmAbertoEstrangeiro",
    "valorDescontoEstrangeiroDisponivel",
    "valorDescontoEstrangeiroObtido",
    "valoEstrangeiroTribut\u00e1vel",
    "valorEstrangeiroNaoTribut\u00e1vel",
    "valorImpostoEstrangeiro",
    "classeGL",
    "idConta",
    "codigoContabilizadoGL",
    "identificacaoCurtaTransacaoBancaria",
    "codigoCondicoesPagamento",
    "indicadorCancelado",
    "numeroSequenciaOperacoes",
    "referencia",
    "unidade",
    "unidadeNegocios2",
    "descricao",
    "frequenciaPeriodica",
    "frequenciaPeriodicaNumeroPagamentos",
    "campoControleDemonstrativo",
    "itemFechadoAPartirProcessamento",
    "unidades",
    "unidadeMedida",
    "instrumentoPagamento",
    "areaTaxadeImposto2Retencao",
    "codigoExplicacaoImposto3Retencao",
    "codigoMiscelaneos",
    "codigoMiscelaneos2",
    "codigoMiscelaneos3",
    "indicador1099",
    "entradaDomestDistMultiMoedas",
    "historicoTaxaConversaoMoeda",
    "dataHistorica",
    "orignadorTransacao",
    "numeroTransacaoBanc\u00e1ria",
    "codigoNegociacaoPagamento",
    "statusImpostoDiferidoIVA",
    "codigoMoeda",
    "valorDistribuir",
    "valorMoedaDistribuir",
    "valorImpostoIrrecuperavel",
    "impostoIrrecuperavelMoedaEstr",
    "criadoPorComprasas",
    "consolidado",
    "pagarQuandoPago",
    "numeroGrupoPagarQuandoPago",
    "idTransacaoEncontroContas",
    "numeroDocumentoEncontroContas",
    "codigoStatusEncontroContas",
    "chaveLinhaVoucher",
    "chavePedidoOriginal",
    "chavePedidoCompraLinha",
    "codigoCategoriaConta",
    "reservadoUsoFuturo",
    "dadosFornecedor"
})
public class ResultadoVoucher {

    protected String tipoDocumentoAjuste;
    protected Long codPagador;
    protected Long codAprovador;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFatura;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataServicoImposto;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataVencimento;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataVencimentoDesconto;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataContabilizacao;
    protected Long ano;
    protected Long seculo;
    protected Long periodo;
    protected String documentoBalanceado;
    protected String statusPagamento;
    protected BigDecimal valorBruto;
    protected BigDecimal valorEmAberto;
    protected BigDecimal descontoDisponivel;
    protected BigDecimal descontoObtido;
    protected BigDecimal valorTributável;
    protected BigDecimal valorNaoTributável;
    protected BigDecimal valorImposto;
    protected String areaTaxaImposto;
    protected String codigoExplicacaoImposto1;
    protected BigDecimal valorMoeda;
    protected BigDecimal valorEmAbertoEstrangeiro;
    protected BigDecimal valorDescontoEstrangeiroDisponivel;
    protected BigDecimal valorDescontoEstrangeiroObtido;
    protected BigDecimal valoEstrangeiroTributável;
    protected BigDecimal valorEstrangeiroNaoTributável;
    protected BigDecimal valorImpostoEstrangeiro;
    protected String classeGL;
    @XmlElement(name = "iDConta")
    protected String idConta;
    protected String codigoContabilizadoGL;
    protected String identificacaoCurtaTransacaoBancaria;
    protected String codigoCondicoesPagamento;
    protected String indicadorCancelado;
    protected BigDecimal numeroSequenciaOperacoes;
    protected String referencia;
    protected String unidade;
    protected String unidadeNegocios2;
    protected String descricao;
    protected String frequenciaPeriodica;
    protected Long frequenciaPeriodicaNumeroPagamentos;
    protected String campoControleDemonstrativo;
    protected String itemFechadoAPartirProcessamento;
    protected BigDecimal unidades;
    protected String unidadeMedida;
    protected String instrumentoPagamento;
    protected String areaTaxadeImposto2Retencao;
    protected String codigoExplicacaoImposto3Retencao;
    protected String codigoMiscelaneos;
    protected String codigoMiscelaneos2;
    protected String codigoMiscelaneos3;
    protected String indicador1099;
    protected String entradaDomestDistMultiMoedas;
    protected Long historicoTaxaConversaoMoeda;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataHistorica;
    protected String orignadorTransacao;
    protected String numeroTransacaoBancária;
    protected String codigoNegociacaoPagamento;
    protected String statusImpostoDiferidoIVA;
    protected String codigoMoeda;
    protected BigDecimal valorDistribuir;
    protected BigDecimal valorMoedaDistribuir;
    protected BigDecimal valorImpostoIrrecuperavel;
    protected BigDecimal impostoIrrecuperavelMoedaEstr;
    protected Boolean criadoPorComprasas;
    protected Boolean consolidado;
    protected String pagarQuandoPago;
    protected Long numeroGrupoPagarQuandoPago;
    protected Long idTransacaoEncontroContas;
    protected Long numeroDocumentoEncontroContas;
    protected String codigoStatusEncontroContas;
    protected ChaveLinhaVoucher chaveLinhaVoucher;
    protected ChavePedidoOriginal chavePedidoOriginal;
    protected ChavePedidoCompraLinha chavePedidoCompraLinha;
    protected CodigoCategoriaConta codigoCategoriaConta;
    protected ReservadoUsoFuturo reservadoUsoFuturo;
    protected DadosFornecedor dadosFornecedor;

    
    public String getTipoDocumentoAjuste() {
        return tipoDocumentoAjuste;
    }

    
    public void setTipoDocumentoAjuste(String value) {
        this.tipoDocumentoAjuste = value;
    }

    
    public Long getCodPagador() {
        return codPagador;
    }

    
    public void setCodPagador(Long value) {
        this.codPagador = value;
    }

    
    public Long getCodAprovador() {
        return codAprovador;
    }

    
    public void setCodAprovador(Long value) {
        this.codAprovador = value;
    }

    
    public XMLGregorianCalendar getDataFatura() {
        return dataFatura;
    }

    
    public void setDataFatura(XMLGregorianCalendar value) {
        this.dataFatura = value;
    }

    
    public XMLGregorianCalendar getDataServicoImposto() {
        return dataServicoImposto;
    }

    
    public void setDataServicoImposto(XMLGregorianCalendar value) {
        this.dataServicoImposto = value;
    }

    
    public XMLGregorianCalendar getDataVencimento() {
        return dataVencimento;
    }

    
    public void setDataVencimento(XMLGregorianCalendar value) {
        this.dataVencimento = value;
    }

    
    public XMLGregorianCalendar getDataVencimentoDesconto() {
        return dataVencimentoDesconto;
    }

    
    public void setDataVencimentoDesconto(XMLGregorianCalendar value) {
        this.dataVencimentoDesconto = value;
    }

    
    public XMLGregorianCalendar getDataContabilizacao() {
        return dataContabilizacao;
    }

    
    public void setDataContabilizacao(XMLGregorianCalendar value) {
        this.dataContabilizacao = value;
    }

    
    public Long getAno() {
        return ano;
    }

    
    public void setAno(Long value) {
        this.ano = value;
    }

    
    public Long getSeculo() {
        return seculo;
    }

    
    public void setSeculo(Long value) {
        this.seculo = value;
    }

    
    public Long getPeriodo() {
        return periodo;
    }

    
    public void setPeriodo(Long value) {
        this.periodo = value;
    }

    
    public String getDocumentoBalanceado() {
        return documentoBalanceado;
    }

    
    public void setDocumentoBalanceado(String value) {
        this.documentoBalanceado = value;
    }

    
    public String getStatusPagamento() {
        return statusPagamento;
    }

    
    public void setStatusPagamento(String value) {
        this.statusPagamento = value;
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

    
    public BigDecimal getDescontoDisponivel() {
        return descontoDisponivel;
    }

    
    public void setDescontoDisponivel(BigDecimal value) {
        this.descontoDisponivel = value;
    }

    
    public BigDecimal getDescontoObtido() {
        return descontoObtido;
    }

    
    public void setDescontoObtido(BigDecimal value) {
        this.descontoObtido = value;
    }

    
    public BigDecimal getValorTributável() {
        return valorTributável;
    }

    
    public void setValorTributável(BigDecimal value) {
        this.valorTributável = value;
    }

    
    public BigDecimal getValorNaoTributável() {
        return valorNaoTributável;
    }

    
    public void setValorNaoTributável(BigDecimal value) {
        this.valorNaoTributável = value;
    }

    
    public BigDecimal getValorImposto() {
        return valorImposto;
    }

    
    public void setValorImposto(BigDecimal value) {
        this.valorImposto = value;
    }

    
    public String getAreaTaxaImposto() {
        return areaTaxaImposto;
    }

    
    public void setAreaTaxaImposto(String value) {
        this.areaTaxaImposto = value;
    }

    
    public String getCodigoExplicacaoImposto1() {
        return codigoExplicacaoImposto1;
    }

    
    public void setCodigoExplicacaoImposto1(String value) {
        this.codigoExplicacaoImposto1 = value;
    }

    
    public BigDecimal getValorMoeda() {
        return valorMoeda;
    }

    
    public void setValorMoeda(BigDecimal value) {
        this.valorMoeda = value;
    }

    
    public BigDecimal getValorEmAbertoEstrangeiro() {
        return valorEmAbertoEstrangeiro;
    }

    
    public void setValorEmAbertoEstrangeiro(BigDecimal value) {
        this.valorEmAbertoEstrangeiro = value;
    }

    
    public BigDecimal getValorDescontoEstrangeiroDisponivel() {
        return valorDescontoEstrangeiroDisponivel;
    }

    
    public void setValorDescontoEstrangeiroDisponivel(BigDecimal value) {
        this.valorDescontoEstrangeiroDisponivel = value;
    }

    
    public BigDecimal getValorDescontoEstrangeiroObtido() {
        return valorDescontoEstrangeiroObtido;
    }

    
    public void setValorDescontoEstrangeiroObtido(BigDecimal value) {
        this.valorDescontoEstrangeiroObtido = value;
    }

    
    public BigDecimal getValoEstrangeiroTributável() {
        return valoEstrangeiroTributável;
    }

    
    public void setValoEstrangeiroTributável(BigDecimal value) {
        this.valoEstrangeiroTributável = value;
    }

    
    public BigDecimal getValorEstrangeiroNaoTributável() {
        return valorEstrangeiroNaoTributável;
    }

    
    public void setValorEstrangeiroNaoTributável(BigDecimal value) {
        this.valorEstrangeiroNaoTributável = value;
    }

    
    public BigDecimal getValorImpostoEstrangeiro() {
        return valorImpostoEstrangeiro;
    }

    
    public void setValorImpostoEstrangeiro(BigDecimal value) {
        this.valorImpostoEstrangeiro = value;
    }

    
    public String getClasseGL() {
        return classeGL;
    }

    
    public void setClasseGL(String value) {
        this.classeGL = value;
    }

    
    public String getIDConta() {
        return idConta;
    }

    
    public void setIDConta(String value) {
        this.idConta = value;
    }

    
    public String getCodigoContabilizadoGL() {
        return codigoContabilizadoGL;
    }

    
    public void setCodigoContabilizadoGL(String value) {
        this.codigoContabilizadoGL = value;
    }

    
    public String getIdentificacaoCurtaTransacaoBancaria() {
        return identificacaoCurtaTransacaoBancaria;
    }

    
    public void setIdentificacaoCurtaTransacaoBancaria(String value) {
        this.identificacaoCurtaTransacaoBancaria = value;
    }

    
    public String getCodigoCondicoesPagamento() {
        return codigoCondicoesPagamento;
    }

    
    public void setCodigoCondicoesPagamento(String value) {
        this.codigoCondicoesPagamento = value;
    }

    
    public String getIndicadorCancelado() {
        return indicadorCancelado;
    }

    
    public void setIndicadorCancelado(String value) {
        this.indicadorCancelado = value;
    }

    
    public BigDecimal getNumeroSequenciaOperacoes() {
        return numeroSequenciaOperacoes;
    }

    
    public void setNumeroSequenciaOperacoes(BigDecimal value) {
        this.numeroSequenciaOperacoes = value;
    }

    
    public String getReferencia() {
        return referencia;
    }

    
    public void setReferencia(String value) {
        this.referencia = value;
    }

    
    public String getUnidade() {
        return unidade;
    }

    
    public void setUnidade(String value) {
        this.unidade = value;
    }

    
    public String getUnidadeNegocios2() {
        return unidadeNegocios2;
    }

    
    public void setUnidadeNegocios2(String value) {
        this.unidadeNegocios2 = value;
    }

    
    public String getDescricao() {
        return descricao;
    }

    
    public void setDescricao(String value) {
        this.descricao = value;
    }

    
    public String getFrequenciaPeriodica() {
        return frequenciaPeriodica;
    }

    
    public void setFrequenciaPeriodica(String value) {
        this.frequenciaPeriodica = value;
    }

    
    public Long getFrequenciaPeriodicaNumeroPagamentos() {
        return frequenciaPeriodicaNumeroPagamentos;
    }

    
    public void setFrequenciaPeriodicaNumeroPagamentos(Long value) {
        this.frequenciaPeriodicaNumeroPagamentos = value;
    }

    
    public String getCampoControleDemonstrativo() {
        return campoControleDemonstrativo;
    }

    
    public void setCampoControleDemonstrativo(String value) {
        this.campoControleDemonstrativo = value;
    }

    
    public String getItemFechadoAPartirProcessamento() {
        return itemFechadoAPartirProcessamento;
    }

    
    public void setItemFechadoAPartirProcessamento(String value) {
        this.itemFechadoAPartirProcessamento = value;
    }

    
    public BigDecimal getUnidades() {
        return unidades;
    }

    
    public void setUnidades(BigDecimal value) {
        this.unidades = value;
    }

    
    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    
    public void setUnidadeMedida(String value) {
        this.unidadeMedida = value;
    }

    
    public String getInstrumentoPagamento() {
        return instrumentoPagamento;
    }

    
    public void setInstrumentoPagamento(String value) {
        this.instrumentoPagamento = value;
    }

    
    public String getAreaTaxadeImposto2Retencao() {
        return areaTaxadeImposto2Retencao;
    }

    
    public void setAreaTaxadeImposto2Retencao(String value) {
        this.areaTaxadeImposto2Retencao = value;
    }

    
    public String getCodigoExplicacaoImposto3Retencao() {
        return codigoExplicacaoImposto3Retencao;
    }

    
    public void setCodigoExplicacaoImposto3Retencao(String value) {
        this.codigoExplicacaoImposto3Retencao = value;
    }

    
    public String getCodigoMiscelaneos() {
        return codigoMiscelaneos;
    }

    
    public void setCodigoMiscelaneos(String value) {
        this.codigoMiscelaneos = value;
    }

    
    public String getCodigoMiscelaneos2() {
        return codigoMiscelaneos2;
    }

    
    public void setCodigoMiscelaneos2(String value) {
        this.codigoMiscelaneos2 = value;
    }

    
    public String getCodigoMiscelaneos3() {
        return codigoMiscelaneos3;
    }

    
    public void setCodigoMiscelaneos3(String value) {
        this.codigoMiscelaneos3 = value;
    }

    
    public String getIndicador1099() {
        return indicador1099;
    }

    
    public void setIndicador1099(String value) {
        this.indicador1099 = value;
    }

    
    public String getEntradaDomestDistMultiMoedas() {
        return entradaDomestDistMultiMoedas;
    }

    
    public void setEntradaDomestDistMultiMoedas(String value) {
        this.entradaDomestDistMultiMoedas = value;
    }

    
    public Long getHistoricoTaxaConversaoMoeda() {
        return historicoTaxaConversaoMoeda;
    }

    
    public void setHistoricoTaxaConversaoMoeda(Long value) {
        this.historicoTaxaConversaoMoeda = value;
    }

    
    public XMLGregorianCalendar getDataHistorica() {
        return dataHistorica;
    }

    
    public void setDataHistorica(XMLGregorianCalendar value) {
        this.dataHistorica = value;
    }

    
    public String getOrignadorTransacao() {
        return orignadorTransacao;
    }

    
    public void setOrignadorTransacao(String value) {
        this.orignadorTransacao = value;
    }

    
    public String getNumeroTransacaoBancária() {
        return numeroTransacaoBancária;
    }

    
    public void setNumeroTransacaoBancária(String value) {
        this.numeroTransacaoBancária = value;
    }

    
    public String getCodigoNegociacaoPagamento() {
        return codigoNegociacaoPagamento;
    }

    
    public void setCodigoNegociacaoPagamento(String value) {
        this.codigoNegociacaoPagamento = value;
    }

    
    public String getStatusImpostoDiferidoIVA() {
        return statusImpostoDiferidoIVA;
    }

    
    public void setStatusImpostoDiferidoIVA(String value) {
        this.statusImpostoDiferidoIVA = value;
    }

    
    public String getCodigoMoeda() {
        return codigoMoeda;
    }

    
    public void setCodigoMoeda(String value) {
        this.codigoMoeda = value;
    }

    
    public BigDecimal getValorDistribuir() {
        return valorDistribuir;
    }

    
    public void setValorDistribuir(BigDecimal value) {
        this.valorDistribuir = value;
    }

    
    public BigDecimal getValorMoedaDistribuir() {
        return valorMoedaDistribuir;
    }

    
    public void setValorMoedaDistribuir(BigDecimal value) {
        this.valorMoedaDistribuir = value;
    }

    
    public BigDecimal getValorImpostoIrrecuperavel() {
        return valorImpostoIrrecuperavel;
    }

    
    public void setValorImpostoIrrecuperavel(BigDecimal value) {
        this.valorImpostoIrrecuperavel = value;
    }

    
    public BigDecimal getImpostoIrrecuperavelMoedaEstr() {
        return impostoIrrecuperavelMoedaEstr;
    }

    
    public void setImpostoIrrecuperavelMoedaEstr(BigDecimal value) {
        this.impostoIrrecuperavelMoedaEstr = value;
    }

    
    public Boolean isCriadoPorComprasas() {
        return criadoPorComprasas;
    }

    
    public void setCriadoPorComprasas(Boolean value) {
        this.criadoPorComprasas = value;
    }

    
    public Boolean isConsolidado() {
        return consolidado;
    }

    
    public void setConsolidado(Boolean value) {
        this.consolidado = value;
    }

    
    public String getPagarQuandoPago() {
        return pagarQuandoPago;
    }

    
    public void setPagarQuandoPago(String value) {
        this.pagarQuandoPago = value;
    }

    
    public Long getNumeroGrupoPagarQuandoPago() {
        return numeroGrupoPagarQuandoPago;
    }

    
    public void setNumeroGrupoPagarQuandoPago(Long value) {
        this.numeroGrupoPagarQuandoPago = value;
    }

    
    public Long getIdTransacaoEncontroContas() {
        return idTransacaoEncontroContas;
    }

    
    public void setIdTransacaoEncontroContas(Long value) {
        this.idTransacaoEncontroContas = value;
    }

    
    public Long getNumeroDocumentoEncontroContas() {
        return numeroDocumentoEncontroContas;
    }

    
    public void setNumeroDocumentoEncontroContas(Long value) {
        this.numeroDocumentoEncontroContas = value;
    }

    
    public String getCodigoStatusEncontroContas() {
        return codigoStatusEncontroContas;
    }

    
    public void setCodigoStatusEncontroContas(String value) {
        this.codigoStatusEncontroContas = value;
    }

    
    public ChaveLinhaVoucher getChaveLinhaVoucher() {
        return chaveLinhaVoucher;
    }

    
    public void setChaveLinhaVoucher(ChaveLinhaVoucher value) {
        this.chaveLinhaVoucher = value;
    }

    
    public ChavePedidoOriginal getChavePedidoOriginal() {
        return chavePedidoOriginal;
    }

    
    public void setChavePedidoOriginal(ChavePedidoOriginal value) {
        this.chavePedidoOriginal = value;
    }

    
    public ChavePedidoCompraLinha getChavePedidoCompraLinha() {
        return chavePedidoCompraLinha;
    }

    
    public void setChavePedidoCompraLinha(ChavePedidoCompraLinha value) {
        this.chavePedidoCompraLinha = value;
    }

    
    public CodigoCategoriaConta getCodigoCategoriaConta() {
        return codigoCategoriaConta;
    }

    
    public void setCodigoCategoriaConta(CodigoCategoriaConta value) {
        this.codigoCategoriaConta = value;
    }

    
    public ReservadoUsoFuturo getReservadoUsoFuturo() {
        return reservadoUsoFuturo;
    }

    
    public void setReservadoUsoFuturo(ReservadoUsoFuturo value) {
        this.reservadoUsoFuturo = value;
    }

    
    public DadosFornecedor getDadosFornecedor() {
        return dadosFornecedor;
    }

    
    public void setDadosFornecedor(DadosFornecedor value) {
        this.dadosFornecedor = value;
    }

}
