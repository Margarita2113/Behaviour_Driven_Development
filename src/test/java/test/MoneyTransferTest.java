package test;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import data.DataHelper;
import page.DashboardPage;
import page.LoginPageV2;


import static com.codeborne.selenide.Selenide.open;
import static data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import data.DataHelper.*;

public class MoneyTransferTest {

    DashboardPage dashboardPage;

    @BeforeEach
    void setup() {
        var loginPageV2 = open("http://localhost:9999", LoginPageV2.class);
        var authInfo = getAuthInfo();
        var verificationPage = LoginPageV2.validLogin(authInfo);
        var verificationCode = getVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferFromFirstCardToSecond() {
        var firstCardInfo = getFirstCartInfo(); // инфо о первой карте
        var secondCardInfo = getSecondCartInfo(); // инфо о второй карте
        var firstCardBalance = dashboardPage.getCardBalance(getFirstCartInfo()); //  поиск баланса по номеру карты, последние 4 цифры
        var secondCardBalance = dashboardPage.getCardBalance(getSecondCartInfo()); // поиск баланса по номеру карты, последние 4 цифры
        var amount = generateValidBalance(firstCardBalance); // генерирует валидный баланс
        var expectedBalanceFirstCard = firstCardBalance - amount; //ожидаемый результат первой карты
        var expectedBalanceSecondCard = secondCardBalance + amount;
        var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

    @Test
    void shouldGetErrorMessageIfAmountMoreBalance() {
        var firstCardInfo = getFirstCartInfo();
        var secondCardInfo = getSecondCartInfo();
        var firstCardBalance = dashboardPage.getCardBalance(getFirstCartInfo());
        var secondCardBalance = dashboardPage.getCardBalance(getSecondCartInfo());
        var amount = generateInvalidBalance(secondCardBalance);
        var transferPage = dashboardPage.selectCardToTransfer(firstCardInfo);
        transferPage.makeTransfer(String.valueOf(amount), secondCardInfo);
        transferPage.findErrorMessage("Выполнена попытка перевода на суммы,превышающей остаток на карте списания");
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(firstCardBalance, actualBalanceFirstCard);
        assertEquals(secondCardBalance, actualBalanceSecondCard);
    }
}