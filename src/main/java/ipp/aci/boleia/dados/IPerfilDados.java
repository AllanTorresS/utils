package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Perfil;
import ipp.aci.boleia.dominio.UsuarioPerfil;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPerfilVo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades Perfil
 */
public interface IPerfilDados extends IRepositorioBoleiaDados<Perfil> {

    /**
     * Obtém lista de perfis do usuário
     *
     * @param filtro Filtro da pesquisa de perfis
     * @return Lista de perfis do usuário
     */
    List<Perfil> obterPerfis(FiltroPesquisaPerfilVo filtro);

    /**
     * Pesquisa Perfis a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return Uma lista de entidades localizadas
     */
    ResultadoPaginado<Perfil> pesquisaPaginada(FiltroPesquisaPerfilVo filtro);
}