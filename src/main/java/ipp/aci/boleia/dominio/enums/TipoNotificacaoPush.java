package ipp.aci.boleia.dominio.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.IEnumComLabel;
import org.apache.commons.lang.StringUtils;

/**
 * Indica o tipo de notificação a ser disparada no aplicativo motorista
 */
public enum TipoNotificacaoPush implements IEnumComLabel<TipoNotificacaoPush> {

    PAGAMENTO(1),
    KMV_ACUMULADO(2),
    NOTIFICACAO_PORTAL(3);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor da enumeracao
     */
    TipoNotificacaoPush(Integer value) {
        this.value = value;
    }

    /**
     * Constrói uma instância da entidade de acordo com o código para o serializador JSON
     * @param codigo O valor para qual se deseja obter a representação
     * @return A representação correspondente ao código
     * @throws ExcecaoValidacao Se o código não existe
     */
    @JsonCreator
    public static TipoNotificacaoPush forValue(String codigo) throws ExcecaoValidacao {
        if(codigo == null){
            throw new ExcecaoValidacao(Erro.JSON_MAL_FORMADO);
        }
        if (StringUtils.isBlank(codigo)){
            return null;
        }
        for (TipoNotificacaoPush e : TipoNotificacaoPush.values()) {
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
    public static TipoNotificacaoPush obterPorValor(Integer value) {
        for (TipoNotificacaoPush status : TipoNotificacaoPush.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}

