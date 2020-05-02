package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.dominio.ApiToken;
import ipp.aci.boleia.util.i18n.IEnumComLabel;
import org.apache.commons.lang3.BooleanUtils;

import java.util.Date;

/**
 * Indica o status dos tokens
 */
public enum StatusTermoAceite implements IEnumComLabel<StatusTermoAceite> {

    SEM_PENDENCIA(0),
    PENDENTE_EXPIRACAO_PROXIMA(1),
    CIENTE_EXPIRACAO_PROXIMA(2),
    PENDENTE_RENOVACAO_AUTOMATICA(3),
    CIENTE_RENOVACAO_AUTOMATICA(4),
    PENDENTE_EXPIRADO(5),
    CIENTE_EXPIRADO(6);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor do status
     */
    StatusTermoAceite(Integer value) {
        this.value = value;
    }

    /**
     * Obtem o valor do status
     * @return O valor do status
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     *
     * @param value value
     * @return Enum para o valor
     */
    public static StatusTermoAceite obterPorValor(Integer value) {
        for (StatusTermoAceite status : StatusTermoAceite.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }

    /**
     * Defini um status dado token e data atual
     * @param token a obter status
     * @param dataAmbiente data atual
     * @return Status termo do token
     */
    public static StatusTermoAceite definirStatusTermoAceite(ApiToken token, Date dataAmbiente){
        if(token.isContingencia() && dataAmbiente.before(token.getDataExpiracao())){
            if(token.getStatusTermoAceite().equals(StatusTermoAceite.CIENTE_RENOVACAO_AUTOMATICA.getValue())){
                return StatusTermoAceite.CIENTE_RENOVACAO_AUTOMATICA;
            } else {
                return StatusTermoAceite.PENDENTE_RENOVACAO_AUTOMATICA;
            }
        }else if(dataAmbiente.after(token.getDataExpiracao())){
            if(token.getStatusTermoAceite().equals(StatusTermoAceite.CIENTE_EXPIRADO.getValue())){
                return StatusTermoAceite.CIENTE_EXPIRADO;
            } else {
                return StatusTermoAceite.PENDENTE_EXPIRADO;
            }
        } else if(BooleanUtils.isTrue(token.getExpiracaoProxima())){
            if(token.getStatusTermoAceite().equals(StatusTermoAceite.CIENTE_EXPIRACAO_PROXIMA.getValue())){
                return StatusTermoAceite.CIENTE_EXPIRACAO_PROXIMA;
            } else {
                return StatusTermoAceite.PENDENTE_EXPIRACAO_PROXIMA;
            }
        }
        return StatusTermoAceite.SEM_PENDENCIA;
    }

    /**
     * Obtem o proximo status dado o atual
     * @param status atual
     * @return proximo status
     */
    public static StatusTermoAceite obterProximoStatus(StatusTermoAceite status) {
        if(status.equals(StatusTermoAceite.PENDENTE_EXPIRACAO_PROXIMA)){
            return StatusTermoAceite.CIENTE_EXPIRACAO_PROXIMA;
        } else{
            if(status.equals(StatusTermoAceite.PENDENTE_EXPIRADO)){
                return StatusTermoAceite.CIENTE_EXPIRADO;
            } else{
                return StatusTermoAceite.CIENTE_RENOVACAO_AUTOMATICA;
            }
        }
    }
}
