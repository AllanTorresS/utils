package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.ClonagemPerfil;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades ClonagemPerfil
 */
public interface IClonagemPerfilDados extends IRepositorioBoleiaDados<ClonagemPerfil>{

    
    /**
     * Pesquisa as clonagens de perfis temporários de um usuário
     *
     * @param idUsuario o id do Usuario
     * @return Uma lista de ClonagemPerfil
     */
    List<ClonagemPerfil> obterClonagensPerfisTemporariosPorUsuario(Long idUsuario);
}
