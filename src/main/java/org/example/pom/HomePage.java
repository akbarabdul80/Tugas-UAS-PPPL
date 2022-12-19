package org.example.pom;

import org.example.pom.account.CreateAccountPage;
import org.example.pom.account.LoginAccountPage;
import org.example.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    public WebDriver driver;
    public By signUp = By.linkText("Create an Account");
    public By signIn = By.linkText("Sign In");
    WebDriverWait wait;

    public By mainContent = By.id("maincontent");
    public By base = By.xpath("//*[@id=\"maincontent\"]/div[1]/h1/span");
    public By menuUser = By.xpath("/html/body/div[2]/header/div[1]/div/ul/li[2]/span/button");
    public By btnSignOut = By.xpath("/html/body/div[2]/header/div[1]/div/ul/li[2]/div/ul/li[3]/a");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, WaitUtils.TIMEOUT);
    }

    public CreateAccountPage clickSignUp() {
        driver.findElement(signUp).click();

        new WebDriverWait(driver, 10).until(webDriver -> webDriver.findElement(mainContent).isDisplayed());

        return new CreateAccountPage(driver);
    }

    public LoginAccountPage clickLogin() {
        driver.findElement(signIn).click();
        new WebDriverWait(driver, 10).until(webDriver -> webDriver.findElement(mainContent).isDisplayed());
        return new LoginAccountPage(driver);
    }

    public void clickSignOut() {
        wait.until(ExpectedConditions.elementToBeClickable(menuUser));
        driver.findElement(menuUser).click();
        wait.until(ExpectedConditions.elementToBeClickable(btnSignOut));
        driver.findElement(btnSignOut).click();
    }

    public String getSignOutMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(base));
        return driver.findElement(base).getText();
    }

}
