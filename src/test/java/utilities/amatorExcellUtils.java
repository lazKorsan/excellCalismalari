package utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class amatorExcellUtils {

    public Object[][] getExcellData(String fileName, String sheetName, int numberOfColums) {

        File file = new File(fileName);

        XSSFWorkbook xssfWorkbook;
        XSSFSheet xssfSheet;
        Object[][] arr;

        try {
            FileInputStream excellFile = new FileInputStream(file);
            xssfWorkbook = new XSSFWorkbook(excellFile);
            xssfSheet = xssfWorkbook.getSheet(sheetName);

            if (xssfSheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in the Excel file");
            }

            int firstRow = xssfSheet.getFirstRowNum();
            int lastRow = xssfSheet.getLastRowNum();
            int rowCount = lastRow - firstRow;

            // Eğer hiç satır yoksa veya sadece başlık varsa
            if (rowCount <= 0) {
                return new Object[0][0];
            }

            arr = new Object[rowCount][numberOfColums];

            for (int i = 0; i < rowCount; i++) {
                Row row = xssfSheet.getRow(i + firstRow);
                if (row != null) {
                    for (int j = 0; j < numberOfColums; j++) {
                        Cell cell = row.getCell(j);
                        if (cell != null) {
                            arr[i][j] = cell.toString();
                        } else {
                            arr[i][j] = "";
                        }
                    }
                }
            }

            xssfWorkbook.close();
            excellFile.close();

            return arr;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}