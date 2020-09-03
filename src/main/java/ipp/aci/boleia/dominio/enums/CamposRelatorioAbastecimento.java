package ipp.aci.boleia.dominio.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Opções de campos para o relatório de abastecimento
 */
public enum CamposRelatorioAbastecimento {
    ID_TRANSACAO(100,"enum.CamposRelatorioAbastecimento.ID_TRANSACAO.label", true, true, 1, 20, false),
    NUMERO_NOTA_FISCAL(101,"enum.CamposRelatorioAbastecimento.NUMERO_NOTA_FISCAL.label", true, true, 1, 20, false),
    CODIGO_AUTORIZACAO(102, "enum.CamposRelatorioAbastecimento.CODIGO_AUTORIZACAO.label", false, true, 1, 50, false),
    HORA_TRANSACAO(103, "enum.CamposRelatorioAbastecimento.HORA_TRANSACAO.label", false, true, 1, 20, false),
    DATA_TRANSACAO(104, "enum.CamposRelatorioAbastecimento.DATA_TRANSACAO.label", false, true, 1, 20, false),
    HORA_ABASTECIMENTO(105, "enum.CamposRelatorioAbastecimento.HORA_ABASTECIMENTO.label", true, true, 1, 25, false),
    DATA_ABASTECIMENTO(106, "enum.CamposRelatorioAbastecimento.DATA_ABASTECIMENTO.label", true, true, 1, 25, false),
    CNPJ_FROTA_MATRIZ(200, "enum.CamposRelatorioAbastecimento.CNPJ_FROTA_MATRIZ.label", true, true, 2, 25, false),
    RAZAO_SOCIAL_FROTA_MATRIZ(201, "enum.CamposRelatorioAbastecimento.RAZAO_SOCIAL_FROTA_MATRIZ.label", true, true, 2, 50, false),
    CNPJ_UNIDADE(202, "enum.CamposRelatorioAbastecimento.CNPJ_UNIDADE.label", true, true, 2, 25, false),
    RAZAO_SOCIAL_UNIDADE(203, "enum.CamposRelatorioAbastecimento.RAZAO_SOCIAL_UNIDADE.label", true, true, 2, 50, false),
    CIDADE_UNIDADE(204, "enum.CamposRelatorioAbastecimento.CIDADE_UNIDADE.label", true, true, 2, 30, false),
    UF_UNIDADE(205, "enum.CamposRelatorioAbastecimento.UF_UNIDADE.label", true, true, 2, 30, false),
    NOME_FANTASIA_FROTA(206, "enum.CamposRelatorioAbastecimento.NOME_FANTASIA_FROTA.label", false, true, 2, 50, false),
    CNPJ_EMPRESA_AGREGADA(300, "enum.CamposRelatorioAbastecimento.CNPJ_EMPRESA_AGREGADA.label", true, true, 3, 25, false),
    RAZAO_SOCIAL_EMPRESA_AGREGADA(301, "enum.CamposRelatorioAbastecimento.RAZAO_SOCIAL_EMPRESA_AGREGADA.label", true, true, 3, 50, false),
    CIDADE_EMPRESA_AGREGADA(302, "enum.CamposRelatorioAbastecimento.CIDADE_EMPRESA_AGREGADA.label", true, true, 3, 30, false),
    UF_EMPRESA_AGREGADA(303, "enum.CamposRelatorioAbastecimento.UF_EMPRESA_AGREGADA.label", true, true, 3, 30, false),
    CODIGO_GRUPO_OPERACIONAL(400, "enum.CamposRelatorioAbastecimento.CODIGO_GRUPO_OPERACIONAL.label", false, true, 4, 20, false),
    NOME_GRUPO_OPERACIONAL(401, "enum.CamposRelatorioAbastecimento.NOME_GRUPO_OPERACIONAL.label", false, true, 4, 50, false),
    CNPJ_ESTABELECIMENTO_CREDENCIADO(402, "enum.CamposRelatorioAbastecimento.CNPJ_ESTABELECIMENTO_CREDENCIADO.label", true, true, 4, 25, false),
    RAZAO_SOCIAL_ESTABELECIMENTO_CREDENCIADO(403, "enum.CamposRelatorioAbastecimento.RAZAO_SOCIAL_ESTABELECIMENTO_CREDENCIADO.label", true, true, 4, 50, false),
    NOME_FANTASIA_ESTABELECIMENTO_CREDENCIADO(404, "enum.CamposRelatorioAbastecimento.NOME_FANTASIA_ESTABELECIMENTO_CREDENCIADO.label", true, true, 4, 50, false),
    ENDERECO_ESTABELECIMENTO_CREDECIADO(405, "enum.CamposRelatorioAbastecimento.ENDERECO_ESTABELECIMENTO_CREDECIADO.label", true, true, 4, 100, false),
    CIDADE_ESTABELECIMENTO_CREDECIADO(406, "enum.CamposRelatorioAbastecimento.CIDADE_ESTABELECIMENTO_CREDECIADO.label", true, true, 4, 30, false),
    UF_ESTABELECIMENTO_CREDENCIADO(407, "enum.CamposRelatorioAbastecimento.UF_ESTABELECIMENTO_CREDENCIADO.label", true, true, 4, 30, false),
    PLACA_VEICULO(500, "enum.CamposRelatorioAbastecimento.PLACA_VEICULO.label", true, true, 5, 25, false),
    CLASSIFICACAO_VEICULO(501, "enum.CamposRelatorioAbastecimento.CLASSIFICACAO_VEICULO.label", false, true, 5, 30, false),
    CPF_MOTORISTA(600, "enum.CamposRelatorioAbastecimento.CPF_MOTORISTA.label", true, true, 6, 20, false),
    NOME_MOTORISTA(601, "enum.CamposRelatorioAbastecimento.NOME_MOTORISTA.label", true, true, 6, 30, false),
    MATRICULA_MOTORISTA(602, "enum.CamposRelatorioAbastecimento.MATRICULA_MOTORISTA.label", false, true, 6, 30, false),
    RASTREADOR(603, "enum.CamposRelatorioAbastecimento.RASTREADOR.label", false, true, 6, 15, false),
    LAT_LONG_DISPOSITIVO(604, "enum.CamposRelatorioAbastecimento.LAT_LONG_DISPOSITIVO.label", false, true, 6, 45, false),
    LAT_LONG_VEICULO_RASTREADOR(605, "enum.CamposRelatorioAbastecimento.LAT_LONG_VEICULO_RASTREADOR.label", false, true, 6, 45, false),
    LAT_LONG_POSTO(606, "enum.CamposRelatorioAbastecimento.LAT_LONG_POSTO.label", false, true, 6, 45, false),
    HODOMETRO_HORIMETRO_RASTREADOR(607, "enum.CamposRelatorioAbastecimento.HODOMETRO_HORIMETRO_RASTREADOR.label", false, true, 6, 45, false),
    HODOMETRO_HORIMETRO(608, "enum.CamposRelatorioAbastecimento.HODOMETRO_HORIMETRO.label", true, true, 6, 40, false),
    HODOMETRO_HORIMETRO_ANTERIOR(609, "enum.CamposRelatorioAbastecimento.HODOMETRO_HORIMETRO_ANTERIOR.label", false, true, 6, 40, false),
    HODOMETRO_HORIMETRO_PERCORRIDO(610, "enum.CamposRelatorioAbastecimento.HODOMETRO_HORIMETRO_PERCORRIDO.label", false, true, 6, 40, false),
    MEDIA_KML_HL(611, "enum.CamposRelatorioAbastecimento.MEDIA_KML_HL.label", false, true, 6, 30, false),
    FOTO_HOD_HOR(612, "enum.CamposRelatorioAbastecimento.FOTO_HOD_HOR.label", false, true, 6, 30, false),
    JUSTIFICATIVA_FOTO_HOD_HOR(613, "enum.CamposRelatorioAbastecimento.JUSTIFICATIVA_FOTO_HOD_HOR.label", false, true, 6, 50, false),
    MODO(614, "enum.CamposRelatorioAbastecimento.MODO.label", true, true, 6, 15, false),
    MEIO_PAGAMENTO(615, "enum.CamposRelatorioAbastecimento.MEIO_PAGAMENTO.label", true, true, 6, 40, false),
    TIPO_AUTORIZACAO(616, "enum.CamposRelatorioAbastecimento.TIPO_AUTORIZACAO.label", false, true, 6, 30, false),
    HODOMETRO_HORIMETRO_DISPOSITIVO(617, "enum.CamposRelatorioAbastecimento.HODOMETRO_HORIMETRO_DISPOSITIVO.label", false, true, 6, 50, false),
    ESTORNO(700, "enum.CamposRelatorioAbastecimento.ESTORNO.label", false, true, 7, 20, false),
    ID_ESTORNO(701, "enum.CamposRelatorioAbastecimento.ID_ESTORNO.label", false, true, 7, 20, false),
    PEDIDO(702, "enum.CamposRelatorioAbastecimento.PEDIDO.label", false, true, 7, 30, false),
    NUMERO_PEDIDO(703, "enum.CamposRelatorioAbastecimento.NUMERO_PEDIDO.label", false, true, 7, 30, false),
    NUMERO_PEDIDO_PRE_PAGO(704, "enum.CamposRelatorioAbastecimento.NUMERO_PEDIDO_PRE_PAGO.label", false, true, 7, 40, false),
    CODIGO_VIP(706, "enum.CamposRelatorioAbastecimento.CODIGO_VIP.label", true, true, 7, 20, false),
    PRODUTO(707, "enum.CamposRelatorioAbastecimento.PRODUTO.label", true, true, 7, 30, false),
    LITROS(708, "enum.CamposRelatorioAbastecimento.LITROS.label", true, true, 7, 20, false),
    LITROS_VALOR_UNITARIO_PRODUTO(709, "enum.CamposRelatorioAbastecimento.LITROS_VALOR_UNITARIO_PRODUTO.label", true, true, 7, 30, false),
    LITROS_VALOR_TOTAL_PRODUTO(710, "enum.CamposRelatorioAbastecimento.LITROS_VALOR_TOTAL_PRODUTO.label", true, true, 7, 30, false),
    ALIMENTACAO(711, "enum.CamposRelatorioAbastecimento.ALIMENTACAO.label", true, true, 7, 30, false),
    ALIMENTACAO_VALOR_UNITARIO_SERVICO(712, "enum.CamposRelatorioAbastecimento.ALIMENTACAO_VALOR_UNITARIO_SERVICO.label", true, true, 7, 30, false),
    ALIMENTACAO_VALOR_TOTAL_SERVICO(713, "enum.CamposRelatorioAbastecimento.ALIMENTACAO_VALOR_TOTAL_SERVICO.label", true, true, 7, 30, false),
    ARLA_32_BALDE(714, "enum.CamposRelatorioAbastecimento.ARLA_32_BALDE.label", true, true, 7, 30, false),
    ARLA_32_BALDE_VALOR_UNITARIO_SERVICO(715, "enum.CamposRelatorioAbastecimento.ARLA_32_BALDE_VALOR_UNITARIO_SERVICO.label", true, true, 7, 30, false),
    ARLA_32_BALDE_VALOR_TOTAL_SERVICO(716, "enum.CamposRelatorioAbastecimento.ARLA_32_BALDE_VALOR_TOTAL_SERVICO.label", true, true, 7, 30, false),
    ARLA_32_GRANEL(717, "enum.CamposRelatorioAbastecimento.ARLA_32_GRANEL.label", true, true, 7, 30, false),
    ARLA_32_GRANEL_VALOR_UNITARIO_SERVICO(718, "enum.CamposRelatorioAbastecimento.ARLA_32_GRANEL_VALOR_UNITARIO_SERVICO.label", true, true, 7, 30, false),
    ARLA_32_GRANEL_VALOR_TOTAL_SERVICO(719, "enum.CamposRelatorioAbastecimento.ARLA_32_GRANEL_VALOR_TOTAL_SERVICO.label", true, true, 7, 30, false),
    BORRACHARIA(720, "enum.CamposRelatorioAbastecimento.BORRACHARIA.label", true, true, 7, 30, false),
    BORRACHARIA_VALOR_UNITARIO_SERVICO(721, "enum.CamposRelatorioAbastecimento.BORRACHARIA_VALOR_UNITARIO_SERVICO.label", true, true, 7, 30, false),
    BORRACHARIA_VALOR_TOTAL_SERVICO(722, "enum.CamposRelatorioAbastecimento.BORRACHARIA_VALOR_TOTAL_SERVICO.label", true, true, 7, 30, false),
    LAVAGEM(723, "enum.CamposRelatorioAbastecimento.LAVAGEM.label", true, true, 7, 30, false),
    LAVAGEM_VALOR_UNITARIO_SERVICO(724, "enum.CamposRelatorioAbastecimento.LAVAGEM_VALOR_UNITARIO_SERVICO.label", true, true, 7, 30, false),
    LAVAGEM_VALOR_TOTAL_SERVICO(725, "enum.CamposRelatorioAbastecimento.LAVAGEM_VALOR_TOTAL_SERVICO.label", true, true, 7, 30, false),
    MANUTENCAO(726, "enum.CamposRelatorioAbastecimento.MANUTENCAO.label", true, true, 7, 30, false),
    MANUTENCAO_VALOR_UNITARO_SERVICO(727, "enum.CamposRelatorioAbastecimento.MANUTENCAO_VALOR_UNITARIO_SERVICO.label", true, true, 7, 30, false),
    MANUTENCAO_VALOR_TOTAL_SERVICO(728, "enum.CamposRelatorioAbastecimento.MANUTENCAO_VALOR_TOTAL_SERVICO.label", true, true, 7, 30, false),
    TROCA_OLEO(729, "enum.CamposRelatorioAbastecimento.TROCA_OLEO.label", true, true, 7, 30, false),
    TROCA_OLEO_VALOR_UNITARO_SERVICO(730, "enum.CamposRelatorioAbastecimento.TROCA_OLEO_VALOR_UNITARIO_SERVICO.label", true, true, 7, 30, false),
    TROCA_OLEO_VALOR_TOTAL_SERVICO(731, "enum.CamposRelatorioAbastecimento.TROCA_OLEO_VALOR_TOTAL_SERVICO.label", true, true, 7, 30, false),
    VALOR_TOTAL_TRANSACAO(732, "enum.CamposRelatorioAbastecimento.VALOR_TOTAL_TRANSACAO.label", true, true, 7, 30, false),
    STATUS(733, "enum.CamposRelatorioAbastecimento.STATUS.label", true, true, 7, 25, false),
    MOTIVO_RECUSA(734, "enum.CamposRelatorioAbastecimento.MOTIVO_RECUSA.label", true, true, 7, 30, false),
    RESUMO_OPERACAO(735, "enum.CamposRelatorioAbastecimento.RESUMO_OPERACAO.label", true, true, 7, 100, false),
    VALOR_DESCONTO_FROTAS_LEVES(736, "enum.CamposRelatoriosAbastecimento.VALOR_DESCONTO_FROTAS_LEVES.label", false, true, 7, 30, true),
    VALOR_COM_DESCONTO_FROTAS_LEVES(737, "enum.CamposRelatorioAbastecimento.VALOR_TOTAL_DESCONTO_FROTAS_LEVES.label", false, true, 7, 30, true);



    private int codigo;
    private String label;
    private boolean visaoPosto;
    private boolean visaoFrota;
    private int categoria;
    private int larguraColunaTxt;
    private boolean oculto;

    private static final int CAT_TRANSACAO = 1;
    private static final int CAT_UNIDADE = 2;
    private static final int CAT_EMPRESA_AGREGADA = 3;
    private static final int CAT_GRUPO_OPERACIONAL = 4;
    private static final int CAT_VEICULO = 5;
    private static final int CAT_MOTORISTA = 6;
    private static final int CAT_PEDIDO = 7;
    private static final String CAT_TRANSACAO_LABEL = "enum.CamposRelatorioAbastecimento.categoria.TRANSACAO.label";
    private static final String CAT_UNIDADE_LABEL = "enum.CamposRelatorioAbastecimento.categoria.UNIDADE.label";
    private static final String CAT_EMPRESA_AGREGADA_LABEL = "enum.CamposRelatorioAbastecimento.categoria.EMPRESA_AGREGADA.label";
    private static final String CAT_GRUPO_OPERACIONAL_LABEL = "enum.CamposRelatorioAbastecimento.categoria.GRUPO_OPERACIONAL.label";
    private static final String CAT_VEICULO_LABEL = "enum.CamposRelatorioAbastecimento.categoria.VEICULO.label";
    private static final String CAT_MOTORISTA_LABEL = "enum.CamposRelatorioAbastecimento.categoria.MOTORISTA.label";
    private static final String CAT_PEDIDO_LABEL = "enum.CamposRelatorioAbastecimento.categoria.PEDIDO.label";


    /**
     * Construtor.
     *
     * @param codigo o código do campo
     * @param label a descrição do campo
     * @param visaoFrota indica se é para mostrar na visão do frotista
     * @param visaoPosto indica se apresenta na visão da revenda
     * @param categoria a categoria do campo
     * @param larguraColunaTxt largura da coluna de texto
     */
    CamposRelatorioAbastecimento(int codigo, String label, boolean visaoPosto, boolean visaoFrota, int categoria, int larguraColunaTxt, boolean oculto) {
        this.codigo = codigo;
        this.label = label;
        this.visaoPosto = visaoPosto;
        this.visaoFrota = visaoFrota;
        this.categoria = categoria;
        this.larguraColunaTxt = larguraColunaTxt;
        this.oculto = oculto;
    }

    public String getLabel() { return this.label; }
    public int getCategoria() { return this.categoria; }
    public int getCodigo() { return this.codigo; }
    public int getLarguraColunaTxt() { return this.larguraColunaTxt; }
    public boolean isOculto() {
        return this.oculto;
    }

    /**
    * Retorna o label correspondente ao valor
     *
     * @return o label correspondente ao valor
    * */
    public String getCategoriaLabel() {
        switch (this.categoria) {
            case CAT_TRANSACAO: return CAT_TRANSACAO_LABEL;
            case CAT_UNIDADE: return CAT_UNIDADE_LABEL;
            case CAT_EMPRESA_AGREGADA: return CAT_EMPRESA_AGREGADA_LABEL;
            case CAT_GRUPO_OPERACIONAL: return CAT_GRUPO_OPERACIONAL_LABEL;
            case CAT_VEICULO: return CAT_VEICULO_LABEL;
            case CAT_MOTORISTA: return CAT_MOTORISTA_LABEL;
            case CAT_PEDIDO: return CAT_PEDIDO_LABEL;
        }

        return "";
    }

    /**
     * Lista de campos que podem ser utilizados no relatório de abastecimento na visão da solução
     * @return lista de campos
     */
    public static List<CamposRelatorioAbastecimento> listarCamposVisaoSolucao() {
        return Arrays.asList(CamposRelatorioAbastecimento.values())
                .stream()
                .filter(campo -> !listarCamposOcultos().contains(campo))
                .collect(Collectors.toList());
    }

    /**
     * Lista de campos que podem ser utilizados no relatório de abastecimento na visão da revenda
     * @return lista de campos
     */
    public static List<CamposRelatorioAbastecimento> listarCamposVisaoRevenda() {
        List<CamposRelatorioAbastecimento> campos = listarCamposVisaoSolucao();
        return campos.stream()
                .filter(campo -> campo.visaoPosto)
                .collect(Collectors.toList());
    }

    /**
     * Lista de campos que podem ser utilizados no relatório de abastecimento na visão da frota
     * @return lista de campos
     */
    public static List<CamposRelatorioAbastecimento> listarCamposVisaoFrota() {
        List<CamposRelatorioAbastecimento> campos = listarCamposVisaoSolucao();
        return campos.stream()
                .filter(campo -> campo.visaoFrota)
                .collect(Collectors.toList());
    }

    /**
     * Obtem o campo do relatório por id
     * @param id do campo desejado
     * @return campo do relatório de abastecimento
     */
    public static CamposRelatorioAbastecimento obterCampoPorId(int id) {
        for (CamposRelatorioAbastecimento campo : CamposRelatorioAbastecimento.values()) {
            if (campo.getCodigo() == id) {
                return campo;
            }
        }

        return null;
    }

    /**
     * Filtra os campos favoritos na ordem em que foram salvos
     * @param favoritos campos a ser filtrados
     * @return lista de campos favoritos
     */
    public static List<CamposRelatorioAbastecimento> filtrarCamposFavoritos(List<Integer> favoritos) {
        return listarCamposVisaoSolucao().stream()
                .filter(campo -> favoritos.contains(campo.codigo))
                .collect(Collectors.toList());
    }

    /**
     * Lista os campos ocultos da personalização de campos do relatório
     * @return lista de campos ocultos
     */
    public static List<CamposRelatorioAbastecimento> listarCamposOcultos() {
        return Arrays.asList(CamposRelatorioAbastecimento.values())
                .stream()
                .filter(campo -> campo.oculto)
                .collect(Collectors.toList());
    }

}
