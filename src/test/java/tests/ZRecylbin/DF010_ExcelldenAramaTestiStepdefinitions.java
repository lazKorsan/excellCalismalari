package tests.ZRecylbin;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.Driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class DF010_ExcelldenAramaTestiStepdefinitions {

    private String satirdakiUrunIsmi;
    private int minUrunMiktari;
    private int bulunanUrunSayisi;

    @Test
    public void aramaTestiEXCELL(){

        Driver.getDriver().get("https://www.loyalfriendcare.com");

        try {
            // Excel dosya yolunu belirtin
            String excelDosyaYolu = "src/test/resources/features/wip/urunler.xlsx";
            FileInputStream fis = new FileInputStream(excelDosyaYolu);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0); // İlk sayfa

            String satirNumarasi = "";
            int satirNo = Integer.parseInt(satirNumarasi);
            Row row = sheet.getRow(satirNo);

            // Excel'den ürün adı ve min miktarı oku
            Cell urunAdiHucresi = row.getCell(0); // A sütunu
            Cell minMiktarHucresi = row.getCell(1); // B sütunu

            satirdakiUrunIsmi = urunAdiHucresi.getStringCellValue();
            minUrunMiktari = (int) minMiktarHucresi.getNumericCellValue();

            System.out.println("Excel'den okunan: " + satirdakiUrunIsmi + " - Min Miktar: " + minUrunMiktari);

            workbook.close();
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();

        }


        try {
            // Arama kutusunu bul ve ürünü ara
            WebElement aramaKutusu = Driver.getDriver().findElement(By.name("search"));
            aramaKutusu.clear();
            aramaKutusu.sendKeys(satirdakiUrunIsmi + Keys.ENTER);

            // Ürün sonuçlarını bekle (kısa bir bekleme süresi)
            Thread.sleep(2000);

            // Ürün sonuçlarını say
            List<WebElement> urunler = Driver.getDriver().findElements(By.cssSelector(".product-item"));
            bulunanUrunSayisi = urunler.size();

            System.out.println("Bulunan ürün sayısı: " + bulunanUrunSayisi);

        } catch (Exception e) {
            e.printStackTrace();
            bulunanUrunSayisi = 0;
        }


        Assert.assertTrue(  bulunanUrunSayisi >= minUrunMiktari);
    }







    }

