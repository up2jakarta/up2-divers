package io.github.up2jakarta.test.core.misc.map.oneshot;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;

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

    public String getCurrency() {
        return currency;
    }

    public SimpleAddress getSimpleAddress() {
        return simpleAddress;
    }

    public ComplexAddress getComplexAddress() {
        return complexAddress;
    }

}
