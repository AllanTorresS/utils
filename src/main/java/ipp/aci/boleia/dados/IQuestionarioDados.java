package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Questionario;
import ipp.aci.boleia.dominio.enums.TipoQuestionario;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades Questionario
 */
public interface IQuestionarioDados extends IRepositorioBoleiaDados<Questionario> {

    /**
     * Obter questionarios pelo tipo
     *
     * @param tipoQuestionario O tipo do questionario
     * @return lista de questionarios localizados
     */
    List<Questionario> obterPorTipo(TipoQuestionario tipoQuestionario);
}
