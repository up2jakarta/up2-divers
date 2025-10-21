package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.xml.clv.CodeList;
import io.github.up2jakarta.xml.clv.CodeListConverter;
import io.github.up2jakarta.xml.clv.TypeConverter;

import java.util.List;

import static io.github.up2jakarta.csv.core.EventHandler.ERROR_CODE_LIST;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;

/**
 * Generic {@link CodeList} formatter and parser.
 *
 * @param <T> the code-list type
 */
public class Up2ListParser<T extends CodeList<T>> extends TypeConverter<T> {

    private final List<T> values;

    public Up2ListParser(Class<T> type, List<T> values) {
        super(type, ERROR, ERROR_CODE_LIST);
        this.values = values;
    }

    @Override
    public T parse(String value) {
        return CodeListConverter.parse(value, supportedType, values.stream(), errorSeverity, errorCode);
    }

    @Override
    public String format(T value) {
        return value.getCode();
    }

}
