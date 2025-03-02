package io.github.up2jakarta.cii.api;

import io.github.up2jakarta.cii.CII;
import io.github.up2jakarta.csv.exception.PropertyException;

import java.util.List;
import java.util.Map;

public interface MessageEnhancer {

    String NS_FORMAT = "[namespace-uri()='%s']";

    Map<String, String> NS_ENHANCEMENTS = Map.of(
            "\"" + CII.XML_SCHEMA_UDT_NAMESPACE_URL + "\"", "udt",
            "\"" + CII.XML_SCHEMA_QDT_NAMESPACE_URL + "\"", "qdt",
            "\"" + CII.XML_SCHEMA_RSM_NAMESPACE_URL + "\"", "rsm",
            "\"" + CII.XML_SCHEMA_RAM_NAMESPACE_URL + "\"", "ram"
    );

    List<String> NS_CLEANINGS = List.of(
            String.format(NS_FORMAT, CII.XML_SCHEMA_UDT_NAMESPACE_URL),
            String.format(NS_FORMAT, CII.XML_SCHEMA_QDT_NAMESPACE_URL),
            String.format(NS_FORMAT, CII.XML_SCHEMA_RSM_NAMESPACE_URL),
            String.format(NS_FORMAT, CII.XML_SCHEMA_RAM_NAMESPACE_URL)
    );

    static String enhance(Throwable cause, String msg) {
        if (cause instanceof PropertyException pex) {
            return pex.getErrorCode() + ": " + msg + ".";
        }
        for (var replacement : NS_ENHANCEMENTS.entrySet()) {
            msg = msg.replace(replacement.getKey(), replacement.getValue());
        }
        return msg;
    }

    static String clean(String msg) {
        for (var replacement : NS_CLEANINGS) {
            msg = msg.replace(replacement, "").replace("*:", "");
        }
        return msg;
    }

    @SuppressWarnings("unchecked")
    static <E extends Throwable> E getCause(Throwable ex, Class<E> type) {
        if (ex == null) {
            return null;
        }
        if (type.isInstance(ex)) {
            return (E) ex;
        }
        return getCause(ex.getCause(), type);
    }
}
