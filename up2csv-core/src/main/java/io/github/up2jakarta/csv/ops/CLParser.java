package io.github.up2jakarta.csv.ops;

import io.github.up2jakarta.xml.clv.CodeList;
import io.github.up2jakarta.xml.clv.CodeListConverter;
import io.github.up2jakarta.xml.clv.TypeConverter;

import java.util.stream.Stream;

import static io.github.up2jakarta.csv.core.Errors.ERROR_CODE_LIST;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;

/**
 * Generic {@link CodeList} formatter and parser.
 *
 * @param <T> the code-list type
 */
public class CLParser<T extends CodeList<T>> extends TypeConverter<T> {

    final T[] values;

    public CLParser(Class<T> type, T[] values) {
        super(type, ERROR, ERROR_CODE_LIST);
        this.values = values;
    }

    @Override
    public T parse(String value) {
        return CodeListConverter.parse(value, supportedType, Stream.of(values), errorSeverity, errorCode);
    }

    @Override
    public String format(T value) {
        return value.getCode();
    }

}
