package io.github.up2jakarta.xml.clv;

import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.api.TypeConverter;
import jakarta.persistence.AttributeConverter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.util.List;

import static java.util.Arrays.asList;


/**
 * Abstract {@link XmlAdapter} and {@link AttributeConverter} mapping {@link CodeList} to xsd:token
 *
 * @param <C> the CodeList type
 */
public abstract class CodeListConverter<C extends CodeList<C>> extends TypeConverter<C> {

    protected final List<C> values;

    /**
     * Constructor any code-list with all arguments.
     *
     * @param type   the code-list type
     * @param level  the error severity
     * @param code   the error code
     * @param values the list of values (LOV)
     */
    protected CodeListConverter(Class<C> type, SeverityType level, String code, List<C> values) {
        super(type, level, code);
        this.values = values;
    }

    /**
     * Constructor with all arguments for enumerations.
     *
     * @param type  the code-list type
     * @param level the error severity
     * @param code  the error code
     */
    @SuppressWarnings("unchecked")
    protected <E extends Enum<E> & CodeList<C>> CodeListConverter(Class<E> type, SeverityType level, String code) {
        this((Class<C>) type, level, code, asList((C[]) type.getEnumConstants()));
    }

    /**
     * Constructor for enumerations without error severity, default {@link SeverityType#ERROR}.
     *
     * @param type the code-list type
     * @param code the error code
     */
    protected <E extends Enum<E> & CodeList<C>> CodeListConverter(Class<E> type, String code) {
        this(type, SeverityType.ERROR, code);
    }

    /**
     * Find the corresponding CodeList constant from the given value.
     *
     * @param value  the CodeList code
     * @param type   the type of CodeList implementation
     * @param values the stream values
     * @param level  the error severity
     * @param code   the error code
     * @param <C>    the type of CodeList
     * @return the found Documented constant
     * @throws CodeListException if not found
     */
    public static <C extends CodeList<?>> C find(String value, Class<C> type, List<C> values, SeverityType level, String code) {
        return find(CodeList::getCode, value::equals, values).orElseThrow(() -> new CodeListException(type, value, level, code));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String format(C value) {
        return value.getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public C parse(String value) throws CodeListException {
        return find(value, type, values, level, code);
    }

}
