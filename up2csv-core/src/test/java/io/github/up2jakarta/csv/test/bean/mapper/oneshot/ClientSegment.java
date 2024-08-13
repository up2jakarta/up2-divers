package io.github.up2jakarta.csv.test.bean.mapper.oneshot;

import io.github.up2jakarta.csv.annotation.Fragment;
import io.github.up2jakarta.csv.annotation.Position;

@SuppressWarnings("unused")
public final class ClientSegment extends PersonSegment<String> {

    @Position(3)
    private String bankAccount;

    @Position(4)
    private String currency;

    @Fragment(5)
    private SimpleAddress simpleAddress;

    @Fragment(9)
    private ComplexAddress complexAddress;

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public SimpleAddress getSimpleAddress() {
        return simpleAddress;
    }

    public void setSimpleAddress(SimpleAddress simpleAddress) {
        this.simpleAddress = simpleAddress;
    }

    public ComplexAddress getComplexAddress() {
        return complexAddress;
    }

    public void setComplexAddress(ComplexAddress complexAddress) {
        this.complexAddress = complexAddress;
    }
}
