package mail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static mail.TimeConstants.*;


/**
 * Created by Anastasiya on 05.03.2017.
 */
public class UtilMethods {

    static int getNumberIncomingMessages(WebDriver driver, By countIncomingMessages) {
        WebElement numberLetters = waitWebDriver(driver, TEN_SECONDS, countIncomingMessages);
        return Integer.parseInt(numberLetters.getText());
    }

    static WebElement waitWebDriver(WebDriver driver, int time, By element) {
        return (new WebDriverWait(driver, time))
                .until(ExpectedConditions.presenceOfElementLocated(element));
    }
}
