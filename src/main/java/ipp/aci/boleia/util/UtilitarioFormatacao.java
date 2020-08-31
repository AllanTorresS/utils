package ipp.aci.boleia.util;

import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.apache.commons.lang3.StringUtils;

import javax.swing.text.MaskFormatter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Utilitario para formatacao de datas
 */
public final class UtilitarioFormatacao {

    public static final int TAMANHO_CPF = 11;
    public static final int TAMANHO_CNPJ = 14;

    /**
     * Impede a instanciacao e a heranca
     */
    private UtilitarioFormatacao() {
        // Impede a instanciacao
    }

    /**
     * Remove quaisquer caracteres nao-numericos e converte para inteiro.
     *
     * @param campo o valor a ser tratado
     * @return Um inteiro
     */
    public static Integer obterInteiroMascara(String campo) {
        String numeral = obterDigitosMascara(campo);
        return StringUtils.isBlank(numeral) ? null : Integer.parseInt(numeral);
    }

    /**
     * Remove quaisquer caracteres nao-numericos e converte para long.
     *
     * @param campo o valor a ser tratado
     * @return Um long
     */
    public static Long obterLongMascara(String campo) {
        String numeral = obterDigitosMascara(campo);
        return StringUtils.isBlank(numeral) ? null : new Long(numeral);
    }

    /**
     * Obtém o valor de hodômetro através da string informada.
     * @param campo string com o valor recebido.
     * @return o valor do hodômetro convertido.
     */
    public static Long obterLongHodometro(String campo) {
        String campoTratado = campo.replace(',','.');
        BigDecimal valorGerado = new BigDecimal(campoTratado);
        return valorGerado.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
    }

    /**
     * Obtém o valor do intervalo em km a partir de string fornecida
     * @param campo String com o valor recebido
     * @return O valor convertido
     */
    public static Long obterLongIntervaloKm(String campo) {
        if(campo != null) {
            return Long.parseLong(campo);
        }
        else {
            return null;
        }
    }

    /**
     * Obtém o valor de horímetro através da string informada.
     * @param campo string com o valor recebido.
     * @return o valor do horímetro convertido.
     */
    public static BigDecimal obterHorimetro(String campo) {
        String campoTratado = campo.replace(',','.');
        BigDecimal valorGerado = new BigDecimal(campoTratado);
        return valorGerado;
    }

    /**
     * Remove quaisquer caracteres nao-numericos do texto informado
     *
     * @param campo o valor a ser tratado
     * @return O texto contendo apenas caracters numericos
     */
    public static String obterDigitosMascara(Serializable campo) {
        return campo != null ? campo.toString().replaceAll("[^0-9]", "") : "";
    }

    /**
     * Formata um decimal de horas para apresentação na tela
     *
     * @param decimal valor decimal
     * @return decimal formatado em horas
     */
    public static String formatarHorimetro(BigDecimal decimal) {
        return formatarDecimal(decimal, ConstantesFormatacao.FORMATO_HORAS);
    }


    /**
     * Formata um decimal para apresentação na tela com 3 casas decimais
     *
     * @param decimal valor decimal
     * @return decimal formatado
     */
    public static String formatarDecimalComTresCasas(BigDecimal decimal) {
        decimal = decimal != null ? decimal.setScale(3, BigDecimal.ROUND_HALF_UP) : null;
        return formatarDecimal(decimal);
    }

    /**
     * Formata um decimal para apresentação na tela
     *
     * @param decimal valor decimal
     * @return decimal formatado
     */
    public static String formatarDecimal(BigDecimal decimal) {
        String formato = "###,##0.";
        return formatarDecimal(decimal, formato);
    }

    /**
     * Formata uma placa de veiculo para AAA-9999 ou AAA9A99 para placas mercosul
     *
     * @param placa A placa sem formatacao
     * @return A placa formatada
     */
    public static String formatarPlacaVeiculo(String placa) {
        if (placa == null || placa.length() < 7) {
            return null;
        }
        String placaFormatada = placa.replaceAll("[^0-9a-zA-Z]", "");
        placaFormatada = placaFormatada.replaceAll("-","");
        if (placaFormatada.length() > 5 && placaFormatada.substring(4, placaFormatada.length() - 2).matches("^[0-9]*$")) {
            return placaFormatada.substring(0, 3).toUpperCase() + "-" + placaFormatada.substring(3, 7);
        }
        return placaFormatada.substring(0, 7).toUpperCase();
    }

    /**
     * Formata uma placa de veiculo para AAA-9999
     *
     * @param placa A placa sem formatacao
     * @return A placa formatada
     */
    public static String retiraFormatacaoPlacaVeiculo(String placa) {
        if (placa != null) {
            return placa.replaceAll("[^0-9a-zA-Z]", "");
        }
        return null;
    }

    /**
     * Formata a String recebida de acordo com o valor e limite
     * informado
     *
     * @param valor  String sem formatacao
     * @param limite Tamanho maximo da String a ser formatada
     * @return String formatada
     */
    public static String formatarLimiteString(String valor, int limite) {
        if (valor != null) {
            return valor.length() > limite ? valor.substring(0, limite) : valor;
        }
        return null;
    }

    /**
     * Formata um decimal para apresentação na tela de acordo com o formato indicado
     *
     * @param decimal valor decimal
     * @param formato o formato do decimal
     * @return valor formatado
     */
    public static String formatarDecimal(BigDecimal decimal, String formato) {
        if (decimal == null) {
            return null;
        }
        Integer decimais = decimal.scale();
        formato = (formato.split("\\.").length > 1
                ? formato
                : StringUtils.rightPad(formato, formato.length() + (decimais == 0 ? 2 : decimais), '0'));

        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');

        DecimalFormat formatoDecimal = new DecimalFormat(formato, symbols);

        return formatoDecimal.format(decimal.doubleValue());
    }

    /**
     * Formata um decimal para apresentação na tela com prefixo do Real (R$).
     * Se a quantidade de casas decimais do valor fornecido estiver abaixo de 2,
     * modificamos para 2.
     *
     * @param decimal valor decimal
     * @return valor formatado
     */
    public static String formatarDecimalMoedaReal(BigDecimal decimal) {
        if (decimal != null && decimal.scale() < 2) {
            decimal = decimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return formatarDecimal(decimal, ConstantesFormatacao.FORMATO_MOEDA_REAL);
    }

    /**
     * Formata um decimal para apresentação na tela com prefixo do Real (R$)
     * definindo a escala utilizada e arredondamento {@link java.math.BigDecimal#ROUND_HALF_UP}.
     *
     * @param decimal valor decimal
     * @param scale   valor do número de casas decimais
     * @return valor formatado
     */
    public static String formatarDecimalMoedaReal(BigDecimal decimal, int scale) {
        return decimal != null ? formatarDecimalMoedaReal(decimal.setScale(scale, BigDecimal.ROUND_HALF_UP)) : null;
    }

    /**
     * Formata um decimal para apresentação na tela com prefixo do Real (R$)
     * definindo qual escala e o tipo de arredondamento utilizado.
     *
     * @param decimal  valor decimal
     * @param scale    valor do número de casas decimais
     * @param rounding valor do arredondamento utilizado
     * @return valor formatado
     */
    public static String formatarDecimalMoedaReal(BigDecimal decimal, int scale, int rounding) {
        return decimal != null ? formatarDecimalMoedaReal(decimal.setScale(scale, rounding)) : null;
    }

    /**
     * Formata um decimal para apresentação na tela para o formato de porcentagem.
     *
     * @param decimal valor decimal
     * @return valor formatado
     */
    public static String formatarDecimalPercentual(BigDecimal decimal) {
        return formatarDecimal(decimal, ConstantesFormatacao.FORMATO_PERCENTUAL);
    }

    /**
     * Formata um decimal para apresentação na tela para o formato de distancia em kilometros
     *
     * @param decimal valor decimal
     * @return valor formatado
     */
    public static String formatarDecimalKilometros(BigDecimal decimal) {
        return formatarInteiro(decimal, ConstantesFormatacao.FORMATO_KILOMETROS);
    }

    /**
     * Formata um inteiro para apresentação na tela para o formato de distancia em kilometros
     *
     * @param kilometros valor inteiro de kms
     * @return valor formatado
     */
    public static String formatarHodometro(Long kilometros) {
        return formatarInteiro(kilometros, ConstantesFormatacao.FORMATO_KILOMETROS);
    }

    /**
     * Formata um decimal para apresentacao em tela com o formato de Litros
     *
     * @param decimal para formatar
     * @return decimal formatado
     */
    public static String formatarLitros(BigDecimal decimal) {
        return formatarDecimal(decimal, ConstantesFormatacao.FORMATO_LITROS);
    }

    /**
     * Formata um decimal para apresentacao em tela com o formato de Litros
     *
     * @param decimal para formatar
     * @return decimal formatado
     */
    public static String formatarLitrosDuasCasas(BigDecimal decimal) {
        return formatarDecimal(decimal, ConstantesFormatacao.FORMATO_LITROS_DUAS_CASAS);
    }

    /**
     * Formata um valor de hodometro ou horimetr (aquele que nao for nulo)
     *
     * @param hodometro        O valor do hodometro
     * @param horimetro        O valor do horimetro
     * @param comUnidadeMedida Se true, adiciona Km ou H ao final do valor formatado
     * @return O valor formatado
     */
    public static String formataHodometroOuHorimetro(Long hodometro, BigDecimal horimetro, boolean comUnidadeMedida) {
        String valor = null;
        if (hodometro != null) {
            valor = formatarHodometro(hodometro);
        } else if (horimetro != null) {
            valor = formatarHorimetro(horimetro);
        }
        if (valor != null && !comUnidadeMedida) {
            int idx = valor.indexOf(" ");
            if (idx > 0) {
                valor = valor.substring(0, idx);
            }
        }
        return valor;
    }

    /**
     * Formata um valor de hodometro ou horimetro (aquele que nao for nulo)
     *
     * @param hodometro        O valor do hodometro
     * @param horimetro        O valor do horimetro
     * @param comUnidadeMedida Se true, adiciona Km ou H ao final do valor formatado
     * @param utilizaHorimetro Se o veiculo utiliza horimetro
     * @return O valor formatado
     */
    public static String formataHodometroOuHorimetro(Long hodometro, BigDecimal horimetro, Boolean comUnidadeMedida, Boolean utilizaHorimetro) {
        String valor = null;
        if (utilizaHorimetro && horimetro != null) {
            valor = formatarHorimetro(horimetro);

        } else if (!utilizaHorimetro && hodometro != null) {
            valor = formatarHodometro(hodometro);
        }
        if (valor != null && !comUnidadeMedida) {
            int idx = valor.indexOf(" ");
            if (idx > 0) {
                valor = valor.substring(0, idx);
            }
        }
        return valor;
    }

    /**
     * Formata um decimal para apresentação na tela em separação de milhar
     *
     * @param longNumber valor Long
     * @return decimal formatado
     */
    public static String formatarInteiro(Number longNumber) {
        return formatarInteiro(longNumber, ConstantesFormatacao.FORMATO_INTEIRO);
    }

    /**
     * Formata um decimal para apresentação na tela em separação de milhar
     *
     * @param longNumber valor Long
     * @param format     formato da String
     * @return decimal formatado
     */
    private static String formatarInteiro(Number longNumber, String format) {
        if (longNumber == null) {
            return null;
        }

        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat formato = new DecimalFormat(format, symbols);

        return formato.format(longNumber.doubleValue());
    }

    /**
     * Converte valor string para big decimal.
     *
     * @param campo o valor a ser tratado
     * @return Um big decimal
     */
    public static BigDecimal obterDecimalMascara(String campo) {
        String campoTratado = campo != null ? campo.replace(".", "") : "";
        campoTratado = campoTratado.replace(",", ".");
        campoTratado = campoTratado.replaceAll("[^0-9.,\\-]", "");
        return StringUtils.isBlank(campoTratado) ? null : new BigDecimal(campoTratado);
    }

    /**
     * Obtem o valor textual do campo. Caso o valor atual seja nulo, retorna uma string vazia.
     *
     * @param campo O campo desejado
     * @return O valor textual do campo
     */
    public static String obterString(Serializable campo) {
        return obterString(campo, "");
    }

    /**
     * Obtem o valor textual do campo. Caso o valor atual seja nulo, retorna o valor padrão fornecido
     *
     * @param campo O campo desejado
     * @param valorPadrao O valor retornado caso o campo seja nulo.
     * @return O valor textual do campo
     */
    public static String obterString(Serializable campo, String valorPadrao) {
        return campo != null ? campo.toString() : valorPadrao;
    }

    /**
     * Formata um CPF.
     *
     * @param cpf Um objeto contendo o CPF
     * @return O CPF formatado
     */
    public static String formatarCpfApresentacao(Serializable cpf) {
        if(cpf != null) {
            String cpfTratado = formatarNumeroZerosEsquerda(cpf, 11);
            return formatarNumero(cpfTratado, 11, ConstantesFormatacao.FORMATO_CPF);
        }
        return null;
    }

    /**
     * Formata um CNPJ.
     *
     * @param cnpj Um objeto contendo o cnpj
     * @return O CNPJ formatado
     */
    public static String formatarCnpjApresentacao(Serializable cnpj) {
        String cnpjTratado = formatarNumeroZerosEsquerda(cnpj, 14);
        return formatarNumero(cnpjTratado, 14, ConstantesFormatacao.FORMATO_CNPJ);
    }

    /**
     * Formata um CPF para o formato de CPF oculto.
     *
     * @param cpf Um objeto contendo o CPF
     * @return O CPF formatado
     */
    public static String formatarCpfOcultoApresentacao(Serializable cpf) {
        if(cpf != null) {
            String cpfTratado = formatarNumeroZerosEsquerda(cpf, 11);
            return formatarNumero(cpfTratado.substring(8), 3, ConstantesFormatacao.FORMATO_CPF_OCULTO);
        }
        return null;
    }

    /**
     * Formata um CNPJ ou CPF
     *
     * @param valor O valor a ser formatado
     * @return O valor formatado
     */
    public static String formatarCpjCnpjApresentacao(String valor) {
        if (valor == null) {
            return "";
        }
        valor = valor.trim();
        return valor.length() > 11 ?
                UtilitarioFormatacao.formatarCnpjApresentacao(valor) :
                UtilitarioFormatacao.formatarCpfApresentacao(valor);
    }

    /**
     * Formata um CEP.
     *
     * @param cep Um objeto contendo o CEP
     * @return O CEP formatado
     */
    public static String formatarCepApresentacao(Serializable cep) {
        String cepTratado = formatarNumeroZerosEsquerda(cep, 8);
        return formatarNumero(cepTratado, 8, ConstantesFormatacao.FORMATO_CEP);
    }

    /**
     * Formata um numero de telefone
     *
     * @param telefone Um objeto contendo o numero de telefone
     * @return O telefone formatado
     */
    public static String formatarNumeroTelefone(Serializable telefone) {
        if (telefone == null) {
            return null;
        } else if (telefone.toString().length() == 10) {
            return formatarNumero(telefone, 10, ConstantesFormatacao.FORMATO_TELEFONE_DEZ);
        } else if (telefone.toString().length() == 11) {
            return formatarNumero(telefone, 11, ConstantesFormatacao.FORMATO_TELEFONE_ONZE);
        } else {
            return telefone.toString();
        }
    }

    /**
     * Formata um numero com zeros a esquerda
     *
     * @param numero  numero a formatar
     * @param tamanho tamanho esperado do numero
     * @return O telefone formatado
     */
    public static String formatarNumeroZerosEsquerda(Serializable numero, Integer tamanho) {
        if (numero == null || !(numero instanceof String) && !StringUtils.isNumeric(numero.toString())) {
            return null;
        }
        return StringUtils.leftPad(numero instanceof String ? (String) numero : numero.toString(), tamanho, '0');
    }

    /**
     * Formata numero para dado formato
     *
     * @param numero  numero
     * @param tamanho tamanho de formatacao esperado
     * @param formato formato do numero
     * @return numero formatado
     */
    private static String formatarNumero(Serializable numero, Integer tamanho, String formato) {
        if (numero != null) {
            if (numero.toString().length() == tamanho) {
                return format(formato, numero.toString());
            } else {
                return numero.toString();
            }
        }
        return null;
    }

    /**
     * Formata um objeto com o padrao informado
     *
     * @param pattern O padrao desejado
     * @param value   O valor a ser formatado
     * @return O valor formatado
     */
    private static String format(String pattern, Object value) {
        MaskFormatter mask;
        try {
            mask = new MaskFormatter(pattern);
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(value);
        } catch (ParseException e) {
            throw new ExcecaoBoleiaRuntime(e);
        }
    }

    /**
     * Substitui caracteres acentuados de uma string por caracteres simples
     *
     * @param valor A string a ser tratada
     * @return A sstring resultante
     */
    public static String removerAcentos(String valor) {
        return StringUtils.stripAccents(valor);
    }

    /**
     * Formata um texto preenchendo-o com espaços em branco à direita ou à esquerda
     *
     * @param texto    O texto a ser formatado
     * @param tamanho  O tamanho final que se deseja obter
     * @param esquerda Determina se deve ser preenchido com espaços à esquerda, caso contrário será considerado a direita.
     * @return O texto formatado
     */
    public static String preencherComEspacos(String texto, Integer tamanho, Boolean esquerda) {
        return esquerda ? StringUtils.leftPad(texto, tamanho, ' ') : StringUtils.rightPad(texto, tamanho, ' ');
    }

    /**
     * Formata um valor para ser apresentado por extenso
     *
     * @param valor O valor a ser formatado
     * @return O valor formatado
     */
    public static String preencherValorEmExtensoApresentacao(String valor) {
        return ConstantesFormatacao.FORMATO_PREFIXO_VALOR_EXTENSO + valor + ConstantesFormatacao.FORMATO_SUFIXO_VALOR_EXTENSO;
    }

    /**
     * Formata uma chave de acesso.
     *
     * @param chave Um objeto contendo a CHAVE DE ACESSO
     * @return A Chave de Acesso formatada
     */
    public static String formatarChaveAcesso(Serializable chave) {
        String chaveAcessoTratada = formatarNumeroZerosEsquerda(chave, 44);
        return formatarNumero(chaveAcessoTratada, 44, ConstantesFormatacao.FORMATO_CHAVE_ACESSO);
    }

    /**
     * Formata um valor numerico para string e caso o valor seja nulo retorna 0
     *
     * @param valor O valor que sera formatado
     * @return Valor formatado ou zero
     */
    public static String formatarValorNumericoString(Serializable valor) {
        return valor != null ? valor.toString() : formatarDecimalMoedaReal(BigDecimal.ZERO);
    }

    /**
     * Formata Número da Nota DANFe.
     *
     * @param nota Um objeto contendo o Número da Nota DANFe
     * @return O número da nota DANFe formatado
     */
    public static String formatarNumeroNota(Serializable nota) {
        String numeroNotaTratado = formatarNumeroZerosEsquerda(nota, 9);
        return formatarNumero(numeroNotaTratado, 9, ConstantesFormatacao.FORMATO_NUMERO_NOTA);
    }

    /**
     * Formata Série da Nota.
     *
     * @param serie Um objeto contendo a série da nota DANFe
     * @return A série da nota DANFe formatada
     */
    public static String formatarSerieNota(Serializable serie) {
        String serieNotaTratado = formatarNumeroZerosEsquerda(serie, 3);
        return formatarNumero(serieNotaTratado, 3, ConstantesFormatacao.FORMATO_SERIE_NOTA_FISCAL);
    }

    /**
     * Formata um Cnpj raíz
     *
     * @param cnpj O Cnpj que sera formatado
     * @return Cnpj formatado com os 8 primeiros números (raíz)
     */
    public static String formatarCnpjRaizApresentacao(Serializable cnpj) {
        String cnpjRaizTratado = formatarNumeroZerosEsquerda(cnpj, 14);
        return cnpjRaizTratado.substring(0, 8);
    }

    /**
     * Obtem apenas os numeros de uma string formatada, ignorando os demais caracteres adicionais
     * @param numeracaoFormatado string formatada
     * @return string contendo apenas numeros
     */
    public static String obterNumeracaoSemFormatacao(String numeracaoFormatado) {

        if (numeracaoFormatado != null) {
            return numeracaoFormatado.replaceAll("[^0-9]", "").trim();
        }
        return null;
    }

    /**
     * Obtem o cnpj formatado para Long
     * @param cnpj o cnpj em String
     * @return cnpj o cnpj em Long
     */
    public static Long obtemCnpjNumerico(String cnpj){
        return Long.parseLong(obterNumeracaoSemFormatacao(cnpj));
    }

    /**
     * Concatena uma lista de Strings para formar uma chave de uma mensagem do sistema
     *
     * @param items A lista de strings a ser concatenada
     * @return A string que representa uma chave de uma mensagem do sistema
     */
    public static String formatarChaveMensagem(String... items) {
        final String SEPARADOR = ".";
        return String.join(SEPARADOR, Arrays.stream(items).map(i -> i.toLowerCase()).collect(Collectors.toList()));
    }

    /**
     * Concatena uma lista de Strings para formar uma chave de um enum do sistema
     *
     * @param items A lista de strings a ser concatenada
     * @return A string que representa uma chave de um enum do sistema
     */
    public static String formatarChaveEnum(String... items) {
        final String SEPARADOR = "_";
        return String.join(SEPARADOR, Arrays.stream(items).map(i -> i.toUpperCase()).collect(Collectors.toList()));
    }
}
