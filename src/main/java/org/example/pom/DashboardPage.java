package org.example.pom;

import org.example.data.DataProduct;
import org.example.pom.product.ProductPage;
import org.example.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DashboardPage {
    private WebDriver driver;

    WebDriverWait wait;
    Actions actions;
    private By product = By.className("product-item-info");

    private By menMenu = By.xpath("//*[@id=\"ui-id-5\"]");

    private By menTopSubMenu = By.xpath("//*[@id=\"ui-id-2\"]/li[3]/ul/li[1]");

    private By menTopSubMenuItem = By.xpath("//*[@id=\"ui-id-2\"]/li[3]/ul/li[1]/ul/li[1]");

    private By btnCart = By.cssSelector(".action.showcart");
    private By btnRemoveCart = By.cssSelector(".action.delete");
    private By btnAccept = new By.ByClassName("action-accept");

    private By miniCart = By.className("minicart-items");
    private By miniCartProduct = By.xpath("//*[@id=\"mini-cart\"]/li");

    private By miniCartProductName = By.className("product-item-name");
    private By miniCartProductQty = By.className("item-qty");

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

    public void clickCart() {
        driver.findElement(btnCart).click();
    }

    public void removeAllCart() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(btnRemoveCart));
        List<WebElement> button = driver.findElements(btnRemoveCart);
        for (WebElement webElement : button) {
            webElement.click();
            wait.until(ExpectedConditions.elementToBeClickable(btnAccept));
            driver.findElement(btnAccept).click();
            wait.until(ExpectedConditions.invisibilityOf(webElement));
        }
    }

    public List<DataProduct> getCart() {
        return getDataProducts(wait, miniCart, miniCartProduct, miniCartProductName, miniCartProductQty);
    }

    public static List<DataProduct> getDataProducts(WebDriverWait wait, By miniCart, By miniCartProduct, By miniCartProductName, By miniCartProductQty) {
        WebElement cart = wait.until(ExpectedConditions.visibilityOfElementLocated(miniCart));
        List<WebElement> productList = cart.findElements(miniCartProduct);
        List<DataProduct> dataProducts = new ArrayList<>();
        for (WebElement product : productList) {
            DataProduct dataProduct = new DataProduct(product.findElement(miniCartProductName).getText(), product.findElement(miniCartProductQty).getAttribute("value"));
            dataProducts.add(dataProduct);
        }
        Collections.reverse(dataProducts);
        return dataProducts;
    }
}
