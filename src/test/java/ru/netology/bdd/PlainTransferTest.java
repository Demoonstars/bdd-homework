package ru.netology.bdd;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.netology.bdd.data.DataHelper;
import ru.netology.bdd.page.LoginPage;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class PlainTransferTest {

    @BeforeEach
    void setup() {
        Configuration.browserSize = "1920x1080";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        Configuration.browserCapabilities = options;
        open("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(new DataHelper.AuthInfo("vasya", "qwerty123"));
        var dashboardPage = verificationPage.validVerify(new DataHelper.VerificationCode("12345"));

        // Получаем балансы карт ДО перевода по индексу (0 - первая, 1 - вторая)
        int initialFirstCardBalance = dashboardPage.getCardBalance(0);
        int initialSecondCardBalance = dashboardPage.getCardBalance(1);
        int transferAmount = 1000;

        var transferPage = dashboardPage.selectCardToTransfer(0);
        transferPage.makeTransfer(String.valueOf(transferAmount), "5559 0000 0000 0002");

        // Вычисляем математически правильный баланс ПОСЛЕ перевода
        int expectedFirstCardBalance = initialFirstCardBalance + transferAmount;
        int expectedSecondCardBalance = initialSecondCardBalance - transferAmount;

        Assertions.assertEquals(expectedFirstCardBalance, dashboardPage.getCardBalance(0));
        Assertions.assertEquals(expectedSecondCardBalance, dashboardPage.getCardBalance(1));
    }
}