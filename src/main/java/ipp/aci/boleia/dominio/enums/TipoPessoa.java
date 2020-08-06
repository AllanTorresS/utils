package ipp.aci.boleia.dominio.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.IEnumComLabel;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.apache.commons.lang3.StringUtils;

/**
 * Indica os tipos de pessoa
 */

public enum TipoPessoa implements IEnumComLabel<TipoPessoa> {

    PESSOA_FISICA(1),
    PESSOA_JURIDICA(2);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor da enum
     */
    TipoPessoa(Integer value) {
        this.value = value;
    }

    /**
     * Constrói uma instância da entidade de acordo com o código para o serializador JSON
     * @param codigo O valor para qual se deseja obter a representação
     * @return A enumeração correspondente ao código
     * @throws ExcecaoValidacao Se o código não existe
     */
    @JsonCreator
    public static TipoPessoa forValue(String codigo) throws ExcecaoValidacao {
        if(codigo == null){
            throw new ExcecaoValidacao(Erro.JSON_MAL_FORMADO);
        }
        if (StringUtils.isBlank(codigo)){
            return null;
        }
        for (TipoPessoa e : TipoPessoa.values()) {
            if (e.value.toString().equals(codigo) || e.name().equals(codigo)) {
                return e;
            }
        }
        throw new ExcecaoValidacao(Erro.JSON_MAL_FORMADO_CAMPO, codigo);
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
    public static TipoPessoa obterPorValor(Integer value) {
        for (TipoPessoa status : TipoPessoa.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
    /**
     * Obtem o label da enumeracao mapeado com o utilizado na base do corporativo
     * @return O label abreviado da enumeracao
     */
     public String valorIpiranga() {
        return Mensagens.obterLabelValorIpiranga(this);
    }
}

