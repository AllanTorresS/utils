package ipp.aci.boleia.util.negocio;

import ipp.aci.boleia.dominio.enums.Estado;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.validador.inscricaoestadual.ValidadorInscricaoEstadualGenerico;

/**
 * Validador de inscrição estadual
 */
public class ValidadorInscricaoEstadual {

    /**
     * Verifica validade da inscricao estadual para dado estado
     * @param inscricaoEstadual inscricao
     * @param siglaUf sigla do estado
     * @return true para valido
     */
    public static boolean isValidIE(String inscricaoEstadual, String siglaUf) {
        String strIE = UtilitarioFormatacao.obterDigitosMascara(inscricaoEstadual);
        Estado estado = Estado.obterPorSigla(siglaUf);
        if(estado != null) {
            if(estado == Estado.SP && inscricaoEstadual.charAt(0) == 'P') {
                strIE = String.format("P%s", strIE);
            }
            ValidadorInscricaoEstadualGenerico validador = estado.getValidadorInscricao();
            return validador.validar(strIE);
        }
        return false;
    }
}
