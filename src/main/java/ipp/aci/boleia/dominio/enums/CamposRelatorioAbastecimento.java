package ipp.aci.boleia.dominio.enums;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Opções de campos para o relatório de abastecimento
 */
public enum CamposRelatorioAbastecimento {
    ID_TRANSACAO(100,100,"enum.CamposRelatorioAbastecimento.ID_TRANSACAO.label", true, true, true, 1, 20, false),
    NUMERO_NOTA_FISCAL(101,736,"enum.CamposRelatorioAbastecimento.NUMERO_NOTA_FISCAL.label", true, true,true, 1, 20, false),
    CODIGO_AUTORIZACAO(102, 102, "enum.CamposRelatorioAbastecimento.CODIGO_AUTORIZACAO.label", false, true,true, 1, 50, false),
    HORA_TRANSACAO(103, 103, "enum.CamposRelatorioAbastecimento.HORA_TRANSACAO.label", false, true,true, 1, 20, false),
    DATA_TRANSACAO(104, 104, "enum.CamposRelatorioAbastecimento.DATA_TRANSACAO.label", false, true,true, 1, 20, false),
    HORA_ABASTECIMENTO(105, 105, "enum.CamposRelatorioAbastecimento.HORA_ABASTECIMENTO.label", true, true,true, 1, 25, false),
    DATA_ABASTECIMENTO(106, 106, "enum.CamposRelatorioAbastecimento.DATA_ABASTECIMENTO.label", true, true,true, 1, 25, false),
    CNPJ_FROTA_MATRIZ(200, 200, "enum.CamposRelatorioAbastecimento.CNPJ_FROTA_MATRIZ.label", true, true,true, 2, 25, false),
    RAZAO_SOCIAL_FROTA_MATRIZ(201, 201, "enum.CamposRelatorioAbastecimento.RAZAO_SOCIAL_FROTA_MATRIZ.label", true,true, true, 2, 50, false),
    CNPJ_UNIDADE(202, 202, "enum.CamposRelatorioAbastecimento.CNPJ_UNIDADE.label", true, true,true, 2, 25, false),
    RAZAO_SOCIAL_UNIDADE(203, 203, "enum.CamposRelatorioAbastecimento.RAZAO_SOCIAL_UNIDADE.label", true, true,true, 2, 50, false),
    CIDADE_UNIDADE(204, 204, "enum.CamposRelatorioAbastecimento.CIDADE_UNIDADE.label", true, true,true, 2, 30, false),
    UF_UNIDADE(205, 205, "enum.CamposRelatorioAbastecimento.UF_UNIDADE.label", true, true,true, 2, 30, false),
    NOME_FANTASIA_FROTA(206, 206, "enum.CamposRelatorioAbastecimento.NOME_FANTASIA_FROTA.label", false, true,true, 2, 50, false),
    CNPJ_EMPRESA_AGREGADA(300, 300, "enum.CamposRelatorioAbastecimento.CNPJ_EMPRESA_AGREGADA.label", true, true,true, 3, 25, false),
    RAZAO_SOCIAL_EMPRESA_AGREGADA(301, 301, "enum.CamposRelatorioAbastecimento.RAZAO_SOCIAL_EMPRESA_AGREGADA.label", true, true,true, 3, 50, false),
    CIDADE_EMPRESA_AGREGADA(302, 302, "enum.CamposRelatorioAbastecimento.CIDADE_EMPRESA_AGREGADA.label", true, true,true, 3, 30, false),
    UF_EMPRESA_AGREGADA(303, 303, "enum.CamposRelatorioAbastecimento.UF_EMPRESA_AGREGADA.label", true, true,true, 3, 30, false),
    CODIGO_CENTRO_DE_CUSTO(400, 400, "enum.CamposRelatorioAbastecimento.CODIGO_CENTRO_DE_CUSTO.label", false, true,true, 4, 25, false),
    NOME_CENTRO_DE_CUSTO(401, 401, "enum.CamposRelatorioAbastecimento.NOME_CENTRO_DE_CUSTO.label", false, true,true, 4, 50, false),

    // Ordem das categorias é feita pela ordem do ENUM - considerar criar propriedade para ordem da categoria
    CNPJ_ESTABELECIMENTO_CREDENCIADO(800, 410, "enum.CamposRelatorioAbastecimento.CNPJ_ESTABELECIMENTO_CREDENCIADO.label", true, true, true,8, 25, false),
    RAZAO_SOCIAL_ESTABELECIMENTO_CREDENCIADO(801, 411, "enum.CamposRelatorioAbastecimento.RAZAO_SOCIAL_ESTABELECIMENTO_CREDENCIADO.label", true, true, true,8, 50, false),
    NOME_FANTASIA_ESTABELECIMENTO_CREDENCIADO(802, 412, "enum.CamposRelatorioAbastecimento.NOME_FANTASIA_ESTABELECIMENTO_CREDENCIADO.label", true, true, true,8, 50, false),
    ENDERECO_ESTABELECIMENTO_CREDECIADO(803, 413, "enum.CamposRelatorioAbastecimento.ENDERECO_ESTABELECIMENTO_CREDECIADO.label", true, true, true,8, 100, false),
    CIDADE_ESTABELECIMENTO_CREDECIADO(804, 414, "enum.CamposRelatorioAbastecimento.CIDADE_ESTABELECIMENTO_CREDECIADO.label", true, true, true,8, 30, false),
    UF_ESTABELECIMENTO_CREDENCIADO(805, 415, "enum.CamposRelatorioAbastecimento.UF_ESTABELECIMENTO_CREDENCIADO.label", true, true, true,8, 30, false),

    PLACA_VEICULO(500, 500, "enum.CamposRelatorioAbastecimento.PLACA_VEICULO.label", true, true,true, 5, 25, false),
    CLASSIFICACAO_VEICULO(501, 501, "enum.CamposRelatorioAbastecimento.CLASSIFICACAO_VEICULO.label", false, true,true, 5, 30, false),
    IDENTIFICADOR_INTERNO(502, 502, "enum.CamposRelatorioAbastecimento.IDENTIFICADOR_INTERNO.label", false, true,true, 5, 30, false),
    CPF_MOTORISTA(600, 600, "enum.CamposRelatorioAbastecimento.CPF_MOTORISTA.label", true, true,true, 6, 20, false),
    NOME_MOTORISTA(601, 601, "enum.CamposRelatorioAbastecimento.NOME_MOTORISTA.label", true, true,true, 6, 30, false),
    MATRICULA_MOTORISTA(602, 602, "enum.CamposRelatorioAbastecimento.MATRICULA_MOTORISTA.label", false, true,true, 6, 30, false),
    RASTREADOR(603, 603, "enum.CamposRelatorioAbastecimento.RASTREADOR.label", false, true,true, 6, 15, false),
    LAT_LONG_DISPOSITIVO(604, 604, "enum.CamposRelatorioAbastecimento.LAT_LONG_DISPOSITIVO.label", false, true,true, 6, 45, false),
    LAT_LONG_VEICULO_RASTREADOR(605, 605, "enum.CamposRelatorioAbastecimento.LAT_LONG_VEICULO_RASTREADOR.label", false, true,true, 6, 45, false),
    LAT_LONG_POSTO(606, 606, "enum.CamposRelatorioAbastecimento.LAT_LONG_POSTO.label", false, true,true, 6, 45, false),
    HODOMETRO_HORIMETRO_RASTREADOR(607, 607, "enum.CamposRelatorioAbastecimento.HODOMETRO_HORIMETRO_RASTREADOR.label", false, true,true, 6, 45, false),
    HODOMETRO_HORIMETRO(608, 608, "enum.CamposRelatorioAbastecimento.HODOMETRO_HORIMETRO.label", true, true,true, 6, 40, false),
    HODOMETRO_HORIMETRO_ANTERIOR(609, 609, "enum.CamposRelatorioAbastecimento.HODOMETRO_HORIMETRO_ANTERIOR.label", false, true,true, 6, 40, false),
    HODOMETRO_HORIMETRO_PERCORRIDO(610, 610, "enum.CamposRelatorioAbastecimento.HODOMETRO_HORIMETRO_PERCORRIDO.label", false, true,true, 6, 40, false),
    MEDIA_KML_LH(611, 611, "enum.CamposRelatorioAbastecimento.MEDIA_KML_LH.label", false, true,true, 6, 30, false),
    FOTO_HOD_HOR(612, 612, "enum.CamposRelatorioAbastecimento.FOTO_HOD_HOR.label", false, true,true, 6, 30, false),
    JUSTIFICATIVA_FOTO_HOD_HOR(613, 613, "enum.CamposRelatorioAbastecimento.JUSTIFICATIVA_FOTO_HOD_HOR.label", false, true,true, 6, 50, false),
    MODO(614, 614, "enum.CamposRelatorioAbastecimento.MODO.label", true, true,true, 6, 15, false),
    MEIO_PAGAMENTO(615, 615, "enum.CamposRelatorioAbastecimento.MEIO_PAGAMENTO.label", true, true,true, 6, 40, false),
    TIPO_AUTORIZACAO(616, 616, "enum.CamposRelatorioAbastecimento.TIPO_AUTORIZACAO.label", false, true,true, 6, 30, false),
    HODOMETRO_HORIMETRO_DISPOSITIVO(617, 617, "enum.CamposRelatorioAbastecimento.HODOMETRO_HORIMETRO_DISPOSITIVO.label", false, true,true, 6, 50, false),
    ESTORNO(700, 700, "enum.CamposRelatorioAbastecimento.ESTORNO.label", false, true,true, 7, 20, false),
    ID_ESTORNO(701, 701, "enum.CamposRelatorioAbastecimento.ID_ESTORNO.label", false, true,true, 7, 20, false),
    PEDIDO(702, 702, "enum.CamposRelatorioAbastecimento.PEDIDO.label", false, true,true, 7, 30, false),
    NUMERO_PEDIDO(703, 101, "enum.CamposRelatorioAbastecimento.NUMERO_PEDIDO.label", false, true,true, 7, 30, false),
    NUMERO_PEDIDO_PRE_PAGO(704, 704, "enum.CamposRelatorioAbastecimento.NUMERO_PEDIDO_PRE_PAGO.label", false, true,true, 7, 40, false),
    CODIGO_VIP(706, 706, "enum.CamposRelatorioAbastecimento.CODIGO_VIP.label", true, true,true, 7, 20, false),
    PRODUTO(707, 707, "enum.CamposRelatorioAbastecimento.PRODUTO.label", true, true, true,7, 30, false),
    LITROS(708, 708, "enum.CamposRelatorioAbastecimento.LITROS.label", true, true, true,7, 20, false),
    LITROS_VALOR_UNITARIO_PRODUTO(709, 709, "enum.CamposRelatorioAbastecimento.LITROS_VALOR_UNITARIO_PRODUTO.label", true, true,true, 7, 30, false),
    LITROS_VALOR_TOTAL_PRODUTO(710, 710, "enum.CamposRelatorioAbastecimento.LITROS_VALOR_TOTAL_PRODUTO.label", true, true, true,7, 30, false),
    ALIMENTACAO(711, 711, "enum.CamposRelatorioAbastecimento.ALIMENTACAO.label", true, true,true, 7, 30, false),
    ALIMENTACAO_VALOR_UNITARIO_SERVICO(712, 712, "enum.CamposRelatorioAbastecimento.ALIMENTACAO_VALOR_UNITARIO_SERVICO.label", true, true,true, 7, 30, false),
    ALIMENTACAO_VALOR_TOTAL_SERVICO(713, 713, "enum.CamposRelatorioAbastecimento.ALIMENTACAO_VALOR_TOTAL_SERVICO.label", true, true, true,7, 30, false),
    ARLA_32_BALDE(714, 714, "enum.CamposRelatorioAbastecimento.ARLA_32_BALDE.label", true, true, true,7, 30, false),
    ARLA_32_BALDE_VALOR_UNITARIO_SERVICO(715, 715, "enum.CamposRelatorioAbastecimento.ARLA_32_BALDE_VALOR_UNITARIO_SERVICO.label", true, true, true,7, 30, false),
    ARLA_32_BALDE_VALOR_TOTAL_SERVICO(716, 716, "enum.CamposRelatorioAbastecimento.ARLA_32_BALDE_VALOR_TOTAL_SERVICO.label", true, true, true,7, 30, false),
    ARLA_32_GRANEL(717, 717, "enum.CamposRelatorioAbastecimento.ARLA_32_GRANEL.label", true, true, true,7, 30, false),
    ARLA_32_GRANEL_VALOR_UNITARIO_SERVICO(718, 718, "enum.CamposRelatorioAbastecimento.ARLA_32_GRANEL_VALOR_UNITARIO_SERVICO.label", true, true, true,7, 30, false),
    ARLA_32_GRANEL_VALOR_TOTAL_SERVICO(719, 719, "enum.CamposRelatorioAbastecimento.ARLA_32_GRANEL_VALOR_TOTAL_SERVICO.label", true, true, true,7, 30, false),
    BORRACHARIA(720, 720, "enum.CamposRelatorioAbastecimento.BORRACHARIA.label", true, true, true,7, 30, false),
    BORRACHARIA_VALOR_UNITARIO_SERVICO(721, 721, "enum.CamposRelatorioAbastecimento.BORRACHARIA_VALOR_UNITARIO_SERVICO.label", true, true, true,7, 30, false),
    BORRACHARIA_VALOR_TOTAL_SERVICO(722, 722, "enum.CamposRelatorioAbastecimento.BORRACHARIA_VALOR_TOTAL_SERVICO.label", true, true, true,7, 30, false),
    LAVAGEM(723, 723, "enum.CamposRelatorioAbastecimento.LAVAGEM.label", true, true, true,7, 30, false),
    LAVAGEM_VALOR_UNITARIO_SERVICO(724, 724, "enum.CamposRelatorioAbastecimento.LAVAGEM_VALOR_UNITARIO_SERVICO.label", true, true, true,7, 30, false),
    LAVAGEM_VALOR_TOTAL_SERVICO(725, 725, "enum.CamposRelatorioAbastecimento.LAVAGEM_VALOR_TOTAL_SERVICO.label", true, true, true,7, 30, false),
    MANUTENCAO(726, 726, "enum.CamposRelatorioAbastecimento.MANUTENCAO.label", true, true, true,7, 30, false),
    MANUTENCAO_VALOR_UNITARO_SERVICO(727, 727, "enum.CamposRelatorioAbastecimento.MANUTENCAO_VALOR_UNITARIO_SERVICO.label", true, true, true,7, 30, false),
    MANUTENCAO_VALOR_TOTAL_SERVICO(728, 728, "enum.CamposRelatorioAbastecimento.MANUTENCAO_VALOR_TOTAL_SERVICO.label", true, true, true,7, 30, false),
    TROCA_OLEO(729, 729, "enum.CamposRelatorioAbastecimento.TROCA_OLEO.label", true, true, true,7, 30, false),
    TROCA_OLEO_VALOR_UNITARO_SERVICO(730, 730, "enum.CamposRelatorioAbastecimento.TROCA_OLEO_VALOR_UNITARIO_SERVICO.label", true, true, true,7, 30, false),
    TROCA_OLEO_VALOR_TOTAL_SERVICO(731, 731, "enum.CamposRelatorioAbastecimento.TROCA_OLEO_VALOR_TOTAL_SERVICO.label", true, true, true,7, 30, false),
    VALOR_TOTAL_TRANSACAO(732, 732, "enum.CamposRelatorioAbastecimento.VALOR_TOTAL_TRANSACAO.label", true, true, true,7, 30, false),
    VALOR_DESCONTO_FROTAS_LEVES(736, 733, "enum.CamposRelatoriosAbastecimento.VALOR_DESCONTO_FROTAS_LEVES.label", false, true, true,7, 30, true),
    VALOR_COM_DESCONTO_FROTAS_LEVES(737, 734, "enum.CamposRelatorioAbastecimento.VALOR_TOTAL_DESCONTO_FROTAS_LEVES.label", false, true, true,7, 30, true),

    STATUS(733, 735, "enum.CamposRelatorioAbastecimento.STATUS.label", true, true, true,7, 25, false),
    MOTIVO_RECUSA(734, 737, "enum.CamposRelatorioAbastecimento.MOTIVO_RECUSA.label", true, true, true,7, 30, false),
    MOTIVO_CONTINGENCIA(735, 738, "enum.CamposRelatorioAbastecimento.MOTIVO_CONTINGENCIA.label", true, true, true,7, 30, false),
    RESUMO_OPERACAO(738, 739, "enum.CamposRelatorioAbastecimento.RESUMO_OPERACAO.label", true, true, true,7, 100, false);

    private final int codigo;
    private final int ordem;
    private final String label;
    private final boolean visaoPosto;
    private final boolean visaoFrota;
    private final boolean visaoSolucao;
    private final int categoria;
    private final int larguraColunaTxt;
    private final boolean oculto;

    private static final int CAT_TRANSACAO = 1;
    private static final int CAT_UNIDADE = 2;
    private static final int CAT_EMPRESA_AGREGADA = 3;
    private static final int CAT_CENTRO_DE_CUSTO = 4;
    private static final int CAT_VEICULO = 5;
    private static final int CAT_MOTORISTA = 6;
    private static final int CAT_PEDIDO = 7;
    private static final int CAT_ESTABELECIMENTO_CREDENCIADO = 8;
    private static final String CAT_TRANSACAO_LABEL = "enum.CamposRelatorioAbastecimento.categoria.TRANSACAO.label";
    private static final String CAT_UNIDADE_LABEL = "enum.CamposRelatorioAbastecimento.categoria.UNIDADE.label";
    private static final String CAT_EMPRESA_AGREGADA_LABEL = "enum.CamposRelatorioAbastecimento.categoria.EMPRESA_AGREGADA.label";
    private static final String CAT_CENTRO_DE_CUSTO_LABEL = "enum.CamposRelatorioAbastecimento.categoria.CENTRO_DE_CUSTO.label";
    private static final String CAT_VEICULO_LABEL = "enum.CamposRelatorioAbastecimento.categoria.VEICULO.label";
    private static final String CAT_MOTORISTA_LABEL = "enum.CamposRelatorioAbastecimento.categoria.MOTORISTA.label";
    private static final String CAT_PEDIDO_LABEL = "enum.CamposRelatorioAbastecimento.categoria.PEDIDO.label";
    private static final String CAT_ESTABELECIMENTO_CREDENCIADO_LABEL = "enum.CamposRelatorioAbastecimento.categoria.ESTABELECIMENTO_CREDENCIADO.label";

    /**
     * Construtor.
     *
     * @param codigo o código do campo
     * @param ordem a ordem em que o campo deve ser exibido no relatório
     * @param label a descrição do campo
     * @param visaoFrota indica se é para mostrar na visão do frotista
     * @param visaoPosto indica se apresenta na visão da revenda
     * @param visaoSolucao indica se apresenta na visão da solução
     * @param categoria a categoria do campo
     * @param larguraColunaTxt largura da coluna de texto
     * @param oculto True caso o campo esteja oculto
     */
    CamposRelatorioAbastecimento(int codigo, int ordem, String label, boolean visaoPosto, boolean visaoFrota, boolean visaoSolucao, int categoria, int larguraColunaTxt, boolean oculto) {
        this.codigo = codigo;
        this.ordem = ordem;
        this.label = label;
        this.visaoPosto = visaoPosto;
        this.visaoFrota = visaoFrota;
        this.visaoSolucao = visaoSolucao;
        this.categoria = categoria;
        this.larguraColunaTxt = larguraColunaTxt;
        this.oculto = oculto;
    }

    public String getLabel() { return this.label; }
    public int getCategoria() { return this.categoria; }
    public int getCodigo() { return this.codigo; }
    public int getOrdem() { return ordem; }
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
            case CAT_CENTRO_DE_CUSTO: return CAT_CENTRO_DE_CUSTO_LABEL;
            case CAT_VEICULO: return CAT_VEICULO_LABEL;
            case CAT_MOTORISTA: return CAT_MOTORISTA_LABEL;
            case CAT_PEDIDO: return CAT_PEDIDO_LABEL;
            case CAT_ESTABELECIMENTO_CREDENCIADO: return CAT_ESTABELECIMENTO_CREDENCIADO_LABEL;
        }

        return "";
    }

    /**
     * Lista os campos ocultos da personalização de campos do relatório
     * @return lista de campos ocultos
     */
    public static List<CamposRelatorioAbastecimento> listarCamposOcultos() {
        return Arrays.stream(CamposRelatorioAbastecimento.values())
                .filter(campo -> campo.oculto)
                .collect(Collectors.toList());
    }

    /**
     * Lista de campos não ocultos que podem ser utilizados no relatório de abastecimento (sem visão específica)
     * @return lista de campos
     */
    public static List<CamposRelatorioAbastecimento> listarCamposSolucao() {
        return Arrays.stream(CamposRelatorioAbastecimento.values())
                .filter(campo -> !listarCamposOcultos().contains(campo))
                .collect(Collectors.toList());
    }

    /**
     * Lista de campos que podem ser utilizados no relatório de abastecimento na visão da solução
     * @return lista de campos
     */
    public static List<CamposRelatorioAbastecimento> listarCamposVisaoSolucao() {
        List<CamposRelatorioAbastecimento> campos = listarCamposSolucao();
        return campos.stream()
                .filter(campo -> campo.visaoSolucao)
                .collect(Collectors.toList());
    }

    /**
     * Lista de campos que podem ser utilizados no relatório de abastecimento na visão da revenda
     * @return lista de campos
     */
    public static List<CamposRelatorioAbastecimento> listarCamposVisaoRevenda() {
        List<CamposRelatorioAbastecimento> campos = listarCamposSolucao();
        return campos.stream()
                .filter(campo -> campo.visaoPosto)
                .collect(Collectors.toList());
    }

    /**
     * Lista de campos que podem ser utilizados no relatório de abastecimento na visão da frota
     * @return lista de campos
     */
    public static List<CamposRelatorioAbastecimento> listarCamposVisaoFrota() {
        List<CamposRelatorioAbastecimento> campos = listarCamposSolucao();
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
     * Obtém lista de Campos ordenados a partir de conjunto de codigos que representam os campos
     *
     * @param codigos conjunto de codigo dos campos
     * @return lista de campos ordenados
     */
    public static List<CamposRelatorioAbastecimento> obterCamposApartirDeCodigos(int[] codigos) {
        return Arrays.stream(codigos)
                .mapToObj(CamposRelatorioAbastecimento::obterCampoPorId)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingInt(CamposRelatorioAbastecimento::getOrdem))
                .collect(Collectors.toList());
    }

}
