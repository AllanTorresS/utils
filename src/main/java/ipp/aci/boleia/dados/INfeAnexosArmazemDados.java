package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.NfeAnexosArmazem;

import java.util.List;

/**
 * Contrato para implementação de repositório de armazém de anexos
 */
public interface INfeAnexosArmazemDados extends IRepositorioBoleiaDados<NfeAnexosArmazem> {

    /**
     * Procura na tabela de armazém de anexos os registros
     * que tenham sido processados com sucesso para importação
     * @return Lista de anexos a serem importados
     */
    List<NfeAnexosArmazem> buscarAnexosProcessadosParaImportacao();

    /**
     * Procura na tabela de armazém de anexos os registros
     * que tenham sido importados porém estejam com ciclo pendente de emissão
     *
     * @return Lista de anexos encontrados
     */
    List<NfeAnexosArmazem> buscarAnexosPendentesDeEmissao();

    /**
     * Busca os anexos já importados como notas para conciliação
     *
     * @return A lista de anexos encontrados
     */
    List<NfeAnexosArmazem> buscarAnexosImportadosParaConciliacao();

    /**
     * Busca o anexo que gerou a nota, em caso de conciliação automática
     * @param idNota o id da nota fiscal
     * @return o anexo associado à nota fiscal informada
     */
    NfeAnexosArmazem obterPorNotaFiscal(Long idNota);
}
