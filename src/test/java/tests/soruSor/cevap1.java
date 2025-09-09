package tests.soruSor;

import Pages.AdminPages;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import utilities.Driver;
import utilities.ReusableMethods;

import java.io.*;
import java.util.List;

public class cevap1 {
    AdminPages adminPages = new AdminPages();
    String excelPath = "C:\\Users\\Hp\\OneDrive\\Desktop\\loyalfriendcare\\urunler.xlsx";

    @Test
    public void exceldenAramaveSonuclariExcelleKyitEtme() throws IOException {
        // Excel dosyasını aç
        FileInputStream fis = new FileInputStream(excelPath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        // Ana sayfaya git
        Driver.getDriver().get("https://qa.loyalfriendcare.com/en");

        // Excel'deki her satır için işlem yap (başlık satırını atla)
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            // Excel'den ürün adını al
            String urunAdi = row.getCell(0).getStringCellValue();

            // Excel'den stok sayısını al
            int stokSayisi = (int) row.getCell(1).getNumericCellValue();

            // Arama kutusuna ürün adını yaz ve ara
            adminPages.searchBox.clear();
            adminPages.searchBox.sendKeys(urunAdi + Keys.ENTER);

            // Ürünlerin yüklenmesini bekle
            ReusableMethods.bekle(2);

            // Çıkan ürün sayısını al (sayfadaki ürün elementlerini say)
            List<WebElement> urunler = adminPages.urunListesi; // Bu elementi Pages class'ında tanımlamanız gerekiyor
            int cikanUrunSayisi = urunler.size();

            // Çıkan ürün sayısını Excel'e yaz
            row.createCell(2).setCellValue(cikanUrunSayisi);

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
            Driver.getDriver().navigate().back();
            ReusableMethods.bekle(1);
        }

        // Değişiklikleri Excel'e kaydet
        FileOutputStream fos = new FileOutputStream(excelPath);
        workbook.write(fos);

        // Kaynakları kapat
        fos.close();
        workbook.close();
        fis.close();

        // Driver'ı kapat
        Driver.closeDriver();
    }
}