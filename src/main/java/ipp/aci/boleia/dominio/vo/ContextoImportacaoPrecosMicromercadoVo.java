package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.Micromercado;
import ipp.aci.boleia.dominio.PrecoMicromercado;
import ipp.aci.boleia.dominio.TipoCombustivel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Armazena dados coletados antes do processamento das linhas da planilha, para
 * ganho de performance.
 */
public class ContextoImportacaoPrecosMicromercadoVo implements IDadosIniciaisImportacaoLoteVo {

    private Map<String, PrecoMicromercado> anterioresIndexados;
    private Map<String, TipoCombustivel> combustiveisIndexados;
    private Map<String, Micromercado> micromercadoIndexados;
    private Date dataAtual;

    /**
     * Construtor default
     */
    public ContextoImportacaoPrecosMicromercadoVo() {
        // construtor default
    }

    /**
     * Constroi o contexto para importacao dos precos micromercado
     *
     * @param precosMicromercadoAntesImportacao A lista de precos atuais, antes da importacao
     * @param combustiveis A lista dos tipos de combustiveis
     * @param micromercados A lista de micromercados
     * @param dataAtual A data atual passada
     */
    public ContextoImportacaoPrecosMicromercadoVo(List<PrecoMicromercado> precosMicromercadoAntesImportacao, List<TipoCombustivel> combustiveis, List<Micromercado> micromercados, Date dataAtual) {
        this.anterioresIndexados = indexarPrecosMicromercado(precosMicromercadoAntesImportacao);
        this.combustiveisIndexados = indexarCombustiveis(combustiveis);
        this.micromercadoIndexados = indexarMicromercados(micromercados);
        this.dataAtual = dataAtual;
    }

    /**
     * Dada uma lista de micromercados, cria um mapa indexando-os pela chave,
     * para performance na busca.
     *
     * @param micromercados a lista de micromercados
     * @return O indice
     */
    private Map<String,Micromercado> indexarMicromercados(List<Micromercado> micromercados) {
        Map<String,Micromercado> indice = new HashMap<>();
        if(micromercados != null) {
            for(Micromercado micromercado : micromercados) {
                indice.put(micromercado.getChave(), micromercado);
            }
        }
        return indice;
    }

    /**
     * Dada uma lista de combustiveis, cria um mapa indexando-os pela descricao,
     * para performance na busca.
     *
     * @param combustiveis a lista de combustiveis
     * @return O indice
     */
    private Map<String,TipoCombustivel> indexarCombustiveis(List<TipoCombustivel> combustiveis) {
        Map<String,TipoCombustivel> indice = new HashMap<>();
        if(combustiveis != null) {
            for(TipoCombustivel combustivel : combustiveis) {
                indice.put(combustivel.getDescricao(), combustivel);
            }
        }
        return indice;
    }

    /**
     * Dado um preco micromercado atual, busca o anterior
     * @param precoMicromercadoAtual preco atual de micromercado
     * @return O preco anterior, caso exista
     */
    public PrecoMicromercado getPrecoMicromercadoAnterior(PrecoMicromercado precoMicromercadoAtual) {
        return anterioresIndexados.get(criarChaveMicromercadoCombustivel(precoMicromercadoAtual));
    }

    /**
     * Obtem o combustivel por descricao
     * @param descricao do combustivel
     * @return O tipo combustivel
     */
    public TipoCombustivel getTipoCombustivelPorDescricao(String descricao) {
        return combustiveisIndexados.get(descricao);
    }

    /**
     * Obtem o micromercado por chave
     * @param chave do micromercado
     * @return O micromercado da chave
     */
    public Micromercado getMicromercadoPorChave(String chave) {
        return micromercadoIndexados.get(chave);
    }

    /**
     * Dada uma lista de precos micromercado, cria um mapa indexando-os pelo micromercado e produto,
     * para performance na busca.
     *
     * @param precoMicromercados a lista de precos micromercado
     * @return O indice
     */
    private Map<String,PrecoMicromercado> indexarPrecosMicromercado(List<PrecoMicromercado> precoMicromercados) {
        Map<String,PrecoMicromercado> indice = new HashMap<>();
        if(precoMicromercados != null) {
            for (PrecoMicromercado p : precoMicromercados) {
                indice.put(criarChaveMicromercadoCombustivel(p), p);
            }
        }
        return indice;
    }

    /**
     * Cria uma chave de indexacao para o preco micromercado,
     * baseada no tipo combustivel e micromercado
     *
     * @param p O preco micromercado
     * @return A chave de indexacao
     */
    private String criarChaveMicromercadoCombustivel(PrecoMicromercado p) {
        if(p.getMicromercado()!=null && p.getTipoCombustivel()!=null) {
            return String.format("%d_%d", p.getMicromercado().getId(), p.getTipoCombustivel().getId());
        }
        return null;
    }

    /**
     * Obtem a data atual
     * @return data atual
     */
    public Date getDataAtual() {
        return dataAtual;
    }

    public Map<String, PrecoMicromercado> getAnterioresIndexados() {
        return anterioresIndexados;
    }

    public void setAnterioresIndexados(Map<String, PrecoMicromercado> anterioresIndexados) {
        this.anterioresIndexados = anterioresIndexados;
    }

    public Map<String, TipoCombustivel> getCombustiveisIndexados() {
        return combustiveisIndexados;
    }

    public void setCombustiveisIndexados(Map<String, TipoCombustivel> combustiveisIndexados) {
        this.combustiveisIndexados = combustiveisIndexados;
    }

    public Map<String, Micromercado> getMicromercadoIndexados() {
        return micromercadoIndexados;
    }

    public void setMicromercadoIndexados(Map<String, Micromercado> micromercadoIndexados) {
        this.micromercadoIndexados = micromercadoIndexados;
    }

    public void setDataAtual(Date dataAtual) {
        this.dataAtual = dataAtual;
    }
}