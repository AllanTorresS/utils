package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaParcialMotoristaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaMotoristaVo;
import ipp.aci.boleia.dominio.vo.externo.FiltroPesquisaMotoristaExtVo;
import ipp.aci.boleia.dominio.vo.frotista.FiltroPesquisaMotoristaFrtVo;
import ipp.aci.boleia.dominio.vo.frotista.ResultadoPaginadoFrtVo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades Motorista
 */
public interface IMotoristaDados extends IRepositorioBoleiaDados<Motorista> {

    /**
     * Busca a lista de motoristas presentes em um consolidado.
     *
     * @param termo Termo usado para filtrar os motoristas por nome.
     * @param idConsolidado Identificador do consolidado.
     * @return a lista de motoristas
     */
    List<Motorista> listarMotoristasPorTermoEConsolidado(String termo, Long idConsolidado);

    /**
     * Pesquisa Motoristas a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return Uma lista de entidades localizadas
     */
    ResultadoPaginado<Motorista> pesquisar(FiltroPesquisaMotoristaVo filtro);

    /**
     * Pesquisa Motoristas para a API do frotista a partir do filtro informado
     * @param filtro informado pelo frotista
     * @return Lista de motoristas localizados
     */
    ResultadoPaginadoFrtVo<Motorista> pesquisar(FiltroPesquisaMotoristaFrtVo filtro);

    /**
     * Pesquisa Motoristas para APIs externas a partir do filtro informado
     * @param filtro informado pelo sistema externo
     * @return Lista de motoristas localizados
     */
    ResultadoPaginado<Motorista> pesquisar(FiltroPesquisaMotoristaExtVo filtro);

    /**
     * Localiza o motorista pelo seu CPF
     *
     * @param cpf O CPF desejado
     * @return O motorista localizado ou null
     */
    Motorista obterPorCpf(Long cpf);

    /**
     * Localiza todos os motoristas cadastrado na solução com o CPF especificado.
     *
     * @param cpf O CPF desejado
     * @return Os motoristas localizados
     */
    List<Motorista> obterPorCpfSemIsolamento(Long cpf);

    /**
     * Localiza todos os motoristas cadastrado na solução com o CPF especificado.
     * Limita a quantidade de resultados da busca ao parâmetro fornecido.
     *
     * @param cpf O CPF desejado
     * @param quantidadeResultados máximo de resultados que podem ser retornados na consulta
     * @return Os motoristas localizados
     */
    List<Motorista> obterPorCpfSemIsolamento(Long cpf, Integer quantidadeResultados);

    /**
     * Retorna todos os motoristas da solução que utilizam app e possuam o CPF especificado.
     * A consulta considera apenas Motoristas e Frotas ativos.
     *
     * @param cpf O CPF desejado
     * @return os motoristas localizados
     */
    List<Motorista> obterAtivosPorCpfSemIsolamento(String cpf);

    /**
     * Localiza um motorista pelo telefone
     * @param celular o telefone do motorista no formato (XX)XXXXX-XXXX
     * @return O motorista localizado ou null
     */
    Motorista obterPorCelular(String celular);

    /**
     * Localiza o motorista pelo seu CPF e Frota
     *
     * @param cpf O CPF desejado
     * @param idFrota id da frota do motorista
     * @return O motorista localizado ou null
     */
    Motorista obterPorCpfFrotaSemIsolamento(Long cpf, Long idFrota);

    /**
     * Recupera um motorista que possua o cpf especificado.
     * Dados como nome e número de telefone dos motoristas são replicados entre todas as frotas às quais ele pertence.
     * Esse método obtém apenas o primeiro desses registros de Motorista.
     *
     * @param cpf O CPF desejado
     * @return o motorista localizado ou null
     */
    Motorista obterUnicoPorCpfSemIsolamento(Long cpf);

    /**
     * Desvincula os motoristas das unidades informadas
     * @param unidadeId os id da unidade em questão
     */
    void desvincularUnidades(Long unidadeId);

    /**
     * Obtem um motorista ativo por cpf
     * @param cpf o cpf informado
     * @return o motorista
     */
    Motorista obterAtivoPorCpf(String cpf);

    /**
     * Pesquisa motoristas por nome ou CPF
     * @param filtro filtro contendo nome ou CPF do motorista
     * @return os motoristas encontrados
     */
    List<Motorista> pesquisarPorCpfNome(FiltroPesquisaParcialMotoristaVo filtro);

    /**
     * Exclui os dados pessoais dos motoristas armazenados após o tempo determinado via parãmetro
     * @param diasDeArmazenamento quantidade de dias nos quais os dados permaneceram armazenados na base de dados
     */
    void excluirDadosPessoais(Integer diasDeArmazenamento);

    /**
     * Obtem motoristas que não realizaram abastecimentos até o momento
     * @param diasDeVerificacao quantidade de dias que os motoristas estão cadastrados
     * @return os motoristas encontrados
     */
    List<Motorista> obterMotoristasSemAbastecimento(Integer diasDeVerificacao);

    /**
     * Obtem motoristas que realizaram abastecimentos e estão inativos
     * @param diasDeVerificacao quantidade de dias que os motoristas estão cadastrados
     * @return os motoristas encontrados
     */
    List<Motorista> obterMotoristasInativosComAbastecimento(Integer diasDeVerificacao);

    /**
     * Obtem motoristas que estão excluídos
     * @return os motoristas encontrados
     */
    List<Motorista> obterMotoristasExcluidos();

    /**
     * Obtem motorista excluido por id
     * @return o motorista encontrado
     */
    Motorista obterMotoristaExcluidoPorId(Long idMotorista);

}
