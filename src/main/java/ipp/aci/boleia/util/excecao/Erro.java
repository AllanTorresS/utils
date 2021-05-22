package ipp.aci.boleia.util.excecao;

/**
 * Erros de negócio lançados pela aplicação.
 * Os Erros devem ser separados por intervalos de 1000
 * quando são referênciados pelo código em plataformas Externas, como APP Mobile e API Externa.
 * Como ocorre no caso do App do motorista, onde começam em 1000.
 *
 * Ao adicionar novo valor de erro, caso o código desse valor esteja
 * sendo referênciado externamente, esse valor deverá ocupar uma faixa não existente.
 *
 */
public enum Erro {

    ARQUIVO_NAO_ENCONTRADO(1),
    ALTERAR_REGISTRO_DESATUALIZADO(2),
    CONVERSAO_DATA(3),
    ERRO_GENERICO(4),
    ERRO_INTEGRACAO(5),
    ERRO_VALIDACAO(6),
    ERRO_VALIDACAO_TAMANHO(7),
    ERRO_VALIDACAO_OBRIGATORIO(8),
    ERRO_VALIDACAO_NUMERO_REGISTROS_IMPORTACAO(9),
    ERRO_VALIDACAO_SERVICO_TAMANHO(10),
    ERRO_VALIDACAO_SERVICO_TIPO(11),
    REGISTRO_INEXISTENTE(12),
    JSON_MAL_FORMADO(13),
    JSON_MAL_FORMADO_CAMPO(14),
    ERRO_VALIDACAO_NUMERO_COLUNAS_IMPORTACAO(15),
    ERRO_VALIDACAO_TOKEN_EXPIRADO(16),
    ERRO_VALIDACAO_JA_HABILITADO(17),
    AUTENTICACAO_CREDENCIAIS_INVALIDAS(18),
    AUTENTICACAO_USUARIO_INVALIDO(19),
    PERMISSAO_NEGADA(20),
    ERRO_VALIDACAO_BLOQUEADO(22),
    ERRO_VALIDACAO_INATIVO(23),
    SERVICO_BLOQUEADO(25),
    AUTENTICACAO_USUARIO_BLOQUEADO(26),
    DISPOSITIVO_SEM_INTEGRACAO(27),
    IMPORTACAO_LOTE_LINHA_DUPLICADA(28),
    NOTA_FISCAL_EXCLUSAO_NOTA_ANTIGA(30),
    NOTA_FISCAL_UPLOAD(31),
    NOTA_FISCAL_UPLOAD_CNPJ_DESTINATARIO_INVALIDO(32),
    NOTA_FISCAL_UPLOAD_VERSAO_INVALIDA(33),
    NOTA_FISCAL_UPLOAD_TOTAL_INVALIDO(34),
    CREDITO_INSUFICIENTE(35),
    TRANSACAO_VALOR_INVALIDO(36),
    CONCESSAO_CREDITO_HABILITACAO_JA_REALIZADA(37),
    RECAPTCHA_INCORRETO(38),
    ERRO_VALIDACAO_TOKEN_AUTENTICACAO(39),
    ERRO_VALIDACAO_NAO_HABILITADO(40),
    VIOLACAO_ISOLAMENTO_DADOS(41),
    SESSAO_EXPIRADA_INATIVIDADE(42),
    SOLUCAO_INDISPONIVEL(43),
    TOKEN_JWT_INVALIDO(44),
    ERRO_VALIDACAO_VERDADEIRO(45),
    VEICULO_NAO_CADASTRADO_FROTA(46),
    PRE_AUTORIZACAO_ABASTECIMENTO_INVALIDA(47),
    OPERACAO_NAO_PERMITIDA(48),
    AUTORIZACAO_PEDIDO_INVALIDO(49),
    AUTORIZACAO_PRODUTO_DIVERGENTE(50),
    ERRO_VALIDACAO_TIPO(51),
    LIMITE_REQUISICOES_POR_SEGUNDO_EXCEDIDO(52),
    NOTA_FISCAL_NAO_EXISTEM_NOVAS(53),
    VEICULO_SALDO_COTA_INSUFICIENTE(54),
    NOTA_FISCAL_NAO_POSSUI_VALOR_TOTAL(55),
    NOTA_FISCAL_NAO_EXISTEM_EMITIDAS(56),
    DISPOSITIVO_MOTORISTA_STATUS_INVALIDO(57),
    STATUS_INVALIDO(58),
    FROTA_SALDO_INSUFICIENTE(59),
    API_PERMISSAO_NEGADA(60),
    TOKEN_JWT_EXPIRADO(61),
    DISPOSITIVO_MOTORISTA_NAO_HABILITADO(62),
    DISPOSITIVO_MOTORISTA_BLOQUEADO(63),
    DISPOSITIVO_MOTORISTA_EXCLUIDO(64),
    SEM_PV_PROXIMO(65),
    SEM_PV_HABILITADO_PROXIMO(66),
    SEM_PV_PERMITIDO_PROXIMO(67),
    ERRO_VALIDACAO_SERVICOS_EXTERNOS(68),
    NOTA_FISCAL_UPLOAD_TOTAL_INVALIDO_MULT_ABAST(69),
    NOTA_FISCAL_UPLOAD_NOTA_REPETIDA(70),
    ERRO_FROTA_PRE_NEGATIVADA(71),
    ERRO_TAMANHO_SENHA_BCRYPT(72),
    AUTENTICACAO_CREDENCIAIS_INDEFINIDAS(73),
    ERRO_HODOMETRO_HORIMETRO_INVALIDO(74),
    ULTIMA_TENTATIVA_LOGIN(75),
    AUTENTICACAO_SENHA_EXPIRADA(76),
    ERRO_VERSAO_DISPOSITIVO_INCONSISTENTE(77),
    ERRO_SEM_DADOS_PARA_CALCULO(78),
    NOTA_FISCAL_UPLOAD_CNPJ_EMITENTE_INVALIDO(79),
    SEM_CONTROLADORAS_CADASTRADAS(80),
    RECURSO_NAO_ENCONTRADO(81),
    MOTORISTA_NAO_UTILIZA_APP_MOTORISTA(82),
    PRAZO_AUTORIZACAO_ABASTECIMENTO_PENDENTE_EXPIRADO(83),
    AUTORIZACAO_ABASTECIMENTO_PENDENTE_NAO_PERMITIDA(84),
    PEDIDO_MUNDIPAGG_NAO_CANCELADO(85),
    PEDIDO_MUNDIPAGG_JA_CANCELADO(86),
    USUARIO_INVALIDO(87),
    RECURSO_CONCORRENTE_BLOQUEADO(88),
    ERRO_INFORMA_SINCRONISMO_SERVICO_EXTERNO(89),
    ERRO_IMPORTACAO_EM_ANDAMENTO(90),
    ERRO_VALIDACAO_SIMILARIDADE(90),
    ERRO_VALIDACAO_CODIGO_ABASTECIMENTO(91),
    REGISTROS_REPASSE_NAO_ENCONTRADOS(92),
    VIGENCIA_CAMPANHA_NAO_PERMITE_OPERACAO(93),
    CAMPANHA_OPERACAO_NAO_PERMITIDA(94),
    CAMPANHA_DATA_INICIO_INVALIDA(95),
    ERRO_BUSCAR_CAMPANHA(96),
    AUTORIZACAO_NAO_CONSOLIDADA(97),
    AUTORIZACAO_NAO_PROCESSADO_PELO_GERADOR_CAMPANHA(98),
    ERRO_OBTER_ANEXOS_LEITOR_EMAIL(99),
    FROTA_PONTO_VENDA_BLOQUEADO(100),
    FROTA_PONTO_VENDA_BLOQUEADO_NA_REGIAO(101),
    ERRO_CONCILIACAO_NENHUM_ABASTECIMENTO(102),
    ERRO_CONCILIACAO_ABAST_NAO_CONSOLIDADO(103),
    ERRO_VALIDACAO_CNPJ_DEST(104),
    ERRO_VALIDACAO_CNPJ_EMIT(105),
    ERRO_CONEXAO_LEITOR_EMAIL(106),
    ERRO_BUSCA_MENSAGENS_LEITOR_EMAIL(107),
    ERRO_VALIDACAO_NOTA_FISCAL(108),
    ERRO_CONCILIACAO_MULTIPLOS_ABASTECIMENTOS(109),
    ERRO_VALIDACAO_VALOR_NOTA(110),
    ERRO_VALIDACAO_NUMERO_NOTA(111),
    ERRO_VALIDACAO_DATA_EMISSAO(112),
    ERRO_VALIDACAO_SERIE_NOTA(113),
    OBJETO_NAO_SERIALIZAVEL(114),
    ERRO_CONCORRENCIA_TRANS_CONSOL(115),
    ERRO_EDICAO_SEM_CONSOLIDADO(116),
    ERRO_EDICAO_PRE_CONDICOES_INVALIDAS(117),
    ERRO_EDICAO_MOTORISTA_INVALIDO(118),
    ERRO_EDICAO_MOTORISTA_INATIVO(119),
    ERRO_EDICAO_VEICULO_INVALIDO(120),
    ERRO_EDICAO_VEICULO_INATIVO(121),
    ERRO_EDICAO_PONTO_VENDA_INVALIDO(122),
    ERRO_EDICAO_PONTO_VENDA_INATIVO(123),
    ERRO_EDICAO_FROTA_INATIVA(124),
    ERRO_EDICAO_VALORES_INVALIDOS(125),
    ERRO_EDICAO_FROTA_PREPAGA(126),
    ERRO_HISTORICO_NAO_ENCONTRADO(127),
    ERRO_EDICAO_POSTO_NAO_PERMITIDO(128),
    ERRO_EDICAO_INEXISTENTE(129),
    ERRO_AUTORIZACAO_DIAS_HORARIOS_PERMITIDOS(130),
    ERRO_AUTORIZACAO_PRODUTOS_ADICIONAIS(131),
    ERRO_AUTORIZACAO_INTERVALO_PERMITIDO_HORAS(132),
    ERRO_AUTORIZACAO_PRODUTO_ABASTECIDO(133),
    REPASSE_JA_ENVIADO(134),
    ERRO_ENVIO_REPASSE_MANUAL(135),
    NOTA_FISCAL_UPLOAD_NUMERO_SERIE_OBRIGATORIO(136),
    NOTA_FISCAL_UPLOAD_NUMERO_OBRIGATORIO(137),
    NOTA_FISCAL_UPLOAD_NUMERO_SERIE_LIMITE(138),
    ERRO_AJUSTE_PARAMETRO_CICLO(139),
    ERRO_INTEGRACAO_APCO (140),
    ERRO_INTEGRACAO_APCO_CLIENTES (141),
    ERRO_INTEGRACAO_APCO_FROTA_POSTO (142),
    ERRO_INTEGRACAO_APCO_VENDAS (143),
    ERRO_AUTORIZACAO_INTERVALO_PERMITIDO_KM(144),
    ERRO_REGISTRO_KMV(145),
    ERRO_VALIDACAO_CHAVE_ACESSO_NOTA(146),
    ERRO_EDICAO_HODOMETRO_INVALIDO(147),
    ERRO_EDICAO_HORIMETRO_INVALIDO(148),
    FROTA_PONTO_VENDA_NAO_VISIVEL(149),
    ERRO_INTEGRACAO_KMV_SALDO(150),
    ERRO_EDICAO_FROTA_ALTERADA(151),
    NOTA_FISCAL_EXCLUSAO_CICLO_FECHADO(152),    
	ERRO_CONDICAO_COMERCIAL_NAO_ENCONTRADA(153),
	ERRO_INTEGRACAO_CONECTCAR(154),
	ERRO_PLACA_INVALIDA(155),
	ERRO_TAG_NAO_ENCONTRADA(156),
	ERRO_TRANSACAO_ORIGEM_NAO_ENCONTRADA(157),
	ATIVAR_OU_DESATIVAR_FROTA(158),	
	ERRO_FROTA_NAO_ENCONTRADA(159),
	ERRO_LEAD_NAO_ENCONTRADO(160),
	ERRO_INTEGRACAO_APCO_NAO_CONCLUIDA(161),
	ERRO_DIA_REEMBOLSO_INVALIDO(162),
	ERRO_QUANTIDADE_TAGS(163),
	ERRO_ENDERECO_INVALIDO(164),
    ERRO_VALIDACAO_ALTERACAO_FROTA(165),
    NOTAS_FISCAIS_SEPARADAS_NAO_ENCONTRADAS(166),
    NOTA_FISCAL_COMB_OU_PROD_AUSENTE(167),
    NOTA_FISCAL_VALOR_COMB_EXCEDENTE(168),
    NOTA_FISCAL_VALOR_PROD_EXCEDENTE(169),
    NOTA_FISCAL_VALOR_COMB_FALTANTE(170),
    NOTA_FISCAL_VALOR_PROD_FALTANTE(171),
    NOTA_FISCAL_ABASTECIMENTO_ENCONTRADO_FLAG_SEPARACAO(172),
    NOTAS_FISCAIS_REPETIDAS_NO_UPLOAD(173),
    NOTA_FISCAL_POSSUI_SOMATORIO_DIFERENTE_VALOR_TOTAL(174),

    // APP MOTORISTA 1000 - Erros mapeados no Aplicativo Motorista
    ERRO_AUTORIZACAO_CONSUMO_ESTIMADO_MAX(1000),
    ERRO_AUTORIZACAO_VOLUME_ABASTECIDO(1001),
    ERRO_AUTORIZACAO_HODOMETRO_LIMITE(1002),
    ERRO_AUTORIZACAO_HORIMETRO_LIMITE(1003),
    ERRO_AUTORIZACAO_CONSUMO_ESTIMADO_MIN(1004),
    ERRO_AUTORIZACAO_QUANTIDADE_MAX_PRODUTO(1005),
    ERRO_AUTORIZACAO_FLUXO_PLACA_DIVERGENTE(1006),
    PV_NAO_AUTORIZADO(1007),
    ERRO_AUTORIZACAO_PRECO_MAX_PRODUTO(1008),
    ERRO_VALIDACAO_GOEPIK(1009);

    private final Integer codigo;

    Erro(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    /**
     * Obtem a chave de mensagem do properties
     *
     * @return chave mensagem
     */
    public String getChaveMensagem() {
        return this.getClass().getSimpleName() + "." + this.name();
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static Erro obterPorValor(Integer value) {
        for(Erro erro : Erro.values()) {
            if(erro.codigo.equals(value)) {
                return erro;
            }
        }
        return null;
    }
}
