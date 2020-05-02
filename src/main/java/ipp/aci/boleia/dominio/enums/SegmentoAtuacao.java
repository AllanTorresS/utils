package ipp.aci.boleia.dominio.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.IEnumComLabel;
import org.apache.commons.lang3.StringUtils;

/**
 * Indica os tipos de segmentos de atuacao
 */
public enum SegmentoAtuacao implements IEnumComLabel<SegmentoAtuacao> {

    AGRONEGOCIO(0),
    ASSISTENCIA_TECNICA(1),
    COMERCIO(2),
    CONSULTORIA(3),
    DISTRIBUICAO(4),
    INDUSTRIA(5),
    LOCACAO(6),
    PRESTACAO_DE_SERVICO(7),
    TRANSPORTE(8),
    REPRESENTACAO( 9),
    OUTRO(10);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    SegmentoAtuacao(Integer value) {
        this.value = value;
    }

    /**
     * Constrói uma instância da entidade de acordo com o código para o serializador JSON
     * @param codigo O valor para qual se deseja obter a representação
     * @return A enumeração correspondente ao código
     * @throws ExcecaoValidacao Se o código não existe
     */
    @JsonCreator
    public static SegmentoAtuacao forValue(String codigo) throws ExcecaoValidacao {
        if(codigo == null){
            throw new ExcecaoValidacao(Erro.JSON_MAL_FORMADO);
        }
        if (StringUtils.isBlank(codigo)){
            return null;
        }
        for (SegmentoAtuacao e : SegmentoAtuacao.values()) {
            if (e.value.toString().equals(codigo) || e.name().equals(codigo)) {
                return e;
            }
        }
        throw new ExcecaoValidacao(Erro.JSON_MAL_FORMADO_CAMPO, codigo);
    }

    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static SegmentoAtuacao obterPorValor(Integer value) {
        for(SegmentoAtuacao status : SegmentoAtuacao.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
