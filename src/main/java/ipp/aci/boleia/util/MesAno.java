package ipp.aci.boleia.util;

import java.util.Calendar;

/**
 * Meses do ano para formatacao de data
 */
public enum MesAno {

    JANEIRO   (Calendar.JANUARY,   "Janeiro", "JAN"),
    FEVEREIRO (Calendar.FEBRUARY,  "Fevereiro", "FEV"),
    MARCO     (Calendar.MARCH,     "Março", "MAR"),
    ABRIL     (Calendar.APRIL,     "Abril", "ABR"),
    MAIO      (Calendar.MAY,       "Maio", "MAI"),
    JUNHO     (Calendar.JUNE,      "Junho", "JUN"),
    JULHO     (Calendar.JULY,      "Julho", "JUL"),
    AGOSTO    (Calendar.AUGUST,    "Agosto", "AGO"),
    SETEMBRO  (Calendar.SEPTEMBER, "Setembro", "SET"),
    OUTUBRO   (Calendar.OCTOBER,   "Outubro", "OUT"),
    NOVEMBRO  (Calendar.NOVEMBER,  "Novembro", "NOV"),
    DEZEMBRO  (Calendar.DECEMBER,  "Dezembro", "DEZ");

    private final Integer mes;
    private final String nome;
    private final String abreviacao;

    MesAno(Integer mes, String nome, String abreviacao) {
        this.mes = mes;
        this.nome = nome;
        this.abreviacao = abreviacao;
    }

    public Integer getMes() {
        return mes;
    }

    public String getNome() {
        return nome;
    }

    public String getAbreviacao() {
        return abreviacao;
    }

    /**
     * Obtém o mês do ano pelo identificador do mês
     *
     * @param mes mês
     * @return Mês do ano
     */
    public static MesAno obterPorMes(Integer mes) {
        for (MesAno mesAno : values()) {
            if (mesAno.getMes().equals(mes)) {
                return mesAno;
            }
        }
        return null;
    }

    /**
     * Obtem o nome do mes da semana pelo numero do mes
     *
     * @param mes numero do mes
     * @return nome do mes
     */
    public static String obterNomePorMes(Integer mes) {
        MesAno mesAno = obterPorMes(mes);
        return mesAno != null ? mesAno.getNome() : null;
    }
}
