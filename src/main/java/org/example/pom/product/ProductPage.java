package org.example.pom.product;

import org.example.data.DataProduct;
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

public class ProductPage {
    private WebDriver driver;
    private By switchSize = By.cssSelector(".swatch-option.text");
    private By switchColor = By.cssSelector(".swatch-option.color");
    private By etQty = By.cssSelector(".input-text.qty");
    private By addToCart = By.cssSelector("button[title='Add to Cart']");
    private By product = By.cssSelector(".item.product.product-item");
    private By btnCart = By.cssSelector(".action.showcart");
    private By counterCart = By.className("counter-number");

    private By btnCheckout = By.id("top-cart-btn-checkout");

    private By titleProduct = By.className("base");
    private By miniCart = By.className("minicart-items");
    private By miniCartProduct = By.xpath("//*[@id=\"mini-cart\"]/li");
    private By miniCartProductName = By.className("product-item-name");
    private By miniCartProductQty = By.className("item-qty");

    private By menMenu = By.cssSelector("level0.nav-3");

    private By menTopSubMenu = By.xpath("//*[@id=\"ui-id-2\"]/li[3]/ul");

    private By menTopSubMenuItem = By.xpath("//*[@id=\"ui-id-19\"]");
    private Integer cartCount = 0;
    private Integer lastCartCount = 0;

    WebDriverWait wait;
    Actions actions;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, WaitUtils.TIMEOUT);
        actions = new Actions(driver);
    }

    public void setSize(Integer size) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(switchSize));
        driver.findElements(switchSize).get(size).click();
    }

    public void setColor(Integer color) {
        driver.findElements(switchColor).get(color).click();
    }

    public void setQty(Integer qty) {
        driver.findElement(etQty).clear();
        driver.findElement(etQty).sendKeys(qty.toString());
        cartCount = qty;
    }

    public void addToCart() {
        lastCartCount += cartCount;
        System.out.println("Last Cart Count " + lastCartCount);
        driver.findElement(addToCart).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(counterCart, lastCartCount.toString()));
    }

    public void clickProduct(Integer index) {
        driver.findElements(product).get(index).click();
    }

    public void clickCart() {
        driver.findElement(btnCart).click();
    }

    public void clickCheckout() {
        driver.findElement(btnCheckout).click();
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(titleProduct));
        return driver.findElement(titleProduct).getText();
    }


    public String getQty() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(etQty));
        return driver.findElement(etQty).getAttribute("value");
    }


    public List<DataProduct> getCart() {
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

    public void hoverMenMenu() {
        WebElement menMenuElement = wait.until(ExpectedConditions.elementToBeClickable(menMenu));
        actions.moveToElement(menMenuElement).perform();
        actions.moveToElement(driver.findElement(menTopSubMenu)).perform();
        driver.findElement(menTopSubMenuItem).click();
    }
}
