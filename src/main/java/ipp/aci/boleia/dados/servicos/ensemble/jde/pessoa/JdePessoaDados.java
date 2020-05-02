package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa;

import ipp.aci.boleia.dados.IFrotaDados;
import ipp.aci.boleia.dados.IPessoaDados;
import ipp.aci.boleia.dados.IPontoDeVendaDados;
import ipp.aci.boleia.dados.servicos.ensemble.ServicoSoapDados;
import ipp.aci.boleia.dados.servicos.ensemble.WsSecurityUsernameTokenHandler;
import ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.ArrayOfemailPairOflistaEmailKeyemail;
import ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.ArrayOftelefonePairOflistaTelefoneKeytelefone;
import ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.AtualizarJDEReq;
import ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.CadastrarJDEReq;
import ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.CadastrarJDEResp;
import ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.InativarJDEReq;
import ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.InformacoesTributarias;
import ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.PairOflistaEmailKeyemail;
import ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.PairOflistaTelefoneKeytelefone;
import ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.Pessoa;
import ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.PessoaSoap;
import ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws.Response;
import ipp.aci.boleia.dominio.Componente;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.Estado;
import ipp.aci.boleia.dominio.enums.TipoContribuinte;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.net.MalformedURLException;
import java.net.URL;

import static ipp.aci.boleia.util.UtilitarioFormatacao.formatarLimiteString;
import static ipp.aci.boleia.util.UtilitarioFormatacao.formatarNumeroZerosEsquerda;
import static ipp.aci.boleia.util.UtilitarioFormatacao.preencherComEspacos;
import static ipp.aci.boleia.util.UtilitarioFormatacao.removerAcentos;
import static ipp.aci.boleia.util.jde.ConstantesJde.CODIGO_CATEGORIA_EMPRESA;
import static ipp.aci.boleia.util.jde.ConstantesJde.COD_PAIS;
import static ipp.aci.boleia.util.jde.ConstantesJde.COMPANHIA_CLIENTE;
import static ipp.aci.boleia.util.jde.ConstantesJde.PESSOA_CENARIO_FROTISTA;
import static ipp.aci.boleia.util.jde.ConstantesJde.PESSOA_CENARIO_INATIVACAO;
import static ipp.aci.boleia.util.jde.ConstantesJde.PESSOA_CENARIO_REVENDEDOR;
import static ipp.aci.boleia.util.jde.ConstantesJde.PESSOA_CLASSE_CONTRIBUINTE;
import static ipp.aci.boleia.util.jde.ConstantesJde.PESSOA_CODIGO_APURACAO_IPI;
import static ipp.aci.boleia.util.jde.ConstantesJde.PESSOA_CODIGO_MOTIVO_INATIVACAO;
import static ipp.aci.boleia.util.jde.ConstantesJde.PESSOA_CODIGO_STATUS_FORNECEDOR;
import static ipp.aci.boleia.util.jde.ConstantesJde.PESSOA_COD_IBGE_PADRAO;
import static ipp.aci.boleia.util.jde.ConstantesJde.PESSOA_CONDICAO_PAGAMENTO_PV;
import static ipp.aci.boleia.util.jde.ConstantesJde.PESSOA_EMAIL_KEY;
import static ipp.aci.boleia.util.jde.ConstantesJde.PESSOA_INDICADOR_ICMS;
import static ipp.aci.boleia.util.jde.ConstantesJde.PESSOA_INDICADOR_ISS;
import static ipp.aci.boleia.util.jde.ConstantesJde.PESSOA_ISENTO;
import static ipp.aci.boleia.util.jde.ConstantesJde.PESSOA_LIMITE_25;
import static ipp.aci.boleia.util.jde.ConstantesJde.PESSOA_LIMITE_40;
import static ipp.aci.boleia.util.jde.ConstantesJde.PESSOA_REPASSE_ICMS;
import static ipp.aci.boleia.util.jde.ConstantesJde.PESSOA_SOMENTE_INFORMACOES_TRIBUTARIAS;
import static ipp.aci.boleia.util.jde.ConstantesJde.PESSOA_SUBSTITUICAO_ICMS;
import static ipp.aci.boleia.util.jde.ConstantesJde.PESSOA_TELEFONE_KEY;
import static ipp.aci.boleia.util.jde.ConstantesJde.PESSOA_TEM_RECEBIVEIS;
import static ipp.aci.boleia.util.jde.ConstantesJde.PESSOA_TIPO_EMAIL;
import static ipp.aci.boleia.util.jde.ConstantesJde.TAMANHO_ENDERECO_COMPLEMENTO;
import static ipp.aci.boleia.util.jde.ConstantesJde.TAMANHO_ENDERECO_NUMERO;
import static ipp.aci.boleia.util.jde.ConstantesJde.UNIDADE_NEGOCIO;

/**
 * Servico que acessa a JDE para obter os dados da Pessoa
 */
@Repository
@Scope("singleton")
public class JdePessoaDados extends ServicoSoapDados<PessoaSoap> implements IPessoaDados {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdePessoaDados.class);
    private static final String ARQUIVO_JDE_WSDL = "/wsdl/jde/pessoa.wsdl";
    private static final String NOME_SERVICO = "pessoaJDE";

    @Value("${jde.web.service.login}")
    private String jdeLogin;

    @Value("${jde.web.service.senha}")
    private String jdeSenha;

    @Autowired
    private IFrotaDados repositorioFrota;

    @Autowired
    private IPontoDeVendaDados repositorioPv;

    @Override
    protected WsSecurityUsernameTokenHandler instanciarHeadersHandler() {
        return new WsSecurityUsernameTokenHandler(jdeLogin, jdeSenha, NOME_SERVICO);
    }

    @Override
    protected PessoaSoap instanciarServico() throws MalformedURLException {
        String urlJde = obterUrlJdeCompleta();
        return new Pessoa(new URL(urlJde)).getPessoaSoap();
    }

    @Override
    public Long cadastro(PontoDeVenda pontoDeVenda, Usuario usuarioGestor) throws ExcecaoValidacao {
        CadastrarJDEReq req = obterRequisicaoCadastro(pontoDeVenda, usuarioGestor);
        return enviarRequisicaoCadastro(req);
    }

    @Override
    public Long cadastro(Frota frota, Usuario usuarioGestor) throws ExcecaoValidacao {
        CadastrarJDEReq req = obterRequisicaoCadastro(frota, usuarioGestor);
        return enviarRequisicaoCadastro(req);
    }

    @Override
    public Long atualizar(PontoDeVenda pontoDeVenda, Usuario usuarioGestor) throws ExcecaoValidacao {
        AtualizarJDEReq req = obterRequisicaoAtualizacao(pontoDeVenda, usuarioGestor);
        return enviarRequisicaoAlterar(req);
    }

    @Override
    public void atualizar(Frota frota, Usuario usuarioGestor) throws ExcecaoValidacao {
        AtualizarJDEReq req = obterRequisicaoAtualizacao(frota, usuarioGestor);
        enviarRequisicaoAlterar(req);
    }

    @Override
    public void excluir(Frota frota) throws ExcecaoValidacao {
        InativarJDEReq req = obterRequisicaoExclusao(frota);
        enviarRequisicaoInativar(req);
    }

    /**
     * Dado o endereco do wsdl, converte-o em um caminho absoluto
     *
     * @return url JDE completa
     */
    private String obterUrlJdeCompleta() {
        return this.getClass().getResource(ARQUIVO_JDE_WSDL).toString();
    }

    /**
     * Envia uma requisicao de cadastro de pessoa ao JDE
     *
     * @param req A requisicao
     * @return O resultado obtido
     * @throws ExcecaoValidacao Caso a invocacao falhe
     */
    private Long enviarRequisicaoCadastro(CadastrarJDEReq req) throws ExcecaoValidacao {
        CadastrarJDEResp response;
        try {
            response = getServico().cadastrarClienteJDE(req);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, ex, NOME_SERVICO + ".cadastro. " + ex.getMessage());
        }
        if (!response.isStatus() || response.getCodigoERP() == null) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO, response.getMsgErro());
        }
        return response.getCodigoERP();
    }

    /**
     * Envia uma requisicao de atualizacao de pessoa ao JDE
     *
     * @param req A requisicao
     * @return O resultado obtido
     * @throws ExcecaoValidacao Caso a invocacao falhe
     */
    private Long enviarRequisicaoAlterar(AtualizarJDEReq req) throws ExcecaoValidacao {
        Response response;
        try {
            response = getServico().atualizarClienteJDE(req);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, ex, NOME_SERVICO + ".atualizar");
        }
        if (!response.isStatus() || response.getCodigoERP() == null) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO, response.getMsgErro());
        }
        return response.getCodigoERP();
    }

    /**
     * Envia uma requisicao de inativacao de pessoa ao JDE
     *
     * @param req A requisicao
     * @return O resultado obtido
     * @throws ExcecaoValidacao Caso a invocacao falhe
     */
    private Long enviarRequisicaoInativar(InativarJDEReq req) throws ExcecaoValidacao {
        Response response;
        try {
            response = getServico().inativarClienteJDE(req);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, ex, NOME_SERVICO + ".inativar");
        }
        if (!response.isStatus() || response.getCodigoERP() == null) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO, response.getMsgErro());
        }
        return response.getCodigoERP();
    }

    /**
     * Constroi objeto para realizar cadastro de Ponto de Venda
     * no servico pessoa do JDE
     *
     * @param pontoDeVenda ponto de venda
     * @param usuarioGestor usuário gestor do ponto de venda
     * @return requisição que será enviada ao JDE
     */
    public CadastrarJDEReq obterRequisicaoCadastro(PontoDeVenda pontoDeVenda, Usuario usuarioGestor) {
        CadastrarJDEReq req = preencherParametrosFixosCadastro();
        req.setCenario(PESSOA_CENARIO_REVENDEDOR);
        req.setCondicaoPagamento(PESSOA_CONDICAO_PAGAMENTO_PV);

        Componente areaAbastecimento = pontoDeVenda.getComponenteAreaAbastecimento();
        req.setNomeCliente(formatarLimiteString(areaAbastecimento.getNomePessoa(), PESSOA_LIMITE_40).toUpperCase());
        req.setInscricaoEstadual(areaAbastecimento.getInscricaoEstadual() == null ? PESSOA_ISENTO : areaAbastecimento.getInscricaoEstadual().toUpperCase());
        String cnpjString = formatarNumeroZerosEsquerda(areaAbastecimento.getCodigoPessoa(), 14).toUpperCase();
        req.setCpfCnpj(cnpjString);
        pontoDeVenda.setNumeroSequencialJde(obterNumeroSequencialJDE(areaAbastecimento.getCodigoPessoa()));
        req.setPontoEntrega(obterPontoEntrega(cnpjString,pontoDeVenda.getNumeroSequencialJde()));
        req.setEnderecoCompleto(formatarLimiteString(pontoDeVenda.getLogradouroENumero(), PESSOA_LIMITE_40).toUpperCase());
        req.setLogradouro(formatarLimiteString(pontoDeVenda.getLogradouroEndereco(), PESSOA_LIMITE_40).toUpperCase());
        String numero = pontoDeVenda.getNumeroEndereco() != null ? pontoDeVenda.getNumeroEndereco().toString() : "";
        String complemento = pontoDeVenda.getComplementoEndereco() != null ? pontoDeVenda.getComplementoEndereco() : "";
        numero = preencherComEspacos(numero,TAMANHO_ENDERECO_NUMERO,false);
        complemento = preencherComEspacos(complemento,TAMANHO_ENDERECO_COMPLEMENTO,false);
        req.setNumero(formatarLimiteString(numero + complemento, PESSOA_LIMITE_40).toUpperCase());
        req.setBairro(formatarLimiteString(pontoDeVenda.getBairro(), PESSOA_LIMITE_40).toUpperCase());
        req.setMunicipio(obterCodigoIbge(pontoDeVenda.getCodigoIBGE()));
        if (pontoDeVenda.getUf() != null) {
        	Estado estado = Estado.obterPorSigla(pontoDeVenda.getUf());
            req.setEstado(estado.getSigla().toUpperCase());
        }
        req.setCidade(formatarLimiteString(removerAcentos(pontoDeVenda.getMunicipio()), PESSOA_LIMITE_25).toUpperCase());
        req.setCep(formatarNumeroZerosEsquerda(pontoDeVenda.getCep(),8).toUpperCase());
        req.setListaEmail(constroiListaEmailJDE(usuarioGestor));
        req.setNomeCorrespondencia(formatarLimiteString(usuarioGestor.getNome(), PESSOA_LIMITE_40).toUpperCase());
        if(areaAbastecimento.getInscricaoMunicipal() != null){
            req.getInformacoesTributarias().setInscricaoMunicipal(areaAbastecimento.getInscricaoMunicipal().toUpperCase());
        }
        if(usuarioGestor.getDddTelefoneCelular() != null && usuarioGestor.getTelefoneCelular() != null){
            req.setListaTelefone(constroiListaTelefoneJDE(usuarioGestor));
        }

        return req;
    }

    /**
     * Constroi objeto para realizar cadastro de Frota
     * no servico pessoa do JDE
     * @param frota frota
     * @param usuarioGestor usuário gestor da frota
     * @return requisição que será enviada ao JDE
     */
    public CadastrarJDEReq obterRequisicaoCadastro(Frota frota, Usuario usuarioGestor) {
        CadastrarJDEReq cad = preencherParametrosFixosCadastro();
        cad.setCenario(PESSOA_CENARIO_FROTISTA);
        String condicaoPagamento = formatarNumeroZerosEsquerda(frota.getParametroCiclo().getPrazoPagamento(), 3);
        cad.setCondicaoPagamento(condicaoPagamento);
        cad.setCodCatBeneficioFiscal(frota.getCodCatBeneficioFiscal());

        cad.setNomeCliente(formatarLimiteString(frota.getRazaoSocial(), PESSOA_LIMITE_40).toUpperCase());
        cad.setInscricaoEstadual(frota.getInscricaoEstadual() == null ? PESSOA_ISENTO : frota.getInscricaoEstadual().toString().toUpperCase());
        String cnpjString = formatarNumeroZerosEsquerda(frota.getCnpj(), 14).toUpperCase();
        cad.setCpfCnpj(cnpjString);
        frota.setNumeroSequencialJde(obterNumeroSequencialJDE(frota.getCnpj()));
        cad.setPontoEntrega(obterPontoEntrega(cnpjString,frota.getNumeroSequencialJde()));
        cad.setEnderecoCompleto(formatarLimiteString(frota.getLogradouroENumero(), PESSOA_LIMITE_40).toUpperCase());
        cad.setLogradouro(formatarLimiteString(frota.getLogradouro(), PESSOA_LIMITE_40).toUpperCase());
        cad.setNumero(formatarLimiteString(frota.getNumero().toString(), PESSOA_LIMITE_40).toUpperCase());
        cad.setBairro(formatarLimiteString(frota.getBairro(), PESSOA_LIMITE_40).toUpperCase());
        cad.setMunicipio(obterCodigoIbge(frota.getCodigoIBGE()));
        Estado estado = Estado.obterPorSigla(frota.getUnidadeFederativa());
        cad.setEstado(estado.getSigla().toUpperCase());
        cad.setCidade(formatarLimiteString(removerAcentos(frota.getMunicipio()), PESSOA_LIMITE_25).toUpperCase());
        cad.setCep(formatarNumeroZerosEsquerda(frota.getCep(),8).toUpperCase());
        cad.setListaEmail(constroiListaEmailJDE(usuarioGestor));
        cad.setNomeCorrespondencia(formatarLimiteString(frota.getRazaoSocial(), PESSOA_LIMITE_40).toUpperCase());
        if(frota.getInscricaoMunicipal() != null){
            cad.getInformacoesTributarias().setInscricaoMunicipal(frota.getInscricaoMunicipal().toString().toUpperCase());
        }
        if(usuarioGestor.getDddTelefoneCelular() != null && usuarioGestor.getTelefoneCelular() != null){
            cad.setListaTelefone(constroiListaTelefoneJDE(usuarioGestor));
        }

        return cad;
    }

    /**
     * Constroi objeto para realizar atualizacao de Ponto de Venda
     * no servico pessoa do JDE
     * @param pv ponto de venda
     * @param usuarioGestor usuário gestor do ponto de venda
     * @return requisição que será enviada ao JDE
     */
    public AtualizarJDEReq obterRequisicaoAtualizacao(PontoDeVenda pv, Usuario usuarioGestor) {
        AtualizarJDEReq req = preencherParametrosFixosAtualizacao();
        req.setCenario(PESSOA_CENARIO_REVENDEDOR);
        Componente areaAbastecimento = pv.getComponenteAreaAbastecimento();
        req.setNomeCliente(formatarLimiteString(areaAbastecimento.getNomePessoa(), PESSOA_LIMITE_40).toUpperCase());
        req.setInscricaoEstadual(areaAbastecimento.getInscricaoEstadual() == null ? PESSOA_ISENTO : areaAbastecimento.getInscricaoEstadual().toUpperCase());
        String cnpjString = formatarNumeroZerosEsquerda(areaAbastecimento.getCodigoPessoa(), 14).toUpperCase();
        req.setCpfCnpj(cnpjString);
        req.setPontoEntrega(obterPontoEntrega(cnpjString,pv.getNumeroSequencialJde()));
        preencherDadosEnderecoAtualizacao(req, pv.getLogradouroENumero(), pv.getLogradouroEndereco(), pv.getNumeroEndereco().toString(), pv.getBairro(), pv.getCodigoIBGE(), pv.getUf(), pv.getMunicipio(), pv.getCep());
        req.setListaEmail(constroiListaEmailJDE(usuarioGestor));
        req.setNomeCorrespondencia(formatarLimiteString(usuarioGestor.getNome(), PESSOA_LIMITE_40).toUpperCase());
        req.setCodigoCliente(new Long(pv.getNumeroJdeInterno()));
        if(areaAbastecimento.getInscricaoMunicipal() != null){
            req.getInformacoesTributarias().setInscricaoMunicipal(areaAbastecimento.getInscricaoMunicipal().toUpperCase());
        }
        if(usuarioGestor.getDddTelefoneCelular() != null && usuarioGestor.getTelefoneCelular() != null){
            req.setListaTelefone(constroiListaTelefoneJDE(usuarioGestor));
        }
        return req;
    }

    /**
     * Constroi objeto para realizar atualizacao de Frota
     * no servico pessoa do JDE
     * @param frota frota
     * @param usuarioGestor usuário gestor da frota
     * @return requisição que será enviada ao JDE
     */
    public AtualizarJDEReq obterRequisicaoAtualizacao(Frota frota, Usuario usuarioGestor) {
        AtualizarJDEReq req = preencherParametrosFixosAtualizacao();
        req.setCenario(PESSOA_CENARIO_FROTISTA);
        String condicaoPagamento = formatarNumeroZerosEsquerda(frota.getParametroCiclo().getPrazoPagamento(), 3).toUpperCase();
        req.setCondicaoPagamento(condicaoPagamento);
        req.setCodCatBeneficioFiscal(frota.getCodCatBeneficioFiscal());
        req.setNomeCliente(formatarLimiteString(frota.getRazaoSocial(), PESSOA_LIMITE_40).toUpperCase());
        req.setInscricaoEstadual(frota.getInscricaoEstadual() == null ? PESSOA_ISENTO : frota.getInscricaoEstadual().toString().toUpperCase());
        String cnpjString = formatarNumeroZerosEsquerda(frota.getCnpj(), 14).toUpperCase();
        req.setCpfCnpj(cnpjString);
        req.setPontoEntrega(obterPontoEntrega(cnpjString,frota.getNumeroSequencialJde()));
        preencherDadosEnderecoAtualizacao(req, frota.getLogradouroENumero(), frota.getLogradouro(), frota.getNumero().toString(), frota.getBairro(), frota.getCodigoIBGE(), frota.getUnidadeFederativa(), frota.getMunicipio(), frota.getCep());
        req.setListaEmail(constroiListaEmailJDE(usuarioGestor));
        req.setNomeCorrespondencia(formatarLimiteString(frota.getRazaoSocial(), PESSOA_LIMITE_40).toUpperCase());
        req.setCodigoCliente(new Long(frota.getNumeroJdeInterno()));
        if(frota.getInscricaoMunicipal() != null){
            req.getInformacoesTributarias().setInscricaoMunicipal(frota.getInscricaoMunicipal().toString().toUpperCase());
        }
        if(usuarioGestor.getDddTelefoneCelular() != null && usuarioGestor.getTelefoneCelular() != null){
            req.setListaTelefone(constroiListaTelefoneJDE(usuarioGestor));
        }

        return req;
    }

    /**
     * Preenche os dados de endereco na requisicao a ser enviada
     * @param req A requisicao
     * @param logradouroENumero O logradouro concatenado com o numero do estabelecimento
     * @param logradouro  O logradouro
     * @param numero O numero
     * @param bairro O bairro
     * @param codigoIbge O codigo do municipio
     * @param uf A unidade federativa
     * @param municipio O municipio
     * @param cep O CEP
     */
    private void preencherDadosEnderecoAtualizacao(AtualizarJDEReq req, String logradouroENumero, String logradouro, String numero, String bairro, String codigoIbge, String uf, String municipio, Number cep) {
        req.setEnderecoCompleto(formatarLimiteString(logradouroENumero, PESSOA_LIMITE_40).toUpperCase());
        req.setLogradouro(formatarLimiteString(logradouro, PESSOA_LIMITE_40).toUpperCase());
        req.setNumero(formatarLimiteString(numero, PESSOA_LIMITE_40).toUpperCase());
        req.setBairro(formatarLimiteString(bairro, PESSOA_LIMITE_40).toUpperCase());
        req.setMunicipio(obterCodigoIbge(codigoIbge));
        Estado estado = Estado.obterPorSigla(uf);
        req.setEstado(estado.getSigla().toUpperCase());
        req.setCidade(formatarLimiteString(removerAcentos(municipio), PESSOA_LIMITE_25).toUpperCase());
        req.setCep(formatarNumeroZerosEsquerda(cep,8).toUpperCase());
    }

    /**
     * Constroi o objeto de lista de emails da requisicao
     * @param usuarioGestor O usuario gestor destinatario
     * @return O objeto de emails para povoar a requisicao
     */
    private ArrayOfemailPairOflistaEmailKeyemail constroiListaEmailJDE(Usuario usuarioGestor) {
        ArrayOfemailPairOflistaEmailKeyemail listaEmail = new ArrayOfemailPairOflistaEmailKeyemail();
        PairOflistaEmailKeyemail pairEmail = new PairOflistaEmailKeyemail();
        pairEmail.setTipoEmail(PESSOA_TIPO_EMAIL);
        pairEmail.setListaEmailKey(PESSOA_EMAIL_KEY);
        pairEmail.setEMail(usuarioGestor.getEmail().toUpperCase());
        listaEmail.getEmail().add(pairEmail);
        return listaEmail;
    }

    /**
     * Constroi o objeto de lista de telefones da requisicao
     * @param usuarioGestor O usuario gestor em questao
     * @return O objeto de telefones para povoar a requisicao
     */
    private ArrayOftelefonePairOflistaTelefoneKeytelefone constroiListaTelefoneJDE(Usuario usuarioGestor) {
        ArrayOftelefonePairOflistaTelefoneKeytelefone listaTelefone = new ArrayOftelefonePairOflistaTelefoneKeytelefone();
        PairOflistaTelefoneKeytelefone telefone = new PairOflistaTelefoneKeytelefone();

        telefone.setDdd(usuarioGestor.getDddTelefoneCelular().toString().toUpperCase());
        telefone.setNumeroTelefone(usuarioGestor.getTelefoneCelular().toString().toUpperCase());
        telefone.setListaTelefoneKey(PESSOA_TELEFONE_KEY);

        listaTelefone.getTelefone().add(telefone);
        return listaTelefone;
    }

    /**
     * Preenche os dados fixos comuns a todos os cadastros
     * @return O objeto da requisicao povoado com os dados fixos
     */
    private CadastrarJDEReq preencherParametrosFixosCadastro() {
        CadastrarJDEReq cadastro = new CadastrarJDEReq();
        cadastro.setSomenteInformacoesTributarias(PESSOA_SOMENTE_INFORMACOES_TRIBUTARIAS);
        cadastro.setUnidadeNegocio(UNIDADE_NEGOCIO);
        cadastro.setTipoContribuinte(TipoContribuinte.PESSOA_JURIDICA.getValue().toString());
        cadastro.setTemRecebiveis(PESSOA_TEM_RECEBIVEIS);
        cadastro.setCodCatEmpresa(CODIGO_CATEGORIA_EMPRESA);
        cadastro.setCompanhiaCliente(COMPANHIA_CLIENTE);
        cadastro.setPais(COD_PAIS);

        InformacoesTributarias infoTrib = new InformacoesTributarias();
        infoTrib.setClasseContribuinte(PESSOA_CLASSE_CONTRIBUINTE);
        infoTrib.setRepasseICMS(PESSOA_REPASSE_ICMS);
        infoTrib.setIndicadorICMS(PESSOA_INDICADOR_ICMS);
        infoTrib.setIndicadorISS(PESSOA_INDICADOR_ISS);
        infoTrib.setSubstituicaoICMS(PESSOA_SUBSTITUICAO_ICMS);
        infoTrib.setCodigoApuracaoIPI(PESSOA_CODIGO_APURACAO_IPI);
        cadastro.setInformacoesTributarias(infoTrib);
        return cadastro;
    }

    /**
     * Preenche os dados fixos comuns a todas as atualizacoes cadastrais
     * @return O objeto da requisicao povoado com os dados fixos
     */
    private AtualizarJDEReq preencherParametrosFixosAtualizacao() {
        AtualizarJDEReq atualizacao = new AtualizarJDEReq();
        atualizacao.setSomenteInformacoesTributarias(PESSOA_SOMENTE_INFORMACOES_TRIBUTARIAS);
        atualizacao.setUnidadeNegocio(UNIDADE_NEGOCIO);
        atualizacao.setTipoContribuinte(TipoContribuinte.PESSOA_JURIDICA.getValue().toString().toUpperCase());
        atualizacao.setTemRecebiveis(PESSOA_TEM_RECEBIVEIS);
        atualizacao.setCodCatEmpresa(CODIGO_CATEGORIA_EMPRESA);
        atualizacao.setCompanhiaCliente(COMPANHIA_CLIENTE);
        atualizacao.setPais(COD_PAIS);

        InformacoesTributarias infoTrib = new InformacoesTributarias();
        infoTrib.setClasseContribuinte(PESSOA_CLASSE_CONTRIBUINTE);
        infoTrib.setRepasseICMS(PESSOA_REPASSE_ICMS);
        infoTrib.setIndicadorICMS(PESSOA_INDICADOR_ICMS);
        infoTrib.setIndicadorISS(PESSOA_INDICADOR_ISS);
        infoTrib.setSubstituicaoICMS(PESSOA_SUBSTITUICAO_ICMS);
        infoTrib.setCodigoApuracaoIPI(PESSOA_CODIGO_APURACAO_IPI);
        atualizacao.setInformacoesTributarias(infoTrib);
        return atualizacao;
    }

    /**
     * Constroi objeto para realizar exclusão de Frota
     * no servico pessoa do JDE
     * @param frota a excluir
     * @return requisição que será enviada ao JDE
     */
    public InativarJDEReq obterRequisicaoExclusao(Frota frota){
        InativarJDEReq req = preencherParametrosFixosInativacaoFrota();
        String cpfCnpj = formatarNumeroZerosEsquerda(frota.getCnpj(), 14).toUpperCase();
        req.setCpfCnpj(cpfCnpj);
        req.setPontoEntrega(obterPontoEntrega(cpfCnpj,frota.getNumeroSequencialJde()));
        req.setCodigoCliente(frota.getNumeroJdeInterno());
        return req;
    }

    /**
     * Preenche os parametros fixos comuns a todas as inativacoes de frota
     * @return A requisicao com os dados fixos preenchidos
     */
    private InativarJDEReq preencherParametrosFixosInativacaoFrota() {
        InativarJDEReq req = new InativarJDEReq();
        req.setCenario(PESSOA_CENARIO_INATIVACAO);
        req.setCodCatMotivoInativacao(PESSOA_CODIGO_MOTIVO_INATIVACAO);
        req.setCodCatStatusFornecedor(PESSOA_CODIGO_STATUS_FORNECEDOR);
        return req;
    }

    /**
     * Retorna uma string formatada representando o ponto de entrega
     * @param cnpj O cnpj da empresa
     * @param sequencia O numero sequencial da empresa no JDE
     * @return Uma string formatada representando o ponto de entrega
     */
    private String obterPontoEntrega(String cnpj, Long sequencia){
        StringBuilder pontoEntrega = new StringBuilder();
        pontoEntrega.append(COMPANHIA_CLIENTE)
                .append(cnpj)
                .append(StringUtils.leftPad(sequencia.toString(), 2, "0"));
        return pontoEntrega.toString();
    }

    /**
     * Obtem o numero sequencial JDE para a empresa em questao
     * @param cnpj O cnpj da empresa
     * @return O numero sequencial JDE
     */
    private Long obterNumeroSequencialJDE(Long cnpj){
        Long quantidadeCnpjIntegrado = 0L;
        quantidadeCnpjIntegrado += repositorioFrota.obterQuantidadeIntegradaPorCnpj(cnpj);
        quantidadeCnpjIntegrado += repositorioPv.obterQuantidadeIntegradaPorCnpj(cnpj);
        return quantidadeCnpjIntegrado + 1L;
    }

    /**
     * Formata o codigo IBGE do municipio para envio ao JDE
     * @param campoIbge O codigo IBGE
     * @return O codigo formatado
     */
    private String obterCodigoIbge(String campoIbge){
        return formatarLimiteString(campoIbge != null &&
                campoIbge.trim().length() > 0 ?
                campoIbge : PESSOA_COD_IBGE_PADRAO, PESSOA_LIMITE_25)
                .toUpperCase();
    }
}
