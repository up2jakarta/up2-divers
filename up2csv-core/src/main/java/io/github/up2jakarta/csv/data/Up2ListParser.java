package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.lov.CodeListConverter;

import java.util.List;

import static io.github.up2jakarta.csv.api.IEvent.EC_CODE_LIST;
import static io.github.up2jakarta.lov.SeverityType.ERROR;

/**
 * Generic {@link CodeList} formatter and parser.
 *
 * @param <T> the code-list type
 */
public class Up2ListParser<T extends CodeList<T>> extends CodeListConverter<T> {

    public Up2ListParser(Class<T> type, List<T> values) {
        super(type, ERROR, EC_CODE_LIST, values);
    }

}
