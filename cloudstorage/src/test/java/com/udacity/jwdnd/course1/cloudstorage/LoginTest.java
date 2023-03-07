package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginTest {
    @FindBy(id = "inputUsername")
    WebElement usernameField;

    @FindBy(id = "inputPassword")
    WebElement passwordField;

    @FindBy(id = "login-button")
    WebElement btnLogin;

    public LoginTest (WebDriver webDriver){
        PageFactory.initElements(webDriver,this);
    }

    public void login(String username, String password){
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        btnLogin.submit();
    }
}
