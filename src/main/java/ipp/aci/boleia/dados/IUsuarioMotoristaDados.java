package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.UsuarioMotorista;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaUsuarioMotoristaVo;

/**
 * Repositorio de dados da entidade {@link UsuarioMotorista}.
 *
 * @author pedro.silva
 */
public interface IUsuarioMotoristaDados extends IRepositorioBoleiaDados<UsuarioMotorista> {

    /**
     * Obtém um usuario motorista a partir de um usuario.
     *
     * @param usuario o usuario logado no aplicativo
     * @return UsuarioMotorista do id dado
     */
    UsuarioMotorista obterPorUsuario(Usuario usuario);

    /**
     * Pesquisa Usuarios Motoristas a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return Uma lista de entidades localizadas
     */
    ResultadoPaginado<UsuarioMotorista> pesquisaPaginada(FiltroPesquisaUsuarioMotoristaVo filtro);
}
