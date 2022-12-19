package org.example.pom;

import org.example.pom.product.ProductPage;
import org.example.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {
    private WebDriver driver;
    private By product = By.className("product-item-info");

    private By menMenu = By.xpath("//*[@id=\"ui-id-5\"]");

    private By menTopSubMenu = By.xpath("//*[@id=\"ui-id-2\"]/li[3]/ul/li[1]");

    private By menTopSubMenuItem = By.xpath("//*[@id=\"ui-id-2\"]/li[3]/ul/li[1]/ul/li[1]");
    WebDriverWait wait;
    Actions actions;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, WaitUtils.TIMEOUT);
        actions = new Actions(driver);
    }

    public ProductPage clickProduct(Integer index) {
        driver.findElements(product).get(index).click();
        return new ProductPage(driver);
    }

    public void gotoDashboard() {
        driver.get("https://magento.softwaretestingboard.com/");
    }

    public ProductPage hoverMenMenu() {
        WebElement menMenuElement = wait.until(ExpectedConditions.elementToBeClickable(menMenu));
        actions.moveToElement(menMenuElement).perform();
        actions.moveToElement(driver.findElement(menTopSubMenu)).perform();
        driver.findElement(menTopSubMenuItem).click();
        return new ProductPage(driver);
    }
}
