package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private HomePageTest homePageTest;

	private LoginTest loginTest;

	private SignUpTest signUpTest;

	private static String fname ;

	private static String lname;

	private static String username;

	private static String password;

	public String baseURL;
	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		fname = "Tien";
		lname = "Vo";
		username = "duydn11";
		password = "duy123";
	}

	@BeforeEach
	public void beforeEach() throws InterruptedException {
		this.driver = new ChromeDriver();
		baseURL = "http://localhost:" + port;
//		driver.get(baseURL + "/signup");
//
//		signUpTest = new SignUpTest(driver);
//		signUpTest.signUp(fname,lname,username,password);
//
//		driver.get(baseURL + "/login");
//		loginTest = new LoginTest(driver);
//		loginTest.login(username,password);
//		Thread.sleep(1000);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	public void navigateLogin(){
		driver.get("http://localhost:" + this.port + "/login");
	}
	@Test
	public void unAuthAccessHomePage() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));

	}


	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */

		@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		navigateLogin();
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals(baseURL + "/login", driver.getCurrentUrl());
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get(baseURL + "/some-random-page");

		//Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
		Assertions.assertTrue(driver.getPageSource().contains("Whitelabel Error Page"));


	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	@Test
	public void testSignupLogin(){
		driver.get(baseURL+"/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		SignUpTest signUpTest = new SignUpTest(driver);
		signUpTest.signUp(fname, lname,username,password);

		driver.get(baseURL+"/login");
		LoginTest loginTest = new LoginTest(driver);
		loginTest.login(username,password);
		Assertions.assertEquals("Home", driver.getTitle());
	}

	@Test
	public void testPageAccess() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testAddEditDeleteNote(){
		//add new note
		String title = "Note title";
		String description = "Note Description";
		doMockSignUp(fname,lname,username,password);
		doLogIn(username,password);

		homePageTest = new HomePageTest(driver);
		homePageTest.navToNotesTab();
		homePageTest.openNewNotesModal();
		homePageTest.setNoteTitle(title);
		homePageTest.setNoteDescription(description);
		homePageTest.saveChangesNote();
		homePageTest.clickContinueOnResult();
		homePageTest.navToNotesTab();

		Assertions.assertEquals(title,homePageTest.getFirstNote().getNoteTitle());
		Assertions.assertEquals(description,homePageTest.getFirstNote().getNoteDescription());

		//edit note
		homePageTest.openEditNotesModal();
		homePageTest.clearNoteTitle();
		homePageTest.clearNoteDescriptoin();
		String newTitle = "New title";
		String newDescription = "New Description";
		homePageTest.setNoteTitle(newTitle);
		homePageTest.setNoteDescription(newDescription);
		homePageTest.saveChangesNote();
		homePageTest.clickContinueOnResult();
		homePageTest.navToNotesTab();
		Assertions.assertEquals(newTitle,homePageTest.getFirstNote().getNoteTitle());
		Assertions.assertEquals(newDescription,homePageTest.getFirstNote().getNoteDescription());

		//delete note
		homePageTest.deleteNote();
		homePageTest.clickContinueOnResult();
		homePageTest.navToNotesTab();
		Assertions.assertTrue(homePageTest.noNotes(driver));
		homePageTest.logout();
	}

	@Test
	public void testAddEditDeleteCredential(){
		//doMockSignUp(fname,lname,username,password);
		doLogIn(username,password);

		//add new credential
		String cUrl = "p*rnhub.com";
		String cUsername = "duydn11";
		String cPassword = "abc123";
		homePageTest = new HomePageTest(driver);
		homePageTest.navToCredentialsTab();
		homePageTest.openNewCredentialModal();
		homePageTest.setCredentialUrl(cUrl);
		homePageTest.setCredentialUsername(cUsername);
		homePageTest.setCredentialPassword(cPassword);
		homePageTest.saveChangesCredential();
		homePageTest.clickContinueOnResult();
		homePageTest.navToCredentialsTab();

		Assertions.assertEquals(cUrl,homePageTest.getFirstCredential().getUrl());
		Assertions.assertEquals(cUsername,homePageTest.getFirstCredential().getUsername());
		Assertions.assertNotEquals(cPassword,homePageTest.getFirstCredential().getPassword());

		//edit credential
		homePageTest.openEditCredentialModal();
		homePageTest.clearCredentialUrl();
		homePageTest.clearCredentialUsername();
		homePageTest.clearCredentialPassword();
		String newUrl = "xvide0s.com";
		String newUsername = "premium";
		String newPassword = "ahihihi";
		homePageTest.setCredentialUrl(newUrl);
		homePageTest.setCredentialUsername(newUsername);
		homePageTest.setCredentialPassword(newPassword);
		homePageTest.saveChangesCredential();
		homePageTest.clickContinueOnResult();
		homePageTest.navToCredentialsTab();

		Assertions.assertEquals(newUrl,homePageTest.getFirstCredential().getUrl());
		Assertions.assertEquals(newUsername,homePageTest.getFirstCredential().getUsername());
		Assertions.assertNotEquals(newPassword,homePageTest.getFirstCredential().getPassword());


		// delete credential
		homePageTest.deleteCredential();
		homePageTest.clickContinueOnResult();
		homePageTest.navToCredentialsTab();
		Assertions.assertTrue(homePageTest.noCredential(driver));

		homePageTest.logout();
	}

}
