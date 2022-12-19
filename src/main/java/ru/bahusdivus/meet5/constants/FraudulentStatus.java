package ru.bahusdivus.meet5.constants;

public enum FraudulentStatus {
    FRAUDULENT("Fraudulent user"),
    NOT_FRAUDULENT("Not fraudulent user");

    private final String value;

    FraudulentStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
