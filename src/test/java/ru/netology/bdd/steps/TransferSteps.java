package ru.netology.bdd.steps;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.netology.bdd.data.DataHelper;
import ru.netology.bdd.page.DashboardPage;
import ru.netology.bdd.page.LoginPage;
import ru.netology.bdd.page.TransferPage;
import ru.netology.bdd.page.VerificationPage;

import java.util.HashMap;
import java.util.Map;

public class TransferSteps {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private VerificationPage verificationPage;
    private TransferPage transferPage;

    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1920x1080";

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        Configuration.browserCapabilities = options;
    }

    @Пусть("пользователь залогинен с логином {string} и паролем {string}")
    public void login(String login, String password) {
        Selenide.open("http://localhost:9999");
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();

        loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        verificationPage = loginPage.validLogin(new DataHelper.AuthInfo(login, password));
    }

    @И("пользователь вводит корректный проверочный код {string}")
    public void verify(String verificationCode) {
        dashboardPage = verificationPage.validVerify(new DataHelper.VerificationCode(verificationCode));
    }

    @Когда("пользователь переводит {int} рублей с карты с номером {string} на свою {int} карту с главной страницы")
    public void transfer(int amount, String cardNumber, int cardIndex) {
        transferPage = dashboardPage.selectCardToTransfer(cardIndex - 1);
        dashboardPage = transferPage.makeTransfer(String.valueOf(amount), cardNumber);
    }

    @Тогда("баланс его {int} карты из списка на главной странице должен стать {int} рублей")
    public void verifyBalance(int cardIndex, int expectedBalance) {
        int actualBalance = dashboardPage.getCardBalance(cardIndex - 1);
        Assertions.assertEquals(expectedBalance, actualBalance);
    }
}