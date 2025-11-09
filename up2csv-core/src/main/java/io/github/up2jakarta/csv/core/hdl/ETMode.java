package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.core.BSNode;
import io.github.up2jakarta.xml.api.SeverityType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

final class ETMode extends EventType<ETWrapper> {

    public static final ETMode INSTANCE = new ETMode();
    static final List<String> EXCLUSIONS = Stream.of(BSNode.exclusions(), exclusions()).flatMap(Arrays::stream).toList();

    private ETMode() {
        super(ETWrapper.class);
    }

    public static String[] exclusions() {
        return new String[]{
                FastHandler.class.getName(),
                EventHandler.class.getName(),
                FatalCollector.class.getName(),
                EventCollector.class.getName(),
                Property.class.getName(),
                PProperty.class.getName(),
                FProperty.class.getName(),
                PAccessor.class.getName(),
                PProcessor.class.getName(),
                PProperty.class.getName() + "$" + PProperty.POProperty.class.getSimpleName(),
                PProperty.class.getName() + "$" + PProperty.PWProperty.class.getSimpleName(),
                FProperty.class.getName() + "$" + FProperty.FOProperty.class.getSimpleName(),
                FProperty.class.getName() + "$" + FProperty.FWProperty.class.getSimpleName(),
                PFAccessor.class.getName() + "$" + PFAccessor.ROAccess.class.getSimpleName(),
                PFAccessor.class.getName() + "$" + PFAccessor.WOAccess.class.getSimpleName(),
                PPAccessor.class.getName() + "$" + PPAccessor.ROAccess.class.getSimpleName(),
                PPAccessor.class.getName() + "$" + PPAccessor.WOAccess.class.getSimpleName(),
                PPAccessor.class.getName() + "$" + PPAccessor.NOAccess.class.getSimpleName(),
        };
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
