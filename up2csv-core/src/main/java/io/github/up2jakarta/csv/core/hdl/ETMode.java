package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.core.BSNode;
import io.github.up2jakarta.csv.core.Up2Mapper;
import io.github.up2jakarta.xml.api.SeverityType;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

final class ETMode extends EventType<ETWrapper> {

    public static final ETMode INSTANCE = new ETMode();
    private static final List<String> EXCLUSIONS;

    static {
        EXCLUSIONS = Stream.of(
                BSNode.exclusions(),
                new String[]{
                        FastHandler.class.getName(),
                        EventHandler.class.getName(),
                        FatalCollector.class.getName(),
                        EventCollector.class.getName(),
                        Property.class.getName(),
                        PProperty.class.getName(),
                        PFProperty.class.getName(),
                        PAccessor.class.getName(),
                        PProcessor.class.getName(),
                        PProperty.class.getName() + "$" + PProperty.POProperty.class.getSimpleName(),
                        PProperty.class.getName() + "$" + PProperty.PSProperty.class.getSimpleName(),
                        PFAccessor.class.getName() + "$" + PFAccessor.ROAccess.class.getSimpleName(),
                        PFAccessor.class.getName() + "$" + PFAccessor.WOAccess.class.getSimpleName(),
                        PPAccessor.class.getName() + "$" + PPAccessor.ROAccess.class.getSimpleName(),
                        PPAccessor.class.getName() + "$" + PPAccessor.WOAccess.class.getSimpleName(),
                        PPAccessor.class.getName() + "$" + PPAccessor.NOAccess.class.getSimpleName()
                }
        ).flatMap(Arrays::stream).toList();
    }

    private ETMode() {
        super(ETWrapper.class);
    }

    /**
     * Prints this throwable and its backtrace to the specified <code>printer</code>.
     * Notes that the stack elements will be truncated from the given <code>entry</code> point class
     *
     * @param error   the exception to print
     * @param printer the printer stream
     */
    static void stackTrace(Throwable error, PrintWriter printer) {
        final String cn = Up2Mapper.class.getName();
        printer.println(error);
        final StackTraceElement[] traces = error.getStackTrace();
        for (final StackTraceElement element : traces) {
            if (cn.equals(element.getClassName())) {
                break;
            } else if (!EXCLUSIONS.contains(element.getClassName())) {
                printer.println("\t" + element);
            }
        }
        final Throwable cause = error.getCause();
        if (cause != null) {
            printer.println(cause);
        }
    }

    @Override
    public ETWrapper of(SeverityType level, String code, String message) {
        return new ETWrapper(level, code, message, null);
    }

    @Override
    public ETWrapper of(SeverityType level, String code, Throwable cause) {
        return new ETWrapper(level, code, cause.getMessage(), cause);
    }

}
