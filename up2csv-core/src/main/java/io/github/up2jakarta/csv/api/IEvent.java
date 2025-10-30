package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.IError;

/**
 * Contact interface for an input event.
 *
 * @param <D> the input data type
 */
public interface IEvent<D extends DataType<D>> extends IError {

    /**
     * Default error code for JS-303 {@link jakarta.validation.ConstraintViolation}
     */
    String ERROR_VALIDATOR = "UP2-V001";
    /**
     * Default error code for {@link io.github.up2jakarta.csv.cfg.Up2Converter}
     */
    String ERROR_CONVERTER = "UP2-C002";
    /**
     * Default error code for {@link io.github.up2jakarta.csv.cfg.Processor}
     */
    String ERROR_PROCESSOR = "UP2-P001";
    /**
     * Default error code for {@link io.github.up2jakarta.csv.cfg.Up2CodeList}
     */
    String ERROR_CODE_LIST = "UP2-P002";
    /**
     * Default error code for {@link jakarta.xml.bind.annotation.XmlEnum}
     */
    String ERROR_XML_ENUM = "UP2-P003";
    /**
     * Default error code for {@link jakarta.xml.bind.annotation.XmlEnum}
     */
    String ERROR_BOOLEAN = "UP2-P004";

    /**
     * @return the data offset of the input record
     */
    int getOffset();

    /**
     * @return the business data type
     */
    D getType();

}
