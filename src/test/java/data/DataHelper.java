package data;

import lombok.Value;

import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getAuthInfo() {

        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static VerificationCode getVerificationCodeFor() {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardInfo {
        String cardNumber;
        String testId;
    }

    public static CardInfo getFirstCartInfo() {
        return new CardInfo("5559 0000 0000 0001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
    }

    public static CardInfo getSecondCartInfo() {
        return new CardInfo("5559 0000 0000 0002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    }

    public static int generateValidBalance(int balance) { // метод, который умеет генерировать валидную сумму
        return new Random().nextInt(Math.abs(balance)) + 1; // принимает остаток на балансе карты списания и генерирует сумму остатка
    }

    public static int generateInvalidBalance(int balance) {// вернет сумму, которая будет больше баланса, на случайное число от 0 до 10 000 тыс

        return (Math.abs(balance)) + new Random().nextInt(10000);
    }
}