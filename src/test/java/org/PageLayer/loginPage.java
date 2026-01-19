package org.PageLayer;

import org.BaseTestLayer.BaseTestClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class loginPage extends BaseTestClass {
    private  WebDriver driver;

    @FindBy(id="user-name")    WebElement userNameFld;
    @FindBy(id="password")    WebElement passwordFld;
    @FindBy(id="login-button") WebElement loginBtn;

    public loginPage() {
        this.driver=super.driver;
        PageFactory.initElements(driver, this);
    }

    public String loginFunc(String userName,String password) throws InterruptedException {
        userNameFld.sendKeys(userName);
        passwordFld.sendKeys(password);
        loginBtn.click();
        String headerAftLogin= driver.getTitle();
        return  headerAftLogin;
    }
}
