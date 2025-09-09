package tests.soruSor;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public class cevap1_ExcelEntegreliUrunArama {

    public static void main(String[] args) throws IOException, InterruptedException {
        // ChromeDriver path'ini ayarlayın
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        // WebDriver'ı başlat
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(10)
        );

        // Excel dosyasını aç
        String excelPath = "C:\\Users\\Hp\\OneDrive\\Desktop\\loyalfriendcare\\urunler.xlsx";
        FileInputStream fis = new FileInputStream(excelPath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        // Web sitesine git
        driver.get("https://qa.loyalfriendcare.com/en");

        // Excel'deki her satır için işlem yap (başlık satırını atla)
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            if (row == null) continue;

            // Excel'den ürün adını al
            String urunAdi = row.getCell(0).getStringCellValue();

            // Excel'den stok sayısını al
            int stokSayisi = (int) row.getCell(1).getNumericCellValue();

            // Arama kutusunu bul ve ürün adını yaz
            WebElement searchBox = driver.findElement(By.name("search")); // Doğru selector'ü kullanın
            searchBox.clear();
            searchBox.sendKeys(urunAdi + Keys.ENTER);

            // Sayfanın yüklenmesini bekle
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-list"))); // Doğru selector'ü kullanın

            // Ürün listesini al
            List<WebElement> urunler = driver.findElements(By.cssSelector(".product-item")); // Doğru selector'ü kullanın
            int cikanUrunSayisi = urunler.size();

            // Çıkan ürün sayısını Excel'e yaz
            if (row.getCell(2) == null) {
                row.createCell(2).setCellValue(cikanUrunSayisi);
            } else {
                row.getCell(2).setCellValue(cikanUrunSayisi);
            }

            // Stok durumuna göre hücre rengini ayarla
            Cell sonucCell = row.getCell(2);
            CellStyle style = workbook.createCellStyle();

            if (cikanUrunSayisi > stokSayisi) {
                // Kırmızı renk
                style.setFillForegroundColor(IndexedColors.RED.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            } else {
                // Yeşil renk
                style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            }
            sonucCell.setCellStyle(style);

            // Bir sonraki arama için geri git
            driver.navigate().back();

            // Kısa bir bekleme süresi ekle
            Thread.sleep(1000);
        }

        // Değişiklikleri Excel'e kaydet
        FileOutputStream fos = new FileOutputStream(excelPath);
        workbook.write(fos);

        // Kaynakları kapat
        fos.close();
        workbook.close();
        fis.close();

        // Driver'ı kapat
        driver.quit();

        System.out.println("İşlem başarıyla tamamlandı!");
    }
}