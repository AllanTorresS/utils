package ipp.aci.boleia.util;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Dias da semana para formatacao de data
 */
public enum DiaSemana {

    ONTEM    (-1,                 "Ontem"),
    HOJE     (0,                  "Hoje"),

    DOMINGO  (Calendar.SUNDAY,    "Domingo"),
    SEGUNDA  (Calendar.MONDAY,    "Segunda"),
    TERCA    (Calendar.TUESDAY,   "Terça"),
    QUARTA   (Calendar.WEDNESDAY, "Quarta"),
    QUINTA   (Calendar.THURSDAY,  "Quinta"),
    SEXTA    (Calendar.FRIDAY,    "Sexta"),
    SABADO   (Calendar.SATURDAY,  "Sábado");


    private final Integer dia;
    private final String nome;

    DiaSemana(Integer dia, String nome) {
        this.dia = dia;
        this.nome = nome;
    }

    public Integer getDia() {
        return dia;
    }

    public String getNome() {
        return nome;
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
}
