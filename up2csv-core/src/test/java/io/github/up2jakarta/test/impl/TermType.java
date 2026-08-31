package io.github.up2jakarta.test.impl;

import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.lov.CodeList;

public enum TermType implements ITerm<TermType> {

    NONE("D00", "Empty"),
    NODE("D01", "Dummy"),
    UUID("D02", "Business Identifier"),

    D001("001", "Invoice"),
    D002("002", "Seller"),
    D003("003", "Buyer"),
    D004("004", "Items"),
    D005("005", "Notes"),
    D006("006", "Charge or Allowance amounts"),
    D007("007", "Payer"),
    D008("008", "Payee"),
    D009("009", "Item attributes"),

    I001("I01", "Invoice id"),
    I002("I02", "Issue date"),
    I003("I03", "Gross amount"),
    I004("I04", "Net amount"),
    I005("I05", "Tax amount"),
    I006("I06", "Item id"),
    I007("I07", "Product"),
    I008("I08", "Quantity"),

    A001("A01", "Key"),
    A002("A02", "Value"),
    A003("A03", "Description"),
    A004("A04", "Content"),

    P001("P01", "Name"),
    P002("P02", "Country"),
    P003("P03", "City"),
    P004("P04", "ZIP code"),
    P005("P05", "Address 1st line"),
    P006("P06", "Address 2nd line"),
    ;

    private final String name;
    private final String code;

    TermType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return CodeList.toString(code, name);
    }

}
