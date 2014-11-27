package br.gov.presidencia.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DataUtil
{
  public static final String DIA_FORMAT = "dd";
  public static final String MES_FORMAT = "MM";
  public static final String ANO_FORMAT = "yy";
  public static final String DATE_FORMAT = "dd/MM/yy";
  public static final String TIME_FORMAT = "HH:mm";
  public static final String HOUR_MINUTE_SECOND_FORMAT = "HH:mm:ss";
  public static final String DATE_TIME_FORMAT = "dd/MM/yy HH:mm";
  public static final String DATE_HOUR_MINUTE_SECOND_FORMAT = "dd/MM/yy HH:mm:ss";
  public static final String DATE_TIME_NDS_FORMAT = "yyyyMMddhhmmss";

  public static java.util.Date converterStringParaDate(String date)
    throws Exception
  {
    if ((date != null) && (date != "")) {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
      return sdf.parse(date);
    }
    return null;
  }

  public static String converterDateParaString(java.util.Date date) {
    if (date != null) {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
      return sdf.format(date);
    }
    return "";
  }

  public static String converterDateParaStringDataHora(java.util.Date date) throws Exception
  {
    if (date != null) {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
      return sdf.format(date);
    }
    return "";
  }

  public static String converterDateParaStringDataHoraMinutosSegundos(java.util.Date date) throws Exception
  {
    if (date != null) {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
      return sdf.format(date);
    }
    return "";
  }

  public static java.util.Date converterStringParaDateHora(String date) throws Exception
  {
    if (date != null) {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
      return sdf.parse(date);
    }
    return null;
  }

  public static String convertDateToStringHora(java.util.Date date) throws Exception {
    if (date != null) {
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
      return sdf.format(date);
    }
    return "";
  }

  public static java.sql.Date convertDateUtilToDateSql(java.util.Date data) {
    if (data != null) {
      return new java.sql.Date(data.getTime());
    }
    return null;
  }

  public static java.sql.Date convertStringDataToDateSql(String data) throws ParseException
  {
    if ((data != null) && (!data.equals("")))
    {
      return new java.sql.Date(converterStringDataParaDateUtil(data)
        .getTime());
    }return null;
  }

  public static java.util.Date converterStringDataParaDateUtil(String data) throws ParseException
  {
    if ((data != null) && (!data.equals(""))) {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
      return sdf.parse(data);
    }
    return null;
  }

  public static String formataAno(java.util.Date data)
  {
    if (data != null) {
      SimpleDateFormat sdf = new SimpleDateFormat();
      sdf.applyPattern("yy");
      return sdf.format(data);
    }
    return "";
  }

  public static String formataDia(java.util.Date data) {
    if (data != null) {
      SimpleDateFormat sdf = new SimpleDateFormat();
      sdf.applyPattern("dd");
      return sdf.format(data);
    }
    return "";
  }

  public static String formataMes(java.util.Date data) {
    if (data != null) {
      SimpleDateFormat sdf = new SimpleDateFormat();
      sdf.applyPattern("MM");
      return sdf.format(data);
    }
    return "";
  }

  public static String formataHora(java.util.Date data) {
    if (data != null) {
      SimpleDateFormat sdf = new SimpleDateFormat();
      sdf.applyPattern("HH:mm");
      return sdf.format(data);
    }
    return "";
  }

  public static String formataData(java.util.Date data) {
    if (data != null) {
      SimpleDateFormat sdf = new SimpleDateFormat();
      sdf.applyPattern("dd/MM/yy");
      return sdf.format(data);
    }
    return "";
  }

  public static String retornaDataDiaAnterior() {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.set(5, calendar
      .get(5) - 
      1);
    SimpleDateFormat sdf = new SimpleDateFormat();
    sdf.applyPattern("dd/MM/yy");
    return sdf.format(calendar.getTime());
  }

  public static String recuperarStringDataAcrecidaEmDias(java.util.Date data, int numeroDiasAcrescimo)
  {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(data);
    calendar.set(5, calendar.get(5) + numeroDiasAcrescimo);

    SimpleDateFormat sdf = new SimpleDateFormat();
    sdf.applyPattern("dd/MM/yy");
    return sdf.format(calendar.getTime());
  }

  public static String recuperarStringDataAcrecidaEmDias(String data, int numeroDiasAcrescimo) throws Exception
  {
    return recuperarStringDataAcrecidaEmDias(converterStringParaDate(data), numeroDiasAcrescimo);
  }

  public static java.util.Date recuperarDataAcrescidaEmDias(java.util.Date data, int numeroDiasAcrescimo)
  {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(data);
    calendar.set(5, calendar.get(5) + numeroDiasAcrescimo);

    return calendar.getTime();
  }

  public static java.util.Date recuperarDataAcrescidaEmMeses(java.util.Date data, int numeroMesesAcrescimo)
  {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(data);
    calendar.set(2, calendar.get(2) + numeroMesesAcrescimo);

    return calendar.getTime();
  }

  public static String recuperarStringDataUltimoDiaMes(String data) throws Exception
  {
    int mes = Integer.parseInt(data.substring(3, 5));
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(converterStringParaDate(data));
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
    calendar.set(calendar.get(1), mes, 0);
    return sdf.format(calendar.getTime());
  }

  public static String adicionarDiasADataAtual(int quantidadeDeDias) {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.set(5, calendar.get(5) + quantidadeDeDias);

    SimpleDateFormat sdf = new SimpleDateFormat();
    sdf.applyPattern("dd/MM/yy");
    return sdf.format(calendar.getTime());
  }

  public static String adicionarDiasADataAtual(Calendar data, int quantidadeDeDias)
  {
    GregorianCalendar calendar = new GregorianCalendar();
    SimpleDateFormat sdf = new SimpleDateFormat();
    sdf.applyPattern("dd/MM/yy");
    if (null != data)
    {
      calendar = new GregorianCalendar(data.get(1), data
        .get(2), 
        data.get(5));
      calendar.set(5, calendar
        .get(5) + 
        quantidadeDeDias);
    }

    return sdf.format(calendar.getTime());
  }

  public static void adicionarDiasAData(Calendar data, int quantidadeDeDias) {
    if (null != data)
      data.add(5, quantidadeDeDias);
  }

  public static void adicionarDiasAData(java.util.Date data, int quantidadeDeDias)
  {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(data);
    adicionarDiasAData(calendar, quantidadeDeDias);
    data.setTime(calendar.getTime().getTime());
  }

  public static String subtrairDiasADataAtual(int quantidadeDeDias)
  {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.set(5, calendar.get(5) - quantidadeDeDias);

    SimpleDateFormat sdf = new SimpleDateFormat();
    sdf.applyPattern("dd/MM/yy");
    return sdf.format(calendar.getTime());
  }

  public static java.util.Date subtrairDiasAData(int quantidadeDeDias)
  {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.set(5, calendar.get(5) - quantidadeDeDias);
    return calendar.getTime();
  }

  public static String subtrairDiasADataAtual(Calendar data, int quantidadeDeDias)
  {
    GregorianCalendar calendar = new GregorianCalendar();
    SimpleDateFormat sdf = new SimpleDateFormat();
    sdf.applyPattern("dd/MM/yy");
    if (null != data)
    {
      calendar = new GregorianCalendar(data.get(1), data
        .get(2), 
        data.get(5));
      calendar.set(5, calendar
        .get(5) - 
        quantidadeDeDias);
    }

    return sdf.format(calendar.getTime());
  }

  public static String recuperarDataAtualFormatadaComMilisegundos()
  {
    GregorianCalendar calendar = new GregorianCalendar();

    return calendar.get(5) + "/" + calendar
      .get(2) + 
      "/" + calendar
      .get(1) + 
      " - " + calendar
      .get(11) + 
      ":" + calendar
      .get(12) + 
      ":" + calendar
      .get(13) + 
      ":" + calendar
      .get(14);
  }

  public static java.util.Date adicionarMinutosAHora(java.util.Date data, int quantidadeDeMinutos)
  {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(data);
    adicionarMinutosAHora(calendar, quantidadeDeMinutos);
    data.setTime(calendar.getTime().getTime());
    return data;
  }

  public static void adicionarMinutosAHora(Calendar data, int quantidadeDeMinutos) {
    if (null != data)
      data.add(12, quantidadeDeMinutos);
  }

  public static boolean data1MenorQueData2(java.util.Date data1, java.util.Date data2)
  {
    java.util.Date dat1 = data1;
    java.util.Date dat2 = data2;
    try {
      dat1 = converterStringParaDate(formataData(data1));
      dat2 = converterStringParaDate(formataData(data2));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return dat1.before(dat2);
  }

  public static boolean compararDatasIguais(java.util.Date data1, java.util.Date data2)
  {
    if ((data1 == null) || (data2 == null)) {
      return false;
    }
    GregorianCalendar gc = new GregorianCalendar();
    gc.setTime(data1);
    GregorianCalendar gc2 = new GregorianCalendar();
    gc2.setTime(data2);
    return gc.compareTo(gc2) == 0;
  }

  public static String retornaHorasEntreDatas(java.util.Date inicio, java.util.Date fim) {
    Calendar ini = Calendar.getInstance();
    Calendar fin = Calendar.getInstance();
    ini.setTime(inicio);
    fin.setTime(fim);
    Long horaInicio = Long.valueOf(ini.getTimeInMillis());
    Long horaFim = Long.valueOf(fin.getTimeInMillis());
    Long result = Long.valueOf((horaFim.longValue() - horaInicio.longValue()) / 3600000L);
    String resultado;

    if (result.longValue() > 1L) {
      resultado = result.toString() + " hs";
    }
    else
    {
      if (result.longValue() == 1L) {
        resultado = result.toString() + " h";
      } else {
        result = Long.valueOf((horaFim.longValue() - horaInicio.longValue()) / 60000L);
        resultado = result.toString() + " min";
      }
    }
    return resultado;
  }

  public static java.util.Date zerarHorarioData(java.util.Date data)
  {
    Calendar cal = Calendar.getInstance();
    cal.setTime(data);
    cal.set(13, 0);
    cal.set(12, 0);
    cal.set(11, 0);
    cal.set(14, 0);
    return cal.getTime();
  }

  public static java.util.Date ultimoHorarioData(java.util.Date data) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(data);
    cal.set(13, 59);
    cal.set(12, 59);
    cal.set(11, 23);
    cal.set(14, 59);
    return cal.getTime();
  }

  public static int retornaDiasEntreDatas(java.util.Date inicio, java.util.Date fim) {
    Calendar ini = Calendar.getInstance();
    Calendar fin = Calendar.getInstance();
    ini.setTime(inicio);
    ini.set(11, 0);
    ini.set(12, 0);
    ini.set(13, 0);
    ini.set(14, 0);
    fin.setTime(fim);
    fin.set(11, 0);
    fin.set(12, 0);
    fin.set(13, 0);
    fin.set(14, 0);
    Long horaInicio = Long.valueOf(ini.getTimeInMillis());
    Long horaFim = Long.valueOf(fin.getTimeInMillis());
    int resultado = (int)((horaFim.longValue() - horaInicio.longValue()) / 86400000L);
    return resultado;
  }

  public static int calculaDiasEntreDatas(java.util.Date inicio, java.util.Date fim)
  {
    Calendar ini = Calendar.getInstance();
    Calendar fin = Calendar.getInstance();
    ini.setTime(inicio);
    ini.set(11, 0);
    ini.set(12, 0);
    ini.set(13, 0);
    ini.set(14, 0);
    fin.setTime(fim);
    fin.set(11, 0);
    fin.set(12, 0);
    fin.set(13, 0);
    fin.set(14, 0);
    Long horaInicio = Long.valueOf(ini.getTimeInMillis());
    Long horaFim = Long.valueOf(fin.getTimeInMillis());
    return (int)((horaFim.longValue() - horaInicio.longValue()) / 86400000L);
  }

  public static boolean comparaDataMaior(java.util.Date d1, java.util.Date d2)
  {
    int result = d1.compareTo(d2);
    boolean resultado = false;
    if (result <= 0) {
      resultado = true;
    }
    return resultado;
  }
}
