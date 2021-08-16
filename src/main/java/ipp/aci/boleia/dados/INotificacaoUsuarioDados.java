package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.NotificacaoUsuario;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.TipoCategoriaNotificacao;
import ipp.aci.boleia.dominio.enums.TipoSubcategoriaNotificacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaNotificacaoVo;

import java.util.Date;
import java.util.List;

/**
 * Interface de repositorio das notificacoes de usuario
 */
public interface INotificacaoUsuarioDados extends IRepositorioBoleiaDados<NotificacaoUsuario> {

    /**
     * Pesquisa notificacoes do usuario para tela de pesquisa
     * @param filtro da tela de pesquisa
     * @return resultado paginado para tela de pesquisa
     */
    ResultadoPaginado<NotificacaoUsuario> pesquisar(FiltroPesquisaNotificacaoVo filtro);

    /**
     * Pesquisa ultimas notificacoes do usuario para toolbar do menu
     * @param usuario autenticado
     * @param categoria da toolbar
     * @param numero maximo de notificacoes a buscar
     * @return resultado paginado para toolbar
     */
    ResultadoPaginado<NotificacaoUsuario> pesquisarUltimas(Usuario usuario, TipoCategoriaNotificacao categoria, Integer numero);

    /**
     * Lista as notificações não lidas do usuário para uma categoria específica.
     * @param usuario Usuário autenticado.
     * @param categoria Categoria da notificação.
     * @return Lista de notificações não lidas.
     */
    List<NotificacaoUsuario> listarNaoLidas(Usuario usuario, TipoCategoriaNotificacao categoria);

    /**
     * Pesquisa ultimas notificacoes do usuario para toolbar do menu
     * @param usuario autenticado
     * @param categoria categoria
     */
    void visualizarNotificacoesDeUmUsuarioPorCategoria(Usuario usuario, TipoCategoriaNotificacao categoria);

    /**
     * Pesquisa ultimas notificacoes do usuario por Sub Categoria
     * @param usuario autenticado
     * @param tipoSubcategoriaNotificacao Sub Categoria
     * @return ultima notificação do usuario dauqela sub categoria
     */
    NotificacaoUsuario obterUltimaNotificacaoUsuarioPorSubCategoria(Usuario usuario, TipoSubcategoriaNotificacao tipoSubcategoriaNotificacao);

    /**
     * Excluir as notificações anteriores a uma data limite
     * @param dataLimite a data limite
     */
    void excluirNotificacoesAteUmaDataLimite(Date dataLimite);

    /**
     * Excluir as notificações por usuário
     * @param idUsuario o id do usuario
     */
    void excluirNotificacoesPorIdUsuario(Long idUsuario);
}
