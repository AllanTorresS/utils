package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.seguranca.ChavePermissao;

/**
 * Enumera os tipos de token JWT emitidos pela aplicacao
 */
public enum TipoTokenJwt {

    SALES_FORCE              (TipoPerfilUsuario.SISTEMA_EXTERNO,  720,  false, TipoTokenJwt.getPermissoesSalesForce()),
    API_FROTISTA             (TipoPerfilUsuario.FROTA,            720,  false, TipoTokenJwt.getPermissoesApiFrotista()),
    DISPOSITIVO_MOTORISTA    (TipoPerfilUsuario.FROTA,           1440,  true,  TipoTokenJwt.getPermissoesDispositivoMotorista()),
    DISPOSITIVO_FROTISTA     (TipoPerfilUsuario.FROTA,           1440,  true),
    USUARIO_MOTORISTA        (TipoPerfilUsuario.MOTORISTA,       1440,  true,  TipoTokenJwt.getPermissoesDispositivoMotorista()),
    COMANDA_DIGITAL          (null,                              1440,  true,  TipoTokenJwt.getPermissoesComandaDigital()),
    USUARIO_PDV              (TipoPerfilUsuario.REVENDA,            1,  true,  TipoTokenJwt.getPermissoesPDV()),
    USUARIO_BOLEIA           (null,                                24,  false),
    DOWNLOAD_ARQUIVO         (null,                                 1,  false),
    SISTEMA_EXTERNO          (TipoPerfilUsuario.SISTEMA_EXTERNO,  720,  false),
    PRE_CREDENCIAMENTO_POSTO (TipoPerfilUsuario.REVENDA,   		    1,  false, TipoTokenJwt.getPermissoesPreCredenciamentoPosto()),
	CREDENCIAMENTO_POSTO     (TipoPerfilUsuario.REVENDA,           24,  false, TipoTokenJwt.getPermissoesCredenciamentoPosto()),
    MODULO_INTERNO           (TipoPerfilUsuario.MODULO_INTERNO,     1,  false);

    private final int duracaoHoras;
    private final TipoPerfilUsuario tipoPerfil;
    private boolean fingerprintObrigatorio;
    private final String[] permissoes;

    /**
     * Constroi as informacoes basicas do tipo de token
     *
     * @param tipoPerfil O tipo de perfil do usuario associado ao token
     * @param duracaoHoras A duracao do token em horas
     * @param chavePermissao A lista de permissoes a serem concedidas
     */
    TipoTokenJwt(TipoPerfilUsuario tipoPerfil, int duracaoHoras, boolean fingerprintObrigatorio, String... chavePermissao) {
        this.duracaoHoras = duracaoHoras;
        this.tipoPerfil = tipoPerfil;
        this.fingerprintObrigatorio = fingerprintObrigatorio;
        this.permissoes = chavePermissao != null ? chavePermissao : new String[0];
    }

    /**
     * Lista as permissoes que o sistema SalesForce possui ao se comunicar com o Boleia
     * @return Um vetor de chaves de permissoes
     */
    private static String[] getPermissoesSalesForce() {
        return ChavePermissao.getChaves(
            ChavePermissao.FROTA_PRE_CADASTRAR,
            ChavePermissao.FROTA_HABILITAR,
            ChavePermissao.FROTA_ALTERAR,
            ChavePermissao.FROTA_EXCLUIR,
            ChavePermissao.FROTA_ATIVAR_TEMPORARIAMENTE,
            ChavePermissao.API_CREDENCIAMENTO_PDV_CONSULTAR_STATUS,
            ChavePermissao.API_CREDENCIAMENTO_PDV_HABILITAR,
            ChavePermissao.API_CREDENCIAMENTO_PDV_LISTAR_BANDEIRA
        );
    }

    /**
     * Lista as permissoes que o sistema PDV (backend) possui ao se comunicar com o Boleia
     * @return Um vetor de chaves de permissoes
     */
    private static String[] getPermissoesPDV() {
        return ChavePermissao.getChaves(
            ChavePermissao.AUTORIZACAO_PAGAMENTO_PRE_AUTORIZAR,
            ChavePermissao.AUTORIZACAO_PAGAMENTO_AUTORIZAR,
            ChavePermissao.AUTORIZACAO_PAGAMENTO_VALIDAR,
            ChavePermissao.MOTORISTA_GERAR_SOFT_TOKEN,
            ChavePermissao.VIP_AUTENTICAR,
            ChavePermissao.VIP_VALIDAR,
            ChavePermissao.DISPOSITIVO_PEDIDO_CONSULTAR_E_VISUALIZAR,
            ChavePermissao.AUTORIZACAO_PAGAMENTO_AUTORIZAR_PEDIDO,
            ChavePermissao.LISTAR_PONTOS_DE_VENDA_USUARIO,
            ChavePermissao.LISTAR_PRODUTOS_ADICIONAIS_PDV,
            ChavePermissao.ABASTECIMENTO_CONSULTAR_E_VISUALIZAR,
            ChavePermissao.API_INTEGRADOR_CONSULTAR_BICOS,
            ChavePermissao.API_INTEGRADOR_CONSULTAR_ABASTECIMENTOS
        );
    }

    /**
     * Lista as permissoes que os dispositivos dos motoristas possuem ao se comunicarem com o Boleia
     * @return Um vetor de chaves de permissoes
     */
    private static String[] getPermissoesDispositivoMotorista() {
        return ChavePermissao.getChaves(
            ChavePermissao.DISPOSITIVO_MOTORISTA_ATUALIZAR_TOKEN_PUSH,
            ChavePermissao.DISPOSITIVO_MOTORISTA_RECUPERAR_SENHA,
            ChavePermissao.DISPOSITIVO_MOTORISTA_SOLICITAR_AJUDA_NOTA,
            ChavePermissao.DISPOSITIVO_MOTORISTA_OBTER_NOTAS,
            ChavePermissao.DISPOSITIVO_MOTORISTA_VERIFICAR_HABILITADO,
            ChavePermissao.DISPOSITIVO_MOTORISTA_GERAR_PEDIDO
        );
    }

    /**
     * Lista as permissoes dos tokens de API dos Frotista
     * @return Um vetor de chaves de permissoes
     */
    private static String[] getPermissoesApiFrotista() {
        return ChavePermissao.getChaves(ChavePermissao.API_FROTISTA);
    }

    /**
     * Lista as permissoes que as comandas digitais possuem ao se comunicarem com o Boleia
     * @return Um vetor de chaves de permissoes
     */
    private static String[] getPermissoesComandaDigital() {
        return ChavePermissao.getChaves(
            ChavePermissao.AUTORIZACAO_PAGAMENTO_PRE_AUTORIZAR,
            ChavePermissao.VIP_VALIDAR,
            ChavePermissao.VIP_AUTENTICAR,
            ChavePermissao.AUTORIZACAO_PAGAMENTO_AUTORIZAR_ABASTECIMENTO,
            ChavePermissao.AUTORIZACAO_PAGAMENTO_AUTORIZAR_PEDIDO,
            ChavePermissao.AUTORIZACAO_PAGAMENTO_AUTORIZAR,
            ChavePermissao.BUSCAR_ABASTECIMENTOS,
            ChavePermissao.BUSCAR_BICOS,
            ChavePermissao.BUSCAR_PARAMETROS
        );
    }
    
    /**
     * Lista as permissoes que o site do pró-frotas possui ao se comunicarem com o Boleia
     * @return Um vetor de chaves de permissoes
     */
    private static String[] getPermissoesPreCredenciamentoPosto() {
        return ChavePermissao.getChaves(
        	   ChavePermissao.API_CREDENCIAMENTO_PDV_CONSULTAR_STATUS,
        	   ChavePermissao.API_CREDENCIAMENTO_PDV_LISTAR_BANDEIRA,
        	   ChavePermissao.API_CREDENCIAMENTO_PDV_ENVIAR_LEAD
        );
    }
    
    /**
     * Lista as permissoes que o credenciamento de posto do pró-frotas possui ao se comunicarem com o Boleia
     * @return Um vetor de chaves de permissoes
     */
    private static String[] getPermissoesCredenciamentoPosto() {
        return ChavePermissao.getChaves(
        	   ChavePermissao.API_CREDENCIAMENTO_PDV_CONSULTAR_LEAD,
        	   ChavePermissao.API_CREDENCIAMENTO_PDV_ATUALIZAR_LEAD,
        	   ChavePermissao.API_CREDENCIAMENTO_PDV_OBTER_TERMOS_CONTRATO,
        	   ChavePermissao.API_CREDENCIAMENTO_PDV_PESQUISAR_BANCO,
        	   ChavePermissao.API_CREDENCIAMENTO_PDV_OBTER_COMBUSTIVEIS
        );
    }
    
    public int getDuracaoHoras() {
        return duracaoHoras;
    }

    public String[] getPermissoes() {
        return permissoes;
    }

    public TipoPerfilUsuario getTipoPerfil() {
        return tipoPerfil;
    }

    public boolean isFingerprintObrigatorio() {
        return fingerprintObrigatorio;
    }
}
