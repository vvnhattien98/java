package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePageTest {
    @FindBy(id = "btnLogout")
    private WebElement logoutButton;

    @FindBy(id = "fileUpload")
    private WebElement fileUpload;

    @FindBy(id = "btnAddNewNote")
    private WebElement btnAddNewNote;

    @FindBy(id = "btnAddNewCredential")
    private WebElement btnAddNewCredential;

    @FindBy(id = "note-title")
    private WebElement txtNoteTitle;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "note-description")
    private WebElement txtNoteDescription;

    @FindBy(id = "btnSaveChanges")
    private WebElement btnSaveChanges;

    @FindBy(id = "tableNoteTitle")
    private WebElement tableNoteTitle;

    @FindBy(id = "tableNoteDescription")
    private WebElement tableNoteDescription;

    @FindBy(id = "btnEditNote")
    private WebElement btnEditNote;

    @FindBy(id = "btnEditCredential")
    private WebElement btnEditCredential;

    @FindBy(id = "note-description")
    private WebElement txtModifyNoteDescription;

    @FindBy(id = "btnDeleteNote")
    private WebElement btnDeleteNote;

    @FindBy(id = "btnDeleteCredential")
    private WebElement btnDeleteCredential;

    @FindBy(id = "credential-url")
    private WebElement txtCredentialUrl;

    @FindBy(id = "credential-username")
    private WebElement txtCredentialUsername;

    @FindBy(id = "credential-password")
    private WebElement txtCredentialPassword;

    @FindBy(id = "btnCredentialSaveChanges")
    private WebElement btnCredentialSaveChanges;

    @FindBy(id="tblCredentialUrl")
    private WebElement credentialUrl;

    @FindBy(id="tblCredentialUsername")
    private WebElement credentialUsername;

    @FindBy(id="tblCredentialPassword")
    private WebElement credentialPassword;

    @FindBy(id = "credentialSubmit")
    private WebElement btnCredentialSubmit;

    @FindBy(id="aResultSuccess")
    public WebElement btnContinue;

    private final JavascriptExecutor js;
    private final WebDriverWait wait;

    public HomePageTest(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
        js = (JavascriptExecutor) webDriver;
        wait = new WebDriverWait(webDriver, 500);
    }



    public void logout(){
        js.executeScript("arguments[0].click();", logoutButton);
    }

    public void navToNotesTab(){
        js.executeScript("arguments[0].click();", navNotesTab);
    }

    public void navToCredentialsTab(){
        js.executeScript("arguments[0].click();", navCredentialsTab);

    }

    public void setNoteTitle(String title){
        js.executeScript("arguments[0].value='" + title + "';", txtNoteTitle);
    }

    public void setNoteDescription(String description){
        js.executeScript("arguments[0].value='" + description + "';", txtNoteDescription);
    }

    public Note getFirstNote(){
       String title = wait.until(ExpectedConditions.elementToBeClickable(tableNoteTitle)).getText();
       String description = tableNoteDescription.getText();
       return new Note(title,description);
    }

    public void clearNoteTitle(){
        js.executeScript("arguments[0].value='" + "" + "';", txtNoteTitle);
    }

    public void clearNoteDescriptoin(){
        js.executeScript("arguments[0].value='" + "" + "';", txtNoteDescription);
    }

    public void saveChangesNote(){
        js.executeScript("arguments[0].click();", btnSaveChanges);
    }

    public void clickContinueOnResult(){
        js.executeScript("arguments[0].click();", btnContinue);
    }

    public void openNewNotesModal(){
        js.executeScript("arguments[0].click();", btnAddNewNote);
    }
    public void openEditNotesModal(){
        js.executeScript("arguments[0].click();", btnEditNote);
    }

    public boolean isElementExists(By locatorKey, WebDriver driver) {
        try {
            driver.findElement(locatorKey);

            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
    public boolean noNotes(WebDriver driver) {
        return !isElementExists(By.id("tableNoteTitle"), driver) && !isElementExists(By.id("tableNoteDescription"), driver);
    }

    public void deleteNote(){
        js.executeScript("arguments[0].click();", btnDeleteNote);
    }

    public void openNewCredentialModal(){
        js.executeScript("arguments[0].click();", btnAddNewCredential);
    }
    public void setCredentialUrl(String url){
        js.executeScript("arguments[0].value='" + url + "';", txtCredentialUrl);
    }

    public void setCredentialUsername(String username){
        js.executeScript("arguments[0].value='" + username + "';", txtCredentialUsername);
    }

    public void setCredentialPassword(String password){
        js.executeScript("arguments[0].value='" + password + "';", txtCredentialPassword);
    }

    public void clearCredentialUrl(){
        js.executeScript("arguments[0].value='" + "" + "';", txtCredentialUrl);
    }

    public void clearCredentialUsername(){
        js.executeScript("arguments[0].value='" + "username" + "';", txtCredentialUsername);
    }

    public void clearCredentialPassword(){
        js.executeScript("arguments[0].value='" + "password" + "';", txtCredentialPassword);
    }

    public Credential getFirstCredential(){
        String url = wait.until(ExpectedConditions.elementToBeClickable(credentialUrl)).getText();
        String username = credentialUsername.getText();
        String password = credentialPassword.getText();
        return new Credential(url,username,password);
    }
    public void saveChangesCredential(){
        js.executeScript("arguments[0].click();", btnCredentialSaveChanges);
    }

    public void openEditCredentialModal(){
        js.executeScript("arguments[0].click();", btnEditCredential);
    }

    public boolean noCredential(WebDriver driver) {
        return !isElementExists(By.id("tblCredentialUrl"), driver)
                && !isElementExists(By.id("tblCredentialUsername"), driver)
                && !isElementExists(By.id("tblCredentialPassword"), driver);
    }

    public void deleteCredential(){
        js.executeScript("arguments[0].click();", btnDeleteCredential);
    }
}
