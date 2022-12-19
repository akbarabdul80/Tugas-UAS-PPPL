package org.example.pom.cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {

    private WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public void gotoCheckout() {
        driver.findElement(By.cssSelector("button[title='Proceed to Checkout']")).click();
    }

}
