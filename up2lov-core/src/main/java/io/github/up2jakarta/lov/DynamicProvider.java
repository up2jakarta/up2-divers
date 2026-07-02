package io.github.up2jakarta.lov;

import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.TypeContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static io.github.up2jakarta.lov.DynamicProvider.FILE;
import static java.lang.Thread.currentThread;

/**
 * <ul>
 *     Simple implementation of {@link CodeListProvider} that supports file values registry
 *     within the following formats:
 *     <li><code>.properties</code> files with <code>=</code> key-value delimiter</li>
 *     <li><code>.yaml</code> or <code>.yml</code> files with <code>:</code> key-value delimiter</li>
 * </ul>
 * This provider requires only one parameter {@link #FILE}, the specified file must be in class path.
 */
@Support(@Parameter(FILE))
public final class DynamicProvider extends CodeListProvider<DynamicCode> {

    public static final DynamicProvider INSTANCE = new DynamicProvider();
    public static final String FILE = "file";

    private DynamicProvider() {
    }

    public static List<DynamicCode> values(TypeContext context) {
        final Properties ps = new Properties();
        try (final InputStream is = currentThread().getContextClassLoader().getResourceAsStream(context.get(FILE))) {
            ps.load(is);
        } catch (IOException cause) {
            throw new AccessException(context, cause);
        }
        final List<DynamicCode> values = new ArrayList<>(ps.size());
        ps.forEach((c, n) -> values.add(new DynamicCode((String) c, (String) n)));
        return values;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <C extends DynamicCode> List<C> values(Class<C> type, TypeContext context) throws AccessException {
        return (List<C>) values(context);
    }

}
