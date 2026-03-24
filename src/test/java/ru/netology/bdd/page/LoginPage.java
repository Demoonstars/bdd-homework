package ru.netology.bdd.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.bdd.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        loginField.setValue(info.getLogin());

        passwordField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        passwordField.setValue(info.getPassword());

        loginButton.click();
        return new VerificationPage();
    }
}