package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.data.ITerm;
import io.github.up2jakarta.lov.IError;

/**
 * Contact interface for an input event.
 *
 * @param <D> the business term type
 */
public interface IEvent<D extends ITerm<D>> extends IError {

    /**
     * Default event code for JSR-303 {@link jakarta.validation.ConstraintViolation}
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
     * Default event code for {@link io.github.up2jakarta.csv.cfg.Up2Date}
     */
    String EC_UTIL_DATE = "UP2-C006";
    /**
     * Default event code for {@link io.github.up2jakarta.csv.cfg.Up2Character}
     */
    String EC_CHARACTER = "UP2-C007";

    /**
     * <ul>
     *      Note that the offset can be <code>null</code> in the following cases:
     *      <li>Unknown property violates JSR-303 constraint</li>
     *      <li>Segment violates cardinality constraint</li>
     * </ul>
     *
     * @return the data offset of the input record
     */
    Integer getOffset();

    /**
     * @return the business term
     */
    D getType();

}
