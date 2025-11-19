package io.github.up2jakarta.cii.core;

import io.github.up2jakarta.lov.CodeList;

@SuppressWarnings("unused")
public enum Agency implements CodeList<Agency> {

    /**
     * International Organization for Standardization
     */
    ISO("5", "ISO", "ISO"),

    /**
     * United Nations Economic Commission for Europe
     */
    UN_ECE("6", "UN/CEFACT", "ECE"),

    /**
     * EU, EDI for financial, informational, cost, accounting, auditing and social areas
     */
    EU_EDI("210", "EU/EDIFICAS", "EDI"),

    /**
     * European standard EN16931, CEF (Connecting Europe Facility).
     */
    EN_16931("FR", "FR/PPF", "PPF"),
    ;

    private final String code;
    private final String name;
    private final String trigram;

    Agency(String code, String name, String trigram) {
        this.code = code;
        this.name = name;
        this.trigram = trigram;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getTrigram() {
        return trigram;
    }

}
