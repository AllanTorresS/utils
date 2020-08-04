package ipp.aci.boleia.util.rotas;

/**
 * Rotas das APIs REST do sistema
 */
public final  class Rotas {

    // Prefixos
    public static final  String BASE_API                     = "/api";
    public static final  String BASE_API_PUBLICA             = "/api/publico";
    public static final  String BASE_API_ESTATICA            = "/api/estatico";
    public static final  String BASE_API_DOCS                = "/v2/api-docs";

    // Login e logout
    public static final  String LOGIN                        = BASE_API_PUBLICA + "/login";
    public static final  String LOGOUT                       = BASE_API + "/logout";

    // APIs publicas
    public static final  String API_PUBLICA_ACESSO                    = BASE_API_PUBLICA + "/acesso";
    public static final  String API_PUBLICA_DISPOSITIVO               = BASE_API_PUBLICA + "/dispositivo";
    public static final  String API_PUBLICA_DISPOSITIVO_FROTISTA      = BASE_API_PUBLICA + "/dispositivoFrotista";
    public static final  String API_PUBLICA_COMANDA                   = BASE_API_PUBLICA + "/comanda";
    public static final  String API_PUBLICA_APP_MOTORISTA             = "/appMotorista";
    public static final  String API_PUBLICA_USUARIO_MOTORISTA         = BASE_API_PUBLICA + "/usuarioMotorista";
    public static final  String SISTEMA_EXTERNO_ACESSO_API            = BASE_API_PUBLICA + "/externo/acesso";
    public static final  String CREDENCIAMENT0_POSTO_ACESSO_API       = BASE_API_PUBLICA + "/credenciamentoPosto/acesso";
    public static final  String CREDENCIAMENT0_FROTA_ACESSO_API       = BASE_API_PUBLICA + "/credenciamentoFrota/acesso";
    public static final  String MODULO_INTERNO_ACESSO_API             = BASE_API_PUBLICA + "/moduloInterno/acesso";

    // APIs estaticas
    public static final  String API_ESTATICA_MOTORISTA       = BASE_API_ESTATICA + "/motorista";
    public static final  String API_ESTATICA_PV              = BASE_API_ESTATICA + "/pv";
    public static final  String API_ESTATICA_AUTORIZACAO     = BASE_API_ESTATICA + "/autorizacao";

    // APIs protegidas
    public static final  String MOTORISTA_API                        = BASE_API + "/motorista";
    public static final  String UNIDADE_API                          = BASE_API + "/unidade";
    public static final  String REDE_API                             = BASE_API + "/rede";
    public static final  String GRUPO_OPERACIONAL_API                = BASE_API + "/grupoOperacional";
    public static final  String EMPRESA_AGREGADA_API                 = BASE_API + "/empresaAgregada";
    public static final  String UNIDADE_EMPRESA_AGREGADA_API         = BASE_API + "/unidadeEmpresaAgregada";
    public static final  String STATUS_ATIVACAO_API                  = BASE_API + "/statusAtivacao";
    public static final  String STATUS_PERMISSAO_PRECO_API           = BASE_API + "/statusPermissaoPreco";
    public static final  String STATUS_INTEGRACAO_API                = BASE_API + "/statusIntegracao";
    public static final  String STATUS_INTEGRACAO_REEMBOLSO_API      = BASE_API + "/statusIntegracaoReembolso";
    public static final  String STATUS_BLOQUEIO_API                  = BASE_API + "/statusBloqueio";
    public static final  String STATUS_HABILITACAO_API               = BASE_API + "/statusHabilitacao";
    public static final  String STATUS_POSSE_API                     = BASE_API + "/statusPosse";
    public static final  String STATUS_COBRANCA_API                  = BASE_API + "/statusCobranca";
    public static final  String STATUS_REEMBOLSO_API                 = BASE_API + "/statusReembolso";
    public static final  String CLASSIFICACAO_API                    = BASE_API + "/classificacao";
    public static final  String ESTADOS_API                          = BASE_API + "/estado";
    public static final  String VEICULO_API                          = BASE_API + "/veiculo";
    public static final  String ABRIR_CHAMADO_API                    = BASE_API + "/chamado";
    public static final  String MOTIVO_CHAMADO_API                   = BASE_API + "/motivoChamado";
    public static final  String FROTA_API                            = BASE_API + "/frota";
    public static final  String PV_API                               = BASE_API + "/pv";
    public static final  String PERFIL_API                           = BASE_API + "/perfil";
    public static final  String PERMISSAO_API                        = BASE_API + "/permissao";
    public static final  String USUARIO_API                          = BASE_API + "/usuario";
    public static final  String USUARIO_FROTA_API                    = BASE_API + "/usuarioFrota";
    public static final  String USUARIO_REVENDA_API                  = BASE_API + "/usuarioRevenda";
    public static final  String USUARIO_INTERNO_API                  = BASE_API + "/usuarioInterno";
    public static final  String USUARIO_MOTORISTA_API                = BASE_API + "/usuarioMotorista";
    public static final  String COMANDA_API                          = BASE_API + "/comanda";
    public static final  String COMANDA_POSTO_INTERNO_API            = BASE_API + "/comandaPostoInterno";
    public static final  String DISPOSITIVO_API                      = BASE_API + "/dispositivo";
    public static final  String DISPOSITIVO_FROTISTA_API             = BASE_API + "/dispositivoFrotista";
    public static final  String DISPOSITIVO_PEDIDO_API               = BASE_API + "/pedido";
    public static final  String DISPOSITIVO_PEDIDO_PDV_API           = BASE_API + "/pedidoPdv";
    public static final  String NOTIFICACAO_API                      = BASE_API + "/notificacao";
    public static final  String FORMA_PAGAMENTO_API                  = BASE_API + "/formaPagamento";
    public static final  String VIP_API                              = BASE_API + "/vip";
    public static final  String AUTORIZACAO_API                      = BASE_API + "/autorizacao";
    public static final  String PRODUTO_API                          = BASE_API + "/produto";
    public static final  String NOTA_FISCAL_API                      = BASE_API + "/notaFiscal";
    public static final  String TRANS_CONSOL_API                     = BASE_API + "/transacaoConsolidada";
    public static final  String TRANS_CONSOL_FROTA_API               = BASE_API + "/transacaoConsolidadaFrota";
    public static final  String TRANS_CONSOL_REVENDA_API             = BASE_API + "/transacaoConsolidadaRevenda";
    public static final  String TRANS_CONSOL_SOLUCAO_API             = BASE_API + "/transacaoConsolidadaSolucao";
    public static final  String FINANCEIRO_REVENDA                   = BASE_API + "/financeiroRevenda";
    public static final  String PRECO_API                            = BASE_API + "/preco";
    public static final  String PRECO_BASE_API                       = BASE_API + "/precoBase";
    public static final  String PRECO_MICROMERCADO_API               = BASE_API + "/precoMicromercado";
    public static final  String MICROMERCADO_API                     = BASE_API + "/importarMicromercado";
    public static final  String NEGOCIACOES_API                      = BASE_API + "/negociacoes";
    public static final  String COBRANCA_API                         = BASE_API + "/cobranca";
    public static final  String CICLO_REPASSE_API                    = BASE_API + "/cicloRepasse";
    public static final  String REEMBOLSO_API                        = BASE_API + "/reembolso";
    public static final  String ABASTECIMENTO_API                    = BASE_API + "/abastecimento";
    public static final  String ABASTECIMENTO_FROTA_API              = BASE_API + "/abastecimentoFrota";
    public static final  String ABASTECIMENTO_REVENDA_API            = BASE_API + "/abastecimentoRevenda";
    public static final  String ABASTECIMENTO_SOLUCAO_API            = BASE_API + "/abastecimentoSolucao";
    public static final  String QUESTIONARIO_API                     = BASE_API + "/questionario";
    public static final  String CEP_API                              = BASE_API + "/cep";
    public static final  String ROTA_API                             = BASE_API + "/rota";
    public static final  String SERVICO_API                          = BASE_API + "/servico";
    public static final  String ACEITAR_PV_API                       = BASE_API + "/aceitarPv";
    public static final  String TIPO_PESSOA_API                      = BASE_API + "/tipoPessoa";
    public static final  String BANCO_API                            = BASE_API + "/banco";
    public static final  String PARAMETRO_SISTEMA_API                = BASE_API + "/parametroSistema";
    public static final  String PRODUTOS_ADICIONAIS_PDV              = BASE_API + "/produtosAdicionaisPDV";
    public static final  String SISTEMA_API                          = BASE_API + "/sistema";
    public static final  String API_TOKEN_API                        = BASE_API + "/apiToken";
    public static final  String KMVPDVCAMINHONEIRO_API               = BASE_API + "/kmvPdvCaminhoneiro";
    public static final  String CONECT_CAR_API                       = BASE_API + "/conectCar";
    public static final  String PEDIDO_CREDITO_FROTA_API             = BASE_API + "/pedidoCreditoFrota";
    public static final  String CONFIGURACAO_API                     = BASE_API + "/configuracao";
    public static final  String SISTEMA_EXTERNO_API                  = BASE_API + "/sistemaExterno";
    public static final  String MODULO_INTERNO_API                   = BASE_API + "/moduloInterno";
    public static final  String INTEGRADOR_API                       = BASE_API + "/integrador";
    public static final  String SALDO_FROTA_API                      = BASE_API + "/saldoFrota";
    public static final  String CONFIGURACAO_ROTINA_AVISO_DEBITO     = BASE_API + "/configuracaoRotinaAvisoDebito";
    public static final  String MOTOR_GERACAO_RELATORIO              = BASE_API + "/motorGeracaoRelatorio";
    public static final  String CREDENCIAMENTO_POSTO_API             = BASE_API + "/credenciamentoPosto";
    public static final  String POSTO_CREDENCIADO_API                = BASE_API + "/postoCredenciado";
    public static final  String CREDENCIAMENTO_FROTA_API             = BASE_API + "/credenciamentoFrota";

    public static final String CAMPANHA_API                          = BASE_API + "/campanha";
    public static final String BANDEIRA_API                          = BASE_API + "/bandeira";
    public static final String GRUPO_ECONOMICO_API                   = BASE_API + "/grupoEconomico";
    public static final String MUNICIPIO_API                         = BASE_API + "/municipio";
    public static final String PARAMETRO_CAMPANHA_API                = BASE_API + "/parametroCampanha";
    public static final String COORDENADORIA_API                     = BASE_API + "/coordenadoria";

    /**
     * Construtor privado, impede instanciacao e heranca
     */
    private Rotas() {
        // impede instanciacao e heranca
    }
}
