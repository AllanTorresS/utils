package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

import java.util.stream.Stream;

/**
 * Enumera os possíveis filtros secundarios para pesquisa de ponto de venda
 */
public enum TipoFiltroPontoVendaSecundario implements IEnumComLabel<TipoFiltroPontoVendaSecundario> {

    AM_PM(TipoServico.AM_PM),
    LAVAGEM(TipoServico.LAVAGEM),
    FARMACIA(TipoServico.FARMACIA),
    CONVENIENCIA(TipoServico.CONVENIENCIA),
    BORRACHARIA(TipoServico.BORRACHARIA),
    TOMADA_FRIGORIFICA(TipoServico.TOMADA_FRIGORIFICA),
    INTERNET(TipoServico.INTERNET_WIFI),
    TROCA_OLEO(TipoServico.TROCA_OLEO),
    OFICINA(TipoServico.OFICINA_LONA, TipoServico.OFICINA_MECANICA);

    private final TipoServico[] servicos;

    /**
     * Constroi o enum a partir dos tipos de serviço
     * @param servicos O servico associado a enum
     */
    TipoFiltroPontoVendaSecundario(TipoServico... servicos) {
        this.servicos = servicos;
    }

    /**
     * Obtem os serviços associado ao enum
     *
     * @return Os serviços
     */
    public Stream<TipoServico> getServicos() {
        return Stream.of(servicos);
    }
}
