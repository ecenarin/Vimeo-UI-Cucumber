package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.ConfigReader;
import utilities.CustomException;
import utilities.Driver;
import utilities.Messages;

import java.util.List;

public class SearchPage {
    public final int RESULTS_PER_PAGE = 18;

    public SearchPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//div[@class='results_count']")
    public WebElement resultReport;
    @FindBy(xpath = "//*[@id='wrap']/div[2]/div/div/div[2]/nav/div[1]/ul")
    public WebElement resultCategories;
    @FindBy (xpath = "//div[@class='iris_p_infinite__grid']/div")
    public List<WebElement> results;
    @FindBy (xpath = "//*[@id='pagination']/div/ol/li")
    public List<WebElement> pagination;

    public WebElement paginationButtonFirst() {
        return pagination.get(0).findElement(By.xpath("a"));
    }

    public WebElement paginationButtonLast() {
        return pagination.get(pagination.size()-1).findElement(By.xpath("a"));
    }

    public int resultCount() {
        String resultCountString = resultReport.getText().split(" ")[0];

        return prepareResultCount(resultCountString);
    }

    public int resultCountForCategory(String category) {
        WebElement resultCategory = resultCategories.findElement(By.partialLinkText(category));
        String resultCountString = resultCategory.getText().split("\\(")[1].replace(")", "");

        return prepareResultCount(resultCountString);
    }

    private int prepareResultCount(String countString) {
        /*
        The search engine shows up to 555 pages of results.
        So it can handle up to 9990 results.
        That's why we throw an exception when we get a number of results containing "K" or "M".
        */
        try {
            if (countString.contains("K") || countString.contains("M")) {
                throw new CustomException(Messages.SEARCH_EXCEPTION);
            }
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        }

        // Find commas and delete
        String countStringClean = countString.replace(",", "");
        return Integer.parseInt(countStringClean);
    }

    public int lastPage() {
        float pageCount = (float) resultCount() / RESULTS_PER_PAGE;
        return (int) Math.ceil(pageCount);
    }

    public void goToPage(int page, String keyword) {
        Driver.getDriver().get(ConfigReader.getProperty("BaseURL") + "search/page:" + page + "?q=" + keyword);
    }

    public void goToCategory(String category, String keyword) {
        Driver.getDriver().get(ConfigReader.getProperty("BaseURL") + "search/" + category + "?q=" + keyword);
    }

    public boolean isNavButtonVisible(String button) {
        try {
            if (!(button.equals("Prev") || button.equals("Next"))) {
                throw new CustomException(Messages.NAV_BUTTON_EXCEPTION);
            }
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        }

        switch (button) {
            case "Prev":
                return paginationButtonFirst().getText().equals(button);
            case "Next":
                return paginationButtonLast().getText().equals(button);
            default:
                return false;
        }
    }

    public WebElement getVideoLink(WebElement result) {
        return result.findElement(By.xpath("div"));
    }

    public String getVideoName(WebElement result) {
        return result.findElement(By.xpath("div/a/div[2]/h5/span")).getText();
    }
}
