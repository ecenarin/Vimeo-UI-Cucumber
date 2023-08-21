package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import pages.HeaderPage;
import pages.SearchPage;
import utilities.Driver;

public class SearchSteps {
    HeaderPage header = new HeaderPage();
    SearchPage search = new SearchPage();

    public static String searchKeyword;
    public static String videoName;

    @When("search {string}")
    public void search(String keyword) {
        searchKeyword = keyword;
        header.inputSearch.sendKeys(keyword + Keys.ENTER);
    }

    @When("select {string} category")
    public void selectCategory(String category) {
        search.goToCategory(category, searchKeyword);
    }

    @When("go to result page {int}")
    public void goToResultPage(int page) {
        search.goToPage(page, searchKeyword);
    }

    @When("go to last result page")
    public void goToLastResultPage() {
        search.goToPage(search.lastPage(), searchKeyword);
    }

    @When("click a video link")
    public void clickAVideoLink() {
        videoName = search.getVideoName(search.results.get(0));
        search.getVideoLink(search.results.get(0)).click();
    }

    @Then("results per page should not exceed the max number")
    public void resultsPerPageShouldNotExceedTheMaxNumber() {
        // Check for the first page
        Assert.assertTrue(search.results.size() <= search.RESULTS_PER_PAGE);

        // Check for the first ten pages
        for (int i = 2; i <= Math.min(10, search.lastPage()); i++) {
            goToResultPage(i);
            Assert.assertTrue(search.results.size() <= search.RESULTS_PER_PAGE);
        }
    }

    @Then("search results should contain {string}")
    public void searchResultsShouldContain(String keyword) {
        Assert.assertTrue(search.resultReport.getText().contains(keyword));
    }

    @Then("result count should be correct")
    public void resultCountShouldBeCorrect() {
        search.goToPage(search.lastPage(), searchKeyword);

        int lastPageResultCountExpected = search.resultCount() % search.RESULTS_PER_PAGE;

        if (lastPageResultCountExpected == 0) {
            lastPageResultCountExpected = search.RESULTS_PER_PAGE;
        }

        int resultCountExpected = (search.lastPage()-1) * search.RESULTS_PER_PAGE + lastPageResultCountExpected;

        Assert.assertEquals(resultCountExpected, search.resultCount());
    }

    @Then("result count for {string} should be correct")
    public void resultCountForShouldBeCorrect(String category) {
        Assert.assertEquals(search.resultCountForCategory(category), search.resultCount());
    }

    @Then("{string} button should be displayed")
    public void buttonShouldBeDisplayed(String button) {
        Assert.assertTrue(search.isNavButtonVisible(button));
    }

    @Then("{string} button should not be displayed")
    public void buttonShouldNotBeDisplayed(String button) {
        Assert.assertFalse(search.isNavButtonVisible(button));
    }

    @Then("page title contains video name")
    public void pageTitleContainsVideoName() {
        Assert.assertTrue(Driver.getDriver().getTitle().contains(videoName));
    }
}
