
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contabilGeral", propOrder = {
    "chaveContabilGeral",
    "contaContabilGeral",
    "subContabil",
    "codContabilizacaoAlternativo",
    "codigoContabilizacao",
    "numeroLote",
    "tipoLote",
    "dataLote",
    "dataSistemaLote",
    "horaSistemaLote",
    "companhia",
    "idConta",
    "codigoConta",
    "modoConta",
    "periodo",
    "seculo",
    "ano",
    "moeda",
    "taxaConversaoMoeda",
    "historicoTaxaConversaoMoeda",
    "dataHistorica",
    "valor",
    "valorMoeda",
    "unidades",
    "unidadeMedida",
    "classeGL",
    "estornado",
    "explicacao",
    "explicacaoObservacao",
    "referencia1",
    "referencia2",
    "referencia3",
    "numeroPagamento",
    "dataPagamento",
    "dataChequeCompensado",
    "numeroSerie",
    "codigoContabilizacaoFinalLote",
    "codigoReconciliado",
    "consolidado",
    "codigoRemocao",
    "indicador1099",
    "exclusaoNaoPermitida",
    "formatoLivreParaClienteAltern1",
    "formatoLivreParaClienteAltern2",
    "codContabilizadoRazaoCustoArrendam",
    "codigoFatura",
    "dataFatura",
    "categoriasOrdemServico01",
    "anoFiscalSemanal",
    "periodoFiscalSemanal",
    "itemFechadoAPartirProcessamento",
    "numeroSequenciaOperacoes",
    "codigoTipoServico",
    "etapaServico",
    "unidadeNegociosPrincipal",
    "divisaoInteresse",
    "idArrendamentoBemExterno",
    "tipoIdentificacao",
    "dataImposto",
    "originador",
    "numeroRegistro",
    "identificacaoPagamentoInterno",
    "codigoMoeda",
    "impostoNumeroCurtoItem",
    "codigoCustoComBaseNaAtividade",
    "numeroLinhaDistribuicao",
    "numeroRecebimento"
})
public class ContabilGeral {

    protected ChaveContabilGeral chaveContabilGeral;
    protected ContaContabilGeral contaContabilGeral;
    protected SubContabil subContabil;
    protected CodContabilizacaoAlternativo codContabilizacaoAlternativo;
    protected String codigoContabilizacao;
    protected Long numeroLote;
    protected String tipoLote;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataLote;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataSistemaLote;
    protected Long horaSistemaLote;
    protected String companhia;
    protected String idConta;
    protected String codigoConta;
    protected String modoConta;
    protected Long periodo;
    protected Long seculo;
    protected Long ano;
    protected String moeda;
    protected Long taxaConversaoMoeda;
    protected Long historicoTaxaConversaoMoeda;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataHistorica;
    protected BigDecimal valor;
    protected BigDecimal valorMoeda;
    protected BigDecimal unidades;
    protected String unidadeMedida;
    protected String classeGL;
    protected Boolean estornado;
    protected String explicacao;
    protected String explicacaoObservacao;
    protected String referencia1;
    protected String referencia2;
    protected String referencia3;
    protected String numeroPagamento;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPagamento;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataChequeCompensado;
    protected String numeroSerie;
    protected String codigoContabilizacaoFinalLote;
    protected String codigoReconciliado;
    protected Boolean consolidado;
    protected String codigoRemocao;
    protected String indicador1099;
    protected String exclusaoNaoPermitida;
    protected String formatoLivreParaClienteAltern1;
    protected String formatoLivreParaClienteAltern2;
    protected String codContabilizadoRazaoCustoArrendam;
    protected String codigoFatura;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFatura;
    protected String categoriasOrdemServico01;
    protected Long anoFiscalSemanal;
    protected Long periodoFiscalSemanal;
    protected String itemFechadoAPartirProcessamento;
    protected BigDecimal numeroSequenciaOperacoes;
    protected String codigoTipoServico;
    protected String etapaServico;
    protected String unidadeNegociosPrincipal;
    protected BigDecimal divisaoInteresse;
    protected String idArrendamentoBemExterno;
    protected String tipoIdentificacao;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataImposto;
    protected String originador;
    protected Long numeroRegistro;
    protected Long identificacaoPagamentoInterno;
    protected String codigoMoeda;
    protected Long impostoNumeroCurtoItem;
    protected String codigoCustoComBaseNaAtividade;
    protected Long numeroLinhaDistribuicao;
    protected String numeroRecebimento;

    
    public ChaveContabilGeral getChaveContabilGeral() {
        return chaveContabilGeral;
    }

    
    public void setChaveContabilGeral(ChaveContabilGeral value) {
        this.chaveContabilGeral = value;
    }

    
    public ContaContabilGeral getContaContabilGeral() {
        return contaContabilGeral;
    }

    
    public void setContaContabilGeral(ContaContabilGeral value) {
        this.contaContabilGeral = value;
    }

    
    public SubContabil getSubContabil() {
        return subContabil;
    }

    
    public void setSubContabil(SubContabil value) {
        this.subContabil = value;
    }

    
    public CodContabilizacaoAlternativo getCodContabilizacaoAlternativo() {
        return codContabilizacaoAlternativo;
    }

    
    public void setCodContabilizacaoAlternativo(CodContabilizacaoAlternativo value) {
        this.codContabilizacaoAlternativo = value;
    }

    
    public String getCodigoContabilizacao() {
        return codigoContabilizacao;
    }

    
    public void setCodigoContabilizacao(String value) {
        this.codigoContabilizacao = value;
    }

    
    public Long getNumeroLote() {
        return numeroLote;
    }

    
    public void setNumeroLote(Long value) {
        this.numeroLote = value;
    }

    
    public String getTipoLote() {
        return tipoLote;
    }

    
    public void setTipoLote(String value) {
        this.tipoLote = value;
    }

    
    public XMLGregorianCalendar getDataLote() {
        return dataLote;
    }

    
    public void setDataLote(XMLGregorianCalendar value) {
        this.dataLote = value;
    }

    
    public XMLGregorianCalendar getDataSistemaLote() {
        return dataSistemaLote;
    }

    
    public void setDataSistemaLote(XMLGregorianCalendar value) {
        this.dataSistemaLote = value;
    }

    
    public Long getHoraSistemaLote() {
        return horaSistemaLote;
    }

    
    public void setHoraSistemaLote(Long value) {
        this.horaSistemaLote = value;
    }

    
    public String getCompanhia() {
        return companhia;
    }

    
    public void setCompanhia(String value) {
        this.companhia = value;
    }

    
    public String getIdConta() {
        return idConta;
    }

    
    public void setIdConta(String value) {
        this.idConta = value;
    }

    
    public String getCodigoConta() {
        return codigoConta;
    }

    
    public void setCodigoConta(String value) {
        this.codigoConta = value;
    }

    
    public String getModoConta() {
        return modoConta;
    }

    
    public void setModoConta(String value) {
        this.modoConta = value;
    }

    
    public Long getPeriodo() {
        return periodo;
    }

    
    public void setPeriodo(Long value) {
        this.periodo = value;
    }

    
    public Long getSeculo() {
        return seculo;
    }

    
    public void setSeculo(Long value) {
        this.seculo = value;
    }

    
    public Long getAno() {
        return ano;
    }

    
    public void setAno(Long value) {
        this.ano = value;
    }

    
    public String getMoeda() {
        return moeda;
    }

    
    public void setMoeda(String value) {
        this.moeda = value;
    }

    
    public Long getTaxaConversaoMoeda() {
        return taxaConversaoMoeda;
    }

    
    public void setTaxaConversaoMoeda(Long value) {
        this.taxaConversaoMoeda = value;
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

    
    public BigDecimal getValor() {
        return valor;
    }

    
    public void setValor(BigDecimal value) {
        this.valor = value;
    }

    
    public BigDecimal getValorMoeda() {
        return valorMoeda;
    }

    
    public void setValorMoeda(BigDecimal value) {
        this.valorMoeda = value;
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

    
    public String getClasseGL() {
        return classeGL;
    }

    
    public void setClasseGL(String value) {
        this.classeGL = value;
    }

    
    public Boolean isEstornado() {
        return estornado;
    }

    
    public void setEstornado(Boolean value) {
        this.estornado = value;
    }

    
    public String getExplicacao() {
        return explicacao;
    }

    
    public void setExplicacao(String value) {
        this.explicacao = value;
    }

    
    public String getExplicacaoObservacao() {
        return explicacaoObservacao;
    }

    
    public void setExplicacaoObservacao(String value) {
        this.explicacaoObservacao = value;
    }

    
    public String getReferencia1() {
        return referencia1;
    }

    
    public void setReferencia1(String value) {
        this.referencia1 = value;
    }

    
    public String getReferencia2() {
        return referencia2;
    }

    
    public void setReferencia2(String value) {
        this.referencia2 = value;
    }

    
    public String getReferencia3() {
        return referencia3;
    }

    
    public void setReferencia3(String value) {
        this.referencia3 = value;
    }

    
    public String getNumeroPagamento() {
        return numeroPagamento;
    }

    
    public void setNumeroPagamento(String value) {
        this.numeroPagamento = value;
    }

    
    public XMLGregorianCalendar getDataPagamento() {
        return dataPagamento;
    }

    
    public void setDataPagamento(XMLGregorianCalendar value) {
        this.dataPagamento = value;
    }

    
    public XMLGregorianCalendar getDataChequeCompensado() {
        return dataChequeCompensado;
    }

    
    public void setDataChequeCompensado(XMLGregorianCalendar value) {
        this.dataChequeCompensado = value;
    }

    
    public String getNumeroSerie() {
        return numeroSerie;
    }

    
    public void setNumeroSerie(String value) {
        this.numeroSerie = value;
    }

    
    public String getCodigoContabilizacaoFinalLote() {
        return codigoContabilizacaoFinalLote;
    }

    
    public void setCodigoContabilizacaoFinalLote(String value) {
        this.codigoContabilizacaoFinalLote = value;
    }

    
    public String getCodigoReconciliado() {
        return codigoReconciliado;
    }

    
    public void setCodigoReconciliado(String value) {
        this.codigoReconciliado = value;
    }

    
    public Boolean isConsolidado() {
        return consolidado;
    }

    
    public void setConsolidado(Boolean value) {
        this.consolidado = value;
    }

    
    public String getCodigoRemocao() {
        return codigoRemocao;
    }

    
    public void setCodigoRemocao(String value) {
        this.codigoRemocao = value;
    }

    
    public String getIndicador1099() {
        return indicador1099;
    }

    
    public void setIndicador1099(String value) {
        this.indicador1099 = value;
    }

    
    public String getExclusaoNaoPermitida() {
        return exclusaoNaoPermitida;
    }

    
    public void setExclusaoNaoPermitida(String value) {
        this.exclusaoNaoPermitida = value;
    }

    
    public String getFormatoLivreParaClienteAltern1() {
        return formatoLivreParaClienteAltern1;
    }

    
    public void setFormatoLivreParaClienteAltern1(String value) {
        this.formatoLivreParaClienteAltern1 = value;
    }

    
    public String getFormatoLivreParaClienteAltern2() {
        return formatoLivreParaClienteAltern2;
    }

    
    public void setFormatoLivreParaClienteAltern2(String value) {
        this.formatoLivreParaClienteAltern2 = value;
    }

    
    public String getCodContabilizadoRazaoCustoArrendam() {
        return codContabilizadoRazaoCustoArrendam;
    }

    
    public void setCodContabilizadoRazaoCustoArrendam(String value) {
        this.codContabilizadoRazaoCustoArrendam = value;
    }

    
    public String getCodigoFatura() {
        return codigoFatura;
    }

    
    public void setCodigoFatura(String value) {
        this.codigoFatura = value;
    }

    
    public XMLGregorianCalendar getDataFatura() {
        return dataFatura;
    }

    
    public void setDataFatura(XMLGregorianCalendar value) {
        this.dataFatura = value;
    }

    
    public String getCategoriasOrdemServico01() {
        return categoriasOrdemServico01;
    }

    
    public void setCategoriasOrdemServico01(String value) {
        this.categoriasOrdemServico01 = value;
    }

    
    public Long getAnoFiscalSemanal() {
        return anoFiscalSemanal;
    }

    
    public void setAnoFiscalSemanal(Long value) {
        this.anoFiscalSemanal = value;
    }

    
    public Long getPeriodoFiscalSemanal() {
        return periodoFiscalSemanal;
    }

    
    public void setPeriodoFiscalSemanal(Long value) {
        this.periodoFiscalSemanal = value;
    }

    
    public String getItemFechadoAPartirProcessamento() {
        return itemFechadoAPartirProcessamento;
    }

    
    public void setItemFechadoAPartirProcessamento(String value) {
        this.itemFechadoAPartirProcessamento = value;
    }

    
    public BigDecimal getNumeroSequenciaOperacoes() {
        return numeroSequenciaOperacoes;
    }

    
    public void setNumeroSequenciaOperacoes(BigDecimal value) {
        this.numeroSequenciaOperacoes = value;
    }

    
    public String getCodigoTipoServico() {
        return codigoTipoServico;
    }

    
    public void setCodigoTipoServico(String value) {
        this.codigoTipoServico = value;
    }

    
    public String getEtapaServico() {
        return etapaServico;
    }

    
    public void setEtapaServico(String value) {
        this.etapaServico = value;
    }

    
    public String getUnidadeNegociosPrincipal() {
        return unidadeNegociosPrincipal;
    }

    
    public void setUnidadeNegociosPrincipal(String value) {
        this.unidadeNegociosPrincipal = value;
    }

    
    public BigDecimal getDivisaoInteresse() {
        return divisaoInteresse;
    }

    
    public void setDivisaoInteresse(BigDecimal value) {
        this.divisaoInteresse = value;
    }

    
    public String getIdArrendamentoBemExterno() {
        return idArrendamentoBemExterno;
    }

    
    public void setIdArrendamentoBemExterno(String value) {
        this.idArrendamentoBemExterno = value;
    }

    
    public String getTipoIdentificacao() {
        return tipoIdentificacao;
    }

    
    public void setTipoIdentificacao(String value) {
        this.tipoIdentificacao = value;
    }

    
    public XMLGregorianCalendar getDataImposto() {
        return dataImposto;
    }

    
    public void setDataImposto(XMLGregorianCalendar value) {
        this.dataImposto = value;
    }

    
    public String getOriginador() {
        return originador;
    }

    
    public void setOriginador(String value) {
        this.originador = value;
    }

    
    public Long getNumeroRegistro() {
        return numeroRegistro;
    }

    
    public void setNumeroRegistro(Long value) {
        this.numeroRegistro = value;
    }

    
    public Long getIdentificacaoPagamentoInterno() {
        return identificacaoPagamentoInterno;
    }

    
    public void setIdentificacaoPagamentoInterno(Long value) {
        this.identificacaoPagamentoInterno = value;
    }

    
    public String getCodigoMoeda() {
        return codigoMoeda;
    }

    
    public void setCodigoMoeda(String value) {
        this.codigoMoeda = value;
    }

    
    public Long getImpostoNumeroCurtoItem() {
        return impostoNumeroCurtoItem;
    }

    
    public void setImpostoNumeroCurtoItem(Long value) {
        this.impostoNumeroCurtoItem = value;
    }

    
    public String getCodigoCustoComBaseNaAtividade() {
        return codigoCustoComBaseNaAtividade;
    }

    
    public void setCodigoCustoComBaseNaAtividade(String value) {
        this.codigoCustoComBaseNaAtividade = value;
    }

    
    public Long getNumeroLinhaDistribuicao() {
        return numeroLinhaDistribuicao;
    }

    
    public void setNumeroLinhaDistribuicao(Long value) {
        this.numeroLinhaDistribuicao = value;
    }

    
    public String getNumeroRecebimento() {
        return numeroRecebimento;
    }

    
    public void setNumeroRecebimento(String value) {
        this.numeroRecebimento = value;
    }

}
