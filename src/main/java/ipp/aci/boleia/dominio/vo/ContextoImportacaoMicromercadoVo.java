package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.Micromercado;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Armazena dados coletados antes do processamento das linhas da planilha, para
 * ganho de performance.
 */
public class ContextoImportacaoMicromercadoVo implements IDadosIniciaisImportacaoLoteVo {

    private Map<String, Micromercado> micromercadoIndexados;
    private Date dataAtual;

    /**
     * Construtor default
     */
    public ContextoImportacaoMicromercadoVo() {
        // construtor default
    }

    /**
     * Constroi o contexto para importacao dos micromercados
     *
     * @param micromercados A lista de micromercados
     * @param dataAtual A data atual
     */
    public ContextoImportacaoMicromercadoVo(List<Micromercado> micromercados, Date dataAtual) {
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
     * Obtem o micromercado por chave
     * @param chave do micromercado
     * @return O micromercado da chave
     */
    public Micromercado getMicromercadoPorChave(String chave) {
        return micromercadoIndexados.get(chave);
    }

    /**
     * Obtem a data atual
     * @return data atual
     */
    public Date getDataAtual() {
        return dataAtual;
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