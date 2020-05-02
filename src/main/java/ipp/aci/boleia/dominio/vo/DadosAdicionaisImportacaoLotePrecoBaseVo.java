package ipp.aci.boleia.dominio.vo;

import java.util.Date;

/**
 * Objeto que encapsula as informacoes adicionais de uma importacao em lote de precos posto.
 */
public class DadosAdicionaisImportacaoLotePrecoBaseVo implements IDadosAdicionaisImportacaoLoteVo{

    private Date dataEnvio;
    private Boolean requerAceite;

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Boolean getRequerAceite() {
        return requerAceite;
    }

    public void setRequerAceite(Boolean requerAceite) {
        this.requerAceite = requerAceite;
    }
}
