package ipp.aci.boleia.dominio.vo;

import java.util.List;


/**
 * Classe que encapsula resposta da SASCAR
 * @param <T> Item da telemetria
 */
public class RespostaSascarVo <T> {

    private List<T> dados;
    private Integer ponteiroParaNovaRequisicao;
    private boolean proximaRequisicao;

    /**
     * Construtor padrão
     */
    public RespostaSascarVo() {

    }

    /**
     * Construtor
     * @param dados Dados da resposta
     * @param ponteiroParaNovaRequisicao Ponteiro para próxima requisição
     */
    public RespostaSascarVo(List<T> dados, Integer ponteiroParaNovaRequisicao) {
        this.dados = dados;
        this.ponteiroParaNovaRequisicao = ponteiroParaNovaRequisicao;
        this.proximaRequisicao = ponteiroParaNovaRequisicao != null;
    }

    /**
     * Construtor
     * @param dados Dados da resposta
     * @param proximaRequisicao Indica se necessita fazer próxima requisição
     */
    public RespostaSascarVo(List<T> dados, boolean proximaRequisicao) {
        this.dados = dados;
        this.proximaRequisicao = proximaRequisicao;
    }

    public List<T> getDados() {
        return dados;
    }

    public void setDados(List<T> dados) {
        this.dados = dados;
    }

    public Integer getPonteiroParaNovaRequisicao() {
        return ponteiroParaNovaRequisicao;
    }

    public void setPonteiroParaNovaRequisicao(Integer ponteiroParaNovaRequisicao) {
        this.ponteiroParaNovaRequisicao = ponteiroParaNovaRequisicao;
    }

    public boolean isProximaRequisicao() {
        return proximaRequisicao;
    }

    public void setProximaRequisicao(boolean proximaRequisicao) {
        this.proximaRequisicao = proximaRequisicao;
    }
}
