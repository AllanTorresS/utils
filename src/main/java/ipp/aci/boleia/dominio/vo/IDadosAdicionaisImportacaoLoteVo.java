package ipp.aci.boleia.dominio.vo;

/**
 * Contrato para implementação de objetos utilizados como contexto
 * durante a importação de dados em lotes.
 */
public interface IDadosAdicionaisImportacaoLoteVo {

    /**
     * Utilizado por serviços de importação que nao necessitam
     * implementar um contexto para carga inicial de dados.
     */
    interface Vazio extends IDadosAdicionaisImportacaoLoteVo {
    }

}
