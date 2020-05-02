package ipp.aci.boleia.dominio.pesquisa.comum;

import ipp.aci.boleia.util.UtilitarioLambda;

import java.util.List;
import java.util.function.Function;

/**
 * Representa um resultado paginado de pesquisa
 *
 * @param <T> A classe dos registros obtidos
 */
public class ResultadoPaginado<T> {

    private int totalItems;
    private List<T> registros;
    private List<String> observacoes;

    /**
     * Construtor padrao, necessario para serializacao JSON
     */
    public ResultadoPaginado() {
        // necessario para serializacao JSON
    }

    /**
     * Construtor
     *
     * @param registros  Os registros encontrados
     * @param totalItems O numero total de itens
     */
    public ResultadoPaginado(List<T> registros, int totalItems) {
        this.registros = registros;
        this.totalItems = totalItems;
    }

    /**
     * Obtem o total de itens localizados
     *
     * @return O total de itens
     */
    public int getTotalItems() {
        return totalItems;
    }

    /**
     * Atribui o total de itens localizados
     *
     * @param totalItems O total de itens
     */
    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    /**
     * Obtem a lista de registros da pagina
     *
     * @return A lista de registros da pagina
     */
    public List<T> getRegistros() {
        return registros;
    }

    /**
     * Atribui os registros da pagina
     *
     * @param registros Os registros da pagina
     */
    public void setRegistros(List<T> registros) {
        this.registros = registros;
    }

    /**
     * Retorna uma mensagem informativa sobre o resultado da busca
     *
     * @return Uma mensagem informativa sobre o resultado da busca
     */
    public List<String> getObservacoes() {
        return observacoes;
    }

    /**
     * Atribui uma mensagem informativa sobre o resultado da busca
     *
     * @param observacoes Uma mensagem informativa sobre o resultado da busca
     */
    public void setObservacoes(List<String> observacoes) {
        this.observacoes = observacoes;
    }

    /**
     * Converte este resultado paginado para o tipo desejado
     *
     * @param mapper Uma lambda mapper capaz de realizar a conversão de <code>T</code> para <code>R</code>
     * @param <R> O tipo dos registros desejados
     * @return Um ResultadoPaginado&lt;<code>R</code>&gt;
     */
    public <R> ResultadoPaginado<R> converter(Function<T,R> mapper) {
        List<R> listaR = UtilitarioLambda.converterLista(this.getRegistros(), mapper);
        return new ResultadoPaginado<>(listaR, this.getTotalItems());
    }
}