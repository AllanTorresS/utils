package ipp.aci.boleia.util.negocio;

import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.i18n.IEnumComLabel;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.validador.ValidadorAlfanumerico;
import ipp.aci.boleia.util.validador.ValidadorEmail;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Classe utilitaria para leitura de dados do excel
 */
@Component
public class UtilitarioExcel {

    private static final Logger LOGGER = LoggerFactory.getLogger(UtilitarioExcel.class);
    private static final String ERRO_VALOROBRIGATORIO = "leitura.excel.erro.valorobrigatorio";
    private static final String ERRO_VALORINVALIDO = "Erro.ERRO_VALIDACAO_SERVICO_TIPO";
    private static final String COLUNA_ERRO = "leitura.excel.erro.titulo.coluna";
    private static final String TIPO_TEXTO = "leitura.excel.tipo.texto.label";
    private static final String TIPO_INTEIRO = "leitura.excel.tipo.inteiro.label";
    private static final String TIPO_DECIMAL = "leitura.excel.tipo.decimal.label";
    private static final String TIPO_ALFANUMERICO = "leitura.excel.tipo.alfanumerico.label";
    private static final String TIPO_DATA = "leitura.excel.tipo.data.label";
    private static final String TIPO_ENUM = "leitura.excel.tipo.enum.label";
    private static final String TIPO_EMAIL = "leitura.excel.tipo.email.label";
    private static final String CELULA_VAZIA = "texto.comum.vazio";
    private static final String SIM = "texto.comum.sim";
    private static final String NAO = "texto.comum.nao";

    public static final String ERRO_VALIDACAO_INVALIDO = "leitura.excel.erro.validacao.invalido";
    public static final String ERRO_VALIDACAO_SUBTIPO = "leitura.excel.erro.validacao.tipoSubtipo";

    @Autowired
    private Mensagens mensagens;

    /**
     * Le uma celula de dados do tipo String
     *
     * @param celula celula
     * @param obrigatorio obrigatorio
     * @return valor da celula
     */
    private String lerCelulaString(Cell celula, Boolean obrigatorio) {
        String valor;

        if(CellType.NUMERIC.equals(celula.getCellTypeEnum())) {
            Double d = celula.getNumericCellValue();
            valor = String.format("%d", d.longValue());
        } else {
            valor = celula.getStringCellValue();
        }

        if(StringUtils.isBlank(valor)) {
            if(obrigatorio != null && obrigatorio) {
                marcarCelulaErro(celula, ERRO_VALOROBRIGATORIO);
            } else {
                return null;
            }
        }
        return valor;
    }

    /**
     * Escreve em uma celula um valor String
     *
     * @param celula celula
     * @param valor valor a ser inserido na célula
     */
    private void escreverCelula(Cell celula, String valor) {
        if(valor != null && !valor.isEmpty()) {
            celula.setCellValue(valor);
        }
        else {
            celula.setCellValue(mensagens.obterMensagem(CELULA_VAZIA));
        }
    }

    /**
     * Escreve em uma celula um valor Double
     *
     * @param celula celula
     * @param valor valor a ser inserido na célula
     */
    private void escreverCelula(Cell celula, Double valor) {
        if(valor != null) {
            celula.setCellValue(valor);
        } else {
            celula.setCellValue(mensagens.obterMensagem(CELULA_VAZIA));
        }
    }

    /**
     * Le uma celula de dados do tipo Enum
     *
     * @param celula celula
     * @param enumClazz tipo enum
     * @param obrigatorio obrigatorio
     * @param <E> classe enum
     * @return valor enum
     */
    private <E extends IEnumComLabel> E lerCelulaEnum(Cell celula, Class<E> enumClazz, Boolean obrigatorio) {
        String valor = lerCelulaString(celula, obrigatorio);
        if(valor!=null) {
            for (E en : enumClazz.getEnumConstants()) {
                if (en.getLabel().contentEquals(valor)) {
                    return en;
                }
            }
            marcarCelulaErro(celula, ERRO_VALORINVALIDO, getMensagens().obterMensagem(TIPO_ENUM));
        }
        return null;
    }

    /**
     * Le uma celula do tipo inteiro
     *
     * @param celula celula
     * @param obrigatorio obrigatorio
     * @return valor inteiro
     */
    private Long lerCelulaInteiro(Cell celula, Boolean obrigatorio) {
        if (validarCelulaNumericaNula(celula, obrigatorio, TIPO_INTEIRO)) {
            return null;
        }

        try {
            Long inteiro = new Double(celula.getNumericCellValue()).longValue();
            if(inteiro==0L && obrigatorio) {
                marcarCelulaErro(celula,ERRO_VALOROBRIGATORIO);
            } else {
                return inteiro;
            }
        } catch (NumberFormatException ex) {
            marcarCelulaErro(celula, ERRO_VALORINVALIDO, getMensagens().obterMensagem(TIPO_INTEIRO));
        }
        return null;
    }

    /**
     * Verifica se uma celular numerica eh nula
     * @param celula A celula
     * @param obrigatorio Se seu preenchimento eh obrigatorio
     * @param tipoValidacao O tipo de mensagem de validacao
     * @return True caso sej avalida
     */
    private boolean validarCelulaNumericaNula(Cell celula, Boolean obrigatorio, String tipoValidacao) {
        try {
            String text = celula.getStringCellValue();
            if(StringUtils.isEmpty(text) && obrigatorio) {
                marcarCelulaErro(celula,ERRO_VALOROBRIGATORIO);
            } else if (!StringUtils.isEmpty(text)){
                marcarCelulaErro(celula, ERRO_VALORINVALIDO, getMensagens().obterMensagem(tipoValidacao));
            }
            return true;
        } catch (IllegalStateException e) {
            // nada a fazer
            LOGGER.trace(e.getMessage(), e);
        }
        return false;
    }

    /**
     * Le uma celula do tipo decimal
     *
     * @param celula celula
     * @param obrigatorio obrigatorio
     * @param possuiPorcentagem true se eh uma celula de valores percentuais
     * @return valor decimal
     */
    private BigDecimal lerCelulaDecimal(Cell celula, Boolean obrigatorio, Boolean possuiPorcentagem) {
        if (validarCelulaNumericaNula(celula, obrigatorio,TIPO_DECIMAL)) {
            return null;
        }

        if(possuiPorcentagem){
            retirarPorcentagem(celula);
        }

        double valor = celula.getNumericCellValue();
        try {
            BigDecimal decimal = BigDecimal.valueOf(valor);
            if(decimal.compareTo(BigDecimal.ZERO)==0) {
                if(obrigatorio) {
                    marcarCelulaErro(celula,ERRO_VALOROBRIGATORIO);
                } else {
                    decimal = null;
                }
            }
            return decimal;
        } catch (NumberFormatException | IllegalStateException ex) {
            LOGGER.trace(ex.getMessage(), ex);
            marcarCelulaErro(celula, ERRO_VALORINVALIDO, getMensagens().obterMensagem(TIPO_DECIMAL));
        }
        return null;
    }

    /**
     * Retira porcentagem de uma celula
     *
     * @param celula celula
     */
    private void retirarPorcentagem(Cell celula) {
        if(celula.getCellStyle().getDataFormatString().contains("%")) {
            celula.setCellValue(celula.getNumericCellValue() * 100);
        }
    }

    /**
     * Le uma celula do tipo date
     *
     * @param celula celula
     * @param obrigatorio obrigatorio
     * @param formatoData formatoData
     * @return valor Date
     */
    private Date lerCelulaDate(Cell celula, Boolean obrigatorio, String formatoData) {
       Date valor = carregarCelulaDateDeUmTexto(celula, formatoData);
       if(valor == null) {
           if (validarCelulaNumericaNula(celula, obrigatorio, TIPO_DATA)) {
               return null;
           }
           valor = celula.getDateCellValue();
       }
        try {
            if(valor == null && obrigatorio) {
                marcarCelulaErro(celula, ERRO_VALOROBRIGATORIO);
            }
            Integer ano = UtilitarioCalculoData.obterCampoData(valor, Calendar.YEAR);
            if(ano != null && ano > 3000) {
                marcarCelulaErro(celula, ERRO_VALORINVALIDO, getMensagens().obterMensagem(TIPO_DATA));
            }
            return valor;
        } catch (IllegalStateException ex) {
            LOGGER.trace(ex.getMessage(), ex);
            marcarCelulaErro(celula, ERRO_VALORINVALIDO, getMensagens().obterMensagem(TIPO_DATA));
        }
        return null;
    }

    /**
     * Realiza um tratamento para ler uma data de uma celula
     *
     * @param celula celula
     * @param formatoData formatoData
     * @return valor Date
     */
    private Date carregarCelulaDateDeUmTexto(Cell celula, String formatoData){
        try {
            String text = celula.getStringCellValue();
            if(!StringUtils.isEmpty(text) && formatoData != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatoData);
                try {
                    Date valor = simpleDateFormat.parse(text);
                    return valor;
                } catch (Exception e){
                    marcarCelulaErro(celula, ERRO_VALORINVALIDO, TIPO_DATA);
                }
            }
        } catch(Exception e){ }
        return null;
    }

    /**
     * Marcar uma celula com estilo de erro adicionando mensagem de erro na ultima coluna
     * 
     * @param linha Linha
     * @param numeroCelula Número de celula
     * @param mensagem Mensagem de erro.
     */
    public void marcarCelulaErro(Row linha, int numeroCelula, String mensagem) {
        Cell celula = linha.getCell(numeroCelula);
        if(celula != null) {
            marcarCelulaErro(celula, mensagem);
        }
    }

    /**
     *  Torna possível a quebra de linha dentro da célula.
     *
     * @param linha Linha
     * @param numeroCelula Número de celula
     */
    public void tornarCelulaMultilinha(Row linha, int numeroCelula) {
        Cell celula = linha.getCell(numeroCelula);

        CellStyle estilo = linha.getSheet().getWorkbook().createCellStyle();
        estilo.setWrapText(true);
        celula.setCellStyle(estilo);
    }

    /**
     * Marcar uma celula com estilo de erro.
     *
     * @param linha Linha
     * @param numeroCelula Número de celula
     */
    public void marcarCelulaErro(Row linha, int numeroCelula) {
        Cell celula = linha.getCell(numeroCelula);
        marcarCelulaErro(celula);
    }

    /**
     * Marca uma celula com estilo de erro
     *
     * @param celula celula
     * @param mensagem mensagem de erro
     */
    private void marcarCelulaErro(Cell celula, String mensagem) {
        String label = obterCabecalhoCelula(celula);

        String mensagemTratada = "∙ " + getMensagens().obterMensagem(mensagem, label);
        adicionarColunaErro(celula.getRow(),mensagemTratada);

        marcarCelulaErro(celula);
    }

    /**
     * Marca uma celula com estilo de erro com tipo
     *
     * @param celula celula
     * @param mensagem mensagem de erro
     * @param tipo mensagem de erro
     */
    private void marcarCelulaErro(Cell celula, String mensagem, String tipo) {
        String label = obterCabecalhoCelula(celula);

        String mensagemTratada = "∙ " + getMensagens().obterMensagem(mensagem, label, tipo);
        adicionarColunaErro(celula.getRow(),mensagemTratada);

        marcarCelulaErro(celula);
    }

    /**
     * Obtem o cabeçalho da célula
     *
     * @param celula celula
     * @return O cabeçalho da célula
     */
    private String obterCabecalhoCelula(Cell celula){
        String label = celula.getSheet().getRow(0).getCell(celula.getColumnIndex()).getStringCellValue();
        Integer complemento = label.contains("*") ? label.indexOf("*") : label.indexOf("(");
        if(complemento>0) {
            label = label.substring(0,complemento).trim();
        }
        return label;
    }

    /**
     * Cria uma nova célula com mensagens de erro
     *
     * @param linha linha que contém o erro
     * @param mensagem mensagem de erro
     */
    private void adicionarColunaErro(Row linha, String mensagem){

        String nomeColunaErro = getMensagens().obterMensagem(COLUNA_ERRO);
        int ultimaColuna = linha.getSheet().getRow(0).getLastCellNum() - 1;

        Cell ultimaCelulaCabecalho = linha.getSheet().getRow(0).getCell(ultimaColuna);

        Cell ultimaCelula = linha.getCell(ultimaColuna);

        if(!ultimaCelulaCabecalho.getStringCellValue().equals(nomeColunaErro)){
            ultimaCelulaCabecalho = linha.getSheet().getRow(0).createCell(ultimaColuna + 1);
            ultimaCelulaCabecalho.setCellValue(nomeColunaErro);

            Font cellFont = linha.getSheet().getWorkbook().createFont();
            cellFont.setBold(true);
            cellFont.setColor(IndexedColors.WHITE.getIndex());

            CellStyle cellStyle = linha.getSheet().getWorkbook().createCellStyle();
            cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setFont(cellFont);
            ultimaCelulaCabecalho.setCellStyle(cellStyle);

            ultimaCelula = linha.createCell(ultimaColuna + 1);
            ultimaCelula.getCellStyle().setWrapText(true);
        }else if(ultimaCelula == null){
            ultimaCelula = linha.createCell(ultimaColuna);
            ultimaCelula.getCellStyle().setWrapText(true);
        }
        ultimaCelula.setCellValue(ultimaCelula.getStringCellValue() + mensagem + "\n");
    }

    /**
     * Remove a coluna de erros caso exista
     *
     * @param linha a ser tratada
     */
    public void limparLinhaErro(Row linha){
        String nomeColunaErro = getMensagens().obterMensagem(COLUNA_ERRO);
        int ultimaColuna = linha.getSheet().getRow(0).getLastCellNum()-1;
        if(linha.getSheet().getRow(0).getCell(ultimaColuna).getStringCellValue().equals(nomeColunaErro) && linha.getCell(ultimaColuna) != null){
            linha.removeCell(linha.getCell(ultimaColuna));
        }
    }

    /**
     * Aplica o estilo de erro para uma celula
     *
     * @param celula celula
     */
    private void marcarCelulaErro(Cell celula) {
        Workbook wb = celula.getSheet().getWorkbook();
        celula.setCellStyle(wb.createCellStyle());

        celula.getCellStyle().setBorderBottom(BorderStyle.MEDIUM_DASHED);
        celula.getCellStyle().setBottomBorderColor(IndexedColors.RED.getIndex());
        celula.getCellStyle().setBorderTop(BorderStyle.MEDIUM_DASHED);
        celula.getCellStyle().setTopBorderColor(IndexedColors.RED.getIndex());
        celula.getCellStyle().setBorderLeft(BorderStyle.MEDIUM_DASHED);
        celula.getCellStyle().setLeftBorderColor(IndexedColors.RED.getIndex());
        celula.getCellStyle().setBorderRight(BorderStyle.MEDIUM_DASHED);
        celula.getCellStyle().setRightBorderColor(IndexedColors.RED.getIndex());
    }

    /**
     * Marca uma linha com erro negocial
     *
     * @param excecao a excecao ocorrida ao processa a linha
     * @param linha linha com erro
     * @param erroExcecao erro da excecao
     * @param parametros parametros da mensagem
     */
    public void marcarLinhaComErro(Exception excecao, Row linha, Erro erroExcecao, Object... parametros) {
        String detalhe = "";
        List<String> erros = getMensagens().obterMensagensExcecao(excecao, erroExcecao, parametros);
        if(erros != null){
            for(String erro : erros){
                detalhe = detalhe.concat("∙ " + erro + "\n");
            }
        }
        adicionarColunaErro(linha,detalhe);
    }


    /**
     * Obtem valor inteiro de celula
     *
     * @param linha linha
     * @param numeroCelula numero da celula
     * @param obrigatorio obrigatorio
     * @return valor da celula
     */
    public Long lerCelulaInteiro(Row linha, int numeroCelula, Boolean obrigatorio) {
        Cell celula = obterCelula(linha,numeroCelula,null);
        return lerCelulaInteiro(celula,obrigatorio);
    }

    /**
     * Obtem valor decimal de celula
     *
     * @param linha linha
     * @param numeroCelula numero da celula
     * @param obrigatorio obrigatorio
     * @return valor da celula
     */
    public BigDecimal lerCelulaDecimal(Row linha, int numeroCelula, Boolean obrigatorio) {
        Cell celula = obterCelula(linha,numeroCelula,null);
        return lerCelulaDecimal(celula,obrigatorio, false);
    }

    /**
     * Obtem valor decimal de celula que possa ter porcentagem
     *
     * @param linha linha
     * @param numeroCelula numero da celula
     * @param obrigatorio obrigatorio
     * @return valor da celula
     */
    public BigDecimal lerCelulaDecimalComPorcentagem(Row linha, int numeroCelula, Boolean obrigatorio) {
        Cell celula = obterCelula(linha,numeroCelula,null);
        return lerCelulaDecimal(celula, obrigatorio, true);
    }

    /**
     * Obtem valor string de celula
     *
     * @param linha linha
     * @param numeroCelula numero da celula
     * @return valor da celula
     */
    public String lerCelulaString(Row linha, int numeroCelula) {
        return lerCelulaString(linha, numeroCelula, null);
    }

    /**
     * Obtem valor string de celula
     *
     * @param linha linha
     * @param numeroCelula numero da celula
     * @param obrigatorio obrigatorio
     * @return valor da celula
     */
    public String lerCelulaString(Row linha, int numeroCelula, Boolean obrigatorio) {
        Cell celula = obterCelula(linha,numeroCelula, null);
        return lerCelulaString(celula, obrigatorio);
    }

    /**
     * Obtem valor string de celula tipo char
     *
     * @param linha linha
     * @param numeroCelula numero da celula
     * @param obrigatorio obrigatorio
     * @return valor da celula
     */
    public String lerCelulaChar(Row linha, int numeroCelula, Boolean obrigatorio) {
        Cell celula = obterCelula(linha,numeroCelula, null);
        String valor = lerCelulaString(celula,obrigatorio);
        if(valor != null && valor.length() != 1){
            marcarCelulaErro(celula, ERRO_VALORINVALIDO, getMensagens().obterMensagem(TIPO_TEXTO));
            return null;
        }else{
            return valor;
        }
    }

    /**
     * Obtem valor date de celula
     *
     * @param linha linha
     * @param numeroCelula numero da celula
     * @param obrigatorio obrigatorio
     * @param formatoData formato exibicao data
     * @return valor da celula
     */
    public Date lerCelulaDate(Row linha, int numeroCelula, Boolean obrigatorio, String formatoData) {
        Cell celula = obterCelula(linha,numeroCelula,formatoData);
        return lerCelulaDate(celula,obrigatorio, formatoData);
    }

    /**
     * Obtem valor enum de celula
     *
     * @param linha linha
     * @param numeroCelula numero da celula
     * @param enumClazz a classe da enumeracao
     * @param obrigatorio obrigatorio
     * @param <E> O tipo enumerado esperado
     * @return valor da celula
     */
    public <E extends IEnumComLabel> E lerCelulaEnum(Row linha, Integer numeroCelula, Class<E> enumClazz, Boolean obrigatorio) {
        Cell celula = obterCelula(linha,numeroCelula,null);
        return lerCelulaEnum(celula, enumClazz, obrigatorio);
    }

    /**
     * Verifica se celula existe e cria caso nao exista antes de validar
     *
     * @param linha linha
     * @param numeroCelula numero da celula
     * @param formatoData formato de exibicao da data
     * @return valor da celula
     */
    private Cell obterCelula(Row linha, int numeroCelula, String formatoData) {
        Cell celula;
        Boolean celulaVazia = false;
        if(linha.getCell(numeroCelula)!=null) {
            celula = linha.getCell(numeroCelula);
            formatoData = StringUtils.isEmpty(formatoData) ? celula.getCellStyle().getDataFormatString() : formatoData;
        } else {
            celulaVazia = true;
            celula = linha.createCell(numeroCelula);
        }

        Workbook wb = linha.getSheet().getWorkbook();
        celula.removeCellComment();
        if(formatoData!=null && !celulaVazia) {
            celula.setCellStyle(wb.createCellStyle());
            celula.getCellStyle().setDataFormat(wb.getCreationHelper().createDataFormat().getFormat(formatoData));
        }
        return celula;
    }

    /**
     * Escreve um determinado valor do tipo boolean em uma célula.
     *
     * @param linha linha
     * @param numeroCelula numero da celula
     * @param valor da celula
     */
    public void escreverCelula(Row linha, int numeroCelula, boolean valor) {
        Cell celula = obterCelula(linha, numeroCelula, null);
        escreverCelula(celula,valor ? mensagens.obterMensagem(SIM) : mensagens.obterMensagem(NAO));
    }

    /**
     * Escreve um determinado valor do tipo string em uma célula.
     *
     * @param linha linha
     * @param numeroCelula numero da celula
     * @param valor da celula
     */
    public void escreverCelula(Row linha, int numeroCelula, String valor) {
        Cell celula = obterCelula(linha, numeroCelula, null);
        escreverCelula(celula,valor);
    }

    /**
     * Escreve um determinado valor do tipo double em uma célula.
     *
     * @param linha linha
     * @param numeroCelula numero da celula
     * @param valor da celula
     */
    public void escreverCelula(Row linha, int numeroCelula, Double valor) {
        Cell celula = obterCelula(linha, numeroCelula, null);
        escreverCelula(celula,valor);
    }

    /**
     * Verifica se uma linha contem estilo de erro
     *
     * @param linha linha
     * @return contem erro
     */
    public Boolean verificarLinhaErro(Row linha) {
        for (Cell celula : linha) {
            if (celula.getCellStyle().getBottomBorderColor() == IndexedColors.RED.getIndex()) {
                return true;
            }
        }
        return false;
    }


    /**
     * Esta implementacao existe apenas para faciliar a criacao de testes
     * de unidade.
     *
     * @return O objeto para resolucao de mensagens.
     */
    public Mensagens getMensagens() {
        return mensagens;
    }

    /**
     * Método que le uma celula do tipo email.
     * 
     * @param linha Linha do xls
     * @param numeroCelula Número da celula xls.
     * @param obrigatorio Indica se campo é obrigatório.
     * @return Campo do tipo email preenchido.
     */
    public String lerCelulaEmail(Row linha, int numeroCelula, Boolean obrigatorio) {
        Cell celula = obterCelula(linha,numeroCelula, null);
        String email = lerCelulaString(celula, obrigatorio);
        if(!ValidadorEmail.validar(email)) {
            marcarCelulaErro(celula, ERRO_VALORINVALIDO, getMensagens().obterMensagem(TIPO_EMAIL));
        }
        return email;
    }


    /**
     * Método que le uma celula do tipo alfanumerica.
     * 
     * @param linha Linha do xls
     * @param numeroCelula Número da celula xls.
     * @param obrigatorio Indica se campo é obrigatório.
     * @return Campo do tipo alfanumerico preenchido.
     */

    public String lerCelulaAlfanumerica(Row linha, int numeroCelula, Boolean obrigatorio) {
        Cell celula = obterCelula(linha,numeroCelula, null);
        String alfanuremerico = lerCelulaString(celula, obrigatorio);
        if(!StringUtils.isBlank(alfanuremerico) && !ValidadorAlfanumerico.isAlfanumerico(alfanuremerico)) {
            marcarCelulaErro(celula, ERRO_VALORINVALIDO,getMensagens().obterMensagem(TIPO_ALFANUMERICO));
        }
        return alfanuremerico;
    }

    /**
     * Método que redefine linha da planilha.
     *l
     * @param sheet Planilha.
     * @param linhasPreenchidas Lista com as linhas que estão preenchidas.
     */
    public void resetarLinhas(Sheet sheet, List<Row> linhasPreenchidas){
        // Copia as linhas para o início, pulando cabeçalho
        for(int i = 1; i <= linhasPreenchidas.size();i++){
            Row linhaPreenchida = linhasPreenchidas.get(i-1);
            if(linhaPreenchida.getRowNum() == i) {
                continue;
            }
            Row linha = sheet.getRow(i);
            if(linha != null) {
                sheet.removeRow(linha);
            }
            copiarLinha(sheet.getWorkbook(),sheet,linhaPreenchida.getRowNum(),i);
            sheet.removeRow(linhaPreenchida);
        }
        limparLinhasApos(sheet, linhasPreenchidas.size() + 1);
        for(Cell celula : sheet.getRow(0)) {
            sheet.autoSizeColumn(celula.getColumnIndex());
        }
    }

    /**
     * Método que apaga linhas a partir do numero.
     *  @param sheet Planilha
     * @param numeroLinha Numero da linha
     * 
     */
    private void limparLinhasApos(Sheet sheet, int numeroLinha) {
        int numeroUltimaLinha = sheet.getLastRowNum();
        for(int  i = numeroLinha; i <= numeroUltimaLinha; i ++) {
            Row linha = sheet.getRow(i);
            if(linha != null) {
                sheet.removeRow(linha);
            }
        }
    }

    /**
     * Método que copia uma linha de origem para um destino especificado.
     *  @param workbook Pasta de trabalho
     * @param worksheet Planilha.
     * @param numLinhaOrigem Número da linha de origem.
     * @param numLinhaDestino Número da linha de destino.
     */
    private static void copiarLinha(Workbook workbook, Sheet worksheet, int numLinhaOrigem, int numLinhaDestino) {
        Row newRow = worksheet.getRow(numLinhaDestino);
        Row sourceRow = worksheet.getRow(numLinhaOrigem);

        if (newRow != null) {
            worksheet.shiftRows(numLinhaDestino, worksheet.getLastRowNum(), 1);
        } else {
            newRow = worksheet.createRow(numLinhaDestino);
        }
        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            copiarCelulaLinha(workbook, newRow, sourceRow, i);
        }

        for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
            CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
            if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(),
                        (newRow.getRowNum() +
                                (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow()
                                )),
                        cellRangeAddress.getFirstColumn(),
                        cellRangeAddress.getLastColumn());
                worksheet.addMergedRegion(newCellRangeAddress);
            }
        }
    }

    /**
     * Copia os estilos de uma celular para a outra
     * @param workbook A planilha
     * @param newRow A linha nova
     * @param sourceRow A linha da celula a ser copiada
     * @param indiceColuna O indice da coluna a ser copiada
     */
    private static void copiarCelulaLinha(Workbook workbook, Row newRow, Row sourceRow, int indiceColuna) {
        Cell oldCell = sourceRow.getCell(indiceColuna);
        Cell newCell = newRow.createCell(indiceColuna);
        if (oldCell == null) {
            return;
        }
        CellStyle newCellStyle = workbook.createCellStyle();
        newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
        newCell.setCellStyle(newCellStyle);
        if (oldCell.getCellComment() != null) {
            newCell.setCellComment(oldCell.getCellComment());
        }
        if (oldCell.getHyperlink() != null) {
            newCell.setHyperlink(oldCell.getHyperlink());
        }
        newCell.setCellType(oldCell.getCellTypeEnum());
        switch (oldCell.getCellTypeEnum()) {
            case BLANK:
                newCell.setCellValue(oldCell.getStringCellValue());
                break;
            case BOOLEAN:
                newCell.setCellValue(oldCell.getBooleanCellValue());
                break;
            case ERROR:
                newCell.setCellErrorValue(oldCell.getErrorCellValue());
                break;
            case FORMULA:
                newCell.setCellFormula(oldCell.getCellFormula());
                break;
            case NUMERIC:
                newCell.setCellValue(oldCell.getNumericCellValue());
                break;
            case STRING:
                newCell.setCellValue(oldCell.getRichStringCellValue());
                break;
        }
    }

    /**
     * Verifica se a linha esta totalmente vazia em conteudo
     * para todas as celulas
     * @param linha linha
     * @return true para linha vazia
     */
    public Boolean verificarLinhaVazia(Row linha) {
        for (Cell celula : linha) {
            switch (celula.getCellTypeEnum()) {
                case BLANK:     break;
                case BOOLEAN:   return !celula.getBooleanCellValue();
                case ERROR:     return false;
                case FORMULA:   return celula.getCellFormula() == null;
                case NUMERIC:   return celula.getNumericCellValue() == 0;
                case STRING:    return StringUtils.isBlank(celula.getStringCellValue());
            }
        }
        return true;
    }
}
