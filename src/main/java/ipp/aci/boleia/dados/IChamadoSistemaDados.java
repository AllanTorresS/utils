package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.ChamadoSistema;
import ipp.aci.boleia.dominio.ChamadoTipo;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades ChamadoSistema
 */
public interface IChamadoSistemaDados extends IRepositorioBoleiaDados<ChamadoSistema> {

    /**
     * Pesquisa os tipos de chamado com base no perfil
     *
     * @param tipoPerfil tipo perfil do usuario
     * @return Uma lista com os sistamas do chamado.
     */
    List<ChamadoSistema> obterSistemasChamado(TipoPerfilUsuario tipoPerfil);


}
