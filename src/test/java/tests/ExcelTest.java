package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.ExcelUtils2222;


public class ExcelTest {

    @DataProvider(name = "excelDataProvider")
    public Object[][] provideExcelData() {
        String filePath = "C:\\Users\\Hp\\OneDrive\\Desktop\\loyalfriendcare\\urunler.xlsx";
        return ExcelUtils2222.readExcelData(filePath, "sayfa1", 3);
    }

    @DataProvider(name = "excelDataWithoutHeader")
    public Object[][] provideExcelDataWithoutHeader() {
        String filePath = "C:\\Users\\Hp\\OneDrive\\Desktop\\loyalfriendcare\\urunler.xlsx";
        return ExcelUtils2222.readExcelData(filePath, "sayfa1", 3, false);
    }

    @Test(dataProvider = "excelDataProvider")
    public void testExcelData(Object value1, Object value2, Object value3) {
        System.out.println("Değer 1: " + value1 + " (" + value1.getClass().getSimpleName() + ")");
        System.out.println("Değer 2: " + value2 + " (" + value2.getClass().getSimpleName() + ")");
        System.out.println("Değer 3: " + value3 + " (" + value3.getClass().getSimpleName() + ")");
        System.out.println("-----------------------------------");
    }

    @Test
    public void testSpecificColumn() {
        String filePath = "C:\\Users\\Hp\\OneDrive\\Desktop\\loyalfriendcare\\urunler.xlsx";
        Object[] columnData = ExcelUtils2222.getColumnData(filePath, "sayfa1", 0);

        System.out.println("Birinci sütun verileri:");
        for (Object data : columnData) {
            System.out.println(data + " (" + data.getClass().getSimpleName() + ")");
        }
    }
}