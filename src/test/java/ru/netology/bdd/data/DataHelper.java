package ru.netology.bdd.data;

import lombok.Value;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "password");
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo info) {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardInfo {
        private String cardNumber;

        public CardInfo(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public String getMaskedNumber() {
            return "**** **** **** " + cardNumber.substring(15);
        }
    }
}