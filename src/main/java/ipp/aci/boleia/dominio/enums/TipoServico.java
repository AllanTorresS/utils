package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.dominio.Servico;
import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera os possíveis tipos de serviços prestados pelos postos
 */
public enum TipoServico implements IEnumComLabel<TipoServico> {

    CONVENIENCIA(1),
    RESTAURANTE(2),
    BANHEIRO(3),
    ESTACIONAMENTO(4),
    TROCA_OLEO(5),
    ARLA_32(6),
    INTERNET_WIFI(7),
    ACADEMIA(8),
    FARMACIA(9),
    ACESSORIOS_PECAS(10),
    LAZER_TV(11),
    BARBEARIA(12),
    HOTEL(13),
    OFICINA_LONA(14),
    TOMADA_FRIGORIFICA(15),
    BORRACHARIA(16),
    LAVAGEM(17),
    CAIXA_ELETRONICO(18),
    LAVANDERIA(19),
    OFICINA_MECANICA(20),
    AM_PM(21);

    private final Integer value;

    /**
     * Construtor
     * @param value O valor da enum
     */
    TipoServico(Integer value) {
        this.value = value;
    }

    /**
     * Obtem o valor da enumercao
     * @return o valor da enumeracao
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static TipoServico obterPorValor(Integer value) {
        for(TipoServico tipoProduto : TipoServico.values()) {
            if(tipoProduto.value.equals(value)) {
                return tipoProduto;
            }
        }
        return null;
    }

    /**
     * Obtem a entidade pelo enum
     * @return entidade preenchida
     */
    public Servico obterEntidade() {
        Servico servico = new Servico();
        servico.setId(Long.valueOf(this.value));
        servico.setDescricao(this.getLabel());
        return servico;
    }
}
