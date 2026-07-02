package io.github.up2jakarta.lov;

import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.TypeContext;

import java.util.List;
import java.util.Objects;

/**
 * List of values provider that's supplies the all possibles values for the specified code-list.
 *
 * @param <T> the code-list values
 */
public abstract class CodeListProvider<T extends CodeList<?>> implements CodeListResolver<T> {

    @Override
    public final TypeAdapter<T> resolve(Class<T> type, TypeContext context) throws AccessException {
        final List<T> values = this.values(type, context).stream().filter(Objects::nonNull).toList();
        final String name = context.getTypeName();
        if (values.isEmpty()) {
            final String code = context.getCode();
            final SeverityType level = context.getLevel();
            return new TypeAdapter<>() {
                @Override
                public Class<T> getType() {
                    return type;
                }

                @Override
                public T parse(String value) throws TypeException {
                    throw new CodeListException(name, value, level, code);
                }

                @Override
                public String format(T value) throws TypeException {
                    return value.getCode();
                }
            };
        }
        for (final CodeList<?> value : values) {
            if (!type.isInstance(value)) {
                throw new AccessException(context, "#[" + value.getCode() + "] should be instance of " + type);
            }
        }
        return new CodeListAdapter<>(type, name, context.getLevel(), context.getCode(), values);
    }

    /**
     * Returns all listed values for the specified code-list <code>type</code> from the input configuration that
     * maye be file or database table name or filter.
     *
     * @param type    the code-list type
     * @param context the code-list context
     * @return the list of values
     * @throws AccessException for some reason the provider cannot list values from the specified arguments
     */
    protected abstract <C extends T> List<C> values(Class<C> type, TypeContext context) throws AccessException;

}
