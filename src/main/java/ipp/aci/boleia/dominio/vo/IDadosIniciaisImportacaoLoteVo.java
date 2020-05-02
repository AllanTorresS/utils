package ipp.aci.boleia.dominio.vo;

/**
 * Contrato para implementação de objetos utilizados como contexto
 * durante a importacao de dados em lotes.
 */
public interface IDadosIniciaisImportacaoLoteVo {

    /**
     * Utilizado por servicos de importacao que nao necessitam
     * implementar um contexto para carga inicial de dados
     */
    interface Vazio extends IDadosIniciaisImportacaoLoteVo {
    }
}
