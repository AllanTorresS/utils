package ipp.aci.boleia.util;


import ipp.aci.boleia.util.i18n.Mensagens;
import org.apache.commons.lang3.StringUtils;

/**
 * Utilitário para transformar um valor monetario no seu correspondente em extenso.
 *
 */
public final class UtilitarioValorMonetarioPorExtenso {


    /**
     * Transforma um valor monetario no seu correspondente em extenso
     * @param mensagens tradutor de mensagem
     * @param valor valor a ser transformado
     * @return valor em extenso
     */
    public static String valorExtenso(Mensagens mensagens, String valor){

        String[] unidade = ValorUnidadeMonetaria.obterArrayNomes(mensagens);
        String[] dezes = ValorDezesMonetaria.obterArrayNomes(mensagens);
        String[] dezenas = ValorDezenasMonetaria.obterArrayNomes(mensagens);
        String[] centenas = ValorCentenasMonetaria.obterArrayNomes(mensagens);
        String[] plural = ValorPluralMonetario.obterArrayNomes(mensagens);
        String[] singular = ValorSingularMonetario.obterArrayNomes(mensagens);

        String extenso = "";
        valor = valor.replace(mensagens.obterMensagem(ValorMoedaMonetario.SIGLA_REAL.getNome()),"");
        String numero = valor.replace(",","").trim();
        numero = StringUtils.leftPad(numero.replace(".",""), 14, "0");
        extenso = formatarReais(mensagens, unidade, dezes, dezenas, centenas, plural, singular, extenso, numero);
        extenso = formatarCentavos(mensagens, unidade, dezes, dezenas, extenso, numero);

        return extenso.substring(3,4)  + extenso.substring(4);
    }

    /**
     * Formata a parte do número de reais e o escreve por extenso
     *
     * @param mensagens obtem a mensagens
     * @param unidade campo de unidade do numero
     * @param dezes campo de dezes do numero
     * @param dezenas campo de dezenas do numero
     * @param extenso valor extenso do numero
     * @param plural valores plurais do numero
     * @param singular valores singulares do numero
     * @param numero numero que sera transformado em extenso
     *
     * @return O valor por extenso
     */
    private static String formatarReais(Mensagens mensagens, String[] unidade, String[] dezes, String[] dezenas, String[] centenas, String[] plural, String[] singular, String extenso, String numero) {
        String fracao;
        if(Double.parseDouble(numero.substring(0,12)) > 0){
            for(int i = 0; i < 4; i++){
                int grupo = i*3;
                fracao = numero.substring(grupo,grupo + 3);
                extenso = StringUtils.isNotEmpty(fracao) && Integer.parseInt(fracao) != 0 ?
                        formatarGrupoDigitos(mensagens, unidade, dezes, dezenas, centenas, plural, singular, extenso, fracao, i) : extenso;
            }
            if(Double.parseDouble(numero.substring(0,12)) > 1) {
                extenso += ValorMoedaMonetario.REAIS.getNomeEspecial(mensagens);
            }else {
                extenso += ValorMoedaMonetario.REAL.getNomeEspecial(mensagens);
            }
        }
        return extenso;
    }

    /**
     * Formata os reais do valor @param numero
     *
     * @param mensagens obtem a mensagens
     * @param unidade campo de unidade do valor
     * @param dezes campo de dezes do valor
     * @param dezenas campo de dezenas do valor
     * @param centenas campo de centenas do valor
     * @param plural campo de valores no plural do valor
     * @param singular campo de valores no singular do valor
     * @param extenso valor extenso do numero
     * @param fracao grupo do valor que sera formatado
     * @param grupo grupo correspondente ao trecho do valor
     *
     * @return o valor em extenso do grupo formatado
     */
    private static String formatarGrupoDigitos(Mensagens mensagens, String[] unidade, String[] dezes, String[] dezenas, String[] centenas, String[] plural, String[] singular, String extenso, String fracao, int grupo) {
        if(Integer.parseInt(fracao.substring(0,2)) == 100) {
            extenso += ValorSingularMonetario.CEM.getNomeCemEspecial(mensagens);
        } else{
            extenso += centenas[Integer.parseInt(fracao.substring(0,1))];
            if(Integer.parseInt(fracao.substring(1,3)) > 10 &&Integer.parseInt(fracao.substring(1,3)) < 20) {
                extenso += dezes[Integer.parseInt(fracao.substring(2,3))];
            } else{
                extenso += dezenas[Integer.parseInt(fracao.substring(1,2))];
                extenso += unidade[Integer.parseInt(fracao.substring(2,3))];
            }
        }
        if(Integer.parseInt(fracao) > 1) {
            extenso += plural[grupo];
        } else {
            extenso += singular[grupo];
        }
        return extenso;
    }

    /**
     * Formata os centavos do valor e escreve este trecho em extenso
     *
     * @param mensagens obtem a mensagens
     * @param unidade campo de unidade do numero
     * @param dezes campo de dezes do numero
     * @param dezenas campo de dezenas do numero
     * @param extenso valor extenso do numero
     * @param numero numero que sera transformado em extenso
     *
     * @return Centavos formatados em extenso
     */
    private static String formatarCentavos(Mensagens mensagens, String[] unidade, String[] dezes, String[] dezenas, String extenso, String numero) {
        String fracao;
        fracao = numero.substring(12,14);
        if(Integer.parseInt(fracao) > 0){
            if(Integer.parseInt(fracao.substring(0,2)) > 10 && Integer.parseInt(fracao.substring(0,2)) < 20) extenso = extenso  + dezes[Integer.parseInt(fracao.substring(1,2))];
			else{
                extenso += dezenas[Integer.parseInt(fracao.substring(0,1))];
                extenso += unidade[Integer.parseInt(fracao.substring(1,2))];
            }
            if(Integer.parseInt(fracao) > 1) {
			    extenso += ValorMoedaMonetario.CENTAVOS.getNomeEspecial(mensagens);
            } else {
			    extenso += ValorMoedaMonetario.CENTAVO.getNomeEspecial(mensagens);
            }
        }
        return extenso;
    }
}

