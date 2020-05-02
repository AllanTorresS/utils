package ipp.aci.boleia.dominio.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.IEnumComLabel;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoAcre;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoAlagoas;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoAmapa;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoAmazonas;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoBahia;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoDistritoFederal;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoEspiritoSanto;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoEstadualDefault;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoEstadualGenerico;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoGoias;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoMaranhao;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoMatoGrosso;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoMatoGrossoSul;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoMinasGerais;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoPara;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoParana;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoPernambuco;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoRioGrandeNorte;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoRioGrandeSul;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoRioJaneiro;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoRondonia;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoRoraima;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoSantaCatarina;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoSaoPaulo;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoTocantins;
import org.apache.commons.lang3.StringUtils;

/**
 * Enumera os Estados da Federação
 * <p>
 * O Enum está ordenado pelas siglas dos estados e deve ser mantida esta ordenação.
 */
public enum Estado implements IEnumComLabel<Estado> {

    AC(new ValidadorInscricaoAcre()),
    AL(new ValidadorInscricaoAlagoas()),
    AM(new ValidadorInscricaoAmazonas()),
    AP(new ValidadorInscricaoAmapa()),
    BA(new ValidadorInscricaoBahia()),
    CE(new ValidadorInscricaoEstadualDefault()),
    DF(new ValidadorInscricaoDistritoFederal()),
    ES(new ValidadorInscricaoEspiritoSanto()),
    GO(new ValidadorInscricaoGoias()),
    MA(new ValidadorInscricaoMaranhao()),
    MG(new ValidadorInscricaoMinasGerais()),
    MS(new ValidadorInscricaoMatoGrossoSul()),
    MT(new ValidadorInscricaoMatoGrosso()),
    PA(new ValidadorInscricaoPara()),
    PB(new ValidadorInscricaoEstadualDefault()),
    PE(new ValidadorInscricaoPernambuco()),
    PI(new ValidadorInscricaoEstadualDefault()),
    PR(new ValidadorInscricaoParana()),
    RJ(new ValidadorInscricaoRioJaneiro()),
    RN(new ValidadorInscricaoRioGrandeNorte()),
    RO(new ValidadorInscricaoRondonia()),
    RR(new ValidadorInscricaoRoraima()),
    RS(new ValidadorInscricaoRioGrandeSul()),
    SC(new ValidadorInscricaoSantaCatarina()),
    SE(new ValidadorInscricaoEstadualDefault()),
    SP(new ValidadorInscricaoSaoPaulo()),
    TO(new ValidadorInscricaoTocantins());

    private final ValidadorInscricaoEstadualGenerico validadorInscricao;

    /**
     * Construtor
     *
     *
     * @param validador O validador da inscricao estatual
     */
    Estado(ValidadorInscricaoEstadualGenerico validador) {
        this.validadorInscricao = validador;
    }

    /**
     * Construtor que procura o estado de acordo com a sigla recebida
     *
     * @param sigla do estado
     * @return o estado equivalente
     * @throws ExcecaoValidacao se ocorrer
     */
    @JsonCreator
    public static Estado forValue(String sigla) throws ExcecaoValidacao {
        if (sigla == null) {
            throw new ExcecaoValidacao(Erro.JSON_MAL_FORMADO);
        }
        if (StringUtils.isBlank(sigla)){
            return null;
        }
        for (Estado e : Estado.values()) {
            if (e.getSigla().equalsIgnoreCase(sigla)) {
                return e;
            }
        }
        throw new ExcecaoValidacao(Erro.JSON_MAL_FORMADO_CAMPO, sigla);
    }

    public String getSigla() {
        return name();
    }

    public ValidadorInscricaoEstadualGenerico getValidadorInscricao() {
        return validadorInscricao;
    }

    /**
     * Obtem por sigla
     *
     * @param sigla sigla
     * @return Enum para a sigla
     */
    public static Estado obterPorSigla(String sigla) {
        for (Estado estado : Estado.values()) {
            if (estado.getSigla().equals(sigla)) {
                return estado;
            }
        }
        return null;
    }
}