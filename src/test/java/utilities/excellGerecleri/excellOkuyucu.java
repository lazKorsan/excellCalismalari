
        package utilities.excellGerecleri;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class excellOkuyucu {

    public static Object[][] getExcelData(String filePath, String sheetName)
            throws IOException, InvalidFormatException {

        try (FileInputStream file = new FileInputStream(new File(filePath));
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheet(sheetName);
            DataFormatter formatter = new DataFormatter();

            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

            Object[][] data = new Object[rowCount - 1][colCount];

            for (int i = 1; i < rowCount; i++) {
                Row currentRow = sheet.getRow(i);

                if(currentRow == null) {
                    System.err.println(i + ". satır boş - Atlanıyor");
                    continue;
                }

                for (int j = 0; j < colCount; j++) {
                    Cell cell = currentRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    data[i - 1][j] = formatter.formatCellValue(cell);
                }
            }
            return data;
        }
    }
}

