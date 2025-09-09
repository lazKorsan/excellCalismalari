package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class ExcelUtils {

    //  %%%exell yolu görüyor

    /**
     * Excel'den ürünleri okur, site üzerinde arar,
     * stok karşılaştırmasını yapar ve sonuçları geri döndürür.
     *
     * @param driver WebDriver
     * @param excelPath Excel dosya yolu
     * @param sheetName Çalışılacak sayfa adı
     * @param productColIndex Ürün adı sütunu
     * @param stockColIndex Stok sütunu
     * @return Map<ÜrünAdı, SonuçObjesi>
     */
    public static Map<String, SearchResult> checkStockWithSite(
            WebDriver driver, String excelPath, String sheetName, int productColIndex, int stockColIndex) {

        Map<String, SearchResult> results = new LinkedHashMap<>();

        try (FileInputStream fis = new FileInputStream(excelPath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sayfa bulunamadı: " + sheetName);
            }

            // Kırmızı stil
            CellStyle redStyle = workbook.createCellStyle();
            redStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
            redStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell productCell = row.getCell(productColIndex);
                Cell stockCell = row.getCell(stockColIndex);

                if (productCell == null || productCell.getCellType() != CellType.STRING) continue;
                if (stockCell == null || stockCell.getCellType() != CellType.NUMERIC) continue;

                String productName = productCell.getStringCellValue().trim();
                int excelStock = (int) stockCell.getNumericCellValue();

                if (productName.isEmpty()) continue;

                try {
                    // Web’de arama
                    driver.get(ConfigReader.getProperty("lfc"));

                    WebElement searchBox = new WebDriverWait(driver, Duration.ofSeconds(15))
                            .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='search']")));
                    searchBox.clear();
                    searchBox.sendKeys(productName);

                    WebElement searchButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                            .until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
                    searchButton.click();

                    new WebDriverWait(driver, Duration.ofSeconds(15))
                            .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product-item")));

                    // Son URL
                    String finalUrl = driver.getCurrentUrl();

                    // Sitede bulunan ürün adedi
                    int siteCount = driver.findElements(By.cssSelector(".product-item")).size();

                    // Excel’e yaz
                    Cell siteResultCell = row.createCell(stockColIndex + 1);
                    siteResultCell.setCellValue(siteCount);

                    Cell urlCell = row.createCell(stockColIndex + 2);
                    urlCell.setCellValue(finalUrl);

                    // Karşılaştırma → eğer Excel stok > siteCount ise kırmızı yap
                    if (excelStock > siteCount) {
                        stockCell.setCellStyle(redStyle);
                        siteResultCell.setCellStyle(redStyle);
                        urlCell.setCellStyle(redStyle);
                    }

                    // Sonucu Map’e ekle
                    results.put(productName, new SearchResult(productName, excelStock, siteCount, finalUrl));

                } catch (Exception e) {
                    System.out.println("Satır " + i + " için hata: " + e.getMessage());
                    Cell errorCell = row.createCell(stockColIndex + 1);
                    errorCell.setCellValue("HATA");
                    errorCell.setCellStyle(redStyle);

                    results.put(productName, new SearchResult(productName, excelStock, 0, "HATA"));
                }
            }

            // Excel’i kaydet
            try (FileOutputStream fos = new FileOutputStream(excelPath)) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            throw new RuntimeException("Excel işlem hatası: " + e.getMessage());
        }

        return results;
    }

    // DTO Class → Testlerde kullanmak için
    public static class SearchResult {
        public String productName;
        public int excelStock;
        public int siteCount;
        public String finalUrl;

        public SearchResult(String productName, int excelStock, int siteCount, String finalUrl) {
            this.productName = productName;
            this.excelStock = excelStock;
            this.siteCount = siteCount;
            this.finalUrl = finalUrl;
        }
    }

    @Test
    public void testExcelStockVsSite() {
        // %%%excell yolu görüyor

        Driver.getDriver().get(ConfigReader.getProperty("lfc"));
        String excelPath = "C:\\Users\\Hp\\OneDrive\\Desktop\\loyalfriendcare\\urunler.xlsx";
        Map<String, ExcelUtils.SearchResult> results =
                ExcelUtils.checkStockWithSite(Driver.getDriver(), excelPath, "Sayfa1", 0, 1);

        // Assert örneği → her ürün için stok >= siteCount olmalı
        for (ExcelUtils.SearchResult result : results.values()) {
            Assert.assertTrue(result.excelStock >= result.siteCount,
                    "Stok yetersiz! Ürün: " + result.productName +
                            " Excel stok: " + result.excelStock +
                            " Site sayısı: " + result.siteCount +
                            " URL: " + result.finalUrl);
        }
    }
}