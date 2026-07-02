package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.hdl.IComplianceEvent;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.core.BSNode.Flat;
import io.github.up2jakarta.csv.core.hdl.ComplianceCollector;
import io.github.up2jakarta.csv.core.hdl.ComplianceHandler;
import io.github.up2jakarta.csv.data.ITerm;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;

import java.util.List;

import static io.github.up2jakarta.csv.core.BSManager.*;

/**
 * Up2J Processor that maps and validates java-beans to flat-data, based on java annotations configuration.
 *
 * @param <S> the segment type
 * @param <D> The business term type
 */
public final class Up2Flatter<S extends Segment, D extends ITerm<D>> extends Pod<S, D, Flat<S, D>> {

    Up2Flatter(Validator validator, Key<D, S> key, Flat<S, D> node) throws BeanException {
        super(validator, key, node);
    }

    /**
     * Computes and returns the header record from the business term types, depending on {@link Up2Factory} resolver.
     *
     * @return the header record
     * @see io.github.up2jakarta.csv.data.TermResolver
     * @see io.github.up2jakarta.csv.data.Header
     */
    public String[] header() {
        return this.header(offset);
    }

    /**
     * Computes and returns the header record from the business term types, depending on {@link Up2Factory} resolver.
     *
     * @param offset the number of columns reserved {@link Truncated#value()}
     * @return the header record
     * @see io.github.up2jakarta.csv.data.TermResolver
     * @see io.github.up2jakarta.csv.data.Header
     */
    public String[] header(final int offset) {
        final String[] result = new String[length + offset];
        if (this.length != 0) {
            this.node.visit(p -> {
                if (p.dataType != null) {
                    result[offset + p.offset] = p.dataType.getName();
                }
            });
        }
        return result;
    }

    /**
     * Flat-Map the given to segment to CSV record within formatting.
     *
     * @param segment the bean that is being mapped to flat-data
     * @return the formatted array of strings
     * @throws AccessException for any problem when reading properties from the specified segment
     */
    public String[] unmap(S segment) throws AccessException {
        return this.unmap(segment, offset);
    }

    /**
     * Flat-Map the given to segment to CSV record within formatting.
     *
     * @param segment the bean that is being mapped to flat-data
     * @param offset  the number of columns reserved {@link Truncated#value()}
     * @return the formatted array of strings
     * @throws AccessException for any problem when reading properties from the specified segment
     */
    public String[] unmap(S segment, int offset) throws AccessException {
        if (segment == null) return null;
        final String[] result = new String[offset + length];
        if (length != 0) {
            node.format(result, offset, segment);
        }
        return result;
    }

    /**
     * Validates the specified segment and its embeddable fragments and gathering events in the specified handler.
     *
     * @param segment the bean that is being validated
     * @param offset  the number of columns reserved {@link Truncated#value()}
     * @param handler the event handler
     * @throws AccessException for any problem when reading properties from the specified segment
     */
    public void validate(S segment, int offset, ComplianceHandler<D> handler) throws AccessException {
        if (segment != null && validate) {
            node.validate(validator, handler, segment, offset);
        }
    }

    /**
     * Validates the specified segment and its embeddable fragments and gathering events in the specified handler.
     *
     * @param segment the bean that is being validated
     * @param handler the event handler
     * @throws AccessException for any problem when reading properties from the specified segment
     */
    public void validate(S segment, ComplianceHandler<D> handler) throws AccessException {
        this.validate(segment, this.offset, handler);
    }

    /**
     * Validates the given bean and recursively its embeddable fragments and returns the collected events.
     *
     * @param segment the bean that is being validated
     * @param offset  the number of columns reserved {@link Truncated#value()}
     * @return the collected constraint violations
     * @throws AccessException for any problem when reading properties from the specified segment
     */
    public List<? extends IComplianceEvent<D>> validate(S segment, int offset) throws AccessException {
        final ComplianceCollector<D> handler = new ComplianceCollector<>();
        this.validate(segment, offset, handler);
        return handler.toList();
    }

    /**
     * Validates the given bean and recursively its embeddable fragments and returns the collected events.
     *
     * @param segment the bean that is being validated
     * @return the collected constraint violations
     * @throws AccessException for any problem when reading properties from the specified segment
     */
    public List<? extends IComplianceEvent<D>> validate(S segment) throws AccessException, ValidationException {
        return this.validate(segment, this.offset);
    }

    /**
     * Converts the current mapper to mapper that is able to map bean-segment to flat-data.
     * This method is faster then {@link Up2Factory#mapper(Class)}
     * because the bean is already scanned.
     *
     * @return preconfigured CSV Mapper for the same segment
     * @throws BeanException if any property is not accessible for write or cannot create segment instances
     */
    public Up2Mapper<S, D> toMapper() throws BeanException {
        return mp(this).build(validator, Up2Mapper::new);
    }

}
