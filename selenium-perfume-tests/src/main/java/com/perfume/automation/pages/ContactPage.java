package com.perfume.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object Model for Contact Page (contact.html)
 * Contains contact form and validation functionality
 */
public class ContactPage extends BasePage {

    // Locators using data-testid
    private By contactForm = By.cssSelector("[data-testid='contact-form']");
    private By nameInput = By.cssSelector("[data-testid='contact-name-input']");
    private By emailInput = By.cssSelector("[data-testid='contact-email-input']");
    private By subjectInput = By.cssSelector("[data-testid='contact-subject-input']");
    private By messageInput = By.cssSelector("[data-testid='contact-message-input']");
    private By submitButton = By.cssSelector("[data-testid='contact-submit-btn']");
    private By formFeedback = By.cssSelector("[data-testid='form-feedback']");
    private By nameError = By.cssSelector("[data-testid='name-error']");
    private By emailError = By.cssSelector("[data-testid='email-error']");
    private By subjectError = By.cssSelector("[data-testid='subject-error']");
    private By messageError = By.cssSelector("[data-testid='message-error']");

    public ContactPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to contact page
     */
    public ContactPage navigateToContactPage(String baseUrl) {
        String contactUrl = baseUrl.replace("index.html", "contact.html");
        navigateTo(contactUrl);
        waitForPageLoad();
        waitHelper.waitForHeaderLoaded();
        return this;
    }

    /**
     * Check if contact form is displayed
     */
    public boolean isContactFormDisplayed() {
        return isElementDisplayed(contactForm);
    }

    /**
     * Enter name in the form
     */
    public ContactPage enterName(String name) {
        enterText(nameInput, name);
        return this;
    }

    /**
     * Enter email in the form
     */
    public ContactPage enterEmail(String email) {
        enterText(emailInput, email);
        return this;
    }

    /**
     * Enter subject in the form
     */
    public ContactPage enterSubject(String subject) {
        enterText(subjectInput, subject);
        return this;
    }

    /**
     * Enter message in the form
     */
    public ContactPage enterMessage(String message) {
        enterText(messageInput, message);
        return this;
    }

    /**
     * Fill complete contact form
     */
    public ContactPage fillContactForm(String name, String email, String subject, String message) {
        enterName(name);
        enterEmail(email);
        enterSubject(subject);
        enterMessage(message);
        return this;
    }

    /**
     * Click submit button
     */
    public ContactPage clickSubmit() {
        click(submitButton);
        waitHelper.waitForUIUpdate();
        return this;
    }

    /**
     * Submit complete form (fill and submit)
     */
    public ContactPage submitContactForm(String name, String email, String subject, String message) {
        fillContactForm(name, email, subject, message);
        clickSubmit();
        return this;
    }

    /**
     * Get form feedback message
     */
    public String getFormFeedback() {
        return getText(formFeedback);
    }

    /**
     * Check if form feedback is displayed
     */
    public boolean isFormFeedbackDisplayed() {
        return isElementDisplayed(formFeedback);
    }

    /**
     * Get name field value
     */
    public String getNameValue() {
        return driver.findElement(nameInput).getAttribute("value");
    }

    /**
     * Get email field value
     */
    public String getEmailValue() {
        return driver.findElement(emailInput).getAttribute("value");
    }

    /**
     * Get subject field value
     */
    public String getSubjectValue() {
        return driver.findElement(subjectInput).getAttribute("value");
    }

    /**
     * Get message field value
     */
    public String getMessageValue() {
        return driver.findElement(messageInput).getAttribute("value");
    }

    /**
     * Check if name error is displayed
     */
    public boolean isNameErrorDisplayed() {
        try {
            return driver.findElement(nameError).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if email error is displayed
     */
    public boolean isEmailErrorDisplayed() {
        try {
            return driver.findElement(emailError).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if subject error is displayed
     */
    public boolean isSubjectErrorDisplayed() {
        try {
            return driver.findElement(subjectError).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if message error is displayed
     */
    public boolean isMessageErrorDisplayed() {
        try {
            return driver.findElement(messageError).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get name error message
     */
    public String getNameErrorMessage() {
        if (isNameErrorDisplayed()) {
            return getText(nameError);
        }
        return "";
    }

    /**
     * Get email error message
     */
    public String getEmailErrorMessage() {
        if (isEmailErrorDisplayed()) {
            return getText(emailError);
        }
        return "";
    }

    /**
     * Get subject error message
     */
    public String getSubjectErrorMessage() {
        if (isSubjectErrorDisplayed()) {
            return getText(subjectError);
        }
        return "";
    }

    /**
     * Get message error message
     */
    public String getMessageErrorMessage() {
        if (isMessageErrorDisplayed()) {
            return getText(messageError);
        }
        return "";
    }

    /**
     * Check if any validation error is displayed
     */
    public boolean hasAnyValidationError() {
        return isNameErrorDisplayed() ||
               isEmailErrorDisplayed() ||
               isSubjectErrorDisplayed() ||
               isMessageErrorDisplayed();
    }

    /**
     * Clear all form fields
     */
    public ContactPage clearForm() {
        driver.findElement(nameInput).clear();
        driver.findElement(emailInput).clear();
        driver.findElement(subjectInput).clear();
        driver.findElement(messageInput).clear();
        return this;
    }

    /**
     * Check if submit button is enabled
     */
    public boolean isSubmitButtonEnabled() {
        return driver.findElement(submitButton).isEnabled();
    }

    /**
     * Check if submit button is displayed
     */
    public boolean isSubmitButtonDisplayed() {
        return isElementDisplayed(submitButton);
    }

    /**
     * Get submit button text
     */
    public String getSubmitButtonText() {
        return getText(submitButton);
    }

    /**
     * Check if name field has required attribute
     */
    public boolean isNameFieldRequired() {
        String required = driver.findElement(nameInput).getAttribute("required");
        return required != null;
    }

    /**
     * Check if email field has required attribute
     */
    public boolean isEmailFieldRequired() {
        String required = driver.findElement(emailInput).getAttribute("required");
        return required != null;
    }

    /**
     * Check if subject field has required attribute
     */
    public boolean isSubjectFieldRequired() {
        String required = driver.findElement(subjectInput).getAttribute("required");
        return required != null;
    }

    /**
     * Check if message field has required attribute
     */
    public boolean isMessageFieldRequired() {
        String required = driver.findElement(messageInput).getAttribute("required");
        return required != null;
    }

    /**
     * Get name field minlength attribute
     */
    public int getNameFieldMinLength() {
        String minLength = driver.findElement(nameInput).getAttribute("minlength");
        return minLength != null ? Integer.parseInt(minLength) : 0;
    }

    /**
     * Get subject field minlength attribute
     */
    public int getSubjectFieldMinLength() {
        String minLength = driver.findElement(subjectInput).getAttribute("minlength");
        return minLength != null ? Integer.parseInt(minLength) : 0;
    }

    /**
     * Get message field minlength attribute
     */
    public int getMessageFieldMinLength() {
        String minLength = driver.findElement(messageInput).getAttribute("minlength");
        return minLength != null ? Integer.parseInt(minLength) : 0;
    }

    /**
     * Verify form was successfully submitted by checking success message
     */
    public boolean isFormValidByBrowser() {
        // Check if success message is displayed
        return (Boolean) executeScript(
            "var feedback = document.getElementById('formFeedback');" +
            "return feedback && feedback.style.display === 'block' && " +
            "feedback.textContent.includes('successfully');"
        );
    }

    /**
     * Get HTML5 validation message for name field
     */
    public String getNameValidationMessage() {
        return (String) executeScript("return document.querySelector('[data-testid=\"contact-name-input\"]').validationMessage;");
    }

    /**
     * Get HTML5 validation message for email field
     */
    public String getEmailValidationMessage() {
        return (String) executeScript("return document.querySelector('[data-testid=\"contact-email-input\"]').validationMessage;");
    }

    /**
     * Get HTML5 validation message for subject field
     */
    public String getSubjectValidationMessage() {
        return (String) executeScript("return document.querySelector('[data-testid=\"contact-subject-input\"]').validationMessage;");
    }

    /**
     * Get HTML5 validation message for message field
     */
    public String getMessageValidationMessage() {
        return (String) executeScript("return document.querySelector('[data-testid=\"contact-message-input\"]').validationMessage;");
    }

    /**
     * Check if name field is marked as invalid by browser
     */
    public boolean isNameFieldInvalid() {
        return (Boolean) executeScript("return !document.querySelector('[data-testid=\"contact-name-input\"]').validity.valid;");
    }

    /**
     * Check if email field is marked as invalid by custom validation
     */
    public boolean isEmailFieldInvalid() {
        waitHelper.waitForUIUpdate();
        String result = (String) executeScript(
            "var errorElem = document.getElementById('email-error');" +
            "return (errorElem && errorElem.textContent && errorElem.textContent.trim().length > 0) ? 'true' : 'false';"
        );
        return "true".equals(result);
    }

    /**
     * Check if subject field is marked as invalid by custom validation
     */
    public boolean isSubjectFieldInvalid() {
        waitHelper.waitForUIUpdate();
        String result = (String) executeScript(
            "var errorElem = document.getElementById('subject-error');" +
            "return (errorElem && errorElem.textContent && errorElem.textContent.trim().length > 0) ? 'true' : 'false';"
        );
        return "true".equals(result);
    }

    /**
     * Check if message field is marked as invalid by custom validation
     */
    public boolean isMessageFieldInvalid() {
        waitHelper.waitForUIUpdate();
        String result = (String) executeScript(
            "var errorElem = document.getElementById('message-error');" +
            "return (errorElem && errorElem.textContent && errorElem.textContent.trim().length > 0) ? 'true' : 'false';"
        );
        return "true".equals(result);
    }

    /**
     * Verify all form elements are present
     */
    public boolean areAllFormElementsPresent() {
        return isContactFormDisplayed() &&
               isElementDisplayed(nameInput) &&
               isElementDisplayed(emailInput) &&
               isElementDisplayed(subjectInput) &&
               isElementDisplayed(messageInput) &&
               isSubmitButtonDisplayed();
    }
}
