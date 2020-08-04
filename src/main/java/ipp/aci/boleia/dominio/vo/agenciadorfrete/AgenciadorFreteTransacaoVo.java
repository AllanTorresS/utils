package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;
import ipp.aci.boleia.dominio.enums.StatusAutorizacao;
import ipp.aci.boleia.util.anotacoes.Numeric;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Classe com informações referentes a transação do consumo de um motorista autonomo em um posto
 */
public class AgenciadorFreteTransacaoVo {

    @NotNull
    private String numeroPedido;
    @NotNull
    private String cpfMotorista;
    @NotNull
    private String codigoAbastecimento;
    @NotNull
    private String placaVeiculo;
    @NotNull
    private AgenciadorFretePostoBasicoVo posto;
    private AgenciadorFreteAbastecimentoVo abastecimento;
    private AgenciadorFreteSaqueVo saque;
    @Size(max=4)
    @Numeric
    private String codigoVip;
    private String motivoRecusa;
    private String status;


    public AgenciadorFreteTransacaoVo() {
        //Construtor default
    }

    public AgenciadorFreteTransacaoVo(Transacao transacao) {
        this.numeroPedido = transacao.getPedido() != null ? transacao.getPedido().getNumero() : null;
        this.cpfMotorista = transacao.getMotorista() != null ? transacao.getMotorista().getCpf() : null;
        this.posto = new AgenciadorFretePostoBasicoVo(transacao.getPosto());
        this.abastecimento = new AgenciadorFreteAbastecimentoVo(transacao.getAbastecimento());
        this.saque = transacao.getSaque() != null ? new AgenciadorFreteSaqueVo(transacao.getSaque()): null;
        this.placaVeiculo = transacao.getPlacaVeiculo();
        this.status = StatusAutorizacao.obterPorValor(transacao.getStatus()).getLabel();
        this.motivoRecusa = transacao.getMotivoRecusa();
        this.codigoVip = transacao.getCodigoVip();
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

    public AgenciadorFretePostoBasicoVo getPosto() {
        return posto;
    }

    public void setPosto(AgenciadorFretePostoBasicoVo posto) {
        this.posto = posto;
    }

    public AgenciadorFreteAbastecimentoVo getAbastecimento() {
        return abastecimento;
    }

    public void setAbastecimento(AgenciadorFreteAbastecimentoVo abastecimento) {
        this.abastecimento = abastecimento;
    }

    public AgenciadorFreteSaqueVo getSaque() {
        return saque;
    }

    public void setSaque(AgenciadorFreteSaqueVo saque) {
        this.saque = saque;
    }

    public String getCodigoVip() {
        return codigoVip;
    }

    public void setCodigoVip(String codigoVip) {
        this.codigoVip = codigoVip;
    }

    public String getCodigoAbastecimento() {
        return codigoAbastecimento;
    }

    public void setCodigoAbastecimento(String codigoAbastecimento) {
        this.codigoAbastecimento = codigoAbastecimento;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public String getMotivoRecusa() {
        return motivoRecusa;
    }

    public void setMotivoRecusa(String motivoRecusa) {
        this.motivoRecusa = motivoRecusa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
