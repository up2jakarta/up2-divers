package io.github.up2jakarta.cii.core;

import io.github.up2jakarta.cii.CII;
import io.github.up2jakarta.lov.PropertyException;

import java.util.Map;

public abstract class ErrorEnhancer {

    private static final Map<String, String> NS_ENHANCEMENTS = Map.of(
            "\"" + CII.XML_SCHEMA_UDT_NAMESPACE_URL + "\"", "udt",
            "\"" + CII.XML_SCHEMA_QDT_NAMESPACE_URL + "\"", "qdt",
            "\"" + CII.XML_SCHEMA_RSM_NAMESPACE_URL + "\"", "rsm",
            "\"" + CII.XML_SCHEMA_RAM_NAMESPACE_URL + "\"", "ram"
    );

    public static String enhance(Throwable cause, String msg) {
        if (cause instanceof PropertyException pex) {
            return pex.getCode() + ": " + msg + ".";
        }
        for (var replacement : NS_ENHANCEMENTS.entrySet()) {
            msg = msg.replace(replacement.getKey(), replacement.getValue());
        }
        return msg;
    }

}
