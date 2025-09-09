package utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class webtablodaUrunAramak {
    /**
     * BELİRTİLEN TABLO İÇİNDE BİR METNİ ARAR VE SONUCU RAPORLAYARAK DOĞRULAR.
     * @param driver WebDriver nesnesi
     * @param targetUrl Kontrol edilecek sayfanın URL'si (opsiyonel - null geçilebilir)
     * @param tableRowsLocator Tablodaki TÜM SATIRLARI veren XPath (//table//tr gibi)
     * @param searchText Aranacak metin
     * @param columnIndex (Opsiyonel) Aramayı belirli bir sütunda sınırlamak için.
     *                    Verilmezse (-1) tüm satırın metninde arar.
     * @return boolean - Metin bulunursa true, bulunamazsa Assertion hatası fırlatır.
     */
    public static boolean verifyTextInTable(WebDriver driver,
                                            String targetUrl,
                                            String tableRowsLocator,
                                            String searchText,
                                            int... columnIndex) {

        // 1. Eğer URL verilmişse, o sayfaya git
        if (targetUrl != null && !targetUrl.isEmpty()) {
            driver.get(targetUrl);
            System.out.println("Sayfaya gidildi: " + targetUrl);

            // Sayfanın yüklenmesini bekle
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 2. Bekleme mekanizması - Tablonun yüklenmesini bekle
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        List<WebElement> allRows;

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(tableRowsLocator)));
            allRows = driver.findElements(By.xpath(tableRowsLocator));
            System.out.println("Toplam satır sayısı: " + allRows.size());

            // DEBUG: İlk 3 satırı göster
            for (int i = 0; i < Math.min(3, allRows.size()); i++) {
                System.out.println("DEBUG - Satır " + (i+1) + ": " + allRows.get(i).getText());
            }

        } catch (TimeoutException e) {
            Assert.fail("TABLO BULUNAMADI! Verilen locator: " + tableRowsLocator);
            return false;
        }

        // 3. Hangi sütunda arama yapılacağını belirle (Varargs handling)
        int searchColumn = columnIndex.length > 0 ? columnIndex[0] : -1;

        // 4. Satırları döngü ile ara
        boolean isFound = false;
        int foundAtRow = -1;
        String foundText = "";

        for (int i = 0; i < allRows.size(); i++) {
            try {
                WebElement currentRow = allRows.get(i);
                String rowTextToCheck;

                if (searchColumn == -1) {
                    // Tüm satırda ara
                    rowTextToCheck = currentRow.getText().trim();
                } else {
                    // Belirli bir sütunda ara
                    List<WebElement> cells = currentRow.findElements(By.xpath(".//td | .//th"));
                    if (cells.size() > searchColumn) {
                        rowTextToCheck = cells.get(searchColumn).getText().trim();
                    } else {
                        rowTextToCheck = "";
                    }
                }

                System.out.println("Aranıyor: Satır " + (i+1) + " -> " + rowTextToCheck);

                // Metni kontrol et (tam eşleşme yerine contains kullan)
                if (rowTextToCheck.contains(searchText)) {
                    isFound = true;
                    foundAtRow = i + 1;
                    foundText = rowTextToCheck;
                    System.out.println("✅ BULUNDU! '" + searchText + "' metni " + foundAtRow + ". satırda yer alıyor.");
                    break;
                }

            } catch (StaleElementReferenceException e) {
                // Element eskimis, yeniden bul
                System.out.println("Stale element, yeniden bulunuyor...");
                allRows = driver.findElements(By.xpath(tableRowsLocator));
                i--; // Index'i azalt ki bu satırı tekrar kontrol et
                continue;
            }
        }

        // 5. Raporlama ve Assertion
        if (isFound) {
            System.out.println("✅ DOĞRULAMA BAŞARILI: '" + searchText + "' tabloda bulundu.");
            System.out.println("   Bulunduğu satır: " + foundAtRow);
            System.out.println("   Satır içeriği: " + foundText);
            return true;
        } else {
            String errorMessage = "❌ DOĞRULAMA BAŞARISIZ: '" + searchText + "' metni tabloda bulunamadı!\n" +
                    "   Arama yapılan locator: " + tableRowsLocator + "\n" +
                    "   Toplam tarama yapılan satır: " + allRows.size();
            System.out.println(errorMessage);

            // Tüm tabloyu yazdır
            printTableContents(driver, tableRowsLocator);

            Assert.fail(errorMessage);
            return false;
        }
    }

    /**
     * TABLO İÇERIĞINI YAZDIRIR (DEBUG İÇİN)
     */
    private static void printTableContents(WebDriver driver, String tableRowsLocator) {
        System.out.println("--- TABLO İÇERİĞİNİN TAMAMI ---");
        List<WebElement> rows = driver.findElements(By.xpath(tableRowsLocator));
        for (int i = 0; i < rows.size(); i++) {
            String rowText = rows.get(i).getText().trim();
            if (!rowText.isEmpty()) {
                System.out.println((i + 1) + ". satır: " + rowText);
            }
        }
        System.out.println("-----------------------------");
    }
}




















