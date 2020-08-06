package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.DispositivoMotorista;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaDispositivoMotoristaVo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades DispositivoMotorista
 */
public interface IDispositivoMotoristaDados extends IRepositorioBoleiaDados<DispositivoMotorista> {

    /**
     * Pesquisa dispositivos a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return Uma lista de entidades localizadas
     */
    ResultadoPaginado<DispositivoMotorista> pesquisar(FiltroPesquisaDispositivoMotoristaVo filtro);

    /**
     * Obtem um dispositivo para dado motorista e token
     * @param cpf CPF do motorista
     * @param celular Celular do motorista
     * @param token Token do dispositivo
     * @return Dispositivo encontrado
     */
    DispositivoMotorista obterPorCpfCelularToken(String cpf, String celular, String token);

    /**
     * Obtem um dispositivo para dado id, udid e token
     * @param id id do dipositivo
     * @param udid udid do dispositivo
     * @param token token do dispositivo
     * @return Dispositivo encontrado
     */
    DispositivoMotorista obterPorIdUdidToken(Long id, String udid, String token);

    /**
     * Obtem um dispositivo a partir do motorista
     * @param idMotorista o id do motorista
     * @return os dispositivos associados, se existirem
     */
    List<DispositivoMotorista> obterPorMotorista(Long idMotorista);

    /**
     * Exclui o dispositivo de um dado motorista
     * @param idMotorista o id do motorista
     */
    void excluirPorMotoristas(Long... idMotorista);

    /**
     * Obtem uma lista contendo os dispositivos pendentes de cadastro no AllowMe
     *
     * @return uma lista dos ids dos dispositivos pendentes
     */
    List<Long> obterPendentesCadastroAllowMe();

    /**
     * Obtem um dispositivo por cpf e frota do motorista
     *
     * @param cpf o cpf do motorista
     * @param idFrota o id da frota
     * @return o dispositivo do motorista
     */
    DispositivoMotorista obterPorCpfFrota(Long cpf, Long idFrota);

    /**
     * Obtem um dispositivo por cpf e frota do motorista sem o isolamento de dados.
     *
     * @param cpf o cpf do motorista
     * @param idFrota o id da frota
     * @return o dispositivo do motorista
     */
    DispositivoMotorista obterPorCpfFrotaSemIsolamento(Long cpf, Long idFrota);

    /**
     * Obtem todos os dispositivos de um motorista
     *
     * @param cpf o cpf do motorista
     * @return todos os dispositivos utilizados por ele
     */
    List<DispositivoMotorista> obterTodosDispositivosPorCpf(Long cpf);
}
