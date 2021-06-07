package ipp.aci.boleia.util;

/**
 * Constantes utilizadas pelo parser da nota fiscal
 */
public final class ConstantesNotaFiscalParser {

    public static final String RAZAO_SOCIAL_NOTA_HOMOLOG   = "NF-E EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL";
    public static final String CNPJ_NOTA_HOMOLOG           = "99999999000191";
    public static final String INSCR_ESTADU_NOTA_HOMOLOG   = "";
    public static final String CAMINHO_NF   = "/nfeProc/NFe/infNFe/det";

    public static final String UF                           = "/nfeProc/NFe/infNFe/ide/cUF/text()";
    public static final String CODIGO                       = "/nfeProc/NFe/infNFe/ide/cNF/text()";
    public static final String NATUREZA_OPERACAO            = "/nfeProc/NFe/infNFe/ide/natOp/text()";
    public static final String INDICADOR_PAGAMENTO          = "/nfeProc/NFe/infNFe/ide/indPag/text()";
    public static final String SERIE                        = "/nfeProc/NFe/infNFe/ide/serie/text()";
    public static final String TIPO                         = "/nfeProc/NFe/infNFe/ide/tpNF/text()";
    public static final String DATA_SAIDA                   = "/nfeProc/NFe/infNFe/ide/dhSaiEnt/text()";
    public static final String DESTINO                      = "/nfeProc/NFe/infNFe/ide/idDest/text()";
    public static final String TIPO_EMISSAO                 = "/nfeProc/NFe/infNFe/ide/tpEmis/text()";
    public static final String DIGITO_VERIFICADOR           = "/nfeProc/NFe/infNFe/ide/cDV/text()";
    public static final String ABIENTE                      = "/nfeProc/NFe/infNFe/ide/tpAmb/text()";
    public static final String FINALIDADE                   = "/nfeProc/NFe/infNFe/ide/finNFe/text()";
    public static final String INDICADOR_FINAL              = "/nfeProc/NFe/infNFe/ide/indFinal/text()";
    public static final String JUSTIFICATIVA                = "/nfeProc/NFe/infNFe/ide/xJust/text()";
    public static final String MODELO                       = "/nfeProc/NFe/infNFe/ide/mod/text()";

    public static final String EMIT_RAZAO_SOCIAL            = "/nfeProc/NFe/infNFe/emit/xNome/text()";
    public static final String EMIT_CNPJ           		    = "/nfeProc/NFe/infNFe/emit/CNPJ/text()";
    public static final String EMIT_FANT           		    = "/nfeProc/NFe/infNFe/emit/xFant/text()";
    public static final String EMIT_INSC_ESTADUAL  		    = "/nfeProc/NFe/infNFe/emit/IE/text()";
    public static final String EMIT_INSC_ESTADUAL_SUB  		= "/nfeProc/NFe/infNFe/emit/IEST/text()";
    public static final String EMIT_INSC_MUNICIPAL 		    = "/nfeProc/NFe/infNFe/emit/IM/text()";
    public static final String EMIT_CRT            		    = "/nfeProc/NFe/infNFe/emit/CRT/text()";

    public static final String ENDER_EMIT_LOGRADOURO        = "/nfeProc/NFe/infNFe/emit/enderEmit/xLgr/text()";
    public static final String ENDER_EMIT_NUMERO            = "/nfeProc/NFe/infNFe/emit/enderEmit/nro/text()";
    public static final String ENDER_EMIT_COMPLEMENTO       = "/nfeProc/NFe/infNFe/emit/enderEmit/xCpl/text()";
    public static final String ENDER_EMIT_BAIRROS           = "/nfeProc/NFe/infNFe/emit/enderEmit/xBairro/text()";
    public static final String ENDER_EMIT_CODIGO_MUNICIPIO  = "/nfeProc/NFe/infNFe/emit/enderEmit/cMun/text()";
    public static final String ENDER_EMIT_MUNICIPIO         = "/nfeProc/NFe/infNFe/emit/enderEmit/xMun/text()";
    public static final String ENDER_EMIT_UF                = "/nfeProc/NFe/infNFe/emit/enderEmit/UF/text()";
    public static final String ENDER_EMIT_TELEFONE          = "/nfeProc/NFe/infNFe/emit/enderEmit/fone/text()";
    public static final String ENDER_EMIT_CEP               = "/nfeProc/NFe/infNFe/emit/enderEmit/CEP/text()";
    public static final String ENDER_EMIT_CODIGO_PAIS       = "/nfeProc/NFe/infNFe/emit/enderEmit/cPais/text()";
    public static final String ENDER_EMIT_PAIS              = "/nfeProc/NFe/infNFe/emit/enderEmit/xPais/text()";

    public static final String NUMERO                       = "/nfeProc/NFe/infNFe/ide/nNF/text()";
    public static final String DATA_EMISSAO                 = "/nfeProc/NFe/infNFe/ide/dhEmi/text()";
    public static final String DEST_CPF                     = "/nfeProc/NFe/infNFe/dest/CPF/text()";
    public static final String DEST_CNPJ                    = "/nfeProc/NFe/infNFe/dest/CNPJ/text()";
    public static final String DEST_RAZAO_SOCIAL            = "/nfeProc/NFe/infNFe/dest/xNome/text()";
    public static final String DEST_INSC_ESTADUAL           = "/nfeProc/NFe/infNFe/dest/IE/text()";
    public static final String VALOR_TOTAL                  = "/nfeProc/NFe/infNFe/total/ICMSTot/vNF/text()";

    public static final String ENDER_DEST_LOGRADOURO        = "/nfeProc/NFe/infNFe/dest/enderDest/xLgr/text()";
    public static final String ENDER_DEST_NUMERO            = "/nfeProc/NFe/infNFe/dest/enderDest/nro/text()";
    public static final String ENDER_DEST_BAIRRO            = "/nfeProc/NFe/infNFe/dest/enderDest/xBairro/text()";
    public static final String ENDER_DEST_MUNICIPIO         = "/nfeProc/NFe/infNFe/dest/enderDest/xMun/text()";
    public static final String ENDER_DEST_UF                = "/nfeProc/NFe/infNFe/dest/enderDest/UF/text()";
    public static final String ENDER_DEST_CEP               = "/nfeProc/NFe/infNFe/dest/enderDest/CEP/text()";
    public static final String ENDER_DEST_TELEFONE          = "/nfeProc/NFe/infNFe/dest/enderDest/fone/text()";

    public static final String DEST_INDIEDEST         	     = "/nfeProc/NFe/infNFe/dest/indIEDest/text()";

    public static final String ITEM_CODIGO_PRODUTO           = "prod/cProd/text()";
    public static final String ITEM_CODIGO_EAN               = "prod/cEAN/text()";
    public static final String ITEM_DESCR_PRODUTO            = "prod/xProd/text()";
    public static final String ITEM_NCM                      = "prod/NCM/text()";
    public static final String ITEM_CST                      = "prod/CST/text()";
    public static final String ITEM_CFOP                     = "prod/CFOP/text()";
    public static final String ITEM_UNIDADE                  = "prod/uCom/text()";
    public static final String ITEM_QUANTIDADE               = "prod/qCom/text()";
    public static final String ITEM_VALOR_UNITARIO           = "prod/vUnCom/text()";
    public static final String ITEM_VALOR_LIQUIDO            = "prod/vProd/text()";
    public static final String ITEM_DESCONTO                 = "prod/vDesc/text()";
    public static final String ITEM_INFO_ADICIONAIS          = "infAdProd/text()";

    public static final String TRANSP_NOME                   = "/nfeProc/NFe/infNFe/transp/transporta/xNome/text()";
    public static final String TRANSP_CPF                    = "/nfeProc/NFe/infNFe/transp/transporta/CPF/text()";
    public static final String TRANSP_CNPJ                   = "/nfeProc/NFe/infNFe/transp/transporta/CNPJ/text()";
    public static final String TRANSP_ENDERECO               = "/nfeProc/NFe/infNFe/transp/transporta/xEnder/text()";
    public static final String TRANSP_MUNICIPIO              = "/nfeProc/NFe/infNFe/transp/transporta/xMun/text()";
    public static final String TRANSP_UF                     = "/nfeProc/NFe/infNFe/transp/transporta/UF/text()";
    public static final String TRANSP_INSCRICAO_ESTADUAL     = "/nfeProc/NFe/infNFe/transp/transporta/IE/text()";
    public static final String TRANSP_CODIGO_ANTT            = "/nfeProc/NFe/infNFe/transp/veicTransp/RNTC/text()";
    public static final String TRANSP_PLACA_VEICULO          = "/nfeProc/NFe/infNFe/transp/veicTransp/placa/text()";
    public static final String TRANSP_VEICULO_UF             = "/nfeProc/NFe/infNFe/transp/veicTransp/UF/text()";
    public static final String TRANSP_MODALIDADE_FRETE       = "/nfeProc/NFe/infNFe/transp/modFrete/text()";

    public static final String VALOR_BASE_CALCULO_ICMS 		 = "/nfeProc/NFe/infNFe/total/ICMSTot/vBC/text()";
    public static final String VALOR_ICMS              		 = "/nfeProc/NFe/infNFe/total/ICMSTot/vICMS/text()";
    public static final String VALOR_BASE_CALCULO_ICMS_SUBST = "/nfeProc/NFe/infNFe/total/ICMSTot/vBCST/text()";
    public static final String VALOR_ICMS_SUBST 		     = "/nfeProc/NFe/infNFe/total/ICMSTot/vST/text()";
    public static final String VALOR_TOTAL_PRODUTOS  		 = "/nfeProc/NFe/infNFe/total/ICMSTot/vProd/text()";
    public static final String VALOR_FRETE           		 = "/nfeProc/NFe/infNFe/total/ICMSTot/vFrete/text()";
    public static final String VALOR_SEGURO          		 = "/nfeProc/NFe/infNFe/total/ICMSTot/vSeg/text()";
    public static final String VALOR_DESCONTO        		 = "/nfeProc/NFe/infNFe/total/ICMSTot/vDesc/text()";
    public static final String VALOR_IPI             		 = "/nfeProc/NFe/infNFe/total/ICMSTot/vIPI/text()";
    public static final String VALOR_OUTRO           		 = "/nfeProc/NFe/infNFe/total/ICMSTot/vOutro/text()";

    public static final String VALOR_TOTAL_TRIBUTOS          = "/nfeProc/NFe/infNFe/det/imposto/vTotTrib/text()";
    public static final String INFOMACOES_COMPLEMENTARES     = "/nfeProc/NFe/infNFe/infAdic/infCpl/text()";
    public static final String INFOMACOES_ADICIONAL_FISCO    = "/nfeProc/NFe/infNFe/infAdic/infAdFisco/text()";
    public static final String OBS_CONT_TEXTO                = "/nfeProc/NFe/infNFe/infAdic/obsCont/xTexto/text()";
    public static final String OBS_CONT_CAMPO                = "/nfeProc/NFe/infNFe/infAdic/obsCont/xCampo/text()";

    public static final String CHAVE_ACESSO                  = "/nfeProc/protNFe/infProt/chNFe/text()";
    public static final String PROTOCOLO_AUTORIZACAO         = "/nfeProc/protNFe/infProt/nProt/text()";
    public static final String PREENCHER_ZERO                = "0,00";

    /**
     * Impede instanciacao
     */
    private ConstantesNotaFiscalParser() {
        // Impede instanciacao
    }
}
