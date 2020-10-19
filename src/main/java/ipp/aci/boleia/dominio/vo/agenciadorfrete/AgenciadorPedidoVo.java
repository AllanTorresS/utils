package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import com.fasterxml.jackson.annotation.JsonFormat;
import ipp.aci.boleia.dominio.agenciadorfrete.MotoristaAutonomo;
import ipp.aci.boleia.dominio.agenciadorfrete.Pedido;
import ipp.aci.boleia.util.ConstantesFormatacao;
import ipp.aci.boleia.util.UtilitarioFormatacao;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Classe com informações referentes do pedido do motorista autonomo
 */
public class AgenciadorPedidoVo {

    private String numero;
    private Long motoristaId;
    private String placa;
    private BigDecimal precoUnitario;
    @JsonFormat(pattern = ConstantesFormatacao.FORMATO_ISO_8601_COM_MILLIS_E_TIMEZONE, timezone = ConstantesFormatacao.FORMATO_TIMEZONE_GMT_3)
    private Date dataPedido;
    @JsonFormat(pattern = ConstantesFormatacao.FORMATO_ISO_8601_COM_MILLIS_E_TIMEZONE, timezone = ConstantesFormatacao.FORMATO_TIMEZONE_GMT_3)
    private Date dataExpiracao;
    private PostoBasicoVo posto;
    private AbastecimentoVo abastecimento;
    private String litragem;
    private CombustivelVo combustivel;
    private Long agenciadorFreteId;


    public AgenciadorPedidoVo() {
        //Contrutor default
    }

    public AgenciadorPedidoVo(Pedido pedido) {
        this.numero = pedido.getNumero();
        this.placa = UtilitarioFormatacao.formatarPlacaVeiculo(pedido.getPlaca());
        this.precoUnitario = pedido.getPrecoUnitario();
        this.dataPedido = pedido.getDataCriacao();
        this.dataExpiracao = pedido.getDataExpiracao();
        this.posto = pedido.getPosto() != null ? new PostoBasicoVo(pedido.getPosto()) : null;
        this.litragem = pedido.getLitragem().toString();
        this.combustivel = pedido.getTipoCombustivel() != null ? new CombustivelVo(pedido.getTipoCombustivel(), pedido.getPrecoUnitario()) : null;
        MotoristaAutonomo motoristaAutonomo = pedido.getMotorista();
        if(motoristaAutonomo != null) {
            this.motoristaId = pedido.getMotorista().getId();
            this.agenciadorFreteId = motoristaAutonomo.getAgenciadorFrete() !=null ? motoristaAutonomo.getAgenciadorFrete().getId() : null;
        }
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public PostoBasicoVo getPosto() {
        return posto;
    }

    public void setPosto(PostoBasicoVo posto) {
        this.posto = posto;
    }

    public AbastecimentoVo getAbastecimento() {
        return abastecimento;
    }

    public void setAbastecimento(AbastecimentoVo abastecimento) {
        this.abastecimento = abastecimento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(Date dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public String getLitragem() {
        return litragem;
    }

    public void setLitragem(String litragem) {
        this.litragem = litragem;
    }

    public CombustivelVo getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(CombustivelVo combustivel) {
        this.combustivel = combustivel;
    }

    public Long getgenciadorFreteId() {
        return agenciadorFreteId;
    }

    public void setIdAgenciadorFrete(Long agenciadorFreteId) {
        this.agenciadorFreteId = agenciadorFreteId;
    }

    public Long getMotoristaId() {
        return motoristaId;
    }

    public void setMotoristaId(Long motoristaId) {
        this.motoristaId = motoristaId;
    }

    public Long getAgenciadorFreteId() {
        return agenciadorFreteId;
    }

    public void setAgenciadorFreteId(Long agenciadorFreteId) {
        this.agenciadorFreteId = agenciadorFreteId;
    }
}
