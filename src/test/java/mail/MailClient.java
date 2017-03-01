package mail;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Anastasiya on 27.02.2017.
 */
public class MailClient {
    private String myEMail = "klient.pochtovyy@mail.ru";
    private static WebDriver driver = new FirefoxDriver();

    @Before
    public void setUp(){
        String urlMail = "https://mail.ru";
        driver.get(urlMail);
        WebElement login = driver.findElement(By.id("mailbox__login"));
        login.sendKeys(myEMail);
        WebElement password = driver.findElement(By.id("mailbox__password"));
        password.sendKeys("123456qwe");
        WebElement authButton = driver.findElement(By.id("mailbox__auth__button"));
        authButton.click();
    }

    @Test
    public void checkMailClient() {
        String themeString = "theme2";
        int numberLettersStart;
        int numberLettersEnd;

        WebElement writeLetterButton = waitWebDriver(20,".//*[@id='b-toolbar__left']//span[contains(@class,\"b-toolbar__btn\")]");
        writeLetterButton.click();
        numberLettersStart = getNumberLetters();
        WebElement inpuTextarea = driver.findElement(By.xpath("//input[@id='compose_to']/following-sibling::textarea[contains(@class, 'js-input')]"));
        inpuTextarea.sendKeys(myEMail);
        WebElement themeMail = driver.findElement(By.xpath(".//*[@class = \"compose-head__field\"]/*[@class = \"b-input\"]"));
        themeMail.sendKeys(themeString);
        WebElement send = driver.findElement(By.xpath(".//div[@data-name=\"send\"]//*[contains(@class,\"b-toolbar__btn\")]"));
        send.click();
        WebElement btnConfirmOk = waitWebDriver(10,".//*[@id='MailRuConfirm']//*[@class = \"is-compose-empty_in\"]//button[contains(@class, \"ok\")]");
        btnConfirmOk.click();

        WebElement incomingMessages = waitWebDriver(30,".//*[@id='b-nav_folders']//*[@data-id=\"0\"]/*[@class = \"b-nav__link\"]");
        incomingMessages.click();
        numberLettersEnd = getNumberLetters();
        Assert.assertTrue(numberLettersStart < numberLettersEnd);

        String themeStringEnd = driver.findElement(By.xpath(".//*[@id='b-letters']//*[@class = \"b-datalist__body\"]/div[1]//*[contains(@class, \"item__link\")]"))
                .getAttribute("data-subject");
        Assert.assertEquals(themeString, themeStringEnd);

        String myEMailCheck = driver.findElement(By.xpath(".//*[@id='b-letters']//*[@class = \"b-datalist__body\"]/div[1]//*[contains(@class, \"item__link\")]"))
                .getAttribute("title");
        Assert.assertTrue(myEMailCheck.contains(myEMail));

    }

    private int getNumberLetters() {
        WebElement numberLetters = waitWebDriver(10,".//*[@id='b-nav_folders']//*[@class=\"b-nav__item__count\"]");
        return Integer.parseInt(numberLetters.getText());
    }

    private WebElement waitWebDriver(int time, String path) {
        return (new WebDriverWait(driver, time))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(path)));
    }

    @After
    public void tearDown(){
        driver.close();
    }
 }
