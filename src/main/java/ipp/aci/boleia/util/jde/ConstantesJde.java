package ipp.aci.boleia.util.jde;

import java.math.BigDecimal;

/**
 * Enumera as constantes utilizadas pelos serviços de integração com o JDE
 */
public final class ConstantesJde {

    public static final String BOLEIA = "BOLEIA";
    public static final String FILIAL = "741000";
    public static final String FILIAL_FABRICA_REPASSE = "746975";
    public static final String COMPANHIA = "00074";
    public static final String COD_PAIS = "BR";
    public static final String COMPANHIA_CLIENTE = "74";
    public static final String UNIDADE_NEGOCIO = "74";
    public static final String CODIGO_CATEGORIA_EMPRESA = "74";
    public static final String CLIENTE_REPASSE = "7059338";

    public static final Long VOUCHER_CENARIO_APROVADO_PAGAMENTO = 187L;
    public static final Long VOUCHER_CENARIO_SUSPENSO_PAGAMENTO = 141L;
    public static final String VOUCHER_CODIGO_PAGAMENTO = "P11";
    public static final Long VOUCHER_INDICE_CONTA_CONTABIL_POS_PAGO = 1L;
    public static final Long VOUCHER_INDICE_CONTA_CONTABIL_PRE_PAGO = 3L;
    public static final Long VOUCHER_INDICE_CONTA_CONTABIL_MDR = 2L;
    public static final String VOUCHER_OBSERVACAO_POS_PAGO = "REEMBOLSO PRO FROTAS";
    public static final String VOUCHER_OBSERVACAO_PRE_PAGO = "PRE PAGO PRO FROTAS";
    public static final String VOUCHER_OBSERVACAO_MDR_REEMBOLSO = "MDR";
    public static final Integer VOUCHER_TEMPO_HORA = 12;
    public static final Integer VOUCHER_TEMPO_MINUTO = 0;
    public static final Integer VOUCHER_TEMPO_SEGUNDO = 0;
    
    public static final Long VOUCHER_CONECTCAR_CENARIO = 734L;
    public static final String VOUCHER_CONECTCAR_OBSERVACAO = "ProFrotas X Connectcar";
    public static final String VOUCHER_CONECTCAR_OBSERVACAO_DISTRIBUICAO_CONTABIL = "PASSAGENS";
    public static final Long VOUCHER_CONECTCAR_INDICE_CONTA_CONTABIL = 5L;

    public static final Long VOUCHER_CONTAS_PAGAR_CENARIO = 73L;
    public static final String VOUCHER_CONTAS_PAGAR_CIA_DOCUMENTO = COMPANHIA;
    public static final String VOUCHER_CONTAS_PAGAR_TIPO_DOCUMENTO = "PV";
    public static final String VOUCHER_CONTAS_PAGAR_LINHA_DOCUMENTO = "001";

    public static final Integer CREDITO_FROTA_CENARIO = 186;
    public static final String CREDITO_FROTA_TIPO_DOCUMENTO = "D4";
    public static final String CREDITO_FROTA_OBSERVACAO = "CRÉDITO PRÉ PAGO";

    public static final String FATURA_CIA_DOCUMENTO = "74090";
    public static final String FATURA_TIPO_DOCUMENTO = "D3";
    public static final String FATURA_REPASSE_TIPO_DOCUMENTO = "DB";
    public static final String FATURA_CONECTCAR_PEDIDO_TAG_TIPO_DOCUMENTO = "F5";
    public static final String FATURA_CENARIO = "140";
    public static final String FATURA_SISTEMA_GERADOR = BOLEIA;
    public static final String FATURA_REPASSE_OBSERVACAO = "REPASSE CONCEDIDO";

    public static final String VINCULO_CREDITO_CENARIO = "700";
    public static final String VINCULO_CREDITO_CONTA = "747090.11269.0017";

    public static final String VINCULAR_JUROS_CENARIO = "701";

    public static final String CONTA_BANCARIA_ACAO = "A";
    public static final String CONTA_BANCARIA_TIPO_REGISTRO = "V";
    public static final String CONTA_BANCARIA_CODIGO_CAD_BANCO = "0";
    public static final String CONTA_BANCARIA_TIPO_CONTA = "";

    public static final boolean PESSOA_SOMENTE_INFORMACOES_TRIBUTARIAS = false;
    public static final long PESSOA_CENARIO_FROTISTA = 131L;
    public static final long PESSOA_CENARIO_REVENDEDOR = 132L;
    public static final long PESSOA_CENARIO_INATIVACAO = 135L;
    public static final boolean PESSOA_TEM_RECEBIVEIS = true;
    public static final String PESSOA_TIPO_EMAIL = "E";
    public static final String PESSOA_CLASSE_CONTRIBUINTE = "1";
    public static final boolean PESSOA_REPASSE_ICMS = false;
    public static final String PESSOA_INDICADOR_ICMS = "1";
    public static final String PESSOA_INDICADOR_ISS = "1";
    public static final String PESSOA_SUBSTITUICAO_ICMS = "N";
    public static final String PESSOA_CODIGO_APURACAO_IPI = "1";
    public static final String PESSOA_CONDICAO_PAGAMENTO_PV = "P11";
    public static final String PESSOA_ISENTO = "ISENTO";
    public static final String PESSOA_EMAIL_KEY = "1";
    public static final String PESSOA_TELEFONE_KEY = "1";
    public static final String PESSOA_CODIGO_MOTIVO_INATIVACAO = "4";
    public static final String PESSOA_CODIGO_STATUS_FORNECEDOR = "1";
    public static final int PESSOA_LIMITE_25 = 25;
    public static final int PESSOA_LIMITE_40 = 40;
    public static final String PESSOA_COD_IBGE_PADRAO = "0000000";
    public static final int TAMANHO_ENDERECO_NUMERO = 10;
    public static final int TAMANHO_ENDERECO_COMPLEMENTO = 30;

    public static final String BOLETO_NUMERO_PARCELA = "001";

    public static final String CONDICAO_PGTO_REPASSE = "015";

    public static final String SUFIXO_PRORROGAR_VENCIMENTO = "001";
    public static final BigDecimal ACRECIMO_PRORROGAR_VENCIMENTO = new BigDecimal(0);
    public static final String ALTERACAO_PRORROGAR_VENCIMENTO = "03";

    /**
     * Impede instanciacao e heranca
     */
    private ConstantesJde() {
        // Impede instanciacao e heranca
    }
}
