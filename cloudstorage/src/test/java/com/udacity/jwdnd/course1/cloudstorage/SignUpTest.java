package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpTest {

    @FindBy(id = "inputFirstName")
    WebElement fnameField;

    @FindBy(id = "inputLastName")
    WebElement lnameField;

    @FindBy(id = "inputUsername")
    WebElement usernameField;

    @FindBy(id = "inputPassword")
    WebElement passwordField;

    @FindBy(id = "buttonSignUp")
    WebElement btnSignUp;

    @FindBy(id = "btnNavigateToLogin")
    WebElement btnLogin;

    public SignUpTest(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public void signUp(String fname, String lname, String username, String password){
        fnameField.sendKeys(fname);
        lnameField.sendKeys(lname);
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        btnSignUp.submit();
    }

    public void navigateLogin(){
        this.btnLogin.submit();
    }

}
