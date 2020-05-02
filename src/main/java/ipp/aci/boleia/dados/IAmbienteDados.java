package ipp.aci.boleia.dados;

import java.util.Date;

/**
 * Contrato para implementacao de repositorios para obtencao de variaveis e dados de ambiente
 */
public interface IAmbienteDados {

    /**
     * Busca data/hora corrente do ambiente
     * @return data/hora do ambiente
     */
    Date obterDataAmbiente();

    /**
     * Retorna o fuso horário do ambiente
     * @return fuso horário do ambiente
     */
    int obterTimeZone();
}
