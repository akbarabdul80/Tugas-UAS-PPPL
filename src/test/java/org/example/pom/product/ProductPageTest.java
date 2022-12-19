package org.example.pom.product;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.hc.core5.util.Asserts;
import org.example.data.DataProduct;
import org.example.data.DataShipping;
import org.example.pom.DashboardPage;
import org.example.pom.HomePage;
import org.example.pom.account.CreateAccountPage;
import org.example.pom.account.LoginAccountPage;
import org.example.pom.account.ProfileAccountPage;
import org.example.pom.checkout.PaymentCheckoutPage;
import org.example.pom.checkout.ShippingCheckoutPage;
import org.example.pom.checkout.SuccessCheckoutPage;
import org.example.utils.Cons;
import org.example.utils.StringUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductPageTest {
    private static WebDriver driver;


    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://magento.softwaretestingboard.com/");
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    public static ArrayList<DataProduct> dataCart = new ArrayList<>();

    @Test
    @Order(1)
    void createAccount() {
        HomePage homePage = new HomePage(driver);
        CreateAccountPage createAccountPage = homePage.clickSignUp();

        String email = StringUtils.getSaltString() + "@gmail.com";
        createAccountPage.setFirstName("Muhamad");
        createAccountPage.setLastName("Akbar");
        createAccountPage.setEmail(email);
        createAccountPage.setPassword("Password123");
        createAccountPage.setConfirmPassword("Password123");
        ProfileAccountPage profileAccountPage = createAccountPage.clickCreateAccount();
        String userInfo = " Muhamad Akbar " + email + " ";
        boolean match = Pattern.compile("(.*)" + userInfo + "(.*)", Pattern.CASE_INSENSITIVE).matcher(profileAccountPage.getBoxInformation()).find();

        assertTrue(match);
    }

    @Test
    @Order(2)
    void testAddCart() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.gotoDashboard();
        ProductPage productPage = dashboardPage.hoverMenMenu();

        productPage.clickProduct(0);
        productPage.setSize(Cons.SIZE_L);
        productPage.setColor(0);
        productPage.setQty(3);
        dataCart.add(new DataProduct(productPage.getTitle(), productPage.getQty()));

        productPage.addToCart();

        productPage.clickProduct(0);
        productPage.setSize(Cons.SIZE_L);
        productPage.setColor(0);
        productPage.setQty(3);
        dataCart.add(new DataProduct(productPage.getTitle(), productPage.getQty()));

        productPage.addToCart();

        productPage.clickCart();

        assertEquals(dataCart.size(), productPage.getCart().size());
        for (int i = 0; i < dataCart.size(); i++) {
            assertEquals(dataCart.get(i).getName(), productPage.getCart().get(i).getName());
            assertEquals(dataCart.get(i).getQty(), productPage.getCart().get(i).getQty());
        }

        productPage.clickCheckout();
    }

    @Test
    @Order(3)
    void testCheckout() {
        System.out.println("dataCart = " + dataCart.size());
        ShippingCheckoutPage shippingCheckoutPage = new ShippingCheckoutPage(driver);
        shippingCheckoutPage.insertShippingAddress(DataShipping.firstname, DataShipping.lastname, DataShipping.company, DataShipping.address1, DataShipping.address2, DataShipping.address3, DataShipping.city, DataShipping.state, DataShipping.zip, DataShipping.country, DataShipping.telephone, DataShipping.methode);

        PaymentCheckoutPage paymentCheckoutPage = shippingCheckoutPage.clickNext();

        boolean match = Pattern.compile("(.*)" + DataShipping.shipping_checkout_detail + "(.*)", Pattern.CASE_INSENSITIVE).matcher(paymentCheckoutPage.getBillingDetail()).find();
        assertTrue(match, "Shipping order detail");
        SuccessCheckoutPage successCheckoutPage = paymentCheckoutPage.clickSubmit();
        successCheckoutPage.clickOrderNumber();
    }

    @Test
    @Order(4)
    void testCheckoutSuccess() {
        SuccessCheckoutPage successCheckoutPage = new SuccessCheckoutPage(driver);
        assertEquals(dataCart.size(), successCheckoutPage.getListOrder().size());
        System.out.println("dataCart = " + dataCart.size());
        System.out.println("successCheckoutPage.getListOrder() = " + successCheckoutPage.getListOrder().size());
        for (int i = 0; i < dataCart.size(); i++) {
            assertEquals(dataCart.get(i).getName(), successCheckoutPage.getListOrder().get(i).getName());
            assertEquals(dataCart.get(i).getQty(), successCheckoutPage.getListOrder().get(i).getQty());
        }
    }

    @Test
    @Order(5)
    void testSignOutLogin() {
        HomePage homePage = new HomePage(driver);
        homePage.clickSignOut();
        LoginAccountPage loginAccountPage = homePage.clickLogin();
        String email = "akbarabdul10@gmail.com";
        String pass = "Password123";
        ProfileAccountPage profileAccountPage = loginAccountPage.login(email, pass);
        profileAccountPage.getBoxInformation();
        String userInfo = " John Doe " + email + " ";
        boolean match = Pattern.compile("(.*)" + userInfo + "(.*)", Pattern.CASE_INSENSITIVE).matcher(profileAccountPage.getBoxInformation()).find();
        assertTrue(match);
    }
}