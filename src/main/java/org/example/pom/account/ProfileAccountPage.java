package org.example.pom.account;

import org.example.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProfileAccountPage {
    private WebDriver driver;

    private By boxContent = By.className("box-content");
    WebDriverWait wait;

    public ProfileAccountPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, WaitUtils.TIMEOUT);
    }

    public String getBoxInformation() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(boxContent));
        return driver.findElement(boxContent).getAttribute("innerHTML")
                .replaceAll("<(.*?)></(.*?)>", "")
                .replaceAll("<(.*?)>", "")
                .replaceAll("\n", " ")
                .replaceAll(" {2}", " ");
    }
}
