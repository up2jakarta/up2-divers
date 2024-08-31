package io.github.up2jakarta.csv.misc;

import io.github.up2jakarta.csv.annotation.Error.Payload;
import io.github.up2jakarta.csv.extension.SeverityType;

/**
 * Simple JSR-303 {@link jakarta.validation.Payload} interface marker annotated by {@link Error}.
 *
 * @see io.github.up2jakarta.csv.annotation.Error.Payload
 * @see Errors.Error
 * @see Errors.Warning
 * @see Errors.Fatal
 */
public final class Errors {

    /**
     * Default error code for JS-303 {@link jakarta.validation.ConstraintViolation}
     */
    public static final String ERROR_VALIDATOR = "UP2-V001";

    /**
     * Default error code for {@link io.github.up2jakarta.csv.annotation.Converter}
     */
    public static final String ERROR_CONVERTER = "UP2-C002";

    /**
     * Default error code for {@link io.github.up2jakarta.csv.annotation.Processor}
     */
    public static final String ERROR_PROCESSOR = "UP2-P003";

    /**
     * Default error code for {@link io.github.up2jakarta.csv.annotation.Up2CodeList}
     */
    public static final String ERROR_CODE_LIST = "UP2-P004";

    /**
     * Default error code for {@link jakarta.xml.bind.annotation.XmlEnum}
     */
    public static final String ERROR_XML_ENUM = "UP2-P005";

    private Errors() {
    }

    /**
     * JSR-303 base marker interface for {@link SeverityType#WARNING}
     */

    @io.github.up2jakarta.csv.annotation.Error(value = ERROR_VALIDATOR, severity = SeverityType.ERROR)
    public interface Error extends Payload {
    }

    /**
     * JSR-303 base marker interface for {@link SeverityType#WARNING}
     */
    @io.github.up2jakarta.csv.annotation.Error(value = ERROR_VALIDATOR, severity = SeverityType.WARNING)
    public interface Warning extends Payload {
    }

    /**
     * JSR-303 base marker interface for {@link SeverityType#FATAL}
     */
    @io.github.up2jakarta.csv.annotation.Error(value = ERROR_VALIDATOR, severity = SeverityType.FATAL)
    public interface Fatal extends Payload {
    }
}
