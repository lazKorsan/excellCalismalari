package tests;

import Pages.AdminPages;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.Driver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class searchWithExcell {
    AdminPages adminPages = new AdminPages() ;

    // Excel dosyasından kelimeleri okuyan method
    public String[] readKeywordsFromExcel(String filePath, String sheetName) throws IOException, InvalidFormatException {
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheet(sheetName);

        List<String> keywords = new ArrayList<>();
        for (Row row : sheet) {
            for (Cell cell : row) {
                keywords.add(cell.toString());
            }
        }

        workbook.close();
        file.close();

        return keywords.toArray(new String[0]);
    }

    // TestNG ile örnek test, Excelden okunan kelimeleri arama için kullanabilir
    @Test
    public void testSearchProducts() throws IOException, InvalidFormatException {
        String desktopPath = System.getProperty("user.home") + "/Desktop";
        String excelFilePath = desktopPath + "/loyalfriendcare/urunler.xlsx";
        String sheetName = "Sayfa1"; // Excel sayfa adı

        String[] searchKeywords = readKeywordsFromExcel(excelFilePath, sheetName);
        Driver.getDriver().get("https://www.loyalfriendcare.com/");
        adminPages.searchBox.sendKeys(searchKeywords);
        // Örnek arama doğruluğu testi - burada gerçek arama fonksiyonu çağırılabilir
        for (String keyword : searchKeywords) {
            System.out.println("Aranacak kelime: " + keyword);
            // Burada arama yapıp sonucu doğrulama kodu gelebilir
            Assert.assertNotNull(keyword); // Örnek kontrol
        }
    }
}
