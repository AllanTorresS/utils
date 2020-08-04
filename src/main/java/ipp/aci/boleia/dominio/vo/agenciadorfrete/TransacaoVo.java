package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import com.fasterxml.jackson.annotation.JsonFormat;
import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;
import ipp.aci.boleia.util.ConstantesFormatacao;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Classe com informações referentes a transação do consumo de um motorista autonomo em um posto
 */
public class TransacaoVo {
    private Long id;
    private String numeroPedido;
    private String cpfMotorista;
    @JsonFormat(pattern = ConstantesFormatacao.FORMATO_ISO_8601_COM_MILLIS_E_TIMEZONE, timezone = ConstantesFormatacao.FORMATO_TIMEZONE_GMT_3)
    private Date dataCriacao;
    private PostoBasicoVo posto;
    private BigDecimal valorTotal;
    private AbastecimentoVo abastecimento;
    private SaqueVo saque;
    private Long consolidado;

    public TransacaoVo() {
        //Construtor default
    }

    public TransacaoVo(Transacao transacao) {
        this.id = transacao.getId();
        this.numeroPedido = transacao.getPedido() != null ? transacao.getPedido().getNumero() : null;
        this.cpfMotorista = transacao.getMotorista() != null ? transacao.getMotorista().getCpf() : null;
        this.dataCriacao = transacao.getDataCriacao();
        this.posto = new PostoBasicoVo(transacao.getPosto());
        this.abastecimento = new AbastecimentoVo(transacao.getAbastecimento());
        this.saque = new SaqueVo(transacao.getSaque());
        this.consolidado = transacao.getConsolidado() != null ? transacao.getConsolidado().getId() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getCpfMotorista() {
        return cpfMotorista;
    }

    public void setCpfMotorista(String cpfMotorista) {
        this.cpfMotorista = cpfMotorista;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public PostoBasicoVo getPosto() {
        return posto;
    }

    public void setPosto(PostoBasicoVo posto) {
        this.posto = posto;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public AbastecimentoVo getAbastecimento() {
        return abastecimento;
    }

    public void setAbastecimento(AbastecimentoVo abastecimento) {
        this.abastecimento = abastecimento;
    }

    public SaqueVo getSaque() {
        return saque;
    }

    public void setSaque(SaqueVo saque) {
        this.saque = saque;
    }

    public Long getConsolidado() {
        return consolidado;
    }

    public void setConsolidado(Long consolidado) {
        this.consolidado = consolidado;
    }
}
