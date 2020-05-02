package ipp.aci.boleia.dominio.pesquisa.comum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ipp.aci.boleia.util.Ordenacao;

/**
 * Representa uma coluna a ser utilizada na ordenacao de pesquisa para clausula
 * SQL ORDER BY
 */
public class ParametroOrdenacaoColuna {

    private String nome;
    private boolean decrescente;
    private String nomeNulo;
    private boolean tratarComoString;
    private boolean truncarData;

    /**
     * Construtor sem parametros, necessario para serializacao JSON
     */
    public ParametroOrdenacaoColuna() {
        // necessario para serializacao JSON
    }

    /**
     * Cira uma ordenacao no sentido desejado para a coluna informada
     *
     * @param nome        O nome da coluna de ordenacao
     * @param ordenacao O sentido da ordenacao desejado
     */
    public ParametroOrdenacaoColuna(String nome, Ordenacao ordenacao) {
        this.nome = nome;
        this.decrescente = Ordenacao.DECRESCENTE.equals(ordenacao);
    }

    /**
     * Cria uma ordenacao crescente para a coluna informada
     *
     * @param nome        O nome da coluna de ordenacao
     */
    public ParametroOrdenacaoColuna(String nome) {
        this(nome, Ordenacao.CRESCENTE);
    }

    /**
     * Cira uma ordenacao no sentido desejado para a coluna temporal informada
     *
     * @param nome        O nome da coluna de ordenacao
     * @param ordenacao O sentido da ordenacao desejado
     * @param truncarData True para truncar data ate o dia
     */
    public ParametroOrdenacaoColuna(String nome, Ordenacao ordenacao, boolean truncarData) {
        this(nome, ordenacao);
        this.truncarData = truncarData;
    }

    /**
     * Returna o nome da coluna de ordenacao
     *
     * @return O nome da coluna de ordenacao
     */
    public String getNome() {
        return nome;
    }

    /**
     * Atribui o nome da coluna de ordenacao
     *
     * @param nome O nome da coluna
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna true caso ordenacao decrescente
     *
     * @return True caso ordenacao decrescente
     */
    public boolean isDecrescente() {
        return decrescente;
    }

    /**
     * Atribui a ordenacao decrescente
     *
     * @param decrescente True caso ordenacao decrescente
     */
    public void setDecrescente(boolean decrescente) {
        this.decrescente = decrescente;
    }

    /**
     * Retorna o nome nulo para ordenação
     *
     * @return O nome nulo da ordenação
     */
    public String getNomeNulo() {
        return nomeNulo;
    }

    /**
     * Atribui o nome nulo para ordenação
     *
     * @param nomeNulo nome nulo da ordenação
     */
    public void setNomeNulo(String nomeNulo) {
        this.nomeNulo = nomeNulo;
    }

    /**
     * Retorna se parametro deve ser tratado como string
     *
     * @return parametro deve ser tratado como string
     */
    public boolean isTratarComoString() {
        return tratarComoString;
    }
    
    /**
     * Atribui se parametro deve ser tratado como string
     *
     * @param tratarComoString se parametro deve ser tratado como string
     */
    public void setTratarComoString(boolean tratarComoString) {
        this.tratarComoString = tratarComoString;
    }

    public boolean isTruncarData() {
        return truncarData;
    }

    public void setTruncarData(boolean truncarData) {
        this.truncarData = truncarData;
    }

    @JsonIgnore
    public Ordenacao getSentidoOrdenacao() { return this.decrescente ? Ordenacao.DECRESCENTE : Ordenacao.CRESCENTE; }
}
