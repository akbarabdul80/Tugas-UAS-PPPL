package org.example.pom.checkout;

import org.example.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PaymentCheckoutPage {
    private WebDriver driver;
    private By billingDetail = new By.ByClassName("billing-address-details");
    private By btnSubmit = new By.ByXPath("//*[@id=\"checkout-payment-method-load\"]/div/div/div[2]/div[2]/div[4]/div/button");

    WebDriverWait wait;

    public PaymentCheckoutPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, WaitUtils.TIMEOUT);
    }

    public String getBillingDetail() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(billingDetail));
        wait.until(ExpectedConditions.visibilityOfElementLocated(new By.ByXPath("//span[text()[contains(.,'Ship To:')]]")));
        return driver.findElement(billingDetail).getAttribute("innerHTML")
                .replaceAll("<!--(.*?)-->", "")
                .replaceAll("<(.*?)></(.*?)>", "")
                .replaceAll("<(.*?)>", "")
                .replaceAll("\n", " ")
                .replaceAll(" {2}", " ");
    }

    public SuccessCheckoutPage clickSubmit() {
        WebElement btnSubmit = wait.until(ExpectedConditions.visibilityOfElementLocated(this.btnSubmit));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        btnSubmit.click();
        return new SuccessCheckoutPage(driver);
    }

}