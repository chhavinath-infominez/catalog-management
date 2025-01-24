package com.infominez.catalog.auth.utils;

public class EnumUtils {

    public enum OPERATOR {
        ADD("ADD"),
        SUBTRACT("SUBTRACT"),
        DIVIDE("DIVIDE"),
        MULTIPLY("MULTIPLY");

        @SuppressWarnings("unused")
        private final String value;

        OPERATOR(String value) {
            this.value = value;
        }
    }

    public enum LoginType {
        FACEBOOK("FACEBOOK"),
        GOOGLE("GOOGLE"),
        PIN("PIN");
        @SuppressWarnings("unused")
        private final String value;

        LoginType(String value) {
            this.value = value;
        }
    }

    public enum SendOtpType {
        REGISTRATION("REGISTRATION"),
        RESET_PIN("RESET_PIN"),
        FORGET_PIN("FORGET_PIN");
        @SuppressWarnings("unused")
        private final String value;

        SendOtpType(String value) {
            this.value = value;
        }
    }

    public enum MeetingStatus {
        CREATED("CREATED"),
        PENDING("PENDING"),
        SCHEDULED("SCHEDULED"),
        CANCELLED("CANCELLED");
        @SuppressWarnings("unused")
        private final String value;

        MeetingStatus(String value) {
            this.value = value;
        }
    }

}
