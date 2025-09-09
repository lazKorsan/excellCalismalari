package utilities;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelProductWriter {

    // < -- ===== excelle listeyi çeker Method başlangıcı

    public static void writeProductsToExcel(WebDriver driver, String categoryUrl, String productLocator, int baslangicSatir) {
        try {
            driver.get(categoryUrl);
            List<WebElement> products = driver.findElements(By.xpath(productLocator));
            String dosyaYolu = "C:\\Users\\Hp\\OneDrive\\Desktop\\loyalfriendcare\\bedManagerList.xlsx";

            Workbook workbook;
            Sheet sheet;

            try {
                FileInputStream fileInputStream = new FileInputStream(dosyaYolu);
                workbook = WorkbookFactory.create(fileInputStream);
                sheet = workbook.getSheetAt(0);
                fileInputStream.close();
            } catch (IOException e) {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("bedManagerList");
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("İlaç Adı");
                headerRow.createCell(1).setCellValue("Eklenme Tarihi");
            }

            Pattern datePattern = Pattern.compile("\\d{2}-\\d{2}-\\d{4}");

            for(int i = 0; i < products.size(); i++) {
                String productText = products.get(i).getText();
                String ilacAdi = productText;
                String eklenmeTarihi = "";

                Matcher matcher = datePattern.matcher(productText);
                if (matcher.find()) {
                    eklenmeTarihi = matcher.group();
                    ilacAdi = productText.replace(eklenmeTarihi, "").replace("\n", "").trim();
                }

                Row row = sheet.getRow(baslangicSatir + i);
                if(row == null) {
                    row = sheet.createRow(baslangicSatir + i);
                }

                row.createCell(0).setCellValue(ilacAdi);
                row.createCell(1).setCellValue(eklenmeTarihi);
            }

            try (FileOutputStream fileOutputStream = new FileOutputStream(dosyaYolu)) {
                workbook.write(fileOutputStream);
            }

            workbook.close();
            System.out.println("Toplam " + products.size() + " ürün Excel'e kaydedildi.");

        } catch (IOException e) {
            System.err.println("Excel kaydetme hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // < -- ===== excelle Liste çekme sonu  -- ===== >

}
