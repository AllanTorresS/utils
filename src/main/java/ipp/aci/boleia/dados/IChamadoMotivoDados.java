package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.ChamadoMotivo;
import ipp.aci.boleia.dominio.ChamadoTipo;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades ChamadoMotivo
 */
public interface IChamadoMotivoDados extends IRepositorioBoleiaDados<ChamadoMotivo> {

    /**
     * Pesquisa os tipos de chamado com base no perfil
     *
     * @param tipoPerfil tipo perfil do usuario
     * @return Uma lista com os motivos chamado.
     */
    List<ChamadoMotivo> obterTipoChamado(TipoPerfilUsuario tipoPerfil);


}
