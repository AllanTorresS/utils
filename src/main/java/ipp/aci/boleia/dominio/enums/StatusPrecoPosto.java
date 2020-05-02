package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;
import ipp.aci.boleia.util.i18n.Mensagens;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Apenas para exibição na tela de preço posto do PV
 */
public enum StatusPrecoPosto implements IEnumComLabel<StatusPrecoPosto> {

    ACEITE_PENDENTE,
    VIGENTE,
    EXPIRADO,
    RECUSADO,
    AGENDADO;

    /**
     * Obtem o status de preco posto de acordo com o seu status de alteracao
     * @param status Status de alteração do preco posto
     * @return Status de exeibicao do preco posto
     */
    public static StatusPrecoPosto obterPorStatusAlteracaoPrecoPosto(StatusAlteracaoPrecoPosto status) {
        if(StatusAlteracaoPrecoPosto.ACEITE_PENDENTE_INTERNO.equals(status) || StatusAlteracaoPrecoPosto.ACEITE_PENDENTE_REVENDA.equals(status)) {
            return ACEITE_PENDENTE;
        }
        if(StatusAlteracaoPrecoPosto.VIGENTE.equals(status) || StatusAlteracaoPrecoPosto.ACEITO.equals(status)) {
            return VIGENTE;
        }
        if(StatusAlteracaoPrecoPosto.EXPIRADO.equals(status)){
            return EXPIRADO;
        }
        if(StatusAlteracaoPrecoPosto.RECUSADO.equals(status)){
            return RECUSADO;
        }
        if(StatusAlteracaoPrecoPosto.AGENDADO_COM_ACEITE_REVENDA.equals(status) || StatusAlteracaoPrecoPosto.AGENDADO_SEM_ACEITE_REVENDA.equals(status)){
            return AGENDADO;
        }
        return null;
    }

    /**
     * Obtem os status de alteracao de preço posto de acordo com o seu status de exibicao
     * @return Status de alteracao de preco posto convertidos
     */
    public List<StatusAlteracaoPrecoPosto> converterParaStatusAlteracaoPrecoPosto() {
        if(ACEITE_PENDENTE.equals(this)) {
            return Arrays.asList(StatusAlteracaoPrecoPosto.ACEITE_PENDENTE_INTERNO, StatusAlteracaoPrecoPosto.ACEITE_PENDENTE_REVENDA);
        }
        if(VIGENTE.equals(this)) {
            return Arrays.asList(StatusAlteracaoPrecoPosto.ACEITO, StatusAlteracaoPrecoPosto.VIGENTE);
        }
        if(EXPIRADO.equals(this)) {
            return Arrays.asList(StatusAlteracaoPrecoPosto.EXPIRADO);
        }
        if(RECUSADO.equals(this)) {
            return Arrays.asList(StatusAlteracaoPrecoPosto.RECUSADO);
        }
        if(AGENDADO.equals(this)) {
            return Arrays.asList(StatusAlteracaoPrecoPosto.AGENDADO_COM_ACEITE_REVENDA, StatusAlteracaoPrecoPosto.AGENDADO_SEM_ACEITE_REVENDA);
        }
        return Collections.emptyList();
    }

    /**
     * Obtem o label do status do preco posto
     * @param statusAlteracao O status do preco
     * @return O label de internacionalizado do status de alteracao do preco posto
     */
    public static String getLabelStatusAlteracao(StatusAlteracaoPrecoPosto statusAlteracao) {
        return Mensagens.obterLabel("enum.StatusPrecoPosto.StatusAlteracaoPrecoPosto." + statusAlteracao.name());
    }

}
