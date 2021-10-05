package ipp.aci.boleia.dominio.servico;

import com.auth0.jwt.interfaces.DecodedJWT;
import ipp.aci.boleia.dados.IEmailEnvioDados;
import ipp.aci.boleia.dados.IFilaEmailsAlteracaoStatusFrota;
import ipp.aci.boleia.dados.IFrotaDados;
import ipp.aci.boleia.dados.ILeadCredenciamentoDados;
import ipp.aci.boleia.dados.IParametroCicloDados;
import ipp.aci.boleia.dados.IUsuarioDados;
import ipp.aci.boleia.dominio.Credenciamento;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.MotivoAlteracaoStatusFrota;
import ipp.aci.boleia.dominio.ParametroCiclo;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.ClassificacaoStatusFrota;
import ipp.aci.boleia.dominio.enums.StatusCredenciamentoFrota;
import ipp.aci.boleia.dominio.enums.StatusFrota;
import ipp.aci.boleia.dominio.enums.TipoAlteracaoStatusFrota;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaFrotaVo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoTokenJwtExpirado;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import ipp.aci.boleia.util.negocio.ValidadorInscricaoEstadual;
import ipp.aci.boleia.util.seguranca.UtilitarioJwt;
import ipp.aci.boleia.util.validador.ValidadorAlfanumerico;
import ipp.aci.boleia.util.validador.ValidadorCnpj;
import ipp.aci.boleia.util.validador.ValidadorCpf;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implementa as regras de negocio relacionadas a entidade Frota
 */
@Component
public class FrotaSd {

    @Autowired
    private IFilaEmailsAlteracaoStatusFrota filaEmailsAlteracaoStatusFrota;

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private IFrotaDados repositorio;

    @Autowired
    private IEmailEnvioDados emailDados;

    @Autowired
    private IUsuarioDados repositorioUsuario;

    @Autowired
    private IParametroCicloDados repositorioParametroCiclo;

    @Autowired
    private NotificacaoUsuarioSd notificacaoUsuarioSd;

    @Autowired
    private UtilitarioAmbiente ambiente;
    
    @Autowired
    private UtilitarioJwt utilitarioJwt;
    
    @Autowired
	private ILeadCredenciamentoDados repositorioLeadCredenciamento;

    @Autowired
    private MotivoAlteracaoStatusFrotaSd motivoAlteracaoStatusFrotaSd;

    /**
     * Armazena os dados de uma frota
     * @param frota A frota  ser armazenada
     * @return A frota armazenada
     */
    public Frota armazenar(Frota frota) {
        return repositorio.armazenar(frota);
    }

    /**
     * Prepara uma frota para realizar pre cadastro
     *
     * @param strCnpj O cnpj da frota a pre cadastrar
     * @throws ExcecaoValidacao Em caso de frota invalida
     */
    public void validarFrotaPreCadastro(String strCnpj) throws ExcecaoValidacao {
        if (!StringUtils.isBlank(strCnpj) && ValidadorAlfanumerico.isNumerico(strCnpj)) {
            Long cnpj = UtilitarioFormatacao.obterLongMascara(strCnpj);
            if (cnpj != null) {
                Frota cadastrada = repositorio.pesquisarPorCnpj(cnpj);
                if (cadastrada != null) {
                    if(cadastrada.getStatus().equals(StatusFrota.PRE_CADASTRO.getValue())) {
                        throw new ExcecaoValidacao(mensagens.obterMensagem("frota.servico.preCadastro.cnpjPreCadastrado"));
                    }
                    throw new ExcecaoValidacao(mensagens.obterMensagem("frota.servico.preCadastro.cnpjHabilitado"));
                }
            }
        }
    }

    /**
     * Valida os dados basicos da frota para inserção/alteração
     *
     * @param cnpj O cnpj da frota
     * @param cpfResponsavel O cpf do responsavel pela frota
     * @param uf A unidade federativa da frota
     * @param inscricaoEstadual A inscricao estadual da frota
     * @throws ExcecaoValidacao Em caso de frota invalida
     */
    public void validarDadosBasicos(String cnpj, String cpfResponsavel, String uf, String inscricaoEstadual) throws ExcecaoValidacao {
        if (!StringUtils.isBlank(cnpj) && !ValidadorCnpj.isValidCNPJ(UtilitarioFormatacao.obterDigitosMascara(cnpj))) {
            throw new ExcecaoValidacao(mensagens.obterMensagem("frota.servico.validacao.cnpj.invalido"));
        }
        if (!StringUtils.isBlank(cpfResponsavel) && ValidadorCpf.invalidCPF(UtilitarioFormatacao.obterDigitosMascara(cpfResponsavel))) {
            throw new ExcecaoValidacao(mensagens.obterMensagem("frota.servico.validacao.cpf.invalido"));
        }
        if (!StringUtils.isBlank(uf) && !StringUtils.isBlank(inscricaoEstadual) && !ValidadorInscricaoEstadual.isValidIE(inscricaoEstadual, uf)) {
            throw new ExcecaoValidacao(mensagens.obterMensagem("frota.servico.validacao.inscricaoEstadual.invalida"));
        }
    }

    /**
     * Valida se o CNPJ da frota encontra-se na base de dados e
     * que se seu staus e de pre-cadastro
     *
     * @param strCnpj O CNPJ da Frota a habilitar
     * @throws ExcecaoValidacao Em caso de violacao de regra de negocio
     */
    public void validarFrotaParaHabilitacao(String strCnpj) throws ExcecaoValidacao {
        if (!StringUtils.isBlank(strCnpj) && ValidadorAlfanumerico.isNumerico(strCnpj) && ValidadorCnpj.isValidCNPJ(strCnpj)) {
            Long cnpj = UtilitarioFormatacao.obterLongMascara(strCnpj);
            if (cnpj != null) {
                Frota cadastrada = repositorio.pesquisarPorCnpj(cnpj);
                if (cadastrada == null) {
                    throw new ExcecaoValidacao(mensagens.obterMensagem("frota.servico.cnpjNaoEncontrado"));
                }
                if (!cadastrada.getStatus().equals(StatusFrota.PRE_CADASTRO.getValue())) {
                    throw new ExcecaoValidacao(mensagens.obterMensagem("frota.servico.habilitacao.jaHabilitado"));
                }
            }
        }
    }

    /**
     * Indica se a frota está em período de ativação temporária
     *
     * @param frota frota
     * @return indica se a frota está em período de ativação temporária
     */
    public boolean isFrotaEmAtivacaoTemporaria(Frota frota) {
        return frota.possuiAtivacaoTemporariaAtiva();
    }

    /**
     * Envia email de notificação aos administradores da solução sobre a alteração de status da frota.
     * @param frota Frota alterada
     * @param motivo motivo de alteração da frota
     */
    public void enviarEmailAlteracaoStatusFrota(Frota frota, MotivoAlteracaoStatusFrota motivo) {
        String nomeUsuario = motivo.getUsuario() != null ? motivo.getUsuario().getNome() : mensagens.obterMensagem("frota.servico.alteracao.email.usuario.sistema");
        enviarEmailAlteracaoStatusFrota(frota, motivo, nomeUsuario);
    }


    /**
     * Altera o status de ativacao de uma frota
     * @param idFrota A frota
     * @param tipoAlteracao O tipo de alteração (ativação ou inativação)
     * @param classificacao A classificacao do motivo da alteracao do status
     * @param justificativa A justificativa da alteracao
     * @param prazoAlteracaoTemporaria O prazo da alteração temporária
     * @return o motivo de inativação
     */
    public MotivoAlteracaoStatusFrota alterarStatusAtivacaoFrota(Long idFrota, TipoAlteracaoStatusFrota tipoAlteracao,
                                                                 ClassificacaoStatusFrota classificacao,
                                                                 String justificativa, Date prazoAlteracaoTemporaria) {
        Frota frota = repositorio.obterPorId(idFrota);
        MotivoAlteracaoStatusFrota motivoAlteracao = motivoAlteracaoStatusFrotaSd.criarMotivoAlteracaoStatus(frota, tipoAlteracao, classificacao, justificativa, prazoAlteracaoTemporaria, frota.getUltimoMotivoDefinitivo());
        frota.setStatus(motivoAlteracao.isAtivacao() ? StatusFrota.ATIVO.getValue() : StatusFrota.INATIVO.getValue());
        repositorio.armazenar(frota);
        return motivoAlteracao;
    }

    /**
     * Envia email de notificação aos administradores da soluçao sobre a alteração de status da frota
     *
     * @param frota Frota alterada
     * @param motivo motivo de alteração da frota
     * @param nomeUsuario Nome do usuário que gerou o incidente.
     */
    public void enviarEmailAlteracaoStatusFrota(Frota frota, MotivoAlteracaoStatusFrota motivo, String nomeUsuario) {
        List<Usuario> administradores = repositorioUsuario.obterPorTipoPerfilGestor(TipoPerfilUsuario.INTERNO, false);
        List<String> destinatarios = new ArrayList<>();
        for (Usuario usuario:administradores) {
            if(StringUtils.isNotBlank(usuario.getEmail())) {
                destinatarios.add(usuario.getEmail());
            }
        }
        String classificacao = motivo.getTipoMotivo() != null ? ClassificacaoStatusFrota.obterPorValor(motivo.getTipoMotivo()).getLabel() : mensagens.obterMensagem("texto.comum.vazio");
        String dataAlteracao = UtilitarioFormatacaoData.formatarDataHora(motivo.getDataCriacao());
        String descricao;
        if (ClassificacaoStatusFrota.DEBITO_VENCIDO.getValue().equals(motivo.getTipoMotivo()) || ClassificacaoStatusFrota.SALDO_ZERADO.getValue().equals(motivo.getTipoMotivo())) {
            descricao = StatusFrota.ATIVO.getValue().equals(motivo.getFrota().getStatus())
                    ? mensagens.obterMensagem(ClassificacaoStatusFrota.obterPorValor(motivo.getTipoMotivo()).getChaveMensagemReativacao())
                    : mensagens.obterMensagem(ClassificacaoStatusFrota.obterPorValor(motivo.getTipoMotivo()).getChaveMensagemInativacao());
        } else {
            descricao = motivo.getMotivo();
        }
        emailDados.enviarEmail(mensagens.obterMensagem("frota.servico.alteracao.email.assunto"),
                mensagens.obterMensagem("frota.servico.alteracao.email.mensagem", UtilitarioFormatacao.formatarCnpjApresentacao(frota.getCnpj()), frota.getRazaoSocial(),
                        StatusFrota.obterPorValor(frota.getStatus()).getLabel(), dataAlteracao, nomeUsuario, classificacao,
                        descricao, ambiente.getURLContextoAplicacao(), frota.getId()), destinatarios);

    }

    /**
     * Envia o motivo de alteração status frota para a fila de processamento.
     * @param idMotivoAlteracaoStatus Id do motivo alteração status frota.
     */
    public void incluirMotivoNaFilaDeEnvio(Long idMotivoAlteracaoStatus){
        filaEmailsAlteracaoStatusFrota.enviarMotivoParaFila(idMotivoAlteracaoStatus);
    }

    /**
     * Dispara emails informando sobre a alteração de status de uma frota.
     *
     * @param motivo o motivo da alteração de status
     */
    public void notificarAlteracaoFrota(MotivoAlteracaoStatusFrota motivo) {
        enviarEmailAlteracaoStatusFrota(motivo.getFrota(), motivo);
    }

    /**
     * Verifica se a frota esta inativa por qualquer motivo
     * @param frota para verificacao
     * @return true se a frota estiver inativa, false caso contrario.
     */
    public boolean isFrotaInativa(Frota frota) {
        return frota.getUltimoMotivoInativacao() != null;
    }

    /**
     * Verifica se a frota esta inativada por causa de saldo zerado
     * @param frota para verificacao
     * @return true se a frota esta inativada, false caso contrario
     */
    public boolean isFrotaInativadaPorSaldoZerado(Frota frota) {
        MotivoAlteracaoStatusFrota motivo = frota.getUltimoMotivoSaldoZeradoVigente();
        return motivo != null;
    }
    
    /**
     * Prepara uma frota para realizar pre cadastro
     *
     * @param frota A frota.
     * @param credenciamento O credenciamento.
     * @param cnpj Número do cnpj da frota.
     * @param cpf Número do CPF do responsável pela frota.
     * @return Status do credenciamento da Frota
     */
    public StatusCredenciamentoFrota obterStatusCredenciamento(Frota frota, Credenciamento credenciamento, String cnpj, Long cpf) {
    	if (frota != null) {
    		if(frota.getStatus().equals(StatusFrota.PRE_CADASTRO.getValue())) {
    			return StatusCredenciamentoFrota.AGUARDANDO_HABILITACAO;
    		}
    		return StatusCredenciamentoFrota.HABILITADO;
    	} else {
    		if (credenciamento == null) {
    			if (Boolean.TRUE.equals(repositorioLeadCredenciamento.validarLeadExistente(cnpj))) {
        			return StatusCredenciamentoFrota.INICIADO_SALESFORCE;
        		}
    			return StatusCredenciamentoFrota.INICIAR;
    		}
    		
    		if (Boolean.TRUE.equals(credenciamento.getFinalizado())) {
    			return StatusCredenciamentoFrota.FINALIZADO;
    		}
    		
    		if (!credenciamento.getCpf().equals(cpf)) {
    			return StatusCredenciamentoFrota.CREDENCIAMENTO_PENDENTE;
    		}
    		
    		if (isTokenValido(credenciamento.getTokenJWT())) {
    			return StatusCredenciamentoFrota.CONTINUAR_VALIDO;
    		} else {
    			return StatusCredenciamentoFrota.CONTINUAR_EXPIRADO;
    		}
    	}
    }
    
    /**
     * Valida a expiração do token. 
     * 
     * @param token Json web token codificado.
     * @return Caso valido true, se não false. 
     */
    private boolean isTokenValido(String token) {
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
     * Faz a pesquisa de frotas para exportação
     * @param filtro para a busca
     * @return os resultados da busca de acordo com o filtro
     */
    public ResultadoPaginado<Frota> pesquisarFrotasParaExportacao(FiltroPesquisaFrotaVo filtro) {
        InformacaoPaginacao informacaoPaginacao = filtro.getPaginacao();
        informacaoPaginacao.setTamanhoPagina(null);
        informacaoPaginacao.setPagina(null);
        filtro.setPaginacao(informacaoPaginacao);

        return repositorio.pesquisar(filtro);
    }

    /**
     * Cria um novo parâmetro de ciclo para a frota.
     * @param prazoCiclo prazo do ciclo enviado pelo SalesForce.
     * @param prazoPagamento prazo de pagamento enviado pelo SalesForce.
     * @return o parâmetro ciclo dcriado.
     */
    public ParametroCiclo criarNovoCicloFrota(String prazoCiclo, String prazoPagamento){
        ParametroCiclo novoParametroCiclo = new ParametroCiclo();
        novoParametroCiclo.setPrazoCiclo(Long.parseLong(prazoCiclo));
        novoParametroCiclo.setPrazoPagamento(Long.parseLong(prazoPagamento));

        novoParametroCiclo.setPrazoReembolso(Long.parseLong(prazoPagamento) + 1);
        repositorioParametroCiclo.armazenar(novoParametroCiclo);
        return novoParametroCiclo;
    }


    /**
     * Verifica o cpf dos participantes da administração da frota .
     *
     * @param cpfDonoFrota o cpf do Dono da frota.
     * @param cpfResponsavelFrota o cpf do responsável da frota.
     * @throws ExcecaoValidacao caso algum deles não possua cpf válido.
     */
    public void validarCpfEnvolvidosFrota(String cpfDonoFrota, String cpfResponsavelFrota) throws ExcecaoValidacao {
        if (ValidadorCpf.invalidCPF(cpfDonoFrota)) {
            throw new ExcecaoValidacao(mensagens.obterMensagem("frota.servico.cpfParticipanteDonoInvalido"));
        }
        if (ValidadorCpf.invalidCPF(cpfResponsavelFrota)) {
            throw new ExcecaoValidacao(mensagens.obterMensagem("frota.servico.cpfParticipanteResponsavelInvalido"));
        }
    }

    /**
     * Verifica se o email do assessor responsavel da frota eh valido.
     *
     * @param emailAssessorResponsavel email do assessor responsavel da frota
     * @throws ExcecaoValidacao caso o email do assessor responsavel da frota nao seja valido.
     */
    public void validarAssessorResponsavel(final String emailAssessorResponsavel) throws ExcecaoValidacao {
        if (emailAssessorResponsavel == null) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO_OBRIGATORIO, "emailAssessorResponsavel");
        }
        final Usuario usuario = repositorioUsuario.obterPorEmail(emailAssessorResponsavel);
        if (usuario == null) {
            throw new ExcecaoValidacao(mensagens.obterMensagem("frota.servico.preCadastro.emailAssessorResponsavel.invalido", emailAssessorResponsavel));
        }
        if (!usuario.isInterno()) {
            throw new ExcecaoValidacao(mensagens.obterMensagem("frota.servico.preCadastro.emailAssessorResponsavel.usuarioNaoInterno"));
        }
        usuario.transformarEmAssessor();
    }

    /**
     * Verifica se o email do consultor da frota eh valido.
     *
     * @param emailConsultor email do assessor responsavel da frota
     * @throws ExcecaoValidacao caso o email do consultor da frota nao seja valido.
     */
    public void validarConsultor(String emailConsultor) throws ExcecaoValidacao {
        if (emailConsultor == null) return;
        
        final Usuario usuario = repositorioUsuario.obterPorEmail(emailConsultor);
        if (usuario == null) {
            throw new ExcecaoValidacao(mensagens.obterMensagem("frota.servico.preCadastro.emailAssessorResponsavel.invalido", emailConsultor));
        }
        if (!usuario.isInterno()) {
            throw new ExcecaoValidacao(mensagens.obterMensagem("frota.servico.preCadastro.emailAssessorResponsavel.usuarioNaoInterno"));
        }
        usuario.transformarEmAssessor(); 
    }
}
