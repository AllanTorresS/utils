package ipp.aci.boleia.util;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Dias da semana para formatacao de data
 */
public enum DiaSemana {

    ONTEM    (-1,             "Ontem",   "Ontem"),
    HOJE     (0,              "Hoje",    "Hoje"),

    DOMINGO  (Calendar.SUNDAY,    "Domingo", "Domingo"),
    SEGUNDA  (Calendar.MONDAY,    "Segunda", "Segunda-feira"),
    TERCA    (Calendar.TUESDAY,   "Terça",   "Terça-feira"),
    QUARTA   (Calendar.WEDNESDAY, "Quarta",  "Quarta-feira"),
    QUINTA   (Calendar.THURSDAY,  "Quinta",  "Quinta-feira"),
    SEXTA    (Calendar.FRIDAY,    "Sexta",   "Sexta-feira"),
    SABADO   (Calendar.SATURDAY,  "Sábado",  "Sábado");


    private final Integer dia;
    private final String nome;
    private final String nomeCompleto;

    DiaSemana(Integer dia, String nome, String nomeCompleto) {
        this.dia = dia;
        this.nome = nome;
        this.nomeCompleto = nomeCompleto;
    }

    public Integer getDia() {
        return dia;
    }

    public String getNome() {
        return nome;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    /**
     * Obtem o dia da semana pelo dia
     *
     * @param dia Dia
     * @return Dia da Semana
     */
    public static DiaSemana obterPorDia(Integer dia) {
        for (DiaSemana diaSemana : values()) {
            if (diaSemana.getDia().equals(dia)) {
                return diaSemana;
            }
        }
        return null;
    }

    /**
     * Obtem o nome do dia da semana pelo numero do dia
     *
     * @param dia numero do dia
     * @return nome do dia
     */
    public static String obterNomePorDia(Integer dia) {
        DiaSemana diaSemana = obterPorDia(dia);
        return diaSemana != null ? diaSemana.getNome() : null;
    }

    /**
     * Obtem o nome completo do dia da semana pelo numero do dia
     *
     * @param dia numero do dia
     * @return nome completo do dia
     */
    public static String obterNomeCompletoPorDia(Integer dia) {
        DiaSemana diaSemana = obterPorDia(dia);
        return diaSemana != null ? diaSemana.getNomeCompleto() : null;
    }

    /**
     * Obtem o dia de semana de uma data
     * @param data a obter
     * @return dia semana
     */
    public static DiaSemana obterPorData(Date data) {
        Integer dia = UtilitarioCalculoData.obterCampoData(data, Calendar.DAY_OF_WEEK);
        return obterPorDia(dia);
    }

    /**
     * Retorna a lista de dias da semana, desconsiderando os
     * dias relativos (hoje e ontem).
     *
     * @return A lista de dias de domingo a segunda.
     */
    public static List<String> diasAbsolutos() {
        return Arrays.asList(DOMINGO.name(), SEGUNDA.name(), TERCA.name(), QUARTA.name(), QUINTA.name(), SEXTA.name(), SABADO.name());
    }

    /**
     * Retorna a lista de dias da semana com nome completo, desconsiderando os
     * dias relativos (hoje e ontem).
     *
     * @return A lista de dias de domingo a sábado.
     */
    public static List<String> diasAbsolutosComNomeCompleto() {
        return Arrays.asList(DOMINGO.getNomeCompleto(), SEGUNDA.getNomeCompleto(), TERCA.getNomeCompleto(), QUARTA.getNomeCompleto(), QUINTA.getNomeCompleto(), SEXTA.getNomeCompleto(), SABADO.getNomeCompleto());
    }
}
