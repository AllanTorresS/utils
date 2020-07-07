package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Coordenadoria;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaCoordenadoriaVo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades Coordenadoria
 */
public interface ICoordenadoriaDados extends IRepositorioBoleiaDados<Coordenadoria> {

    /**
     * Procura as Coordenadorias cadastradas que satisfaçam os parâmetros da pesquisa
     *
     * @param filtro Os parâmetros da pesquisa
     * @param usuarioLogado usuário que está realizando a consulta
     * @return Uma lista de Coordenadorias cadastradas que satisfazem os parâmetros da pesquisa
     */
    ResultadoPaginado<Coordenadoria> pesquisar(final FiltroPesquisaCoordenadoriaVo filtro, final Usuario usuarioLogado);

    /**
     * Procura as Coordenadorias cadastradas que estao ativas
     *
     * @return Uma lista de Coordenadorias cadastradas que estao ativas
     */
    List<Coordenadoria> pesquisarAtivas();

    /**
     * Procura as Coordenadorias cadastradas que estao ativas e possuem frotas associadas
     *
     * @return Uma lista de Coordenadorias cadastradas que estao ativas e possuem frotas associadas
     */
    List<Coordenadoria> pesquisarAtivasComFrotas();

    /**
     * Localiza a coordenadoria pelo seu nome e que nao tenha o id passado
     *
     * @param nome O nome desejado
     * @param notId O id que nao deve ser pesquisado, no caso de edicao.
     * @return A coordenadoria localizada ou null
     */
    Coordenadoria obterPorNome(final String nome, final Long notId);

    /**
     * Localiza a coordenadoria pelo seu coordenador e que nao tenha o id passado
     *
     * @param coordenador O coordenador desejado
     * @param notId O id que nao deve ser pesquisado, no caso de edicao.
     * @return A coordenadoria localizada ou null
     */
    Coordenadoria obterPorCoordenador(final String coordenador, final Long notId);
}
