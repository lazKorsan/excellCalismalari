package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExcelReportHelper {
    // cagirma ornegi aşağıda

    private static final String REPORT_PATH = "C:\\Users\\Hp\\OneDrive\\Desktop\\loyalfriendcare\\reports\\";
    private static final String DEFAULT_SHEET_NAME = "Rapor";
    private static int reportCounter = 1;

    public static void writeProductsToExcel(
            WebDriver driver,
            String pageUrl,
            By productLocator,
            String excelFileName,
            String sheetName,
            String... headers
    ) {
        try {
            // 1. Dizin kontrolü ve oluşturma
            createReportDirectory();

            // 2. Web sayfasına git ve tabloyu al
            driver.get(pageUrl);
            List<WebElement> products = driver.findElements(productLocator);

            // 3. Excel dosyasını hazırla
            Workbook workbook = getOrCreateWorkbook(excelFileName);
            Sheet sheet = createSheet(workbook, sheetName);

            // 4. Başlık satırını oluştur
            createHeaderRow(sheet, headers, products.size());

            // 5. Verileri Excel'e yaz
            int rowNum = 1;
            for(WebElement product : products) {
                Row row = sheet.createRow(rowNum++);
                List<WebElement> cells = product.findElements(By.tagName("td"));
                for(int i = 0; i < cells.size(); i++) {
                    row.createCell(i).setCellValue(cells.get(i).getText());
                }
            }

            // 6. Dosyayı kaydet
            saveWorkbook(workbook, excelFileName);

        } catch (IOException e) {
            handleException("Excel oluşturma hatası: ", e);
        }
    }

    private static void createReportDirectory() throws IOException {
        if(!Files.exists(Paths.get(REPORT_PATH))) {
            Files.createDirectories(Paths.get(REPORT_PATH));
        }
    }

    private static Workbook getOrCreateWorkbook(String fileName) throws IOException {
        File file = new File(REPORT_PATH + fileName + ".xlsx");
        if(file.exists()) {
            return WorkbookFactory.create(file);
        }
        return new XSSFWorkbook();
    }

    private static Sheet createSheet(Workbook workbook, String sheetName) {
        String finalSheetName = sheetName != null ?
                sheetName + "_" + new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date()) :
                DEFAULT_SHEET_NAME + reportCounter++;

        return workbook.createSheet(finalSheetName);
    }

    private static void createHeaderRow(Sheet sheet, String[] headers, int productCount) {
        Row headerRow = sheet.createRow(0);
        for(int i=0; i<headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }
        headerRow.createCell(headers.length).setCellValue("Toplam Ürün: " + productCount);
    }

    private static void saveWorkbook(Workbook workbook, String fileName) throws IOException {
        try(FileOutputStream outputStream = new FileOutputStream(REPORT_PATH + fileName + ".xlsx")) {
            workbook.write(outputStream);
        }
    }

    private static void handleException(String message, Exception e) {
        System.err.println(message + e.getMessage());
        e.printStackTrace();
    }

    @Test
    public void excellReporterCagirma(){


        ExcelReportHelper.writeProductsToExcel(
                Driver.getDriver(),
                "https://ornek-e-ticaret-sitesi.com/urunler",
                By.cssSelector(".urun-listesi tr"),
                "UrunRaporu",
                "EylülRaporu",
                "Ürün Adı",
                "Fiyat",
                "Stok Durumu"
        );

    }
}
