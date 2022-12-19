package org.example.pom.checkout;

import org.example.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShippingCheckoutPage {
    private WebDriver driver;

    private By firstName = By.name("firstname");
    private By lastName = By.name("lastname");
    private By company = By.name("company");
    private By address1 = By.name("street[0]");
    private By address2 = By.name("street[1]");
    private By address3 = By.name("street[2]");
    private By city = By.name("city");
    private By state = By.name("region_id");
    private By zip = By.name("postcode");
    private By country = By.name("country_id");
    private By telephone = By.name("telephone");

    private By btnNext = By.cssSelector("button.action.continue.primary");
    WebDriverWait wait;

    public ShippingCheckoutPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, WaitUtils.TIMEOUT);
    }

    public void clickContinue() {
        driver.findElement(By.cssSelector("button[title='Continue']")).click();
    }

    public void insertShippingAddress(String firstName, String lastName, String company, String address1, String address2, String address3, String city, String state, String zip, String country, String telephone, String methode) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(this.firstName));

        driver.findElement(this.firstName).clear();
        driver.findElement(this.lastName).clear();

        driver.findElement(this.firstName).sendKeys(firstName);
        driver.findElement(this.lastName).sendKeys(lastName);
        driver.findElement(this.company).sendKeys(company);
        driver.findElement(this.address1).sendKeys(address1);
        driver.findElement(this.address2).sendKeys(address2);
        driver.findElement(this.address3).sendKeys(address3);
        driver.findElement(this.city).sendKeys(city);
        driver.findElement(this.zip).sendKeys(zip);
        driver.findElement(this.country).sendKeys(country);
        driver.findElement(this.state).sendKeys(state);
        driver.findElement(this.telephone).sendKeys(telephone);

        wait.until(ExpectedConditions.visibilityOfElementLocated(new By.ByName(methode)));
        driver.findElement(new By.ByName(methode)).click();
    }

    public void setFirstName(String firstName) {
        driver.findElement(this.firstName).sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        driver.findElement(this.lastName).sendKeys(lastName);
    }

    public void setCompany(String company) {
        driver.findElement(this.company).sendKeys(company);
    }

    public void setAddress1(String address1) {
        driver.findElement(this.address1).sendKeys(address1);
    }

    public void setAddress2(String address2) {
        driver.findElement(this.address1).sendKeys(address2);
    }

    public void setAddress3(String address3) {
        driver.findElement(this.address3).sendKeys(address3);
    }

    public void setCity(String city) {
        driver.findElement(this.city).sendKeys(city);
    }

    public void selectState(String state) {
        Select select = new Select(driver.findElement(this.state));
        select.selectByVisibleText(state);
    }

    public void setZip(String zip) {
        driver.findElement(this.zip).sendKeys(zip);
    }

    public void selectCountry(String country) {
        Select select = new Select(driver.findElement(this.country));
        select.selectByVisibleText(country);
    }

    public void setTelephone(String telephone) {
        driver.findElement(this.telephone).sendKeys(telephone);
    }

    public void setMethod(String name) {
        driver.findElement(new By.ByName(name)).click();
    }

    public PaymentCheckoutPage clickNext() {
        driver.findElement(btnNext).click();
        return new PaymentCheckoutPage(driver);
    }
}
