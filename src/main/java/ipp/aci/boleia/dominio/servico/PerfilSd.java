package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IPerfilDados;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.Perfil;
import ipp.aci.boleia.dominio.Rede;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.vo.EntidadeVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPerfilVo;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementa as regras de negocio relacionadas a entidade Perfil
 */
@Component
public class PerfilSd {

    @Autowired
    private IPerfilDados repositorio;

    @Autowired
    private Mensagens mensagens;

    /**
     * Obtém perfis do usuário logado interno
     * @return Lista de Perfil
     */
    public List<Perfil> obterPerfisInterno() {
        FiltroPesquisaPerfilVo filtro = new FiltroPesquisaPerfilVo();
        filtro.setTipoPerfil(new EntidadeVo(TipoPerfilUsuario.INTERNO.getValue()));
        return repositorio.obterPerfis(filtro);
    }

    /**
     * Obtém perfis do usuário logado frotista
     *
     * @param idFrota id da frota
     * @return Lista de Perfil
     */
    public List<Perfil> obterPerfisDaFrota(Long idFrota) {
        FiltroPesquisaPerfilVo filtro = new FiltroPesquisaPerfilVo();
        filtro.setTipoPerfil(new EntidadeVo(TipoPerfilUsuario.FROTA.getValue()));
        filtro.setIdFrota(idFrota);
        return repositorio.obterPerfis(filtro);
    }

    /**
     * Obtém perfis template do tipo frota
     *
     * @return Lista de Perfil
     */
    public List<Perfil> obterPerfisDaFrotaTemplate() {
        FiltroPesquisaPerfilVo filtro = new FiltroPesquisaPerfilVo();
        filtro.setTipoPerfil(new EntidadeVo(TipoPerfilUsuario.FROTA.getValue()));
        filtro.setTemplate(true);
        return repositorio.obterPerfis(filtro);
    }

    /**
     * Obtém perfis do usuário logado revendedor
     *
     * @param idRede id da rede
     * @return Lista de Perfil
     */
    public List<Perfil> obterPerfisDaRevenda(Long idRede) {
        FiltroPesquisaPerfilVo filtro = new FiltroPesquisaPerfilVo();
        filtro.setTipoPerfil(new EntidadeVo(TipoPerfilUsuario.REVENDA.getValue()));
        filtro.setIdRede(idRede);
        return repositorio.obterPerfis(filtro);
    }

    /**
     * Obtém perfis template do tipo revenda
     *
     * @return Lista de Perfil
     */
    public List<Perfil> obterPerfisDaRevendaTemplate() {
        FiltroPesquisaPerfilVo filtro = new FiltroPesquisaPerfilVo();
        filtro.setTipoPerfil(new EntidadeVo(TipoPerfilUsuario.REVENDA.getValue()));
        filtro.setTemplate(true);
        return repositorio.obterPerfis(filtro);
    }

    /**
     * Verifica se há usuários vinculados aos perfis sendo excluídos e se o usuario logado
     * tem permissao para excluir o perfil informado
     *
     * @param tipoPerfil O tipo dos perfis a excluir, obtido a partir do usuario logado
     * @param ids        Os ids dos perfis a excluir
     * @throws ExcecaoValidacao Em caso de excecao de validacao
     */
    public void validarExclusaoPerfil(TipoPerfilUsuario tipoPerfil, Long... ids) throws ExcecaoValidacao{

        if(ids != null) {

            for(Long id : ids) {

                Perfil perfil = repositorio.obterPorId(id);

                boolean perfilPertenceAoTipo =
                    TipoPerfilUsuario.INTERNO.getValue().equals(tipoPerfil.getValue())
                    || perfil.getTipoPerfil().getId().equals(tipoPerfil.getValue());

                if(!perfilPertenceAoTipo) {
                    throw new ExcecaoValidacao(mensagens.obterMensagem("perfil.servico.validacao.excluir.tipo.nao.permitido"));
                }

                if(perfil.getUsuarios().size() > 0){
                    throw new ExcecaoValidacao(mensagens.obterMensagem("perfil.servico.validacao.excluir.relacionados"));
                }
            }
        }
    }



    /**
     * Cria os perfis padrao para uma dada revenda, a partir dos templates previamente
     * cadastrados no banco de dados
     * @param rede A rede a qual pertence o perfil de revenda
     */
    public void criarPerfisPadraoRevenda(Rede rede) {
        FiltroPesquisaPerfilVo filtro = new FiltroPesquisaPerfilVo();
        filtro.setTipoPerfil(new EntidadeVo(TipoPerfilUsuario.REVENDA.getValue()));
        filtro.setTemplate(true);
        List<Perfil> templates = repositorio.obterPerfis(filtro);
        if(templates != null && !templates.isEmpty()) {
            for(Perfil template : templates) {
                Perfil perfilRevenda = Perfil.instanciarTemplate(template);
                perfilRevenda.setRede(rede);
                repositorio.armazenar(perfilRevenda);
            }
        }
    }

    /**
     * Cria os perfis padrao para uma dada frota, a partir dos templates previamente
     * cadastrados no banco de dados
     * @param frota A frota
     */
    public void criarPerfisPadraoFrota(Frota frota) {
        FiltroPesquisaPerfilVo filtro = new FiltroPesquisaPerfilVo();
        filtro.setTipoPerfil(new EntidadeVo(TipoPerfilUsuario.FROTA.getValue()));
        filtro.setTemplate(true);
        List<Perfil> templates = repositorio.obterPerfis(filtro);
        if(templates != null && !templates.isEmpty()) {
            for(Perfil template : templates) {
                Perfil perfilFrota = Perfil.instanciarTemplate(template);
                perfilFrota.setFrota(frota);
                repositorio.armazenar(perfilFrota);
            }
        }
    }

    /**
     * Método que exclui perfis
     *
     * @param tipoPerfil O tipo dos perfis a serem excluidos
     * @param ids ds dos perfis a serem excluidos
     * @throws ExcecaoValidacao Em caso de excecao de validacao
     */
    public void excluir(TipoPerfilUsuario tipoPerfil, Long... ids) throws ExcecaoValidacao {
        validarExclusaoPerfil(tipoPerfil, ids);
        repositorio.excluir(ids);
    }


}