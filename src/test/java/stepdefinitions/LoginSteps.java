package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HeaderPage;
import pages.LoginPage;
import utilities.ConfigReader;
import utilities.Driver;

import java.time.Duration;

public class LoginSteps {
    LoginPage login = new LoginPage();
    HeaderPage header = new HeaderPage();

    @When("click Log in link")
    public void clickLoginLink() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(header.linkLogin));
        header.linkLogin.click();
    }

    @When("type login {string} into email box")
    public void typeLoginIntoEmailBox(String credential) {
        login.inputEmail.sendKeys(ConfigReader.getProperty(credential));
    }

    @When("type login {string} into password box")
    public void typeLoginIntoPasswordBox(String credential) {
        login.inputPassword.sendKeys(ConfigReader.getProperty(credential));
    }

    @When("click login button")
    public void clickLoginButton() {
        login.buttonLogin.click();
    }

    @Then("account avatar should be displayed")
    public void accountAvatarShouldBeDisplayed() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(header.avatar));
        Assert.assertTrue(header.avatar.isDisplayed());
    }
}