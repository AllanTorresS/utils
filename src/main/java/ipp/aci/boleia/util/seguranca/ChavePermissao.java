package ipp.aci.boleia.util.seguranca;

/**
 * Enumera as chaves de permissao do sistema para concessao
 * de permissoes de acesso nas APIs REST
 */
public final class ChavePermissao {

    private static final String SPRING_ROLE_PREFIX = "hasRole('ROLE_";
    private static final String SPRING_ROLE_SUFFIX = "')";

    public static final String PUBLICO                                      = "permitAll()";
    public static final String AUTENTICADO                                  = "isAuthenticated()";

    public static final String COMANDA_BLOQUEIO                             = SPRING_ROLE_PREFIX + "COMANDA_BLOQUEIO" + SPRING_ROLE_SUFFIX;
    public static final String COMANDA_CONSULTAR_E_VISUALIZAR               = SPRING_ROLE_PREFIX + "COMANDA_CONSULTAR_E_VISUALIZAR" + SPRING_ROLE_SUFFIX;
    public static final String COMANDA_EXCLUIR                              = SPRING_ROLE_PREFIX + "COMANDA_EXCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String COMANDA_INCLUIR                              = SPRING_ROLE_PREFIX + "COMANDA_INCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String COMANDA_REGERAR_TOKEN                        = SPRING_ROLE_PREFIX + "COMANDA_REGERAR_TOKEN" + SPRING_ROLE_SUFFIX;
    public static final String COMANDA_PI_BLOQUEIO                          = SPRING_ROLE_PREFIX + "COMANDA_PI_BLOQUEIO" + SPRING_ROLE_SUFFIX;
    public static final String COMANDA_PI_CONSULTAR_E_VISUALIZAR            = SPRING_ROLE_PREFIX + "COMANDA_PI_CONSULTAR_E_VISUALIZAR" + SPRING_ROLE_SUFFIX;
    public static final String COMANDA_PI_EXCLUIR                           = SPRING_ROLE_PREFIX + "COMANDA_PI_EXCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String COMANDA_PI_INCLUIR                           = SPRING_ROLE_PREFIX + "COMANDA_PI_INCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String COMANDA_PI_REGERAR_TOKEN                     = SPRING_ROLE_PREFIX + "COMANDA_PI_REGERAR_TOKEN" + SPRING_ROLE_SUFFIX;
    public static final String DISPOSITIVO_MOTORISTA_BLOQUEIO               = SPRING_ROLE_PREFIX + "DISPOSITIVO_MOTORISTA_BLOQUEIO" + SPRING_ROLE_SUFFIX;
    public static final String DISPOSITIVO_MOTORISTA_CONSULTAR_E_VISUALIZAR = SPRING_ROLE_PREFIX + "DISPOSITIVO_MOTORISTA_CONSULTAR_E_VISUALIZAR" + SPRING_ROLE_SUFFIX;
    public static final String DISPOSITIVO_MOTORISTA_EXCLUIR                = SPRING_ROLE_PREFIX + "DISPOSITIVO_MOTORISTA_EXCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String DISPOSITIVO_MOTORISTA_ATUALIZAR_TOKEN_PUSH   = SPRING_ROLE_PREFIX + "DISPOSITIVO_MOTORISTA_ATUALIZAR_TOKEN_PUSH" + SPRING_ROLE_SUFFIX;
    public static final String DISPOSITIVO_MOTORISTA_RECUPERAR_SENHA        = SPRING_ROLE_PREFIX + "DISPOSITIVO_MOTORISTA_RECUPERAR_SENHA" + SPRING_ROLE_SUFFIX;
    public static final String DISPOSITIVO_MOTORISTA_SOLICITAR_AJUDA_NOTA   = SPRING_ROLE_PREFIX + "DISPOSITIVO_MOTORISTA_SOLICITAR_AJUDA_NOTA" + SPRING_ROLE_SUFFIX;
    public static final String DISPOSITIVO_MOTORISTA_OBTER_NOTAS            = SPRING_ROLE_PREFIX + "DISPOSITIVO_MOTORISTA_OBTER_NOTAS" + SPRING_ROLE_SUFFIX;
    public static final String DISPOSITIVO_MOTORISTA_VERIFICAR_HABILITADO   = SPRING_ROLE_PREFIX + "DISPOSITIVO_MOTORISTA_VERIFICAR_HABILITADO" + SPRING_ROLE_SUFFIX;
    public static final String DISPOSITIVO_MOTORISTA_REGERAR_TOKEN          = SPRING_ROLE_PREFIX + "DISPOSITIVO_MOTORISTA_REGERAR_TOKEN" + SPRING_ROLE_SUFFIX;
    public static final String FROTA_ATIVAR_E_INATIVAR                      = SPRING_ROLE_PREFIX + "FROTA_ATIVAR_E_INATIVAR" + SPRING_ROLE_SUFFIX;
    public static final String FROTA_CONSULTAR_E_VISUALIZAR                 = SPRING_ROLE_PREFIX + "FROTA_CONSULTAR_E_VISUALIZAR" + SPRING_ROLE_SUFFIX;
    public static final String FROTA_PRE_CADASTRAR                          = SPRING_ROLE_PREFIX + "FROTA_PRE_CADASTRAR" + SPRING_ROLE_SUFFIX;
    public static final String FROTA_EXCLUIR                                = SPRING_ROLE_PREFIX + "FROTA_EXCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String FROTA_ALTERAR                                = SPRING_ROLE_PREFIX + "FROTA_ALTERAR" + SPRING_ROLE_SUFFIX;
    public static final String FROTA_EXPORTAR                               = SPRING_ROLE_PREFIX + "FROTA_EXPORTAR" + SPRING_ROLE_SUFFIX;
    public static final String FROTA_HABILITAR                              = SPRING_ROLE_PREFIX + "FROTA_HABILITAR" + SPRING_ROLE_SUFFIX;
    public static final String FROTA_ATIVAR_TEMPORARIAMENTE                 = SPRING_ROLE_PREFIX + "FROTA_ATIVAR_TEMPORARIAMENTE" + SPRING_ROLE_SUFFIX;
    public static final String GRUPO_OPERACIONAL_ALTERAR                    = SPRING_ROLE_PREFIX + "GRUPO_OPERACIONAL_ALTERAR" + SPRING_ROLE_SUFFIX;
    public static final String GRUPO_OPERACIONAL_CONSULTAR_E_VISUALIZAR     = SPRING_ROLE_PREFIX + "GRUPO_OPERACIONAL_CONSULTAR_E_VISUALIZAR" + SPRING_ROLE_SUFFIX;
    public static final String GRUPO_OPERACIONAL_EXCLUIR                    = SPRING_ROLE_PREFIX + "GRUPO_OPERACIONAL_EXCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String GRUPO_OPERACIONAL_INCLUIR                    = SPRING_ROLE_PREFIX + "GRUPO_OPERACIONAL_INCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String MOTORISTA_ALTERAR                            = SPRING_ROLE_PREFIX + "MOTORISTA_ALTERAR" + SPRING_ROLE_SUFFIX;
    public static final String MOTORISTA_CONSULTAR_E_VISUALIZAR             = SPRING_ROLE_PREFIX + "MOTORISTA_CONSULTAR_E_VISUALIZAR" + SPRING_ROLE_SUFFIX;
    public static final String MOTORISTA_GERAR_SOFT_TOKEN                   = SPRING_ROLE_PREFIX + "MOTORISTA_GERAR_SOFT_TOKEN" + SPRING_ROLE_SUFFIX;
    public static final String MOTORISTA_EXCLUIR                            = SPRING_ROLE_PREFIX + "MOTORISTA_EXCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String MOTORISTA_INCLUIR                            = SPRING_ROLE_PREFIX + "MOTORISTA_INCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String MOTORISTA_INCLUIR_EM_LOTES                   = SPRING_ROLE_PREFIX + "MOTORISTA_INCLUIR_EM_LOTES" + SPRING_ROLE_SUFFIX;
    public static final String NOTA_FISCAL_CONSULTAR_E_VISUALIZAR           = SPRING_ROLE_PREFIX + "NOTA_FISCAL_CONSULTAR_E_VISUALIZAR" + SPRING_ROLE_SUFFIX;
    public static final String NOTA_FISCAL_DOWNLOAD                         = SPRING_ROLE_PREFIX + "NOTA_FISCAL_DOWNLOAD" + SPRING_ROLE_SUFFIX;
    public static final String NOTA_FISCAL_EXCLUIR                          = SPRING_ROLE_PREFIX + "NOTA_FISCAL_EXCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String NOTA_FISCAL_UPLOAD                           = SPRING_ROLE_PREFIX + "NOTA_FISCAL_UPLOAD" + SPRING_ROLE_SUFFIX;
    public static final String JUSTIFICATIVA_NOTA_UPLOAD                    = SPRING_ROLE_PREFIX + "JUSTIFICATIVA_NOTA_UPLOAD" + SPRING_ROLE_SUFFIX;
    public static final String JUSTIFICATIVA_NOTA_EXCLUIR                   = SPRING_ROLE_PREFIX + "JUSTIFICATIVA_NOTA_EXCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String PERFIL_ALTERAR                               = SPRING_ROLE_PREFIX + "PERFIL_ALTERAR" + SPRING_ROLE_SUFFIX;
    public static final String PERFIL_CONSULTAR_E_VISUALIZAR                = SPRING_ROLE_PREFIX + "PERFIL_CONSULTAR_E_VISUALIZAR" + SPRING_ROLE_SUFFIX;
    public static final String PERFIL_EXCLUIR                               = SPRING_ROLE_PREFIX + "PERFIL_EXCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String PONTO_DE_VENDA_CONSULTAR_E_VISUALIZAR        = SPRING_ROLE_PREFIX + "PONTO_DE_VENDA_CONSULTAR_E_VISUALIZAR" + SPRING_ROLE_SUFFIX;
    public static final String PONTO_DE_VENDA_EXPORTAR                      = SPRING_ROLE_PREFIX + "PONTO_DE_VENDA_EXPORTAR" + SPRING_ROLE_SUFFIX;
    public static final String PONTO_DE_VENDA_EDITAR                        = SPRING_ROLE_PREFIX + "PONTO_DE_VENDA_EDITAR" + SPRING_ROLE_SUFFIX;
    public static final String PONTO_DE_VENDA_PERMISSAO_PRECO_POSTO_ATIVAR  = SPRING_ROLE_PREFIX + "PONTO_DE_VENDA_PERMISSAO_PRECO_POSTO_ATIVAR" + SPRING_ROLE_SUFFIX;
    public static final String PONTO_DE_VENDA_PERMISSAO_PRECO_POSTO_DESATIVAR = SPRING_ROLE_PREFIX + "PONTO_DE_VENDA_PERMISSAO_PRECO_POSTO_DESATIVAR" + SPRING_ROLE_SUFFIX;
    public static final String PRECO_CONSULTAR_E_VISUALIZAR                 = SPRING_ROLE_PREFIX + "PRECO_CONSULTAR_E_VISUALIZAR" + SPRING_ROLE_SUFFIX;
    public static final String PRECO_CRIAR_E_ALTERAR                        = SPRING_ROLE_PREFIX + "PRECO_CRIAR_E_ALTERAR" + SPRING_ROLE_SUFFIX;
    public static final String PRECO_EXCLUIR                                = SPRING_ROLE_PREFIX + "PRECO_EXCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String PRECO_APROVAR_REPROVAR                       = SPRING_ROLE_PREFIX + "PRECO_APROVAR_REPROVAR" + SPRING_ROLE_SUFFIX;
    public static final String PRECO_EXPORTAR                               = SPRING_ROLE_PREFIX + "PRECO_EXPORTAR" + SPRING_ROLE_SUFFIX;
    public static final String PRECO_MICROMERCADO_CONSULTAR_E_VISUALIZAR    = SPRING_ROLE_PREFIX + "PRECO_MICROMERCADO_CONSULTAR_E_VISUALIZAR" + SPRING_ROLE_SUFFIX;
    public static final String PRECO_MICROMERCADO_INCLUIR                   = SPRING_ROLE_PREFIX + "PRECO_MICROMERCADO_INCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String PRECO_MICROMERCADO_EXPORTAR                  = SPRING_ROLE_PREFIX + "PRECO_MICROMERCADO_EXPORTAR" + SPRING_ROLE_SUFFIX;
    public static final String PRECO_MICROMERCADO_INCLUIR_EM_LOTES          = SPRING_ROLE_PREFIX + "PRECO_MICROMERCADO_INCLUIR_EM_LOTES" + SPRING_ROLE_SUFFIX;
    public static final String TRANSFERIR_GESTAO                            = SPRING_ROLE_PREFIX + "TRANSFERIR_GESTAO" + SPRING_ROLE_SUFFIX;
    public static final String USUARIO_ALTERAR                              = SPRING_ROLE_PREFIX + "USUARIO_ALTERAR" + SPRING_ROLE_SUFFIX;
    public static final String USUARIO_CONSULTAR_E_VISUALIZAR               = SPRING_ROLE_PREFIX + "USUARIO_CONSULTAR_E_VISUALIZAR" + SPRING_ROLE_SUFFIX;
    public static final String USUARIO_EXCLUIR                              = SPRING_ROLE_PREFIX + "USUARIO_EXCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String USUARIO_INCLUIR                              = SPRING_ROLE_PREFIX + "USUARIO_INCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String USUARIO_REENVIAR_LINK_ACESSO                 = SPRING_ROLE_PREFIX + "USUARIO_REENVIAR_LINK_ACESSO" + SPRING_ROLE_SUFFIX;
    public static final String USUARIO_MOTORISTA_CONSULTAR_E_VISUALIZAR     = SPRING_ROLE_PREFIX + "USUARIO_MOTORISTA_CONSULTAR_E_VISUALIZAR" + SPRING_ROLE_SUFFIX;
    public static final String USUARIO_MOTORISTA_REGERAR_SENHA_TEMPORARIA   = SPRING_ROLE_PREFIX + "USUARIO_MOTORISTA_REGERAR_SENHA_TEMPORARIA" + SPRING_ROLE_SUFFIX;
    public static final String VEICULO_ALTERAR                              = SPRING_ROLE_PREFIX + "VEICULO_ALTERAR" + SPRING_ROLE_SUFFIX;
    public static final String VEICULO_CONSULTAR_E_VISUALIZAR               = SPRING_ROLE_PREFIX + "VEICULO_CONSULTAR_E_VISUALIZAR" + SPRING_ROLE_SUFFIX;
    public static final String VEICULO_EXCLUIR                              = SPRING_ROLE_PREFIX + "VEICULO_EXCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String VEICULO_INCLUIR                              = SPRING_ROLE_PREFIX + "VEICULO_INCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String VEICULO_INCLUIR_EM_LOTES                     = SPRING_ROLE_PREFIX + "VEICULO_INCLUIR_EM_LOTES" + SPRING_ROLE_SUFFIX;
    public static final String NEGOCIACAO_CONSULTAR_E_VISUALIZAR            = SPRING_ROLE_PREFIX + "NEGOCIACAO_CONSULTAR_E_VISUALIZAR" + SPRING_ROLE_SUFFIX;
    public static final String NEGOCIACAO_INCLUIR                           = SPRING_ROLE_PREFIX + "NEGOCIACAO_INCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String NEGOCIACAO_ALTERAR                           = SPRING_ROLE_PREFIX + "NEGOCIACAO_ALTERAR" + SPRING_ROLE_SUFFIX;
    public static final String NEGOCIACAO_EXCLUIR                           = SPRING_ROLE_PREFIX + "NEGOCIACAO_EXCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String NEGOCIACAO_INCLUIR_EM_LOTES                  = SPRING_ROLE_PREFIX + "NEGOCIACAO_INCLUIR_EM_LOTES" + SPRING_ROLE_SUFFIX;
    public static final String PRECO_BASE_NEGOCIAR                          = SPRING_ROLE_PREFIX + "PRECO_BASE_NEGOCIAR" + SPRING_ROLE_SUFFIX;
    public static final String PRECO_BASE_CONSULTAR                         = SPRING_ROLE_PREFIX + "PRECO_BASE_CONSULTAR" + SPRING_ROLE_SUFFIX;
    public static final String PRECO_BASE_EXPORTAR                          = SPRING_ROLE_PREFIX + "PRECO_BASE_EXPORTAR" + SPRING_ROLE_SUFFIX;
    public static final String PRECO_BASE_INCLUIR                           = SPRING_ROLE_PREFIX + "PRECO_BASE_INCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String PRECO_BASE_INCLUIR_EM_LOTES                  = SPRING_ROLE_PREFIX + "PRECO_BASE_INCLUIR_EM_LOTES" + SPRING_ROLE_SUFFIX;
    public static final String ABASTECIMENTO_CONSULTAR_E_VISUALIZAR         = SPRING_ROLE_PREFIX + "ABASTECIMENTO_CONSULTAR_E_VISUALIZAR" + SPRING_ROLE_SUFFIX;
    public static final String ABASTECIMENTO_AUTORIZAR                      = SPRING_ROLE_PREFIX + "ABASTECIMENTO_AUTORIZAR" + SPRING_ROLE_SUFFIX;
    public static final String ABASTECIMENTO_APROVAR_REPROVAR               = SPRING_ROLE_PREFIX + "ABASTECIMENTO_APROVAR_REPROVAR" + SPRING_ROLE_SUFFIX;
    public static final String ABASTECIMENTO_INCLUIR_PENDENTE               = SPRING_ROLE_PREFIX + "ABASTECIMENTO_INCLUIR_PENDENTE" + SPRING_ROLE_SUFFIX;
    public static final String ABASTECIMENTO_EXPORTAR                       = SPRING_ROLE_PREFIX + "ABASTECIMENTO_EXPORTAR" + SPRING_ROLE_SUFFIX;
    public static final String ABASTECIMENTO_EXPORTAR_TXT                   = SPRING_ROLE_PREFIX + "ABASTECIMENTO_EXPORTAR_TXT" + SPRING_ROLE_SUFFIX;
    public static final String ABASTECIMENTO_CONSULTAR_SALDO_LIMITE_FROTA   = SPRING_ROLE_PREFIX + "ABASTECIMENTO_CONSULTAR_SALDO_LIMITE_FROTA" + SPRING_ROLE_SUFFIX;
    public static final String ABASTECIMENTO_ESTORNAR                       = SPRING_ROLE_PREFIX + "ABASTECIMENTO_ESTORNAR" + SPRING_ROLE_SUFFIX;
    public static final String ABASTECIMENTO_ALTERAR_HODOMETRO_HORIMETRO    = SPRING_ROLE_PREFIX + "ABASTECIMENTO_ALTERAR_HODOMETRO_HORIMETRO" + SPRING_ROLE_SUFFIX;
    public static final String AUTORIZACAO_PAGAMENTO_AUTORIZAR_CONTINGENCIA = SPRING_ROLE_PREFIX + "AUTORIZACAO_PAGAMENTO_AUTORIZAR_CONTINGENCIA" + SPRING_ROLE_SUFFIX;
    public static final String DISPOSITIVO_PEDIDO_CONSULTAR_E_VISUALIZAR    = SPRING_ROLE_PREFIX + "DISPOSITIVO_PEDIDO_CONSULTAR_E_VISUALIZAR" + SPRING_ROLE_SUFFIX;
    public static final String AUTORIZACAO_PAGAMENTO_AUTORIZAR_ABASTECIMENTO= SPRING_ROLE_PREFIX + "AUTORIZACAO_PAGAMENTO_AUTORIZAR_ABASTECIMENTO" + SPRING_ROLE_SUFFIX;
    public static final String AUTORIZACAO_PAGAMENTO_PRE_AUTORIZAR          = SPRING_ROLE_PREFIX + "AUTORIZACAO_PAGAMENTO_PRE_AUTORIZAR" + SPRING_ROLE_SUFFIX;
    public static final String AUTORIZACAO_PAGAMENTO_AUTORIZAR              = SPRING_ROLE_PREFIX + "AUTORIZACAO_PAGAMENTO_AUTORIZAR" + SPRING_ROLE_SUFFIX;
    public static final String AUTORIZACAO_PAGAMENTO_AUTORIZAR_PEDIDO       = SPRING_ROLE_PREFIX + "AUTORIZACAO_PAGAMENTO_AUTORIZAR_PEDIDO" + SPRING_ROLE_SUFFIX;
    public static final String AUTORIZACAO_ABASTECIMENTO_AUTORIZAR_PI_PORTAL= SPRING_ROLE_PREFIX + "AUTORIZACAO_ABASTECIMENTO_AUTORIZAR_PI_PORTAL" + SPRING_ROLE_SUFFIX;
    public static final String ABASTECIMENTO_INTERNO_FROTA_INCLUIR          = SPRING_ROLE_PREFIX + "ABASTECIMENTO_INTERNO_FROTA_INCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String ABASTECIMENTO_INTERNO_FROTA_ALTERAR          = SPRING_ROLE_PREFIX + "ABASTECIMENTO_INTERNO_FROTA_ALTERAR" + SPRING_ROLE_SUFFIX;
    public static final String ABASTECIMENTO_SOLUCAO_ALTERAR                = SPRING_ROLE_PREFIX + "ABASTECIMENTO_SOLUCAO_ALTERAR" + SPRING_ROLE_SUFFIX;
    public static final String LISTAR_PONTOS_DE_VENDA_USUARIO               = SPRING_ROLE_PREFIX + "LISTAR_PONTOS_DE_VENDA_USUARIO" + SPRING_ROLE_SUFFIX;
    public static final String AUTORIZACAO_PAGAMENTO_VALIDAR                = SPRING_ROLE_PREFIX + "AUTORIZACAO_PAGAMENTO_VALIDAR" + SPRING_ROLE_SUFFIX;
    public static final String VIP_AUTENTICAR                               = SPRING_ROLE_PREFIX + "VIP_AUTENTICAR" + SPRING_ROLE_SUFFIX;
    public static final String VIP_VALIDAR                                  = SPRING_ROLE_PREFIX + "VIP_VALIDAR" + SPRING_ROLE_SUFFIX;
    public static final String PORTAL_PDV_ACESSAR                           = SPRING_ROLE_PREFIX + "PORTAL_PDV_ACESSAR" + SPRING_ROLE_SUFFIX;
    public static final String ROTA_ALTERAR                                 = SPRING_ROLE_PREFIX + "ROTA_ALTERAR" + SPRING_ROLE_SUFFIX;
    public static final String ROTA_CONSULTAR_E_VISUALIZAR                  = SPRING_ROLE_PREFIX + "ROTA_CONSULTAR_E_VISUALIZAR" + SPRING_ROLE_SUFFIX;
    public static final String ROTA_EXCLUIR                                 = SPRING_ROLE_PREFIX + "ROTA_EXCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String ROTA_INCLUIR                                 = SPRING_ROLE_PREFIX + "ROTA_INCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String COBRANCA_CONSULTAR                           = SPRING_ROLE_PREFIX + "COBRANCA_CONSULTAR" + SPRING_ROLE_SUFFIX;
    public static final String COBRANCA_EXPORTAR                            = SPRING_ROLE_PREFIX + "COBRANCA_EXPORTAR" + SPRING_ROLE_SUFFIX;
    public static final String CICLO_REPASSE_EXPORTAR                       = SPRING_ROLE_PREFIX + "CICLO_REPASSE_EXPORTAR" + SPRING_ROLE_SUFFIX;
    public static final String COBRANCA_GERAR_FATURA                        = SPRING_ROLE_PREFIX + "COBRANCA_GERAR_FATURA" + SPRING_ROLE_SUFFIX;
    public static final String COBRANCA_GERAR_BOLETO                        = SPRING_ROLE_PREFIX + "COBRANCA_GERAR_BOLETO" + SPRING_ROLE_SUFFIX;
    public static final String COBRANCA_AJUSTE_BOLETO                       = SPRING_ROLE_PREFIX + "COBRANCA_AJUSTE_BOLETO" + SPRING_ROLE_SUFFIX;
    public static final String REEMBOLSO_CONSULTAR                          = SPRING_ROLE_PREFIX + "REEMBOLSO_CONSULTAR" + SPRING_ROLE_SUFFIX;
    public static final String REEMBOLSO_EXPORTAR                           = SPRING_ROLE_PREFIX + "REEMBOLSO_EXPORTAR" + SPRING_ROLE_SUFFIX;
    public static final String REEMBOLSO_GERAR_VOUCHER                      = SPRING_ROLE_PREFIX + "REEMBOLSO_GERAR_VOUCHER" + SPRING_ROLE_SUFFIX;
    public static final String ACEITAR_TERMO_USO_PV                         = SPRING_ROLE_PREFIX + "ACEITAR_TERMO_USO_PV" + SPRING_ROLE_SUFFIX;
    public static final String PARAMETRO_SISTEMA_CONSULTAR_E_VISUALIZAR     = SPRING_ROLE_PREFIX + "PARAMETRO_SISTEMA_CONSULTAR_E_VISUALIZAR" + SPRING_ROLE_SUFFIX;
    public static final String PARAMETRO_SISTEMA_ALTERAR                    = SPRING_ROLE_PREFIX + "PARAMETRO_SISTEMA_ALTERAR" + SPRING_ROLE_SUFFIX;
    public static final String DISPOSITIVO_MOTORISTA_GERAR_PEDIDO           = SPRING_ROLE_PREFIX + "DISPOSITIVO_MOTORISTA_GERAR_PEDIDO" + SPRING_ROLE_SUFFIX;
    public static final String ROTINA_DISPARAR_MANUALMENTE                  = SPRING_ROLE_PREFIX + "ROTINA_DISPARAR_MANUALMENTE" + SPRING_ROLE_SUFFIX;
    public static final String ABRIR_CHAMADO                                = SPRING_ROLE_PREFIX + "ABRIR_CHAMADO" + SPRING_ROLE_SUFFIX;
    public static final String API_TOKEN_CONSULTAR                          = SPRING_ROLE_PREFIX + "API_TOKEN_CONSULTAR" + SPRING_ROLE_SUFFIX;
    public static final String API_TOKEN_GERAR                              = SPRING_ROLE_PREFIX + "API_TOKEN_GERAR" + SPRING_ROLE_SUFFIX;
    public static final String API_TOKEN_BLOQUEIO                           = SPRING_ROLE_PREFIX + "API_TOKEN_BLOQUEIO" + SPRING_ROLE_SUFFIX;
    public static final String API_TOKEN_EXCLUIR                            = SPRING_ROLE_PREFIX + "API_TOKEN_EXCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String BUSCAR_BICOS                                 = SPRING_ROLE_PREFIX + "BUSCAR_BICOS" + SPRING_ROLE_SUFFIX;
    public static final String BUSCAR_ABASTECIMENTOS                        = SPRING_ROLE_PREFIX + "BUSCAR_ABASTECIMENTOS" + SPRING_ROLE_SUFFIX;
    public static final String BUSCAR_PARAMETROS                            = SPRING_ROLE_PREFIX + "BUSCAR_PARAMETROS" + SPRING_ROLE_SUFFIX;
    public static final String CONECT_CAR                                   = SPRING_ROLE_PREFIX + "CONECT_CAR" + SPRING_ROLE_SUFFIX;
    public static final String CREDITO_PRE_PAGO_COMPRAR                     = SPRING_ROLE_PREFIX + "CREDITO_PRE_PAGO_COMPRAR" + SPRING_ROLE_SUFFIX;
    public static final String CREDITO_PRE_PAGO_CONSULTAR                   = SPRING_ROLE_PREFIX + "CREDITO_PRE_PAGO_CONSULTAR" + SPRING_ROLE_SUFFIX;
    public static final String CREDITO_PRE_PAGO_REENVIAR_JDE                = SPRING_ROLE_PREFIX + "CREDITO_PRE_PAGO_REENVIAR_JDE" + SPRING_ROLE_SUFFIX;
    public static final String CREDITO_PRE_PAGO_CANCELAR                    = SPRING_ROLE_PREFIX + "CREDITO_PRE_PAGO_CANCELAR" + SPRING_ROLE_SUFFIX;
    public static final String CREDITO_PRE_PAGO_APROVAR                     = SPRING_ROLE_PREFIX + "CREDITO_PRE_PAGO_APROVAR" + SPRING_ROLE_SUFFIX;
    public static final String CONFIGURACAO_SMS_ALTERAR                     = SPRING_ROLE_PREFIX + "CONFIGURACAO_SMS_ALTERAR" + SPRING_ROLE_SUFFIX;
    public static final String CONFIGURACAO_AVISO_DEBITO                    = SPRING_ROLE_PREFIX + "CONFIGURACAO_AVISO_DEBITO" + SPRING_ROLE_SUFFIX;
    public static final String LISTAR_PRODUTOS_ADICIONAIS_PDV               = SPRING_ROLE_PREFIX + "LISTAR_PRODUTOS_ADICIONAIS_PDV" + SPRING_ROLE_SUFFIX;
    public static final String SISTEMA_EXTERNO_INCLUIR                      = SPRING_ROLE_PREFIX + "SISTEMA_EXTERNO_INCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String SISTEMA_EXTERNO_CONSULTAR_E_VISUALIZAR       = SPRING_ROLE_PREFIX + "SISTEMA_EXTERNO_CONSULTAR_E_VISUALIZAR" + SPRING_ROLE_SUFFIX;
    public static final String SISTEMA_EXTERNO_ALTERAR_STATUS               = SPRING_ROLE_PREFIX + "SISTEMA_EXTERNO_ALTERAR_STATUS" + SPRING_ROLE_SUFFIX;
    public static final String SISTEMA_EXTERNO_REGERAR_CHAVES               = SPRING_ROLE_PREFIX + "SISTEMA_EXTERNO_REGERAR_CHAVES" + SPRING_ROLE_SUFFIX;
    public static final String SISTEMA_EXTERNO_EXCLUIR                      = SPRING_ROLE_PREFIX + "SISTEMA_EXTERNO_EXCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String SISTEMA_EXTERNO_REENVIAR_CHAVES              = SPRING_ROLE_PREFIX + "SISTEMA_EXTERNO_REENVIAR_CHAVES" + SPRING_ROLE_SUFFIX;
    public static final String SISTEMA_EXTERNO_EDITAR                       = SPRING_ROLE_PREFIX + "SISTEMA_EXTERNO_EDITAR" + SPRING_ROLE_SUFFIX;
    public static final String UNIDADE_EMPRESA_AGREGADA_CONSULTAR           = SPRING_ROLE_PREFIX + "UNIDADE_EMPRESA_AGREGADA_CONSULTAR" + SPRING_ROLE_SUFFIX;
    public static final String UNIDADE_EMPRESA_AGREGADA_INCLUIR             = SPRING_ROLE_PREFIX + "UNIDADE_EMPRESA_AGREGADA_INCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String UNIDADE_EMPRESA_AGREGADA_EDITAR              = SPRING_ROLE_PREFIX + "UNIDADE_EMPRESA_AGREGADA_EDITAR" + SPRING_ROLE_SUFFIX;
    public static final String UNIDADE_EMPRESA_AGREGADA_EXCLUIR             = SPRING_ROLE_PREFIX + "UNIDADE_EMPRESA_AGREGADA_EXCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String MOTORISTA_EXPORTAR                           = SPRING_ROLE_PREFIX + "MOTORISTA_EXPORTAR" + SPRING_ROLE_SUFFIX;
    public static final String RELATORIO_CREDITO_FROTA_CONSULTAR            = SPRING_ROLE_PREFIX + "RELATORIO_CREDITO_FROTA_CONSULTAR" + SPRING_ROLE_SUFFIX;
    public static final String RELATORIO_CREDITO_FROTA_EXPORTAR             = SPRING_ROLE_PREFIX + "RELATORIO_CREDITO_FROTA_EXPORTAR" + SPRING_ROLE_SUFFIX;
    public static final String POSTO_CREDENCIADO_CONSULTAR                  = SPRING_ROLE_PREFIX + "POSTO_CREDENCIADO_CONSULTAR" + SPRING_ROLE_SUFFIX;

    public static final String CANCELAR_ABASTECIMENTO_PI                    = SPRING_ROLE_PREFIX + "CANCELAR_ABASTECIMENTO_PI" + SPRING_ROLE_SUFFIX;

    public static final String CAMPANHA_CONSULTAR_VISUALIZAR                = SPRING_ROLE_PREFIX + "CAMPANHA_CONSULTAR_VISUALIZAR" + SPRING_ROLE_SUFFIX;
    public static final String CAMPANHA_CADASTRAR                           = SPRING_ROLE_PREFIX + "CAMPANHA_CADASTRAR" + SPRING_ROLE_SUFFIX;
    public static final String CAMPANHA_EXCLUIR                             = SPRING_ROLE_PREFIX + "CAMPANHA_EXCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String CAMPANHA_ALTERAR                             = SPRING_ROLE_PREFIX + "CAMPANHA_ALTERAR" + SPRING_ROLE_SUFFIX;
    public static final String CAMPANHA_APROVAR                             = SPRING_ROLE_PREFIX + "CAMPANHA_APROVAR" + SPRING_ROLE_SUFFIX;
    public static final String CAMPANHA_CONFIGURAR                          = SPRING_ROLE_PREFIX + "CAMPANHA_CONFIGURAR" + SPRING_ROLE_SUFFIX;

    public static final String MOTOR_GERACAO_RELATORIO_CONSULTAR_E_VISUALIZAR = SPRING_ROLE_PREFIX + "MOTOR_GERACAO_RELATORIO_CONSULTAR_E_VISUALIZAR" + SPRING_ROLE_SUFFIX;

    public static final String PARCERIAS_VISUALIZAR = SPRING_ROLE_PREFIX + "PARCERIAS_VISUALIZAR" + SPRING_ROLE_SUFFIX;

    // Permissão da API de Frotistas.
    public static final String API_FROTISTA                                         = SPRING_ROLE_PREFIX + "API_FROTISTA" + SPRING_ROLE_SUFFIX;
    public static final String API_FROTISTA_PREFIX                                  = API_FROTISTA + " or " + SPRING_ROLE_PREFIX;
    public static final String API_FROTISTA_ABASTECIMENTO_PESQUISA                  = API_FROTISTA_PREFIX + "API_FROTISTA_ABASTECIMENTO_PESQUISA" + SPRING_ROLE_SUFFIX;
    public static final String API_FROTISTA_MOTORISTA_PESQUISA                      = API_FROTISTA_PREFIX + "API_FROTISTA_MOTORISTA_PESQUISA" + SPRING_ROLE_SUFFIX;
    public static final String API_FROTISTA_COBRANCA_PESQUISA                       = API_FROTISTA_PREFIX + "API_FROTISTA_COBRANCA_PESQUISA" + SPRING_ROLE_SUFFIX;
    public static final String API_FROTISTA_NOTA_FISCAL_PESQUISA                    = API_FROTISTA_PREFIX + "API_FROTISTA_NOTA_FISCAL_PESQUISA" + SPRING_ROLE_SUFFIX;
	public static final String API_FROTISTA_MOTORISTA_INCLUIR                       = API_FROTISTA_PREFIX + "API_FROTISTA_MOTORISTA_INCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String API_FROTISTA_MOTORISTA_EDITAR                        = API_FROTISTA_PREFIX + "API_FROTISTA_MOTORISTA_EDITAR" + SPRING_ROLE_SUFFIX;
    public static final String API_FROTISTA_MOTORISTA_EXCLUIR                       = API_FROTISTA_PREFIX + "API_FROTISTA_MOTORISTA_EXCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String API_FROTISTA_VEICULO_PESQUISA                        = API_FROTISTA_PREFIX + "API_FROTISTA_VEICULO_PESQUISA" + SPRING_ROLE_SUFFIX;
    public static final String API_FROTISTA_VEICULO_INCLUIR                         = API_FROTISTA_PREFIX + "API_FROTISTA_VEICULO_INCLUIR" + SPRING_ROLE_SUFFIX;

    // Permissão da API de Sistemas Externos.
    public static final String API_EXTERNO                                          = SPRING_ROLE_PREFIX + "API_EXTERNO" + SPRING_ROLE_SUFFIX;
    public static final String API_EXTERNO_PREFIX                                   = API_EXTERNO + " or " + SPRING_ROLE_PREFIX;
    public static final String API_EXTERNO_TELEMETRIA_INCLUIR                       = API_EXTERNO_PREFIX + "API_EXTERNO_TELEMETRIA_INCLUIR" + SPRING_ROLE_SUFFIX;
    public static final String API_EXTERNO_CONSULTAR_MOTORISTA                      = API_EXTERNO_PREFIX + "API_EXTERNO_CONSULTAR_MOTORISTA" + SPRING_ROLE_SUFFIX;


    // Permissão da API de Sistemas Externos POS.
    public static final String API_EXTERNO_CONSULTAR_POSTO_ABADI                = SPRING_ROLE_PREFIX + "API_EXTERNO_CONSULTAR_POSTO_ABADI"               + SPRING_ROLE_SUFFIX;
    public static final String API_EXTERNO_CONFIRMAR_STATUS_TRANSACAO_FL        = SPRING_ROLE_PREFIX + "API_EXTERNO_CONFIRMAR_STATUS_TRANSACAO_FL"       + SPRING_ROLE_SUFFIX;
    public static final String API_EXTERNO_CONSULTAR_PEDIDO                     = SPRING_ROLE_PREFIX + "API_EXTERNO_CONSULTAR_PEDIDO"                    + SPRING_ROLE_SUFFIX;
    public static final String API_EXTERNO_CONSULTA_PRODUTOS_ADICIONAIS_FROTA   = SPRING_ROLE_PREFIX + "API_EXTERNO_CONSULTA_PRODUTOS_ADICIONAIS_FROTA"  + SPRING_ROLE_SUFFIX;
    public static final String API_EXTERNO_AUTORIZAR_PAGAMENTO_PEDIDO           = SPRING_ROLE_PREFIX + "API_EXTERNO_AUTORIZAR_PAGAMENTO_PEDIDO"          + SPRING_ROLE_SUFFIX;
    public static final String API_EXTERNO_AUTORIZAR_PAGAMENTO_PLACA            = SPRING_ROLE_PREFIX + "API_EXTERNO_AUTORIZAR_PAGAMENTO_PLACA"           + SPRING_ROLE_SUFFIX;
    public static final String API_EXTERNO_CONSULTAR_FROTA_PLACA                = SPRING_ROLE_PREFIX + "API_EXTERNO_CONSULTAR_FROTA_PLACA"               + SPRING_ROLE_SUFFIX;
    public static final String API_EXTERNO_VALIDAR_CPF_MOTORISTA                = SPRING_ROLE_PREFIX + "API_EXTERNO_VALIDAR_CPF_MOTORISTA"               + SPRING_ROLE_SUFFIX;
    public static final String API_EXTERNO_VALIDAR_HODOMETRO_HORIMETRO          = SPRING_ROLE_PREFIX + "API_EXTERNO_VALIDAR_HODOMETRO_HORIMETRO"         + SPRING_ROLE_SUFFIX;
    public static final String API_EXTERNO_ENVIAR_SMS_MOTORISTA                 = SPRING_ROLE_PREFIX + "API_EXTERNO_ENVIAR_SMS_MOTORISTA"                + SPRING_ROLE_SUFFIX;
    public static final String API_EXTERNO_CONSULTAR_VEICULO_PLACA              = SPRING_ROLE_PREFIX + "API_EXTERNO_CONSULTAR_VEICULO_PLACA"             + SPRING_ROLE_SUFFIX;
    public static final String API_EXTERNO_BUSCAR_VEICULO                       = SPRING_ROLE_PREFIX + "API_EXTERNO_BUSCAR_VEICULO"                      + SPRING_ROLE_SUFFIX;

    // Permissão da API de Frotas
    public static final String API_EXTERNO_CONSULTAR_FROTA                      = SPRING_ROLE_PREFIX + "API_EXTERNO_CONSULTAR_FROTA"                     + SPRING_ROLE_SUFFIX;

    // Permissão da API do Integrador
    public static final String API_INTEGRADOR_CONSULTAR_BICOS                   = SPRING_ROLE_PREFIX + "API_INTEGRADOR_CONSULTAR_BICOS"                  + SPRING_ROLE_SUFFIX;
    public static final String API_INTEGRADOR_CONSULTAR_ABASTECIMENTOS          = SPRING_ROLE_PREFIX + "API_INTEGRADOR_CONSULTAR_ABASTECIMENTOS"         + SPRING_ROLE_SUFFIX;
    
    // Permissão da API do credenciamento de postos
    public static final String API_CREDENCIAMENTO_PDV_CONSULTAR_STATUS         = SPRING_ROLE_PREFIX + "API_CREDENCIAMENTO_PDV_CONSULTAR_STATUS"        + SPRING_ROLE_SUFFIX;
    public static final String API_CREDENCIAMENTO_PDV_LISTAR_BANDEIRA          = SPRING_ROLE_PREFIX + "API_CREDENCIAMENTO_PDV_LISTAR_BANDEIRA"         + SPRING_ROLE_SUFFIX;
    public static final String API_CREDENCIAMENTO_PDV_ENVIAR_LEAD              = SPRING_ROLE_PREFIX + "API_CREDENCIAMENTO_PDV_ENVIAR_LEAD"             + SPRING_ROLE_SUFFIX;
    public static final String API_CREDENCIAMENTO_PDV_ATUALIZAR_LEAD           = SPRING_ROLE_PREFIX + "API_CREDENCIAMENTO_PDV_ATUALIZAR_LEAD"          + SPRING_ROLE_SUFFIX;
    public static final String API_CREDENCIAMENTO_PDV_CONSULTAR_LEAD           = SPRING_ROLE_PREFIX + "API_CREDENCIAMENTO_PDV_CONSULTAR_LEAD"          + SPRING_ROLE_SUFFIX;
    public static final String API_CREDENCIAMENTO_PDV_OBTER_TERMOS_CONTRATO    = SPRING_ROLE_PREFIX + "API_CREDENCIAMENTO_PDV_OBTER_TERMOS_CONTRATO"   + SPRING_ROLE_SUFFIX;
    public static final String API_CREDENCIAMENTO_PDV_PESQUISAR_BANCO          = SPRING_ROLE_PREFIX + "API_CREDENCIAMENTO_PDV_PESQUISAR_BANCO"         + SPRING_ROLE_SUFFIX;
    public static final String API_CREDENCIAMENTO_PDV_OBTER_COMBUSTIVEIS       = SPRING_ROLE_PREFIX + "API_CREDENCIAMENTO_PDV_OBTER_COMBUSTIVEIS"      + SPRING_ROLE_SUFFIX;
    public static final String API_CREDENCIAMENTO_PDV_HABILITAR                = SPRING_ROLE_PREFIX + "API_CREDENCIAMENTO_PDV_HABILITAR"               + SPRING_ROLE_SUFFIX;
    
    /**
     * Impede instanciacao e heranca
     */
    private ChavePermissao() {
        // Impede instanciacao e heranca
    }

    /**
     * Obtem o nome plano da permissao, removendo o sufixo e
     * prefixo usados para compatibilidade com o Spring Security
     *
     * @param permissao O nome da permissao, com o prefixo e sufixo do Spring Secutiry
     * @return A chave da permissao, exatamente como persistido no banco de dados
     */
    public static String getChave(String permissao) {
        return permissao
            .replace(API_FROTISTA_PREFIX, "")
            .replace(SPRING_ROLE_PREFIX, "")
            .replace(SPRING_ROLE_SUFFIX, "");
    }

    /**
     * Obtem o nome plano das permissoes informadas, removendo o sufixo e
     * prefixo usados para compatibilidade com o Spring Security
     *
     * @param permissoes Os nomes das permissoes, com o prefixo e sufixo do Spring Secutiry
     * @return As chaves das permissoes, exatamente como persistido no banco de dados
     */
    public static String[] getChaves(String... permissoes) {
        String[] chaves = new String[permissoes.length];
        for(int i = 0; i < permissoes.length; i++) {
            chaves[i] = getChave(permissoes[i]);
        }
        return chaves;
    }
}
