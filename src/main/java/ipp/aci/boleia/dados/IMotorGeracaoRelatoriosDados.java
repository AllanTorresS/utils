package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.MotorGeracaoRelatorios;

/**
 * Contrato para implementacao de repositorios de entidades do Motor de Geração de Relatórios
 */
public interface IMotorGeracaoRelatoriosDados extends IRepositorioBoleiaDados<MotorGeracaoRelatorios> {

    /**
     * Obtém o relatório informado para processamento, caso ele já não esteja sendo processado
     * @param id O identificador do relatório
     * @return O relatório desejado
     */
    MotorGeracaoRelatorios obterRelatorioParaProcessamento(Long id);
}
