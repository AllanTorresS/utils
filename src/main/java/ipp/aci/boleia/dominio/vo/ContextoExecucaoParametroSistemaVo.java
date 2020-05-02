package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.enums.StatusExecucaoParametroSistema;

import java.util.ArrayList;
import java.util.List;

/**
 * Modela o contexto de execucao de um conjunto de parametros do sistema.
 * @param <D> O tipo do objeto que contem os dados para a execucao dos parametros
 */
public class ContextoExecucaoParametroSistemaVo<D> {

    private D dados;
    private List<ResultadoExecucaoParametroSistemaVo> resultados;
    private List<ResultadoExecucaoParametroSistemaVo> violacoesInformativas;
    private ResultadoExecucaoParametroSistemaVo violacaoRestritiva;
    private StatusExecucaoParametroSistema statusResultado;

    public ContextoExecucaoParametroSistemaVo() {
       // Construtor default
    }

    /**
     * Gera o contexto de execucao dos parametros do sistema de acordo com os dados passados
     * @param dados Os dados para a execucao dos parametros
     */
    public ContextoExecucaoParametroSistemaVo(D dados) {
        this.dados = dados;
        this.statusResultado = StatusExecucaoParametroSistema.SUCESSO;
        this.resultados = new ArrayList<>();
        violacoesInformativas = new ArrayList<>();
    }

    public D getDados() {
        return dados;
    }

    public List<ResultadoExecucaoParametroSistemaVo> getResultados() {
        return resultados;
    }

    public StatusExecucaoParametroSistema getStatusResultado() {
        return statusResultado;
    }

    public List<ResultadoExecucaoParametroSistemaVo> getViolacoesInformativas() {
        return violacoesInformativas;
    }

    public ResultadoExecucaoParametroSistemaVo getViolacaoRestritiva() {
        return violacaoRestritiva;
    }

    /**
     * Guarda o resultado passado da execucao dos parametros do sistema
     * @param resultado O resultado da execucao dos parametros do sistema
     */
    public void acumularResultado(ResultadoExecucaoParametroSistemaVo<D> resultado) {
        this.dados = resultado.getDados();
        this.resultados.add(resultado);
        if(resultado.isErro()) {
            this.statusResultado = StatusExecucaoParametroSistema.ERRO;
            if(resultado.isRestritivo()) {
                violacaoRestritiva = resultado;
            } else {
                violacoesInformativas.add(resultado);
            }
        }
    }

    public boolean isSucesso() {
        return StatusExecucaoParametroSistema.SUCESSO.equals(this.statusResultado);
    }

    /**
     * Verifica se existe violacao restritiva
     * @return True caso exista violacao e false caso nao exista
     */
    public boolean possuiViolacaoRestritiva() {
        return violacaoRestritiva != null;
    }

    /**
     * Verifica se existe violacao informativa
     * @return True caso exista violacao e false caso nao exista
     */
    public boolean possuiViolacaoInformativa() {
        return violacoesInformativas != null && !violacoesInformativas.isEmpty();
    }

    public void setDados(D dados) {
        this.dados = dados;
    }

    public void setResultados(List<ResultadoExecucaoParametroSistemaVo> resultados) {
        this.resultados = resultados;
    }

    public void setViolacoesInformativas(List<ResultadoExecucaoParametroSistemaVo> violacoesInformativas) {
        this.violacoesInformativas = violacoesInformativas;
    }

    public void setViolacaoRestritiva(ResultadoExecucaoParametroSistemaVo violacaoRestritiva) {
        this.violacaoRestritiva = violacaoRestritiva;
    }

    public void setStatusResultado(StatusExecucaoParametroSistema statusResultado) {
        this.statusResultado = statusResultado;
    }
}
