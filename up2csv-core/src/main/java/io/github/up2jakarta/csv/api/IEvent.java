package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.lov.IError;

/**
 * Contact interface for an input event.
 *
 * @param <D> the business data type
 */
public interface IEvent<D extends DataType<D>> extends IError {

    /**
     * Default event code for JS-303 {@link jakarta.validation.ConstraintViolation}
     */
    String EC_COMPLIANCE = "UP2-V001";
    /**
     * Default event code for {@link io.github.up2jakarta.csv.cfg.Processor}
     */
    String EC_PROCESSOR = "UP2-P001";
    /**
     * Default event code for {@link io.github.up2jakarta.csv.cfg.Up2Converter} or those shortcuts
     */
    String EC_CONVERTER = "UP2-C001";
    /**
     * Default event code for {@link io.github.up2jakarta.csv.cfg.Up2CodeList}
     */
    String EC_CODE_LIST = "UP2-C002";
    /**
     * Default event code for {@link jakarta.xml.bind.annotation.XmlEnum}
     */
    String EC_XML_ENUM = "UP2-C003";
    /**
     * Default event code for {@link jakarta.persistence.Enumerated}
     */
    String EC_JPA_ENUM = "UP2-C004";
    /**
     * Default event code for {@link io.github.up2jakarta.csv.cfg.Up2Boolean}
     */
    String EC_BOOLEAN = "UP2-C005";
    /**
     * Default event code for {@link io.github.up2jakarta.csv.cfg.Up2Base64}
     */
    String EC_BASE_64 = "UP2-C006";

    /**
     * <ul>
     *      Note that the offset can be <code>null</code> in the following cases:
     *      <li>Unknown property violates JSR-303 constraint</li>
     *      <li>Segment violates the cardinalities of join relationship ({@link DataType#isValid(DataType, int)})</li>
     * </ul>
     *
     * @return the data offset of the input record
     */
    Integer getOffset();

    /**
     * @return the business data type
     */
    D getType();

}
