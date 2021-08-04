package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.enums.StatusExecucaoParametroSistema;
import ipp.aci.boleia.util.excecao.Erro;

/**
 * Modela o resultado dos dados basicos de execucao de um parametro dinamico do sistema
 */
public abstract class ResultadoExecucaoParametroSistemaBaseVo {

    protected StatusExecucaoParametroSistema statusResultado = StatusExecucaoParametroSistema.SUCESSO;
    protected Erro codigoErro;
    protected String mensagemErro;

    public StatusExecucaoParametroSistema getStatusResultado() {
        return statusResultado;
    }

    public void setStatusResultado(StatusExecucaoParametroSistema statusResultado) {
        this.statusResultado = statusResultado;
    }

    public Erro getCodigoErro() {
        return codigoErro;
    }

    public void setCodigoErro(Erro codigoErro) {
        this.codigoErro = codigoErro;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }
}
