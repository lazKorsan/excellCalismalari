package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcellProductReporter {
    private static final String REPORT_PATH = "C:\\Users\\Hp\\OneDrive\\Desktop\\loyalfriendcare\\reports\\";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");


    // < -- ===== excellReportBaslanici ===== -- >


    public static void generateMedicineReport(WebDriver driver, String categoryUrl,
                                              String productLocator, String filePath) {
        try {
            // 1. Ürün listesini çek
            driver.get(categoryUrl);
            List<WebElement> products = driver.findElements(By.xpath(productLocator));

            // 2. Yeni workbook oluştur
            Workbook workbook = new XSSFWorkbook();

            // 3. Stilleri oluştur
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle dateStyle = createDateStyle(workbook);
            CellStyle defaultStyle = createDefaultStyle(workbook);

            // 4. Sayfa oluştur
            Sheet sheet = workbook.createSheet("Yatak Listesi");

            // 5. Rapor başlıkları
            createReportHeaders(sheet, headerStyle);

            // 6. Rapor bilgileri
            addReportInfo(sheet, defaultStyle, products.size(), categoryUrl);

            // 7. İlaç verilerini yaz
            writeMedicineData(sheet, products, dateStyle, defaultStyle);

            // 8. Sütun genişliklerini ayarla
            autoSizeColumns(sheet);

            // 9. Dosyayı kaydet
            saveWorkbook(workbook, filePath);

            System.out.println("Rapor oluşturma tamamlandı: " + filePath);
            System.out.println("Toplam " + products.size() + " ilaç kaydedildi.");

        } catch (Exception e) {
            System.err.println("Rapor oluşturma hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private static CellStyle createDateStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        style.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private static CellStyle createDefaultStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        return style;
    }

    private static void createReportHeaders(Sheet sheet, CellStyle headerStyle) {
        Row headerRow = sheet.createRow(3);
        String[] headers = {"Sıra No", "İlaç Adı", "Eklenme Tarihi", "Durum", "Notlar"};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private static void addReportInfo(Sheet sheet, CellStyle style, int productCount, String categoryUrl) {
        // Rapor başlığı
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("LOYAL FRIEND CARE - YATAK LİSTESİ  RAPORU");
        titleCell.setCellStyle(style);

        // Rapor tarihi
        Row dateRow = sheet.createRow(1);
        dateRow.createCell(0).setCellValue("Rapor Tarihi: " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));

        // Toplam ilaç sayısı
        Row countRow = sheet.createRow(1);
        countRow.createCell(2).setCellValue("Toplam İlaç: " + productCount);

        // URL bilgisi
        Row urlRow = sheet.createRow(2);
        urlRow.createCell(0).setCellValue("Kaynak: " + categoryUrl);
    }

    private static void writeMedicineData(Sheet sheet, List<WebElement> products,
                                          CellStyle dateStyle, CellStyle defaultStyle) {
        Pattern datePattern = Pattern.compile("\\d{2}-\\d{2}-\\d{4}");
        int startRow = 4;

        for (int i = 0; i < products.size(); i++) {
            String productText = products.get(i).getText();
            String ilacAdi = productText;
            String eklenmeTarihi = "";
            String durum = "Aktif";

            // Tarihi ayır
            Matcher matcher = datePattern.matcher(productText);
            if (matcher.find()) {
                eklenmeTarihi = matcher.group();
                ilacAdi = productText.replace(eklenmeTarihi, "").replace("\n", "").trim();
            }

            // Satır oluştur
            Row row = sheet.createRow(startRow + i);

            // Sıra No
            Cell cell0 = row.createCell(0);
            cell0.setCellValue(i + 1);
            cell0.setCellStyle(defaultStyle);

            // İlaç Adı
            Cell cell1 = row.createCell(1);
            cell1.setCellValue(ilacAdi);
            cell1.setCellStyle(defaultStyle);

            // Eklenme Tarihi
            Cell cell2 = row.createCell(2);
            cell2.setCellValue(eklenmeTarihi);
            cell2.setCellStyle(dateStyle);

            // Durum
            Cell cell3 = row.createCell(3);
            cell3.setCellValue(durum);
            cell3.setCellStyle(defaultStyle);

            // Notlar (boş bırakılabilir)
            row.createCell(4).setCellValue("");
        }
    }

    private static void autoSizeColumns(Sheet sheet) {
        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private static void saveWorkbook(Workbook workbook, String filePath) throws IOException {
        // Klasörü kontrol et ve oluştur
        java.io.File directory = new java.io.File(filePath).getParentFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            workbook.write(fileOutputStream);
            workbook.close();
        }
    }

    // Farklı isimlerle rapor oluşturmak için yardımcı method
    public static String generateCustomReport(WebDriver driver, String categoryUrl,
                                              String productLocator, String reportName) {
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        String fileName = reportName + "_" + timestamp + ".xlsx";
        String fullPath = REPORT_PATH + fileName;

        generateMedicineReport(driver, categoryUrl, productLocator, fullPath);
        return fullPath;
    }


    // < -- ===== excellReportSonu ===== -- >

}
