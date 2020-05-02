package ipp.aci.boleia.dominio.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.IEnumComLabel;
import org.apache.commons.lang3.StringUtils;

/**
 * Indica os tipos de contribuinte
 */

public enum TipoContribuinte implements IEnumComLabel<TipoContribuinte> {

    INDIVIDUO(1),
    PESSOA_JURIDICA(2),
    INDIVIDUO_PESSOA_JURIDICA(3),
    ENTIDADE_NAO_CORPORATIVA(4),
    AUTORIDADE_ALFANDEGARIA(5);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor da enum
     */
    TipoContribuinte(Integer value) {
        this.value = value;
    }

    /**
     * Constrói uma instância da entidade de acordo com o código para o serializador JSON
     * @param codigo O valor para qual se deseja obter a representação
     * @return A enumeração correspondente ao código
     * @throws ExcecaoValidacao Se o código não existe
     */
    @JsonCreator
    public static TipoContribuinte forValue(String codigo) throws ExcecaoValidacao {
        if(codigo == null){
            throw new ExcecaoValidacao(Erro.JSON_MAL_FORMADO);
        }
        if (StringUtils.isBlank(codigo)){
            return null;
        }
        for (TipoContribuinte e : TipoContribuinte.values()) {
            if (e.value.toString().equals(codigo) || e.name().equals(codigo)) {
                return e;
            }
        }
        throw new ExcecaoValidacao(Erro.JSON_MAL_FORMADO_CAMPO, codigo);
    }

    /**
     * Obtem o valor da enum
     * @return O valor da enum
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static TipoContribuinte obterPorValor(Integer value) {
        for (TipoContribuinte status : TipoContribuinte.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}

