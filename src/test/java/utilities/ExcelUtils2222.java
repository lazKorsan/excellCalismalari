package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils2222 {

    private ExcelUtils2222() {
        // Utility class - private constructor
    }

    /**
     * Excel dosyasından verileri okur ve 2D Object array olarak döndürür
     * @param filePath Excel dosya yolu
     * @param sheetName Sayfa adı
     * @param numberOfColumns Okunacak sütun sayısı
     * @return 2D Object array
     */
    public static Object[][] readExcelData(String filePath, String sheetName, int numberOfColumns) {
        return readExcelData(filePath, sheetName, numberOfColumns, true);
    }

    /**
     * Excel dosyasından verileri okur (başlık satırı opsiyonel)
     * @param filePath Excel dosya yolu
     * @param sheetName Sayfa adı
     * @param numberOfColumns Okunacak sütun sayısı
     * @param hasHeader Başlık satırı var mı?
     * @return 2D Object array
     */
    public static Object[][] readExcelData(String filePath, String sheetName, int numberOfColumns, boolean hasHeader) {
        File file = new File(filePath);

        if (!file.exists()) {
            throw new RuntimeException("Excel file not found: " + filePath);
        }

        try (FileInputStream excelFile = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(excelFile)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in the Excel file");
            }

            int firstRow = hasHeader ? sheet.getFirstRowNum() + 1 : sheet.getFirstRowNum();
            int lastRow = sheet.getLastRowNum();

            if (lastRow < firstRow) {
                return new Object[0][0];
            }

            int rowCount = lastRow - firstRow + 1;
            List<Object[]> dataList = new ArrayList<>();

            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.getRow(i + firstRow);
                if (row != null) {
                    Object[] rowData = new Object[numberOfColumns];
                    boolean rowHasData = false;

                    for (int j = 0; j < numberOfColumns; j++) {
                        Cell cell = row.getCell(j);
                        rowData[j] = getCellValue(cell);
                        if (rowData[j] != null && !rowData[j].toString().isEmpty()) {
                            rowHasData = true;
                        }
                    }

                    if (rowHasData) {
                        dataList.add(rowData);
                    }
                }
            }

            return dataList.toArray(new Object[0][0]);

        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel file: " + e.getMessage(), e);
        }
    }

    /**
     * Hücre değerini uygun türde alır
     * @param cell Excel hücresi
     * @return Hücre değeri (String, Number, Boolean veya null)
     */
    private static Object getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    double numericValue = cell.getNumericCellValue();
                    // Tam sayı kontrolü
                    if (numericValue == Math.floor(numericValue)) {
                        return (long) numericValue;
                    } else {
                        return numericValue;
                    }
                }

            case BOOLEAN:
                return cell.getBooleanCellValue();

            case FORMULA:
                return evaluateFormulaCell(cell);

            case BLANK:
                return "";

            default:
                return "";
        }
    }

    /**
     * Formül hücrelerini değerlendirir
     * @param cell Formül hücresi
     * @return Formül sonucu
     */
    private static Object evaluateFormulaCell(Cell cell) {
        try {
            FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
            CellValue cellValue = evaluator.evaluate(cell);

            switch (cellValue.getCellType()) {
                case STRING:
                    return cellValue.getStringValue();
                case NUMERIC:
                    return cellValue.getNumberValue();
                case BOOLEAN:
                    return cellValue.getBooleanValue();
                default:
                    return "";
            }
        } catch (Exception e) {
            return cell.getCellFormula();
        }
    }

    /**
     * Belirli bir sütun için verileri alır
     * @param filePath Excel dosya yolu
     * @param sheetName Sayfa adı
     * @param columnIndex Sütun indexi (0-based)
     * @return Sütun verileri
     */
    public static Object[] getColumnData(String filePath, String sheetName, int columnIndex) {
        Object[][] allData = readExcelData(filePath, sheetName, columnIndex + 1);
        Object[] columnData = new Object[allData.length];

        for (int i = 0; i < allData.length; i++) {
            columnData[i] = allData[i][columnIndex];
        }

        return columnData;
    }
}