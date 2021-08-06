package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.enums.ParametroSistema;
import ipp.aci.boleia.dominio.enums.StatusExecucaoParametroSistema;

/**
 * Modela o resultade de execucao de um parametro dinamico do sistema
 * @param <D> O contexto de execucao
 */
public class ResultadoExecucaoParametroSistemaVo<D> extends ResultadoExecucaoParametroSistemaBaseVo {

    private D dados;
    private FrotaParametroSistema parametro;

    /**
     * Construtor default
     */
    public ResultadoExecucaoParametroSistemaVo() {
        // construtor default
    }

    /**
     * Construtor do resultado de execucao do parametro do sistema
     * @param dados Os dados do contexto de execucao
     */
    public ResultadoExecucaoParametroSistemaVo(D dados) {
        this.dados = dados;
    }

    public D getDados() {
        return dados;
    }

    public void setDados(D dados) {
        this.dados = dados;
    }

    public FrotaParametroSistema getParametro() {
        return parametro;
    }

    public void setParametro(FrotaParametroSistema parametro) {
        this.parametro = parametro;
    }

    public boolean isErro() {
        return !StatusExecucaoParametroSistema.SUCESSO.equals(this.statusResultado);
    }

    public boolean isRestritivo() {
        return parametro.isRestritivo();
    }

    public ParametroSistema getParametroSistema() {
        return ParametroSistema.obterPorCodigo(parametro.getParametroSistema());
    }
}
