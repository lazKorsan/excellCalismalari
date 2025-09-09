package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class HeaderPages {
    public HeaderPages() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    // < -- === HEADER LOCATERS === -- >

    @FindBy(xpath = "//*[@class='logo_normal']")  //1
    public WebElement logoButton ;

    @FindBy(xpath = "(//a[@href='https://qa.loyalfriendcare.com'])[5]")  //2
    public WebElement homeButton ;


    @FindBy(xpath = "(//a[@href='https://qa.loyalfriendcare.com/about'])[2]")  // 3
    public WebElement aboutUsButton ;

    @FindBy(xpath = "(//a[@href='https://qa.loyalfriendcare.com/Departments'])[3]")  //4
    public WebElement departmentsButton ;

    @FindBy(xpath = "(//a[@href='https://qa.loyalfriendcare.com/Doctors'])[3]")  //5
    public WebElement doctorsButton ;

    @FindBy(xpath = "(//a[@href='https://qa.loyalfriendcare.com/Medicines'])[2]")  // 6
    public WebElement medicinesButton ;

    @FindBy(xpath = "(//a[@href='https://qa.loyalfriendcare.com/Pets'])[3]")// 7
    public WebElement vaccinationsButton ;

    // <--==============Header SıgnButtons =============== -->

    @FindBy(xpath = "(//a[@class='btn_add'])[1]")
    public WebElement signInButton ;

    @FindBy(xpath = "(//a[@class='btn_add'])[2]")   // 8
    public WebElement signUpButton ;

    // giriş yapıldıktan sonra kullanılacak locaters

    @FindBy(xpath = "(//a[@class='btn_add'])[1]")
    public WebElement accountButton ;

    // < -- === header  sıgnUp buttonu ile sıgnOutButtonu Lacaterları aynı
    @FindBy(xpath = "(//a[@class='btn_add'])[2]")   // 8
    public WebElement signOutButton ;


    // < -- ===adminPage  Header profileButton
    @FindBy(xpath = "//div[@class='dropdown pull-right d-lg-block d-none']")
    public WebElement profileButton ;

    // < -- === adminPage Header profil dropDownMenü
    @FindBy(xpath = "(//a[@class='dropdown-item'])[1]")
    public WebElement settingsButton;

    // < -- === adminPage Header profil dropDownMenü
    @FindBy(xpath = "(//a[@class='dropdown-item'])[2]")
    public WebElement editProfileButton ;

    // < -- === adminPage Header profil dropDownMenü
    @FindBy(xpath = "//*[@class='pull-left']")
    public WebElement logOutButton;

    // < - === edit sayfası element butonu
    @FindBy(xpath = "//div[@class='error-container text-center']")
    public WebElement errorContainerWebelement  ;

    // < - === adminPage dashboard locaters
    @FindBy(xpath = "//div[@class='sidebar-header']")
    public WebElement dashBoard ;

    // <-- register sayfası loceters

    @FindBy(xpath = "//*[@id=\"name\"]") // 9
    public WebElement userNameBox ;

    @FindBy(xpath = "//*[@id=\"email\"]") //10
    public WebElement mailBox ;

    @FindBy(xpath = "//*[@id=\"password\"]") //11
    public WebElement passwordBox ;

    @FindBy(xpath = "//*[@id=\"password-confirm\"]") //12
    public WebElement confirmPasswordBox ;

    @FindBy(xpath = "/html/body/div[1]/div[2]/div/form/button") //13
    public WebElement registerButton ;

    // < -- loginPage Locaters
    // SıgnInButtons
    @FindBy(xpath = "/html/body/div/div[2]/div/form/button") //14
    public WebElement loginPageSigInButton ;

    // mailBoxButton    register sayfası ile aynı

    // passwordBoxButton  register sayfası ile aynı

    @FindBy(xpath = "(//div[@class='checkbox'])") //15
    public WebElement rememberMeButton ;

    @FindBy(xpath = "//*[@class='text-info small']") //16
    public WebElement forgotPasswordButton ;
    // < -- =====forgot/reset  page Locaters========== -- >

    @FindBy(xpath = "(//*[@class='nav-link'])[1]")
    public WebElement passwordResetPageLoginButton ;

    @FindBy (xpath = "(//*[@class='nav-link'])[2]")
    public WebElement passwordResetPageRegisterButton ;

   // mailBox locater  sigIn sayfasındaki mailBox ile aynı

    @FindBy(xpath = "//*[@type='submit']")
    public WebElement sendPassWordLinkButton ;



    // <-- ==========Arama Kutusu ==============  -->
    // < -- ==== LOCATER VALUE "//input[@type='text']"
    // < -- === OLURSA ADMİN SAYFASINDA İŞLİYOR
    @FindBy(xpath = "//input[@type='text']") //17
    public WebElement searchBox ; // ADMİNDE İŞLEMEZ  : //*[@name='search']
    //< -- ====^^^^^^==== -- >

    @FindBy(xpath = "//*[@class='container'][1]") //18
    public WebElement titleContainer ;

    @FindBy(xpath = "//img[@class='img-fluid']") //19
    public WebElement searchResult ;

    @FindBy(xpath = "//input[@value='Search']") //19
    public WebElement searchButton ;

   @FindBy(xpath = "//div[@class='detail_title_1']/preceding-sibling::h1")  //20
   public WebElement searchResultElementi ;

    //<-- ========== Body locaters ============ -->
    // < -- === BÜTÜN BODY KISMINDA EKLENEN BUTTONLAR İÇİN === -->
    // < -- === LOCATE KAYMASINI ENGELLEYİCİ XPATH LER === -- >

    @FindBy(xpath = "//*[normalize-space(text())='Wellness']") //21
    public WebElement wellnessButton;

    @FindBy(xpath = "//*[normalize-space(text())='Dental Care']") //22
    public WebElement dentalCareButton ;

    @FindBy(xpath = "//*[normalize-space(text())='Anaesthesia']") //23
    public WebElement anaesthesiaButton ;

    @FindBy(xpath = "//*[normalize-space(text())='Dermatology']") // 24
    public WebElement dermatologyButton ;

    @FindBy(xpath = "//*[normalize-space(text())='Diagnostics']") //25
    public WebElement diagnosticsButton ;

    @FindBy(xpath = "(//*[text()='Vaccinations'])[3]") //26
    public WebElement bodyvaccinationsButton;

    @FindBy(xpath = "//*[normalize-space(text())='Pain Control']") //27
    public WebElement painControlButton;

    @FindBy(xpath = "//*[normalize-space(text())='Boarding']") //28
    public WebElement boardingButton ;

    @FindBy(xpath = "//*[normalize-space(text())='ilave']") //29
    public WebElement ilaveButton;

    @FindBy(xpath = "//*[normalize-space(text())='yeni']") //30
    public WebElement yeniButton ;

    // <-- ==============Body mini buttons ================ -->
    @FindBy(xpath = "(//a[@href='https://qa.loyalfriendcare.com/Departments'])[2]") //31
    public WebElement miniDepartmentsButton ; // LİNKSİZ XPATH ALINABİLİR

    @FindBy(xpath = "(//a[@href='https://qa.loyalfriendcare.com/Doctors'])[2]") //32
    public WebElement miniDoctorsButton ; // LİNKSİZ XPATH ALINABİLİR

    @FindBy(xpath = "(//a[@href='https://qa.loyalfriendcare.com/Pets'])[2]") //33
    public WebElement miniMainVaccinationsButton ; // LİNKSİZ XPATH ALINABİLİR

    // <-- =============Footer DepartmentsButtons ============= -->

    // <-- =============Body popularDoctors Buttons =========== -->

    @FindBy(xpath = "//*[@alt='Dr. Alejandro Martinez']") //34
    public WebElement DrAlejandroMartinezButton;

    @FindBy(xpath = "//*[@alt='Dr. Marcus Rodriguez']") //35
    public WebElement DrMarcusRodriguezButton;

    @FindBy(xpath = "//*[@alt='Dr. Olivia Bennett']") //36
    public WebElement DrOliviaBennettButton;

    @FindBy(xpath = "//*[@alt='Dr. Emily Chang']") //37
    public WebElement DrEmilyChangButton;

    @FindBy(xpath = "//*[@alt='Dr. Nathan Patel']") //38
    public WebElement DrNathanPatelButton;

    @FindBy(xpath = "//*[@alt='Dr. Isabella Wong']") //39
    public WebElement DrIsabellaWongButton;

    @FindBy(xpath = "//*[contains(@alt,'Liam')]") //40
    public WebElement DrLiamOConnerButton;


    @FindBy(xpath = "//*[@alt='Dr. Sophia Kim']") //41
    public WebElement DrSophiaKimButton;

    @FindBy(xpath = "//img[@alt='Mr ALi']") //42
    public WebElement MrAliButton;

    // <-- =========== Body VacionationsofPetsButtons locates======== -->

    @FindBy(xpath = "//*[@alt='Rabies Vaccine']") //43
    public WebElement rabiesVaccineButton ;

    @FindBy(xpath = "//div[*='DHPP Vaccine (Distemper, Hepatitis, Parainfluenza, Parvovirus Vaccine):']") //44
    public WebElement DHPPVaccineButton ;

    @FindBy(xpath = "//div[*='Feline Leukemia Vaccine']") //45
    public WebElement felineLeukemiaVaccineButton ;


    @FindBy(xpath = "//div[*='Feline Immunodeficiency Virus (FIV) Vaccine']") //46
    public WebElement felineImmunoeficenyVirusButton ;

    @FindBy(xpath = "//div[*='Bordetella (Kennel Cough) Vaccine']") //47
    public WebElement bordetellaVaccineButton ;

    @FindBy(xpath = "//div[*='Feline Panleukopenia Vaccine']") //48
    public WebElement felinePanleukopeniaVaccineButton ;

    @FindBy(xpath = "//div[*='Feline Herpesvirus Vaccine']") //49
    public WebElement felineHerpesvirusVaccineButton ;

    @FindBy(xpath = "//div[*='Surgical Procedure: Spaying (Ovariohysterectomy)']") //50
    public WebElement surgicalProcedureButton ;

    // < --==============footer locates============-->
    @FindBy(xpath = "//*[@title='LoyalFriendCare - Pet Care & Veterinary ']") //51
    public WebElement footerLogoButtons ;

    @FindBy(xpath = "//a[contains(text(),'Wellness')]") //52
    public WebElement footerWellnesButton ;

    @FindBy(xpath = "//a[contains(text(),' Dental Care')]") //53
    public WebElement footerDentalCareButton  ;

    @FindBy(xpath = "//a[contains(text(),'Anaesthesia')]") //54
    public WebElement footerAnaesthesiaButton ;

    @FindBy(xpath = "//a[contains(text(),'Dermatology')]") //55
    public WebElement footerDermatologyButton ;

    @FindBy(xpath = "//a[contains(text(),'Diagnostics')]") //56
    public WebElement footerDiagnosticsButton ;

    @FindBy(xpath = "//*[@class='fab fa-facebook-square']") //57
    public WebElement footerFacebookButton ;

    @FindBy(xpath = "//*[@class='fab fa-twitter-square']") //58
    public WebElement footerXButton ;

    @FindBy(xpath = "//*[@class='fab fa-youtube-square']") //59
    public WebElement footerYoutubeButton ;

    @FindBy(xpath = "//*[@class='fab fa-pinterest-square']") //60
    public WebElement footerPinterestButton ;

    @FindBy(xpath = "//*[@class='fab fa-instagram']") //61
    public WebElement footerInstagramButton ;

    // <-- ================aboutUs pages Locaters============== -->

    @FindBy(xpath = "//*[@class='fas fa-money-check-alt']") //62
    public WebElement budgetVetCareButton ;

    @FindBy(xpath = "//*[@class='fas fa-dog']") //63
    public WebElement petShelterButton ;

    @FindBy(xpath = "//*[@class='fas fa-certificate']") //64
    public WebElement certifiedVetButton ;

    @FindBy(xpath = "//*[@class='fas fa-paw']") //65
    public WebElement nutritionShop ;

    // <-- ========== contactPage Locaters =============== -->

    @FindBy(xpath = "//*[@id='Date']") //66
    public WebElement dateButton ;

    @FindBy(xpath = "//*[@placeholder='Phone Number']") //67
    public WebElement phoneBox ;

    @FindBy(xpath = "//*[@name='category_id']") //68
    public WebElement departmentButton ;

    @FindBy(xpath = "//*[@class='nice-select wide']") //69
    public WebElement selectDoctorsButton ;

    @FindBy(xpath = "//*[@placeholder='Create Message']") //70
    public WebElement messageBox ;

    @FindBy(xpath = "//*[@value='Submit']") //71
    public WebElement submitButton ;  // bir tane daha var mı kontrol edilecek

    // < -- === HEADER DEPARTMENTS DROPDOWNMENU LOCATE === - >

    @FindBy(xpath = "//*[text()='Wellness']")
    public WebElement ddWelnesButton ;

    @FindBy(xpath = "//*[text()='Dental Care']")
    public WebElement ddDentalCareButton ;

    @FindBy(xpath = "//*[text()='Anaesthesia']")
    public WebElement ddAnaesthesia ;

    @FindBy(xpath = "//*[text()='Dermatology']")
    public WebElement ddDermatology ;

    @FindBy(xpath = "//*[text()='Diagnostics']")
    public WebElement ddDiagnostics ;

    // < -- === Medicines Sayfası Locater === - >

    @FindBy(xpath = "(//div[@class='wrapper'])[1]")
    public WebElement rimadylButton            ;

    @FindBy(xpath = "(//div[@class='wrapper'])[2]")
    public WebElement revolutionButton            ;

    @FindBy(xpath = "(//div[@class='wrapper'])[3]")
    public WebElement baytrilButton            ;

    @FindBy(xpath = "(//div[@class='wrapper'])[4]")
    public WebElement apoquelButton            ;

    @FindBy(xpath = "(//div[@class='wrapper'])[5]")
    public WebElement metacamButton            ;

    @FindBy(xpath = "(//div[@class='wrapper'])[6]")
    public WebElement alpinaButton            ;

    @FindBy(xpath = "(//div[@class='container'])[1]")
    public WebElement statusBar ;

    @FindBy(xpath = "//textarea[@name='problem']")
    public WebElement messageTextarea;

    @FindBy(xpath = "//*[@id='submit-contact-detail']")
    public WebElement appoinmentBookingButton ;

    @FindBy(xpath = "//*[@class='alert alert-success']")
    public WebElement alertSuccesText ;





































}
