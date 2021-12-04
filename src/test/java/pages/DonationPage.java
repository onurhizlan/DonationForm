package pages;

import static org.testng.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import base.Base;


public class DonationPage extends Base {

	private WebDriver driver;
	private BaseFunctions bf;

	
	public DonationPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 180);
		bf = new BaseFunctions(driver);
	}

	
	@FindBy(xpath = "//a[contains(text(), 'Unterstütze Skateistan')]")
	private WebElement waitMainPage;
	
	@FindBy(xpath = "//button[text()='Okay!']")
	private WebElement popUp;
	
	@FindBy(xpath = "//h4[text()='Betrag']//following::input[1]")
	private WebElement donateAmount;
	
	@FindBy(xpath = "//span[text()='Überweisung']")
	private WebElement wireTransferSelection;
	
	@FindBy(xpath = "//label[text()='Vorname*']//following::input[1]")
	private WebElement nameInput;
	
	@FindBy(xpath = "//label[text()='Nachname*']//following::input[1]")
	private WebElement lastnameInput;
	
	@FindBy(xpath = "//label[text()='E‑Mail*']//following::input[1]")
	private WebElement emailInput;
	
	@FindBy(xpath = "//button[text()='Zu den Zahlungsinformationen']")
	private WebElement paymentInformationButton;
	
	@FindBy(xpath = "//a[text()='Weiter']")
	private WebElement proceedToCompleteButton;
	
	@FindBy(xpath = "//div[contains(text(), 'Vielen Dank für deine Spende!')]")
	private WebElement checkDonationisCompleted;
	
	@FindBy(xpath = "//div[@class='payment-method-radios form-group']")
	private WebElement paymentMethodForm;
	
	public void makeSuccessfulDonation(String amount, String name, String lastname, String email) throws Exception {
		bf.waitElement(waitMainPage);
		if(popUp.isEnabled()) {
			bf.click(popUp);
		}
		compareScreenshots();
		bf.clearWithSelectAll(donateAmount);
		bf.write(donateAmount, amount);
		bf.scrollToElement(wireTransferSelection);
		bf.click(wireTransferSelection);
		bf.write(nameInput, name);
		bf.write(lastnameInput, lastname);
		bf.write(emailInput, email);
		bf.scrollToElement(paymentInformationButton);
		bf.click(paymentInformationButton);
		bf.waitElement(proceedToCompleteButton);
		bf.scrollToElement(proceedToCompleteButton);
		bf.click(proceedToCompleteButton);
		bf.waitElement(checkDonationisCompleted);	
	}
	
	public void compareScreenshots() throws Exception{
		
		bf.scrollDown();
        File f = paymentMethodForm.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(f, new File("screenshotTakenFromUI.png"));

		BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources("truePaymentMethodScreenshot.png");
		BufferedImage actualImage = ImageComparisonUtil.readImageFromResources("screenshotTakenFromUI.png");

		ImageComparisonResult imageComparisonResult = new ImageComparison(expectedImage, actualImage).compareImages();
		
		assertEquals(ImageComparisonState.MATCH, imageComparisonResult.getImageComparisonState());

		}
	
}
