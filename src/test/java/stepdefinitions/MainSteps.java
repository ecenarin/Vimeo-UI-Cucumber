package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import utilities.ConfigReader;
import utilities.Driver;

public class MainSteps {
    @Given("go to {string}")
    public void goTo(String URL) {
        Driver.getDriver().get(ConfigReader.getProperty(URL));
    }

    @Given("navigate to {string}")
    public void navigateTo(String page) {
        Driver.getDriver().get(ConfigReader.getProperty("BaseURL") + ConfigReader.getProperty(page));
    }

    @And("close browser")
    public void closeBrowser() {
        Driver.closeDriver();
    }

    @Then("page URL should contain {string}")
    public void urlContains(String partialURL) {
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains(partialURL));
    }
}