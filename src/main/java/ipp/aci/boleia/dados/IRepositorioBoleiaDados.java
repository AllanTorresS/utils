package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;

import java.util.List;

/**
 * Contrato para criacao de intefaces de repositorios de entidades
 * 
 * @param <T> A classe das entidades a serem manipuladas
 */
public interface IRepositorioBoleiaDados<T extends IPersistente> {



    /**
     * Recupera entidade por ID
     * @param id O id da entidade a recuperar
     * @return A entidade recuperada
     */
    T obterPorId(Long id);

    /**
     * Recupera entidade por ID sem isolamento de dados
     * @param id O id da entidade a recuperar
     * @return A entidade recuperada
     */
    T obterPorIdSemIsolamento(Long id);

    /**
     * Cria ou altera (caso possua ID) uma entidade
     * @param t A entidade a ser criada/alterada
     * @return A entidade criada/alterada
     */
    T armazenar(T t);

    /**
     * Cria ou altera (caso possua ID) uma lista de entidade
     * @param list A lista de entidade a ser criada/alterada
     * @return A lista de entidade criada/alterada
     */
    List<T> armazenarLista(List<T> list);

    /**
     * Exclui um conjunto de entidades
     * @param ids os ids das entidades a serem removidas
     */
    void excluir(Long... ids);

    /**
     * Obtem todas entidades considerando o isolamento de dados entre os usuarios e a exclusao logica
     * @param ordenacao Modo de ordenacao
     * @return Todas entidades
     */
    List<T> obterTodos(ParametroOrdenacaoColuna ordenacao);

    /**
     * Obtem todas entidades com relacionamentos
     * @param ordenacao das entidades
     * @param relacionamento a obter
     * @return Entidades com relacionamentos
     */
    List<T> obterTodosComRelacionamento(ParametroOrdenacaoColuna ordenacao, String... relacionamento);

    /**
     * Armazena uma entidade sem verificação de isolamento de dados
     *
     * @param entidade a ser armazenada sem isolamento
     * @return A entidade recem armazenada
     */
    T armazenarSemIsolamentoDeDados(T entidade);

    /**
     * Exclui um conjunto de entidades sem verificação de isolamento de dados
     * @param ids os ids das entidades a serem removidas sem isolamento
     */
    void excluirSemIsolamentoDeDados(Long... ids);

    /**
     * Recupera a classe da entidade associada ao repositório.
     * @return A classe da entidade do repositório
     */
    Class<T> getClasseDoRepositorio();

    /**
     * Recupera a entidade a partir de um identificador padrão
     *
     * @param identificador O identificador
     * @return A entidade obtida
     */
    T obterPorIdentificadorPadrao(String identificador);
}
