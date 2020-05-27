package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.ControleAcesso;
import ipp.aci.boleia.dominio.enums.TipoAcesso;

/**
 * Repositório de dados da entidade {@link ControleAcesso}.
 *
 * @author pedro.silva
 */
public interface IControleAcessoPortalDados extends IRepositorioBoleiaDados<ControleAcesso> {

    /**
     * Obtém o registro de controle de acesso a partir de um login.
     *
     * @param login Login usado na busca.
     * @param tipoAcesso Tipo de acesso.
     * @return Registro encontrado.
     */
    ControleAcesso obterPorLogin(String login, TipoAcesso tipoAcesso);
}
