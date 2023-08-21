package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class HeaderPage {
    public HeaderPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy (xpath = "//header/nav/div[2]/button[1]")
    public WebElement linkLogin;
    @FindBy(id = "topnav-search")
    public WebElement inputSearch;
    @FindBy (id = "topnav_menu_avatar")
    public WebElement avatar;
}
