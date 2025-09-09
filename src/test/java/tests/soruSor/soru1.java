package tests.soruSor;

import Pages.AdminPages;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;
import utilities.Driver;
import utilities.ReusableMethods;

public class soru1 {
    AdminPages adminPages = new AdminPages();
    // merhaba abi .
    // bana bir kaç method lazım
    @Test
    public void exceldenAramaveSonuclariExcelleKyitEtme(){

        // loyalfirencare.com ana sayfaya gidiyorum
        Driver.getDriver().get("https://qa.loyalfriendcare.com/en");

        // arama kutusuna excellden alınan aranaacak ürün giriyorum
        // excell dosya yolu:
        // C:\Users\Hp\OneDrive\Desktop\loyalfriendcare/urunler.xlsx
        // excell de  1nci satırda başlıklar var
        // aranacak urun , stok sayısi, ve arama sonucu
        adminPages.searchBox.sendKeys("a"+ Keys.ENTER);
        // urunler çıkıyor.
        // çıkan urun sayısını excelle kayt ediyorum
        // çıkan urun sayısı stoktakinden fazla ise kırmızıya boyuyorum
        // stok sayısı eşit yada fazla ise yeşile boyuyorum
        // sonra back yapıyorum ikinci aramanın başlamsaı için ,
        Driver.getDriver().navigate().back();

        // ikinci aramayı başlatıyorum
        // ve böylece excel deki arama satırı bitene kadar devam ediyorum

        // BURADA back yapıyorum

        // excell ile ilgili her hangi bir depndcy eksiği yok
        // excell den login testi yaptım .

        // bu kafama çok takıldı
        // bunu yapabilirsem derse devam edeceğim

        //




    }


}
