package io.github.up2jakarta.lov;

import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.SafeAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.lov.core.AccessException.notNull;
import static io.github.up2jakarta.lov.core.Beans.getTypeName;

/**
 * HashMap based implementation of {@link TypeAdapter} for {@link CodeList}.
 *
 * @param <C> the code-list type
 */
public class HashMapAdapter<C extends CodeList<?>> extends SafeAdapter<C> {

    protected final Map<String, C> values;
    protected final String name;

    /**
     * Constructor with full arguments.
     *
     * @param type  the code-list type
     * @param level the error level
     * @param code  the error code
     * @param lov   the list of values
     */
    public HashMapAdapter(Class<C> type, String name, SeverityType level, String code, List<C> lov) {
        super(type, level, code);
        values = this.safe(type, lov);
        this.name = name;
    }

    /**
     * Constructor with default code-list name.
     *
     * @param type   the code-list type
     * @param level  the error level
     * @param code   the error code
     * @param values the list of values (LOV)
     */
    public HashMapAdapter(Class<C> type, SeverityType level, String code, List<C> values) {
        this(type, getTypeName(type), level, code, values);
    }

    /**
     * Constructor with default {@link ConstantProvider} provider.
     *
     * @param type  the code-list type
     * @param level the error level
     * @param code  the error code
     */
    public HashMapAdapter(Class<C> type, SeverityType level, String code) {
        this(type, level, code, ConstantProvider.values(type));
    }

    /**
     * Constructor with default {@link SeverityType#ERROR}.
     *
     * @param type the code-list type
     * @param code the error code
     */
    @SuppressWarnings("unused")
    public HashMapAdapter(Class<C> type, String code) {
        this(type, ERROR, code);
    }

    private Map<String, C> safe(Class<C> type, List<C> values) {
        notNull(values, HashMapAdapter.class, "values");
        final Map<String, C> lov = new HashMap<>(values.size());
        for (final C value : values) {
            final String code;
            if (value != null && (code = value.getCode()) != null) {
                if (lov.put(code, value) != null) {
                    throw new AccessException(type, code, "must be unique");
                }
            }
        }
        return Map.copyOf(lov);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final String doFormat(C value) {
        return value.getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final C doParse(String value) throws CodeListException {
        final C result = values.get(value);
        if (result == null) {
            throw new CodeListException(name, value, level, code);
        }
        return result;
    }

}
