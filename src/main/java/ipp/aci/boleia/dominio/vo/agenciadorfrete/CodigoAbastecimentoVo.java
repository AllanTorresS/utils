package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import com.fasterxml.jackson.annotation.JsonFormat;
import ipp.aci.boleia.util.ConstantesFormatacao;

import java.io.Serializable;
import java.util.Date;

/**
 * Representa um código de abastecimento de um pedido do motorista autonomo
 */
public class CodigoAbastecimentoVo implements Serializable {

    private static final long serialVersionUID = 286809531964171236L;

    private String numeroPedido;
    private String codigo;
    @JsonFormat(pattern = ConstantesFormatacao.FORMATO_ISO_8601_COM_MILLIS_E_TIMEZONE, timezone = ConstantesFormatacao.FORMATO_TIMEZONE_GMT_3)
    private Date dataExpiracao;

    public CodigoAbastecimentoVo() {
        // serializacao json
    }

    public CodigoAbastecimentoVo(String codigo, String numeroPedido, Date dataExpiracao) {
        this.numeroPedido = numeroPedido;
        this.codigo = codigo;
        this.dataExpiracao = dataExpiracao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(Date dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }
}
