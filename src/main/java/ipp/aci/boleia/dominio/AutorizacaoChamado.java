package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Entidade que mantém as associações entre autorização pagamento e chamado.
 */
@Audited
@Entity
@Table(name = "AUTORIZACAO_CHAMADO")
public class AutorizacaoChamado implements IPersistente {

    private static final long serialVersionUID = -4627182934132175922L;

    @Id
    @Column(name = "CD_AUTORIZACAO_CHAMADO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AUTORIZACAO_CHAMADO")
    @SequenceGenerator(name = "SEQ_AUTORIZACAO_CHAMADO", sequenceName = "SEQ_AUTORIZACAO_CHAMADO", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_AUTORIZACAO_PAGAMENTO")
    private AutorizacaoPagamento autorizacaoPagamento;

    @NotNull
    @Column(name = "NO_CHAMADO")
    private Long numeroChamado;

    @NotNull
    @Column(name = "DT_CRIACAO")
    private Date dataCriacao;

    @NotNull
    @Column(name = "ID_OPERACAO")
    private Integer codigoOperacao;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    /**
     * Construtor de uma AutorizacaoChamado
     */
    public AutorizacaoChamado(){

    }

    /**
     * Construtor de uma AutorizacaoChamado
     * @param abastecimento Uma AutorizacaoPagamento associada
     * @param numeroChamado O numero do chamado
     * @param tipoOperacao O valor do enumerado TipoOperacaoChamado que representa o tipo da operação
     */
    public AutorizacaoChamado(AutorizacaoPagamento abastecimento, Long numeroChamado, Integer tipoOperacao) {
        this.setAutorizacaoPagamento(abastecimento);
        this.setCodigoOperacao(tipoOperacao);
        this.setNumeroChamado(numeroChamado);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public AutorizacaoPagamento getAutorizacaoPagamento() {
        return autorizacaoPagamento;
    }

    public void setAutorizacaoPagamento(AutorizacaoPagamento autorizacaoPagamento) {
        this.autorizacaoPagamento = autorizacaoPagamento;
    }

    public Long getNumeroChamado() {
        return numeroChamado;
    }

    public void setNumeroChamado(Long numeroChamado) {
        this.numeroChamado = numeroChamado;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Integer getCodigoOperacao() {
        return codigoOperacao;
    }

    public void setCodigoOperacao(Integer codigoOperacao) {
        this.codigoOperacao = codigoOperacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }
}
