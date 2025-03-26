package io.github.up2jakarta.xml.api;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Consumer;

/**
 * Encapsulate XML multiple validation errors.
 * It is mainly used when processing invoices and the flag <code>failFast</code> is disabled.
 */
public class XMultipleException extends XValidationException {

    private final List<XValidationException> causes;

    public XMultipleException(XValidationException primary, List<XValidationException> causes) {
        super(primary);
        this.causes = causes;
    }

    public List<XValidationException> getCauses() {
        return causes;
    }

    @Override
    public final void printStackTrace(PrintStream stream) {
        print(stream::println, stream::print, e -> e.printStackTrace(stream));
    }

    @Override
    public final void printStackTrace(PrintWriter writer) {
        print(writer::println, writer::print, e -> e.printStackTrace(writer));
    }

    private void print(Consumer<String> println, Consumer<String> print, Consumer<XValidationException> unit) {
        println.accept("Multiple exceptions occurred:");
        for (var i = 0; i < causes.size(); i++) {
            print.accept((i + 1) + ") ");
            unit.accept(causes.get(i));
        }
    }

    @Override
    public final int size() {
        return causes.size();
    }

}