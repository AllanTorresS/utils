package ipp.aci.boleia.util;

import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 * Utilitario para formatacao de datas
 */
public final class UtilitarioFormatacaoData {

    /**
     * Impede a instanciacao e a heranca
     */
    private UtilitarioFormatacaoData() {
        // Impede a instanciacao e a heranca
    }

    /**
     * Formata uma data com o padrao PADRAO_DATA_CURTA
     *
     * @param data a data desejada
     * @return A data formatada
     */
    public static String formatarDataCurta(Date data) {
        return data == null ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_DATA_CURTA).format(data);
    }

    /**
     * Formata uma data com o padrao PADRAO_DATA_CURTA_HIFEN
     *
     * @param data a data desejada
     * @return A data formatada
     */
    public static String formatarDataCurtaHifen(Date data) {
        return data == null ? null : new SimpleDateFormat(ConstantesFormatacao.PADRAO_DATA_CURTA_HIFEN).format(data);
    }

    /**
     * Formata uma data com o padrao PADRAO_DATA_CURTA
     *
     * @param data a data desejada
     * @return A data formatada
     */
    public static String formatarDataCurtaAnoCurto(Date data) {
        return data == null ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_DATA_CURTA_ANO_CURTO).format(data);
    }

    /**
     * Formata da com o padrão PADRAO_DATA_CURTA
     * 
     * @param calendar a data desejada
     * @return A data formatada 
     */
    public static String formatarDataCurta(XMLGregorianCalendar calendar) {
        if(calendar == null) {
            return null;
        }
        calendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
        Date data = calendar.toGregorianCalendar().getTime();
        return formatarDataCurta(data);
    }

    /**
     * Formata uma data com o padrao PADRAO_DATA_HORA
     *
     * @param data a data desejada
     * @return A data formatada
     */
    public static String formatarDataHora(Date data) {
        return data == null ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_DATA_HORA).format(data);
    }

    /**
     * Formata uma data com o padrao PADRAO_DATA_CURTA_HORA
     *
     * @param data a data desejada
     * @return A data formatada
     */
    public static String formatarDataCurtaHora(Date data) {
        return data == null ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_DATA_CURTA_HORA).format(data);
    }

    /**
     * Formata uma data com o padrao PADRAO_HORA_MINUTOS_SEGUNDOS
     *
     * @param data a data desejada
     * @return A data formatada
     */
    public static String formatarHoraMinutosSegundos(Date data) {
        return data == null ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_HORA_MINUTOS_SEGUNDOS).format(data);
    }

    /**
     * Formata uma data com o padrao PADRAO_DATA_HORA_MINUTOS_SEGUNDOS
     *
     * @param data a data desejada
     * @return A data formatada
     */
    public static String formatarDataHoraMinutosSegundos(Date data) {
        return data == null ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_DATA_HORA_MINUTOS_SEGUNDOS).format(data);
    }

    /**
     * Formata uma data com o padrao PADRAO_HORA_MINUTOS
     *
     * @param data a data desejada
     * @return A data formatada
     */
    public static String formatarHoraMinutos(Date data) {
        return data == null ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_HORA_MINUTOS).format(data);
    }

    /**
     * Formata uma valor inteiro em minutos para o padrao PADRAO_HORA_MINUTOS
     *
     * @param minutos O numero de minutos
     * @return A hora/minutos formatados
     */
    public static String formatarHoraMinutos(Integer minutos) {
        if(minutos != null) {
            int min = minutos % 60;
            int hor = minutos / 60;
            String padMin = min < 10 ? "0" : "";
            String padHor = hor < 10 ? "0" : "";
            return padHor + hor + ":" + padMin + min;
        }
        return null;
    }

    /**
     * Le uma data a partir de uma String com o formato PADRAO_DATA_JSON
     *
     * @param texto O texto a ser interpretado
     * @return A data obtida
     */
    public static Date lerDataJson(String texto) {
        try {
            return StringUtils.isEmpty(texto) ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_DATA_JSON).parse(texto);
        } catch (ParseException e) {
            throw new ExcecaoBoleiaRuntime(Erro.CONVERSAO_DATA, e);
        }
    }

    /**
     * Formata uma data com o padrao PADRAO_DATA_JSON
     *
     * @param data a data desejada
     * @return A data formatada
     */
    public static String formatarDataJSON(Date data) {
        return data == null ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_DATA_JSON).format(data);
    }

    /**
     * Le uma data a partir de uma String com o formato PADRAO_ISO_8601
     *
     * @param texto O texto a ser interpretado
     * @return A data obtida
     */
    public static Date lerDataIso8601(String texto) {
        try {
            return StringUtils.isEmpty(texto) ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_ISO_8601).parse(texto);
        } catch (ParseException e) {
            try {
                return StringUtils.isEmpty(texto) ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_ISO_8601_COM_MILLIS).parse(texto);
            } catch (ParseException e1) {
                throw new ExcecaoBoleiaRuntime(Erro.CONVERSAO_DATA, e1);
            }
        }
    }

    /**
     * Le uma data a partir de uma String com o formato PADRAO_ISO_8601 sem Timezone
     *
     * @param texto O texto a ser interpretado
     * @return A data obtida
     */
    public static Date lerDataIso8601SemTimezone(String texto) {
        try {
            return StringUtils.isEmpty(texto) ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_ISO_8601_COM_MILLIS_SEM_TIMEZONE).parse(texto);
        } catch (ParseException e) {
            try {
                return StringUtils.isEmpty(texto) ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_ISO_8601_SEM_TIMEZONE).parse(texto);
            } catch (ParseException e1) {
                throw new ExcecaoBoleiaRuntime(Erro.CONVERSAO_DATA, e1);
            }
        }
    }

    /**
     * Formata uma data para uma String com o formato PADRAO_ISO_8601
     *
     * @param data A data a ser formatada
     * @return A data formatada no formato PADRAO_ISO_8601
     */
    public static String formatarDataIso8601SemTimezone(Date data) {
        return data == null ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_ISO_8601_SEM_TIMEZONE).format(data);
    }

    /**
     * Formata uma data para uma String com o formato PADRAO_ISO_8601 com millis e timezone
     *
     * @param data A data a ser formatada
     * @return A data formatada no formato PADRAO_ISO_8601 com millis e timezone
     */
    public static String formatarDataIso8601ComTimeZoneMillis(Date data) {
        return data == null ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_ISO_8601_COM_MILLIS_E_TIMEZONE).format(data);
    }

    /**
     * Le uma data a partir de uma String o formato PADRAO_DATA_CURTA
     *
     * @param texto O texto a ser interpretado
     * @return A data obtida
     */
    public static Date lerDataCurta(String texto) {
        try {
            return StringUtils.isEmpty(texto) ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_DATA_CURTA).parse(texto);
        } catch (ParseException e) {
            throw new ExcecaoBoleiaRuntime(Erro.CONVERSAO_DATA, e);
        }
    }

    /**
     * Le uma data a partir de uma String o formato PADRAO_MES_ANO
     *
     * @param texto O texto a ser interpretado
     * @return A data obtida
     */
    public static Date lerDataMesAno(String texto) {
        try {
            return StringUtils.isEmpty(texto) ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_MES_ANO).parse(texto);
        } catch (ParseException e) {
            throw new ExcecaoBoleiaRuntime(Erro.CONVERSAO_DATA, e);
        }
    }

    /**
     * Le uma data a partir de uma String o formato PADRAO_DATA_HORA
     *
     * @param texto O texto a ser interpretado
     * @return A data obtida
     */
    public static Date lerDataHora(String texto) {
        try {
            return StringUtils.isEmpty(texto) ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_DATA_HORA).parse(texto);
        } catch (ParseException e) {
            throw new ExcecaoBoleiaRuntime(Erro.CONVERSAO_DATA, e);
        }
    }

    /**
     * Le uma string data/hora no formato PADRAO_HORA_MINUTOS retornando o valor total em minutos representado por ela.
     * Exemplo: 01:30 retorna 90.
     *
     * @param texto O texto a ser interpretado
     * @return O numero de minutos representado
     */
    public static Integer lerHoraMinutosEmMinutos(String texto) {
        try {
            Integer minutos = null;
            if(!StringUtils.isEmpty(texto)) {
                Date valor = new SimpleDateFormat(ConstantesFormatacao.FORMATO_HORA_MINUTOS).parse(texto);
                Date zero = new SimpleDateFormat(ConstantesFormatacao.FORMATO_HORA_MINUTOS).parse("00:00");
                minutos = (int) (valor.getTime() - zero.getTime()) / 1000 / 60;
            }
            return minutos;
        } catch (ParseException e) {
            throw new ExcecaoBoleiaRuntime(Erro.CONVERSAO_DATA, e);
        }
    }

    /**
     * Le uma string hora no formato PADRAO_HORA_MINUTOS retornando o valor total da hora.
     * Exemplo: 01:30 retorna 1.
     *
     * @param texto O texto a ser interpretado
     * @return O valor da hora
     */
    public static Integer lerCampoHora(String texto) {
        Integer hora = null;
        Pattern pattern = Pattern.compile("((?:(?:0|1)\\d|2[0-3])):([0-5]\\d)");
        if(!StringUtils.isEmpty(texto) && pattern.matcher(texto).matches()) {
            String[] horaMinuto = texto.split(":");
            hora = Integer.parseInt(horaMinuto[0]);
        }
        return hora;
    }

    /**
     * Le uma string hora no formato PADRAO_HORA_MINUTOS retornando o valor total do minuto.
     * Exemplo: 01:30 retorna 30.
     *
     * @param texto O texto a ser interpretado
     * @return O valor dos minutos
     */
    public static Integer lerCampoMinutos(String texto) {
        Integer minutos = null;
        Pattern pattern = Pattern.compile("((?:(?:0|1)\\d|2[0-3])):([0-5]\\d)");
        if(!StringUtils.isEmpty(texto) && pattern.matcher(texto).matches()) {
            String[] horaMinuto = texto.split(":");
            minutos = Integer.parseInt(horaMinuto[1]);
        }
        return minutos;
    }

    /**
     * Le uma data a partir de uma String o formato FORMATO_DATA_HORA_MINUTOS_SEGUNDOS
     *
     * @param texto O texto a ser interpretado
     * @return A data obtida
     */
    public static Date lerDataHoraMinutosSegundos(String texto) {
        try {
            return StringUtils.isEmpty(texto) ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_DATA_HORA_MINUTOS_SEGUNDOS).parse(texto);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Obtem uma data formatada de maneira amigavel, apresentando contexto semanal(DOM-SAB) e diario(Hoje-Ontem)
     *
     * @param dataAtual Data atual
     * @param dataAFormatar Data a formatar
     * @param horas Se mostra as horas na data
     * @param diaSemana Se exibe o dia da semana
     * @return Data formatada amigavel
     */
    public static String formatarDataAmigavel(Date dataAtual, Date dataAFormatar, Boolean horas, Boolean diaSemana) {
        if (dataAtual == null || dataAFormatar == null) {
            return null;
        }
        if (UtilitarioCalculoData.isHoje(dataAtual, dataAFormatar)) {
            return formatarDiaSemanaHoras(DiaSemana.HOJE, dataAFormatar, horas);
        }
        if (UtilitarioCalculoData.isOntem(dataAtual, dataAFormatar)) {
            return formatarDiaSemanaHoras(DiaSemana.ONTEM, dataAFormatar, horas);
        }

        Calendar dataAtualCalendar = new GregorianCalendar();
        dataAtualCalendar.setTime(dataAtual);

        Calendar dataFormatadaCalendar = new GregorianCalendar();
        dataFormatadaCalendar.setTime(dataAFormatar);

        Integer diaFormatadoSemana = dataFormatadaCalendar.get(Calendar.DAY_OF_WEEK);

        if (diaSemana && dataAtualCalendar.get(Calendar.YEAR) == dataFormatadaCalendar.get(Calendar.YEAR) &&
                dataAtualCalendar.get(Calendar.WEEK_OF_YEAR) == dataFormatadaCalendar.get(Calendar.WEEK_OF_YEAR)) {
            return formatarDiaSemanaHoras(DiaSemana.obterPorDia(diaFormatadoSemana), dataAFormatar, horas);
        }

        String dia = "";
        if (diaSemana) {
            dia = DiaSemana.obterNomePorDia(diaFormatadoSemana) + ", ";
        }
        if (horas) {
            dia += new SimpleDateFormat(ConstantesFormatacao.FORMATO_DATA_HORA).format(dataAFormatar);
        } else {
            dia += new SimpleDateFormat(ConstantesFormatacao.FORMATO_DATA_CURTA).format(dataAFormatar);
        }
        return dia;
    }

    private static String formatarDiaSemanaHoras(DiaSemana diaSemana, Date data, Boolean horas) {
        String dia = diaSemana.getNome();
        if (horas) {
            dia += ", " + new SimpleDateFormat(ConstantesFormatacao.FORMATO_HORA_MINUTOS).format(data);
        }
        return dia;
    }

    /**
     * Formata um período de dias entre duas datas
     *
     * @param dataInicio Data de início do período
     * @param dataFim Data de fim do período
     * @param abreviacao A opcao de abreviar ou nao o nome do mes
     * @return Período formatado
     */
    public static String formatarPeriodoDiasMes(Date dataInicio, Date dataFim, boolean abreviacao) {
        return formatarPeriodoDias(dataInicio, dataFim, abreviacao, " ");
    }

    /**
     * Formata um período de dias entre duas datas
     *
     * @param dataInicio Data de início do período
     * @param dataFim Data de fim do período
     * @param abreviacao A opcao de abreviar ou nao o nome do mes
     * @return Período formatado
     */
    public static String formatarPeriodoDias(Date dataInicio, Date dataFim, boolean abreviacao) {
        return formatarPeriodoDias(dataInicio, dataFim, abreviacao, " ");
    }

    /**
     * Formata um período entre duas datas
     * @param dataInicio Data de início do período
     * @param dataFim Data de fim do período
     * @param abreviacao A opcao de abreviar ou nao o nome do mes
     * @param separador O separador entre as datas
     * @return Periodo formatado
     */
    public static String formatarPeriodoDias(Date dataInicio, Date dataFim, boolean abreviacao, String separador) {
        SimpleDateFormat sdf = new SimpleDateFormat(ConstantesFormatacao.FORMATO_DIA);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(dataInicio);
        return sdf.format(dataInicio) + "-" + sdf.format(dataFim) + separador + (abreviacao ? MesAno.obterPorMes(calendar.get(Calendar.MONTH)).getAbreviacao() : MesAno.obterPorMes(calendar.get(Calendar.MONTH)).getNome());
    }

    /**
     * Formata um período de dias entre duas datas
     *
     * @param dataInicio Data de início do período
     * @param dataFim Data de fim do período
     * @return Período formatado
     */
    public static String formatarPeriodoDias(Date dataInicio, Date dataFim) {
        SimpleDateFormat sdf = new SimpleDateFormat(ConstantesFormatacao.FORMATO_DIA);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(dataInicio);
        return sdf.format(dataInicio) + "-" + sdf.format(dataFim);
    }

    /**
     * Formata uma data com o padrao DIA_MES
     *
     * @param data a data desejada
     * @return A data formatada
     */
    public static String formatarDataDiaMes(Date data) {
        return data == null ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_DIA_MES).format(data);
    }

    /**
     * Formata uma data com o padrao DIA_MES_HORA_MINUTO
     *
     * @param data a data desejada
     * @return A data formatada
     */
    public static String formatarDataDiaMesHoraMinuto(Date data) {
        return data == null ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_DIA_MES_HORA_MINUTO).format(data);
    }

    /**
     * Formata uma data com o PADRAO_MES
     *
     * @param data a data desejada
     * @return A data formatada
     */
    public static String formatarDataMes(Date data) {
        return data == null ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_MES).format(data);
    }

    /**
     * Formata uma data com o PADRAO_MES_ANO
     *
     * @param data a data desejada
     * @return A data formatada
     */
    public static String formatarDataMesAno(Date data) {
        return data == null ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_MES_ANO).format(data);
    }

    /**
     * Formata uma data com o PADRAO_MES_ANO_ISO
     *
     * @param data a data desejada
     * @return A data formatada
     */
    public static String formatarDataMesAnoIso(YearMonth data) {
        return data == null ? null : data.format(
            new DateTimeFormatterBuilder()
                    .appendPattern(ConstantesFormatacao.FORMATO_MES_ANO_ISO)
                .toFormatter()
        );
    }

    /**
     * Formata uma data com o PADRAO_ANO
     *
     * @param data a data desejada
     * @return A data formatada
     */
    public static String formatarDataAno(Date data) {
        return data == null ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_ANO).format(data);
    }

    /**
     * Formata uma data para o tipo XMLGregorianCalendar
     *
     * @param data a data desejada
     * @return A data formatada
     */
    public static XMLGregorianCalendar formatarDataGregorian(Date data) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        } catch (DatatypeConfigurationException e) {
            throw new ExcecaoBoleiaRuntime(Erro.CONVERSAO_DATA, e);
        }
    }

    /**
     * Ler uma data do tipo XMLGregorianCalendar
     * @param dataGregorian Data do tipo gregoriana
     * @return A data
     */
    public static Date lerDataGregorian(XMLGregorianCalendar dataGregorian) {
        if(dataGregorian == null) {
            return null;
        }
        return dataGregorian.toGregorianCalendar().getTime();
    }

    /**
     * Formata data e hora da DANFe
     *
     * @param texto com a data não formatada
     * @param padrao DATA_CURTA ou HORA_MINUTOS_SEGUNDOS
     * @return A data formatada
     */
    public static String formatarDataHoraDanfe(String texto, String padrao) {
        Date data = lerDataIso8601(texto);
        if (padrao.equals(ConstantesFormatacao.FORMATO_DATA_CURTA)) {
            return formatarDataCurta(data);
        } else if (padrao.equals(ConstantesFormatacao.FORMATO_HORA_MINUTOS_SEGUNDOS)) {
            return formatarHoraMinutosSegundos(data);
        } else if (padrao.equals(ConstantesFormatacao.FORMATO_DATA_HORA_MINUTOS_SEGUNDOS)) {
            return formatarDataHoraMinutosSegundos(data);
        }
        return null;
    }

    /**
     * Formata uma data com o PADRAO_DATA_HORA_MINUTOS_SEGUNDOS para uma DANFe
     *
     * @param data a data desejada
     * @return A data formatada
     */
    public static String formatarDataHoraImpressao(Date data) {
        return data == null ? null : new SimpleDateFormat(ConstantesFormatacao.FORMATO_DATA_HORA_MINUTOS_SEGUNDOS).format(data);
    }

    /**
     * Formata um período (dentro de um mês) na forma DD-DD/MM
     * @param dataInicio a data de inicio
     * @param dataFim a data de fim
     * @return a data formatada
     */
    public static String formatarDataPeriodoMes(Date dataInicio, Date dataFim) {
        SimpleDateFormat padraoDia = new SimpleDateFormat(ConstantesFormatacao.FORMATO_DIA);
        SimpleDateFormat padraoDiaMes = new SimpleDateFormat(ConstantesFormatacao.FORMATO_DIA_MES);
        return padraoDia.format(dataInicio) + "-" + padraoDiaMes.format(dataFim);
    }

    /**
     * Formata um período na forma dd/mm/yyyy - dd/mm/yyyy
     * @param dataInicio a data de inicio
     * @param dataFim a data de fim
     * @return a data formatada
     */
    public static String formatarDataPeriodoDataCurta(Date dataInicio, Date dataFim){
        SimpleDateFormat sdf = new SimpleDateFormat(ConstantesFormatacao.FORMATO_DATA_CURTA);
        return sdf.format(dataInicio) + " - " + sdf.format(dataFim);
    }
}
