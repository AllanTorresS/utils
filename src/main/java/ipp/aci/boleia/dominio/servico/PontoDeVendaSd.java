package ipp.aci.boleia.dominio.servico;

import com.amazonaws.util.CollectionUtils;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Strings;
import ipp.aci.boleia.dados.IBancoDados;
import ipp.aci.boleia.dados.IContaBancariaDados;
import ipp.aci.boleia.dados.IHistoricoPontoVendaDados;
import ipp.aci.boleia.dados.ILeadCredenciamentoDados;
import ipp.aci.boleia.dados.IPontoDeVendaDados;
import ipp.aci.boleia.dados.IQuestionarioDados;
import ipp.aci.boleia.dados.ITaxaPerfilDados;
import ipp.aci.boleia.dominio.ContaBancaria;
import ipp.aci.boleia.dominio.HistoricoPontoVenda;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.Questionario;
import ipp.aci.boleia.dominio.QuestionarioPerguntaOpcao;
import ipp.aci.boleia.dominio.TaxaPerfil;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.PerfilPontoDeVenda;
import ipp.aci.boleia.dominio.enums.StatusCredenciamentoPosto;
import ipp.aci.boleia.dominio.enums.StatusHabilitacaoPontoVenda;
import ipp.aci.boleia.dominio.enums.TipoQuestionario;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPontoDeVendaVo;
import ipp.aci.boleia.util.excecao.ExcecaoTokenJwtExpirado;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import ipp.aci.boleia.util.seguranca.UtilitarioJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementa as regras de negocio relacionadas a entidade Unidade
 */
@Component
public class PontoDeVendaSd {

    @Autowired
    private IQuestionarioDados repositorioQuestionario;

    @Autowired
    private IBancoDados repositorioBanco;

    @Autowired
    private IContaBancariaDados repositorioContas;

    @Autowired
    private UtilitarioJwt utilitarioJwt;

    @Autowired
    private ITaxaPerfilDados repositorioTaxaPerfil;
    
    @Autowired
	private ILeadCredenciamentoDados repositorioLeadCredenciamentoPosto;

    @Autowired
    private IPontoDeVendaDados repositorioPontoVenda;
    
    @Autowired
    private IHistoricoPontoVendaDados repositorioHistoricoPontoVenda;
    
    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Preenche o questionário do ponto de venda com as opções default caso não tenha respostas preenchidas
     *
     * @param pv O ponto de venda sem questionário
     */
    public void preencherRespostasDefaulQuestionario(PontoDeVenda pv) {
        List<Questionario> questionario = repositorioQuestionario.obterPorTipo(TipoQuestionario.PONTOVENDA);
        if(pv.getRespostaQuestionario() == null){
            pv.setRespostaQuestionario(new ArrayList<>());
        }
        questionario.forEach(q ->
                q.getGrupo().forEach(g ->
                        g.getPergunta().forEach(p -> {
                            Boolean perguntaRespondida = !Collections.disjoint(p.getOpcao(),pv.getRespostaQuestionario());
                            QuestionarioPerguntaOpcao opcaoDefault = p.getOpcao().stream().filter(QuestionarioPerguntaOpcao::getOpcaoDefault).findAny().orElse(null);
                            if(!perguntaRespondida && opcaoDefault != null){
                                pv.getRespostaQuestionario().add(opcaoDefault);
                            }
                        })
                )

        );
    }

    /**
     * Caso a conta bancaria ja exista no Ponto de Venda, atualiza-a. Caso contrario
     * insere-a como um novo relacionamento.
     *
     * @param pontoVenda O ponto de venda
     * @param conta A conta a atualizar ou inserir
     * @return O ponto de venda com a conta atualizada
     */
    public PontoDeVenda atualizarContaBancaria(PontoDeVenda pontoVenda, ContaBancaria conta) {
        if (conta == null) {
            return pontoVenda;
        }

        if(pontoVenda.getContasBancarias() == null) {
            pontoVenda.setContasBancarias(new ArrayList<>());
        }

        int posicao = -1;
        for(int i = 0; i < pontoVenda.getContasBancarias().size(); i++) {
            ContaBancaria c = pontoVenda.getContasBancarias().get(i);
            if(c.getId().equals(conta.getId())) {
                posicao = i;
                break;
            }
        }

        conta.setBanco(repositorioBanco.obterPorId(conta.getBanco().getId()));
        conta = repositorioContas.armazenar(conta);

        if(posicao >= 0) {
            pontoVenda.getContasBancarias().set(posicao, conta);
        } else {
            pontoVenda.getContasBancarias().add(conta);
        }

        return pontoVenda;
    }

    /**
     * Faz a pesquisa de frotas para exportação
     *
     * @param filtro para a busca
     * @return os resultados da busca de acordo com o filtro
     */
    public ResultadoPaginado<PontoDeVenda> pesquisarPontoVendaParaExportacao(FiltroPesquisaPontoDeVendaVo filtro) {
        InformacaoPaginacao informacaoPaginacao = filtro.getPaginacao();
        informacaoPaginacao.setTamanhoPagina(null);
        informacaoPaginacao.setPagina(null);
        filtro.setPaginacao(informacaoPaginacao);

        return repositorioPontoVenda.pesquisar(filtro);
    }

    /**
     * Obtem o status do credenciamento de posto dentro dos possíveis casos dos valores da {@link StatusCredenciamentoPosto}.
     *
     * @param usuario {@link Usuario} Usuário.
     * @param pontoDeVenda {@link PontoDeVenda} Ponto de venda.
     * @param cnpj Cnpj do ponto de venda.
     * @return {@link StatusCredenciamentoPosto} Status.
     */
    public StatusCredenciamentoPosto obterStatusCredenciamento(Usuario usuario, PontoDeVenda pontoDeVenda, String cnpj) {
    	StatusCredenciamentoPosto status = verificarStatusCredenciamento(usuario, pontoDeVenda);
    	if ((StatusCredenciamentoPosto.INICIAR_CREDENCIAMENTO == status || StatusCredenciamentoPosto.BANDEIRA_BRANCA == status) 
    			&& Boolean.TRUE.equals(repositorioLeadCredenciamentoPosto.validarLeadExistente(cnpj))) {
    		return StatusCredenciamentoPosto.INICIADO_SALESFORCE;
    	} else {
    		return status;
    	}
    }
    
    /**
     * Obtem o status{@link StatusCredenciamentoPosto} para habilitação do posto.
     *
     * @param usuario {@link Usuario} Usuário.
     * @param pontoDeVenda {@link PontoDeVenda} Ponto de venda.
     * @return {@link StatusCredenciamentoPosto} Status.
     */
    public StatusCredenciamentoPosto obterStatusHabilitacao(Usuario usuario, PontoDeVenda pontoDeVenda) {
    	if (pontoDeVenda != null) {
    		if (verificarCredenciamentoPendente(usuario, pontoDeVenda)) {
        		return StatusCredenciamentoPosto.CREDENCIAMENTO_PENDENTE;
        	}
        	
    		if (StatusHabilitacaoPontoVenda.HABILITADO.getValue().equals(pontoDeVenda.getStatusHabilitacao())) {
    			return StatusCredenciamentoPosto.HABILITADO;
    		}
    		
    		if ((pontoDeVenda.getStatusHabilitacao() == null || StatusHabilitacaoPontoVenda.DESABILITADO.getValue().equals(pontoDeVenda.getStatusHabilitacao())) && 
    				(!CollectionUtils.isNullOrEmpty(pontoDeVenda.getUsuarios()) && !pontoDeVenda.getUsuarios().contains(usuario))) {
    			return StatusCredenciamentoPosto.OUTRO_USUARIO;
    		}
    		
    		if (!verificarMesmaRede(usuario, pontoDeVenda)) {
        		return StatusCredenciamentoPosto.OUTRA_REDE;
        	} 
    	}
		return StatusCredenciamentoPosto.INICIAR_HABILITACAO;
    }
    
    /**
     * Retorna o status do credenciamento através do cpf e cnpj informados.
     *
     * @param usuario {@link Usuario} Usuário.
     * @param pontoDeVenda {@link PontoDeVenda} Ponto de venda.
     * @return {@link StatusCredenciamentoPosto} Status.
     */
    private StatusCredenciamentoPosto verificarStatusCredenciamento(Usuario usuario, PontoDeVenda pontoDeVenda) {
    	if (verificarCredenciamentoPendente(usuario, pontoDeVenda)) {
    			return StatusCredenciamentoPosto.CREDENCIAMENTO_PENDENTE;
    	}

    	if (pontoDeVenda != null && !Boolean.TRUE.equals(pontoDeVenda.getExcluido())) {
    		if (Boolean.TRUE.equals(pontoDeVenda.isBandeiraBranca())) {
    			return StatusCredenciamentoPosto.BANDEIRA_BRANCA;
    		}

    		if (StatusHabilitacaoPontoVenda.HABILITADO.getValue().equals(pontoDeVenda.getStatusHabilitacao())) {
    			return StatusCredenciamentoPosto.HABILITADO;
    		}

    		if (StatusHabilitacaoPontoVenda.PENDENTE_ACEITE.getValue().equals(pontoDeVenda.getStatusHabilitacao())) {
    			return StatusCredenciamentoPosto.PENDENTE_ACEITE;
            }

    		if (pontoDeVenda.getStatusHabilitacao() == null || StatusHabilitacaoPontoVenda.DESABILITADO.getValue().equals(pontoDeVenda.getStatusHabilitacao())) {
    			return obterStatusCredenciamento(usuario, pontoDeVenda);
    		}
    	}
    	return StatusCredenciamentoPosto.BANDEIRA_BRANCA;
    }

    /**
     * Retorna o status atual do credenciamento através do usuario e ponto de venda informados.
     *
     * @param usuario {@link Usuario} Usuário.
     * @param pontoDeVenda {@link PontoDeVenda} Ponto de venda.
     * @return {@link StatusCredenciamentoPosto} Status.
     */
    private StatusCredenciamentoPosto obterStatusCredenciamento(Usuario usuario, PontoDeVenda pontoDeVenda) {
    	if (!CollectionUtils.isNullOrEmpty(pontoDeVenda.getUsuarios())) {
			if (!pontoDeVenda.getUsuarios().contains(usuario) && pontoDeVenda.getIdLeadCredenciamento() != null) {
				return StatusCredenciamentoPosto.OUTRO_USUARIO;
			}

			if (pontoDeVenda.getTokenCredenciamento() != null) {
				if (validarExpiracaoTokenCredenciamento(pontoDeVenda.getTokenCredenciamento())) {
					return StatusCredenciamentoPosto.INICIADO_CREDENCIAMENTO_VALIDO;
				} else {
					return StatusCredenciamentoPosto.INICIADO_CREDENCIAMENTO_EXPIRADO;
				}
			}
		}

    	if (!verificarMesmaRede(usuario, pontoDeVenda)) {
    		return StatusCredenciamentoPosto.OUTRA_REDE;
    	} 
		
		return StatusCredenciamentoPosto.INICIAR_CREDENCIAMENTO;
    }

    /**
     * Verifica se o ponto de venda pertence a mesma rede do usuário através da raiz do cnpj.
     *
     * @param usuario {@link Usuario} Usuário.
     * @param pontoDeVenda {@link PontoDeVenda} Ponto de venda.
     * @return true caso seja da mesma rede se não false.
     */
    private static boolean verificarMesmaRede(Usuario usuario, PontoDeVenda pontoDeVenda) {
    	if (usuario != null && !CollectionUtils.isNullOrEmpty(usuario.getPontosDeVenda())) {

    	    Long usuarioPontoVendaCnpj = usuario.getPontosDeVenda().get(0).getComponenteAreaAbastecimento().getCodigoPessoa();
    	    Long pontoVendaCnpj = pontoDeVenda.getComponenteAreaAbastecimento().getCodigoPessoa();

            return String.format("%014d", usuarioPontoVendaCnpj).substring(0, 8)
                    .equals(String.format("%014d", pontoVendaCnpj).substring(0, 8));

    	}
    	return true;
    }

    /**
     * Verifica se o ponto de venda está ou se o usuário possui algum ponto de venda que esteja em credenciamento(em andamento).
     *
     * @param usuario {@link Usuario} Usuário.
     * @param pontoDeVenda {@link PontoDeVenda} Ponto de venda.
     * @return true caso encontre um credenciamento pendente se não false.
     */
    private static boolean verificarCredenciamentoPendente(Usuario usuario, PontoDeVenda pontoDeVenda) {
        return usuario != null && !CollectionUtils.isNullOrEmpty(usuario.getPontosDeVenda()) && !usuario.getPontosDeVenda().contains(pontoDeVenda)
                && (StatusHabilitacaoPontoVenda.PENDENTE_ACEITE.getValue().equals(pontoDeVenda.getStatusHabilitacao())
                || pontoDeVenda.getTokenCredenciamento() != null);
    }

    /**
     * Obtem a taxa do ponto de venda de acordo com seu perfil de venda.
     *
     * @param pontoDeVenda {@link PontoDeVenda} Ponto de venda.
     * @return {@link TaxaPerfil}.
     */
    public TaxaPerfil obterTaxaPorPerfil(PontoDeVenda pontoDeVenda) {
    	if (Boolean.TRUE.equals(pontoDeVenda.getRodoRede())) {
    		return repositorioTaxaPerfil.obterPorTipo(PerfilPontoDeVenda.RODO_REDE);
    	} 

    	if (!Strings.isNullOrEmpty(pontoDeVenda.getPerfilVenda())) {
    		if(PerfilPontoDeVenda.RODOVIA.name().equalsIgnoreCase(pontoDeVenda.getPerfilVenda())) {
    			return repositorioTaxaPerfil.obterPorTipo(PerfilPontoDeVenda.RODOVIA);
    		}
    		if(PerfilPontoDeVenda.URBANO.name().equalsIgnoreCase(pontoDeVenda.getPerfilVenda())) {
    			return repositorioTaxaPerfil.obterPorTipo(PerfilPontoDeVenda.URBANO);
    		}
    	}
    	return repositorioTaxaPerfil.obterPorTipo(PerfilPontoDeVenda.OUTROS);
    }

    /**
     * Valida a expiração do token do credenciamento.
     *
     * @param token Json web token codificado.
     * @return Caso valido true, se não false.
     */
    private boolean validarExpiracaoTokenCredenciamento(String token) {
    	try {
    		DecodedJWT decodedJWT = utilitarioJwt.validarTokenAutenticacao(token, null);
    		if (decodedJWT != null && !utilitarioJwt.isTokenExpirado(decodedJWT)) {
    			return true;
    		}
    	} catch (ExcecaoTokenJwtExpirado e) {
    		return false;
    	}
    	return false;
    }

    /**
     * Preenche e armazena o historico do ponto de venda.
     * 
     * @param entidade o ponto de venda
     */
    public void armazenarHistorico(PontoDeVenda entidade) {
        HistoricoPontoVenda historicoPontoVenda = new HistoricoPontoVenda();
        historicoPontoVenda.setPontoDeVenda(entidade);
        historicoPontoVenda.setUsuario(ambiente.getUsuarioLogado());
        historicoPontoVenda.setDataHistorico(ambiente.buscarDataAmbiente());
        historicoPontoVenda.setRestricaoVisibilidade(entidade.getRestricaoVisibilidade());
        repositorioHistoricoPontoVenda.armazenar(historicoPontoVenda);
    }
}
