package org.example.pom.checkout;

import org.example.data.DataProduct;
import org.example.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SuccessCheckoutPage {
    WebDriver driver;

    private By orderNumber = By.className("order-number");
    private By orderTable = By.id("my-orders-table");
    private By tableBody = By.xpath("//*[@id=\"my-orders-table\"]/tbody");
    private By productName = By.className("product-item-name");
    private By productQty = By.className("items-qty");

    WebDriverWait wait;

    public SuccessCheckoutPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, WaitUtils.TIMEOUT);
    }

    public void clickOrderNumber() {
        wait.until(ExpectedConditions.elementToBeClickable(orderNumber)).click();
    }

    public List<DataProduct> getListOrder() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderTable));
        List<WebElement> productList = driver.findElement(orderTable).findElements(tableBody);
        List<DataProduct> dataProducts = new ArrayList<>();
        for (WebElement product : productList) {
            DataProduct dataProduct = new DataProduct(product.findElement(productName).getText(), product.findElement(productQty).getText().replaceAll("Ordered ", ""));
            dataProducts.add(dataProduct);
        }
        return dataProducts;
    }

}
