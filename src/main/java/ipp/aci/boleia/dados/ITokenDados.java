package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.enums.TipoToken;
import ipp.aci.boleia.dominio.vo.TokenVo;

/**
 * Contrato para implementacao de armazenamento (storage) de arquivos.
 */
@FunctionalInterface
public interface ITokenDados {

    /**
     * Obtem um novo token a partir da integração
     *
     * @param tipoToken tipo de token a obter
     * @return novo token
     */
    TokenVo novoToken(TipoToken tipoToken);

}