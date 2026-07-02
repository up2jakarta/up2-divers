package io.github.up2jakarta.lov;

import io.github.up2jakarta.lov.bst.Cache;
import io.github.up2jakarta.lov.core.SVCache;
import io.github.up2jakarta.lov.core.SafeAdapter;
import io.github.up2jakarta.lov.core.TypeContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static io.github.up2jakarta.lov.TableResolver.*;
import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_FORWARD_ONLY;

/**
 * Simple implementation of {@link CodeListProvider} that supports {@link java.util.Properties} values registry.
 * <ul>
 *     This provider requires 3 parameters:
 *     <li>{@link #SQL_TABLE}: the name of SQL table</li>
 *     <li>{@link #SQL_NAME}: the name of SQL <code>name</code> column</li>
 *     <li>{@link #SQL_CODE}: the name of SQL <code>code</code> column</li>
 *  </ul>
 */
@Support({@Parameter(value = SQL_TABLE), @Parameter(value = SQL_NAME), @Parameter(value = SQL_CODE)})
public final class TableResolver implements CodeListResolver<DynamicCode> {

    public static final String SQL_TABLE = "sqlTable";
    public static final String SQL_NAME = "sqlName";
    public static final String SQL_CODE = "sqlCode";

    private final Cache<String, DynamicCode> cache = new SVCache<>(String::compareTo);
    private final DataSource dataSource;

    public TableResolver(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static String sqlQuery(TypeContext context) {
        final String tn = context.get(SQL_TABLE);
        final String cn = context.get(SQL_CODE);
        final String nn = context.get(SQL_NAME);
        return String.format("SELECT cl.%s AS code, cl.%s AS name FROM %s cl WHERE cl.%s = ?", cn, nn, tn, cn);
    }

    @Override
    public TypeAdapter<DynamicCode> resolve(Class<DynamicCode> type, TypeContext context) {
        final String query = sqlQuery(context);
        final String name = context.getTypeName();
        return new SafeAdapter<>(type, context.getLevel(), context.getCode()) {

            private DynamicCode find(String value) throws SQLException {
                try (final Connection c = dataSource.getConnection()) {
                    try (final PreparedStatement ps = c.prepareStatement(query, TYPE_FORWARD_ONLY, CONCUR_READ_ONLY)) {
                        ps.setString(1, value);
                        try (final ResultSet rs = ps.executeQuery()) {
                            final List<DynamicCode> result = new LinkedList<>();
                            while (rs.next()) {
                                result.add(new DynamicCode(rs.getString("code"), rs.getString("name")));
                            }
                            return CodeListException.unique(result, name, value, level, code);
                        }
                    }
                }
            }

            @Override
            protected DynamicCode doParse(String value) throws SQLException {
                return cache.get(value, this::find);
            }

            @Override
            protected String doFormat(DynamicCode value) {
                return value.getCode();
            }
        };
    }
}
