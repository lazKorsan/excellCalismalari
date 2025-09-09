
        package tests;

import Pages.AdminPages;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.dataproviders.LoginDataProvider;

public class LoginTest {
    AdminPages adminPages = new AdminPages();

    @Test(
            dataProvider = "excelLoginData",
            dataProviderClass = LoginDataProvider.class,
            description = "Excel'den alınan kullanıcı bilgileriyle login testi"
    )
    public void testLogin(String email, String sifre) {
        // 1. Adım: Login sayfasına git
        Driver.getDriver().get("https://qa.loyalfriendcare.com/login");

        // 2. Adım: Excel'den gelen verilerle formu doldur
        adminPages.mailBox.sendKeys(email);
        adminPages.passwordBox.sendKeys(sifre);
        adminPages.loginPageSignInButton.click();

        if (adminPages.accountButton.isDisplayed()){
            adminPages.signOutButton.click();
            ReusableMethods.bekle(2);
            adminPages.signInButton.click();
        }else {
            System.out.println("giriş başarısız ");
        }


        // 3. Adım: Başarılı login kontrolü
        if(!email.isEmpty() && sifre.length() >= 6) {
            Assert.assertTrue(adminPages.accountButton.isDisplayed(),
                    "Giriş başarısız - Email: " + email + ", Şifre: " + sifre);
        } else {
            Assert.assertTrue(adminPages.mailBox.isDisplayed(),
                    "Hata mesajı gösterilmedi - Geçersiz veri: " + email);
        }
    }
}

