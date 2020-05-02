package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera os possíveis filtros primarios para pesquisa de ponto de venda
 */
public enum TipoFiltroPontoVendaPrimario implements IEnumComLabel<TipoFiltroPontoVendaPrimario> {

    RODO_REDE,
    FUNCIONAMENTO_24H,
    RESTAURANTE(TipoServico.RESTAURANTE),
    ESTACIONAMENTO(TipoServico.ESTACIONAMENTO),
    BANHEIRO(TipoServico.BANHEIRO),
    ARLA_32(TipoServico.ARLA_32);

    private final TipoServico servico;

    /**
     * Construtor padrão
     */
    TipoFiltroPontoVendaPrimario() {
        this.servico = null;
    }

    /**
     * Constroi o enum a partir do tipo de servico
     * @param servico O servico associado a enum
     */
    TipoFiltroPontoVendaPrimario(TipoServico servico) {
        this.servico = servico;
    }

    /**
     * Obtem o servico associado a enum
     * @return O servico
     */
    public TipoServico getServico() {
        return servico;
    }

}
