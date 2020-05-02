package ipp.aci.boleia.dados;
import ipp.aci.boleia.dominio.VersaoTokenJwt;
import ipp.aci.boleia.dominio.enums.TipoTokenJwt;

/**
 * Contrato para implementacao de repositorios
 * de entidades {@link VersaoTokenJwt}
 */
public interface IVersaoTokenJwtDados extends IRepositorioBoleiaDados<VersaoTokenJwt> {

    /**
     * Obtem, do banco de dados, as informacoes sobre a versao corrente do token JWT
     *
     * @param tipoTokenJwt O tipo de token desejado
     * @return As informacoes da versao corrente do token
     */
    VersaoTokenJwt obterPorTipoToken(TipoTokenJwt tipoTokenJwt);
}
