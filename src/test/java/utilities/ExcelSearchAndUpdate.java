package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.*;

public class ExcelSearchAndUpdate {


    public static void searchAndUpdateExcel(WebDriver driver, String url, String excelPath, String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream(excelPath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        // Kırmızı arka plan stili
        CellStyle redBgStyle = workbook.createCellStyle();
        redBgStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        redBgStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // 1'den başla, başlık satırı atla
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Cell searchCell = row.getCell(0); // A sütunu: aranan ürün adı
            if (searchCell == null) continue;
            String productName = searchCell.getStringCellValue();
            if (productName == null || productName.trim().isEmpty()) continue;

            // 1- Siteye git
            driver.get(url);

            // 2- Arama kutusunu bul ve arama yap
            try {
                // TODO: Kendi sitenin arama kutusunun locator'unu kullan!
                WebElement searchBox = new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                        .until(ExpectedConditions.visibilityOfElementLocated(By.id("searchBox"))); // Örnek: id="searchBox"
                searchBox.clear();
                searchBox.sendKeys(productName);
                searchBox.sendKeys(Keys.ENTER);

                // 3- Yeni pencere açıldıysa geç
                String originalWindow = driver.getWindowHandle();
                for (String windowHandle : driver.getWindowHandles()) {
                    if (!windowHandle.equals(originalWindow)) {
                        driver.switchTo().window(windowHandle);
                        break;
                    }
                }

                // 4- Sonuçları bekle ve oku
                // TODO: Sonuç ürünlerinin ve stok bilgisinin locatorlarını güncelle!
                WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                // Stok durumu için örnek locator:
                String stockText = "";
                try {
                    WebElement stockElem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".stock-status"))); // örnek
                    stockText = stockElem.getText(); // stok bilgisini oku
                } catch (TimeoutException ex) {
                    stockText = "YOK";
                }

                // Ürün sayısı (bulunan ürün adedi)
                int foundCount = 0;
                try {
                    foundCount = driver.findElements(By.cssSelector(".product-card")).size(); // örnek ürün kartı locatoru
                } catch (Exception e) {
                    foundCount = 0;
                }

                // 5- Excel'e yaz
                Cell stockCell = row.getCell(1);
                if (stockCell == null) stockCell = row.createCell(1);
                stockCell.setCellValue(stockText);

                Cell countCell = row.getCell(2);
                if (countCell == null) countCell = row.createCell(2);
                countCell.setCellValue(foundCount);

                // 6- Stok uyuşmazlığı varsa kırmızıya boya
                int excelStock = 0;
                try { excelStock = Integer.parseInt(stockText.replaceAll("\\D", "")); } catch (Exception ignore) {}
                if (excelStock != foundCount) {
                    stockCell.setCellStyle(redBgStyle);
                    countCell.setCellStyle(redBgStyle);
                }
            } catch (Exception e) {
                // Hata olursa Excel'e bilgi yaz
                Cell stockCell = row.getCell(1);
                if (stockCell == null) stockCell = row.createCell(1);
                stockCell.setCellValue("HATA");

                Cell countCell = row.getCell(2);
                if (countCell == null) countCell = row.createCell(2);
                countCell.setCellValue(0);

                stockCell.setCellStyle(redBgStyle);
                countCell.setCellStyle(redBgStyle);
            }

            // Eğer yeni sekmeye geçtiysen, eski sekmeye dön
            if (driver.getWindowHandles().size() > 1) {
                String mainHandle = driver.getWindowHandles().iterator().next();
                driver.switchTo().window(mainHandle);
            }
        }

        fis.close();
        FileOutputStream fos = new FileOutputStream(excelPath);
        workbook.write(fos);
        fos.close();
        workbook.close();
    }



    @Test
    public void cagirmaOrnegi() throws IOException {

        WebDriver driver = Driver.getDriver() ;
        ExcelSearchAndUpdate.searchAndUpdateExcel(
                driver, // WebDriver örneği
                "https://loyalfriendcare.com", // Ana sayfa URL'in
                "C:\\Users\\Hp\\OneDrive\\Desktop\\loyalfriendcare\\urunler.xlsx", // Excel yolu
                "UrunListesi" // Sheet adı
        );


    }
}