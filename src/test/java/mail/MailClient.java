package mail;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import static mail.TimeConstants.*;
import static mail.UtilMethods.*;

/**
 * Created by Anastasiya on 27.02.2017.
 */
public class MailClient {
    private String myEMail = "klient.pochtovyy@mail.ru";
    private String urlMail = "https://mail.ru";
    private String myPassword = "123456qwe";
    private static WebDriver driver = new FirefoxDriver();

    private final By login = By.id("mailbox__login");
    private final By password = By.id("mailbox__password");
    private final By logInAuthButton = By.id("mailbox__auth__button");
    private final By buttonWriteLetter = By.xpath(".//*[@id='b-toolbar__left']//span[contains(@class,\'b-toolbar__btn\')]");
    private final By countIncomingMessages = By.xpath(".//*[@id='b-nav_folders']//*[@class=\'b-nav__item__count\']");
    private final By textAreaMailForSendingLetter = By.xpath("//input[@id='compose_to']/following-sibling::textarea[contains(@class, 'js-input')]");
    private final By textAreaThemeForSendingLetter = By.xpath(".//*[@class = \'compose-head__field\']/*[@class = \'b-input\']");
    private final By buttonSendLetter = By.xpath(".//div[@data-name=\'send\']//*[contains(@class,\'b-toolbar__btn\')]");
    private final By buttonConfirmOk = By.xpath(".//*[@id='MailRuConfirm']//*[@class = \'is-compose-empty_in\']//button[contains(@class, \'ok\')]");
    private final By itemIncomingMessages = By.xpath(".//*[@id='b-nav_folders']//*[@data-id=\'0\']/*[@class = \'b-nav__link\']");
    private final By shortDataIncomingLetter = By.xpath(".//*[@id='b-letters']//*[@class = \'b-datalist__body\']/div[1]//*[contains(@class, \'item__link\')]");

    @Before
    public void setUp(){
        driver.get(urlMail);
        driver.findElement(login).sendKeys(myEMail);
        driver.findElement(password).sendKeys(myPassword);
        driver.findElement(logInAuthButton).click();
    }

    @Test
    public void checkMailClient() {
        String themeLetter = "theme";
        String themeLetterAttributeData = "data-subject";
        String attributeTitle = "title";
        int numberIncomingMessages;

        waitWebDriver(driver, TWENTY_SECONDS, buttonWriteLetter).click();

        numberIncomingMessages = getNumberIncomingMessages(driver, countIncomingMessages);
        driver.findElement(textAreaMailForSendingLetter).sendKeys(myEMail);
        driver.findElement(textAreaThemeForSendingLetter).sendKeys(themeLetter);
        driver.findElement(buttonSendLetter).click();
        waitWebDriver(driver, TEN_SECONDS, buttonConfirmOk).click();

        waitWebDriver(driver, THIRTY_SECONDS, itemIncomingMessages).click();
        Assert.assertTrue(numberIncomingMessages < getNumberIncomingMessages(driver, countIncomingMessages));

        String themeIncomingLetterString =  driver.findElement(shortDataIncomingLetter).getAttribute(themeLetterAttributeData);
        Assert.assertEquals(themeLetter, themeIncomingLetterString);

        String myEMailCheck = driver.findElement(shortDataIncomingLetter).getAttribute(attributeTitle);
        Assert.assertTrue(myEMailCheck.contains(myEMail));

    }

    @After
    public void tearDown(){
        driver.close();
    }
 }
