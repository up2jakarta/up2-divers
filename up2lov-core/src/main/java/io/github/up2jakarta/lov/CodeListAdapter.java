package io.github.up2jakarta.lov;

import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.SafeAdapter;

import java.util.ArrayList;
import java.util.List;

import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.lov.core.AccessException.notNull;
import static io.github.up2jakarta.lov.core.Beans.getTypeName;
import static java.util.Collections.unmodifiableList;
import static java.util.Comparator.comparing;

/**
 * Binary search based implementation of {@link TypeAdapter} for {@link CodeList}.
 *
 * @param <C> the code-list type
 */
public class CodeListAdapter<C extends CodeList<?>> extends SafeAdapter<C> {

    protected final List<C> values;
    protected final String name;

    /**
     * Constructor with full arguments.
     *
     * @param type  the code-list type
     * @param level the error level
     * @param code  the error code
     * @param lov   the list of values
     */
    public CodeListAdapter(Class<C> type, String name, SeverityType level, String code, List<C> lov) {
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
    public CodeListAdapter(Class<C> type, SeverityType level, String code, List<C> values) {
        this(type, getTypeName(type), level, code, values);
    }

    /**
     * Constructor with default {@link ConstantProvider} provider.
     *
     * @param type  the code-list type
     * @param level the error level
     * @param code  the error code
     */
    public CodeListAdapter(Class<C> type, SeverityType level, String code) {
        this(type, level, code, ConstantProvider.values(type));
    }

    /**
     * Constructor with default {@link SeverityType#ERROR}.
     *
     * @param type the code-list type
     * @param code the error code
     */
    public CodeListAdapter(Class<C> type, String code) {
        this(type, ERROR, code);
    }

    private List<C> safe(Class<C> type, List<C> values) {
        notNull(values, CodeListAdapter.class, "values");
        final List<C> lov = new ArrayList<>(values);
        lov.removeIf(c -> c == null || c.getCode() == null);
        lov.sort(comparing(CodeList::getCode));
        for (var i = 0; i < lov.size(); i++) {
            final String code = lov.get(i).getCode();
            for (int j = i + 1; j < lov.size(); j++) {
                if (code.equals(lov.get(j).getCode())) {
                    throw new AccessException(type, code, "must be unique");
                }
            }
        }
        return unmodifiableList(lov);
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
        int min = 0;
        int max = values.size() - 1;
        while (min <= max) {
            int mid = (min + max) >>> 1;
            final C cl = values.get(mid);
            final int cmp = value.compareTo(cl.getCode());
            if (cmp < 0) {
                max = mid - 1;
            } else if (cmp > 0) {
                min = mid + 1;
            } else {
                return cl;
            }
        }
        throw new CodeListException(name, value, level, code);
    }

}
