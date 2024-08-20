package io.github.up2jakarta.csv.misc;

import io.github.up2jakarta.csv.exception.CodeListException;
import io.github.up2jakarta.csv.extension.CodeList;
import io.github.up2jakarta.csv.extension.SeverityType;
import io.github.up2jakarta.csv.extension.TypeConverter;
import jakarta.persistence.AttributeConverter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.util.stream.Stream;

/**
 * Abstract {@link XmlAdapter} and {@link AttributeConverter} mapping {@link CodeList} to xsd:token
 *
 * @param <E> the CodeList type
 */
public abstract class CodeListConverter<E extends Enum<E> & CodeList<E>> extends TypeConverter<E> {

    protected CodeListConverter(Class<E> type, SeverityType severityType, String code) {
        super(type, severityType, code);
    }

    protected CodeListConverter(Class<E> type, String code) {
        this(type, SeverityType.ERROR, code);
    }

    /**
     * Find the corresponding CodeList constant from the given value.
     *
     * @param value  the CodeList code
     * @param type   the type of CodeList
     * @param values the stream values
     * @param code   the error code
     * @param <C>    the type of CodeList
     * @return the found Documented constant
     * @throws CodeListException if not found
     */
    public static <C extends CodeList<?>> C parse(String value, Class<C> clType, Stream<C> values, SeverityType type, String code) {
        return values.filter(v -> v.getCode().equals(value))
                .findAny()
                .orElseThrow(() -> new CodeListException(clType, value, type, code));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String format(E value) {
        return value.getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E parse(String value) throws CodeListException {
        final Stream<E> values = Stream.of(this.supportedType.getEnumConstants());
        return parse(value, super.supportedType, values, this.errorSeverity, this.errorCode);
    }

}
