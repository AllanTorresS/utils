package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.ChamadoModulo;
import ipp.aci.boleia.dominio.ChamadoTipo;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades ChamadoModulo
 */
public interface IChamadoModuloDados extends IRepositorioBoleiaDados<ChamadoModulo> {

    /**
     * Pesquisa os tipos de chamado com base no perfil
     *
     * @param tipoPerfil tipo perfil do usuario
     * @return Uma lista com os modulos do chamado.
     */
    List<ChamadoModulo> obterTipoChamado(TipoPerfilUsuario tipoPerfil);


}
