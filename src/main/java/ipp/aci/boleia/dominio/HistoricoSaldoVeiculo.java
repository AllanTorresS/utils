package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de Saldo Veiculo
 */
@Audited
@Entity
@Table(name = "HIST_SALDO_VEICULO")
public class HistoricoSaldoVeiculo implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = 2764912059458940757L;

    @Id
    @Column(name = "CD_HIST_SALDO_VEICULO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HIST_SALDO_VEICULO")
    @SequenceGenerator(name = "SEQ_HIST_SALDO_VEICULO", sequenceName = "SEQ_HIST_SALDO_VEICULO", allocationSize = 1)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_SALDO_VEICULO")
    private SaldoVeiculo saldoVeiculo;

    @Column(name="VA_COTA_VALOR")
    private BigDecimal cotaValor;

    @Column(name="VA_COTA_LITROS")
    private BigDecimal cotaLitros;

    @Column(name="VA_COTA_VALOR_ABAS")
    private BigDecimal cotaValorAbastecimento;

    @Column(name="VA_COTA_LITROS_ABAS")
    private BigDecimal cotaLitrosAbastecimento;

    @Column(name="VA_COTA_VALOR_SUGERIDO")
    private BigDecimal cotaMensalValorSugerido;

    @Column(name="VA_COTA_LITROS_SUGERIDO")
    private BigDecimal cotaMensalLitrosSugerido;

    @Column(name="VA_COTA_VALOR_SUGERIDO_MAX")
    private BigDecimal cotaMensalValorSugeridoMaximo;

    @Column(name="VA_COTA_LITROS_SUGERIDO_MAX")
    private BigDecimal cotaMensalLitrosSugeridoMaximo;

    @Column(name="VA_COTA_VALOR_SUG_ABAS")
    private BigDecimal cotaAbastecimentoValorSugerido;

    @Column(name="VA_COTA_LITROS_SUG_ABAS")
    private BigDecimal cotaAbastecimentoLitrosSugerido;

    @Column(name="VA_COTA_VALOR_SUG_MAX_ABAS")
    private BigDecimal cotaAbastecimentoValorSugeridoMaximo;

    @Column(name="VA_COTA_LITROS_SUG_MAX_ABAS")
    private BigDecimal cotaAbastecimentoLitrosSugeridoMaximo;

    @Column(name="VA_CONSUMO_VALOR")
    private BigDecimal valorConsumido;

    @Column(name="VA_CONSUMO_LITROS")
    private BigDecimal litrosConsumidos;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_VEICULO")
    private Veiculo veiculo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    @NotNull
    @Column(name = "DT_ALTERACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlteracao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public SaldoVeiculo getSaldoVeiculo() {
        return saldoVeiculo;
    }

    public void setSaldoVeiculo(SaldoVeiculo saldoVeiculo) {
        this.saldoVeiculo = saldoVeiculo;
    }

    public BigDecimal getCotaValor() {
        return cotaValor;
    }

    public void setCotaValor(BigDecimal cotaValor) {
        this.cotaValor = cotaValor;
    }

    public BigDecimal getCotaLitros() {
        return cotaLitros;
    }

    public void setCotaLitros(BigDecimal cotaLitros) {
        this.cotaLitros = cotaLitros;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public BigDecimal getCotaValorAbastecimento() {
        return cotaValorAbastecimento;
    }

    public void setCotaValorAbastecimento(BigDecimal cotaValorAbastecimento) {
        this.cotaValorAbastecimento = cotaValorAbastecimento;
    }

    public BigDecimal getCotaLitrosAbastecimento() {
        return cotaLitrosAbastecimento;
    }

    public void setCotaLitrosAbastecimento(BigDecimal cotaLitrosAbastecimento) {
        this.cotaLitrosAbastecimento = cotaLitrosAbastecimento;
    }

    public BigDecimal getValorConsumido() {
        return valorConsumido;
    }

    public void setValorConsumido(BigDecimal valorConsumido) {
        this.valorConsumido = valorConsumido;
    }

    public BigDecimal getLitrosConsumidos() {
        return litrosConsumidos;
    }

    public void setLitrosConsumidos(BigDecimal litrosConsumidos) {
        this.litrosConsumidos = litrosConsumidos;
    }

    public BigDecimal getCotaMensalValorSugerido() {
        return cotaMensalValorSugerido;
    }

    public void setCotaMensalValorSugerido(BigDecimal cotaMensalValorSugerido) {
        this.cotaMensalValorSugerido = cotaMensalValorSugerido;
    }

    public BigDecimal getCotaMensalLitrosSugerido() {
        return cotaMensalLitrosSugerido;
    }

    public void setCotaMensalLitrosSugerido(BigDecimal cotaMensalLitrosSugerido) {
        this.cotaMensalLitrosSugerido = cotaMensalLitrosSugerido;
    }

    public BigDecimal getCotaMensalValorSugeridoMaximo() {
        return cotaMensalValorSugeridoMaximo;
    }

    public void setCotaMensalValorSugeridoMaximo(BigDecimal cotaMensalValorSugeridoMaximo) {
        this.cotaMensalValorSugeridoMaximo = cotaMensalValorSugeridoMaximo;
    }

    public BigDecimal getCotaMensalLitrosSugeridoMaximo() {
        return cotaMensalLitrosSugeridoMaximo;
    }

    public void setCotaMensalLitrosSugeridoMaximo(BigDecimal cotaMensalLitrosSugeridoMaximo) {
        this.cotaMensalLitrosSugeridoMaximo = cotaMensalLitrosSugeridoMaximo;
    }

    public BigDecimal getCotaAbastecimentoValorSugerido() {
        return cotaAbastecimentoValorSugerido;
    }

    public void setCotaAbastecimentoValorSugerido(BigDecimal cotaAbastecimentoValorSugerido) {
        this.cotaAbastecimentoValorSugerido = cotaAbastecimentoValorSugerido;
    }

    public BigDecimal getCotaAbastecimentoLitrosSugerido() {
        return cotaAbastecimentoLitrosSugerido;
    }

    public void setCotaAbastecimentoLitrosSugerido(BigDecimal cotaAbastecimentoLitrosSugerido) {
        this.cotaAbastecimentoLitrosSugerido = cotaAbastecimentoLitrosSugerido;
    }

    public BigDecimal getCotaAbastecimentoValorSugeridoMaximo() {
        return cotaAbastecimentoValorSugeridoMaximo;
    }

    public void setCotaAbastecimentoValorSugeridoMaximo(BigDecimal cotaAbastecimentoValorSugeridoMaximo) {
        this.cotaAbastecimentoValorSugeridoMaximo = cotaAbastecimentoValorSugeridoMaximo;
    }

    public BigDecimal getCotaAbastecimentoLitrosSugeridoMaximo() {
        return cotaAbastecimentoLitrosSugeridoMaximo;
    }

    public void setCotaAbastecimentoLitrosSugeridoMaximo(BigDecimal cotaAbastecimentoLitrosSugeridoMaximo) {
        this.cotaAbastecimentoLitrosSugeridoMaximo = cotaAbastecimentoLitrosSugeridoMaximo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    @Transient
    public BigDecimal getSaldoLitros() {
        if(this.litrosConsumidos == null) {
            return this.cotaLitros;
        }
        return this.cotaLitros != null ? cotaLitros.subtract(litrosConsumidos) : null;
    }

    @Transient
    public BigDecimal getSaldoValor() {
        if(this.valorConsumido == null) {
            return this.cotaValor;
        }
        return this.cotaValor != null ? cotaValor.subtract(valorConsumido).setScale(2, BigDecimal.ROUND_HALF_UP) : null;
    }

    @Override
    public List<Frota> getFrotas() {
        return veiculo != null ? veiculo.getFrotas() : null;
    }

    /**
     * Verifica se o saldo do veiculo é suficiente para a autorizacao
     *
     * @param montanteAbastecimento Valor ou litros abastecidos
     * @param emLitros True se o montante estiver em litros
     * @param mensal True se a cota do veiculo eh mensal
     * @return True caso o saldo seja suficiente.
     */
    @Transient
    public boolean isSaldoSuficienteParaAutorizar(BigDecimal montanteAbastecimento, Boolean emLitros, Boolean mensal) {
        BigDecimal disponivel = saldoDisponivel(emLitros, mensal);
        if (disponivel == null && veiculo.isAgregado()) {
            return false;
        }
        if (disponivel != null) {
            BigDecimal saldoResultante = disponivel.subtract(montanteAbastecimento);
            return saldoResultante.compareTo(BigDecimal.ZERO) >= 0;
        }
        return true;
    }

    /**
     * Verifica se o saldo do veiculo é suficiente para a pre autorizacao
     *
     * @param emLitros True se o montante estiver em litros
     * @param mensal True se a cota do veiculo eh mensal
     * @return True caso o saldo seja suficiente.
     */
    @Transient
    public boolean isSaldoSuficienteParaPreAutorizar(Boolean emLitros, Boolean mensal) {
        BigDecimal disponivel = saldoDisponivel(emLitros, mensal);
        if (disponivel == null && veiculo.isAgregado()) {
            return false;
        }
        if (disponivel != null) {
            return disponivel.compareTo(BigDecimal.ZERO) > 0;
        }
        return true;
    }

    /**
     * Retorna o saldo disponível de um veículo de acordo com sua cota
     * @param emLitros True se a cota do veículo for em litros
     * @param mensal True se a cota do veículo for mensal
     * @return Valor do saldo disponível
     */
    private BigDecimal saldoDisponivel(Boolean emLitros, Boolean mensal){
        return  (mensal == null || !mensal)
                ? (emLitros ? getCotaLitrosAbastecimento() : getCotaValorAbastecimento())
                : (emLitros ? getSaldoLitros() : getSaldoValor());
    }


}
