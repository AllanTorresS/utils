package ipp.aci.boleia.util.negocio;

import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Construtor de parâmetros de pesquisa.
 * 
 */
public class ParametrosPesquisaBuilder {

    private final List<ParametroPesquisa> parametros;

    /**
     * Construtor do builder
     */
    public ParametrosPesquisaBuilder() {
        parametros = new ArrayList<>();
    }

    /**
     * Construtor do builder com parametros iniciais
     */
    public ParametrosPesquisaBuilder(ParametroPesquisa ... params) {
        parametros = new ArrayList<>(params.length);
        adicionarParametros(params);
    }

    /**
     * Método que realiza o build da lista de parâmetros.
     * 
     * @return Lista de Parâmetros criadas.
     */
    public List<ParametroPesquisa> build() {
        return parametros;
    }

    /**
     * Método que realiza o build da lista de parâmetros.
     * 
     * @return Array de Parâmetros criados.
     */
    public ParametroPesquisa[] buildArray() {
        return parametros.toArray(new ParametroPesquisa[parametros.size()]);
    }

    /**
     * Método que adiciona parâmetros a lista de parâmetros usados verificando 
     * se valor é nulo ou string vazia
     * 
     * @param valor Valor do filtro.
     * @param criador Criador para parâmetro (obrigatório)
     * @param criadores Lista de criadores para parâmetros.
     * @return O próprio builder.
     * 
     */
    public ParametrosPesquisaBuilder adicionarParametros(Object valor, CriadorParametro criador, CriadorParametro ... criadores) {
        if(valor != null && valorNaoEhStringVazia(valor)) {
            parametros.add(criador.criar(valor));
            for(CriadorParametro c : criadores) {
                parametros.add(c.criar(valor));
            }
        }
        return this;
    }

    /**
     * Método que adiciona parâmetros.
     * 
     * @param params Os parâmetros a serem adicionados.
     * 
     * @return O próprio builder.
     */
    public ParametrosPesquisaBuilder adicionarParametros(ParametroPesquisa ... params) {
        Collections.addAll(parametros, params);
        return this;
    }

    /**
     * Retorna false caso o objeto seja uma string vazia
     * @param valor O objeto
     * @return False caso o objeto seja uma string vazia
     */
    private boolean  valorNaoEhStringVazia(Object valor) {
        if(valor instanceof String) {
            return StringUtils.isNotBlank((String) valor);
        }
        return true;
    }

    /**
     * Interface de abstracao para criacao dos parametros de pesquisa
     */
    @FunctionalInterface
    public interface  CriadorParametro {
        /**
         * Cria um parametro de pesquisa a partir de um valor dado
         * @param valor O valor do parametro
         * @return Um parametro de pesquisa
         */
        ParametroPesquisa criar(Object valor);
    }

}
