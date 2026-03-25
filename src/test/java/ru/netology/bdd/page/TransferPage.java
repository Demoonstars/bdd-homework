package ru.netology.bdd.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amountInput = $("[data-test-id=amount] input");
    private SelenideElement fromInput = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");

    public TransferPage() {
        amountInput.shouldBe(Condition.visible);
    }

    public DashboardPage makeTransfer(String amountToTransfer, String cardNumber) {
        amountInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        amountInput.setValue(amountToTransfer);

        fromInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        fromInput.setValue(cardNumber);

        transferButton.click();
        return new DashboardPage();
    }
}