package io.github.up2jakarta.lov;

import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.SafeAdapter;
import io.github.up2jakarta.lov.core.TypeContext;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;

import java.util.List;

import static io.github.up2jakarta.lov.EntityResolver.JPA_CODE;
import static io.github.up2jakarta.lov.Support.Parameter;
import static io.github.up2jakarta.lov.core.Beans.getPropertyType;
import static io.github.up2jakarta.lov.core.Beans.getTypeName;
import static io.github.up2jakarta.lov.core.Overrides.get;

/**
 * Simple implementation of {@link CodeListProvider} that supports {@link java.util.Properties} values registry.
 * <ul>
 *     This provider supports only one optional parameter:
 *     <li>{@link #JPA_CODE}: the name of code property</li>
 *  </ul>
 */
@Support({@Parameter(value = JPA_CODE, defaultValue = "code")})
public final class EntityResolver implements CodeListResolver<EntityList<?, ?>> {

    public static final String JPA_CODE = "jpaCode";

    private final EntityManager entityManager;

    public EntityResolver(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private String codeName(Class<?> type, TypeContext context) throws BeanException {
        final String codeName = context.get(JPA_CODE);
        final Class<?> codeType = getPropertyType(type, codeName, p -> p.isAnnotationPresent(Column.class));
        if (codeType == null) {
            throw new BeanException(context, "#argument[" + JPA_CODE + "] must be annotated by @Column");
        }
        if (String.class != codeType) {
            throw new BeanException(context, "#argument[" + JPA_CODE + "] type must be " + String.class);
        }
        return codeName;
    }

    private String entityName(Class<?> type, TypeContext context) throws BeanException {
        final Entity config = get(type, EntityList.class, Entity.class);
        if (config == null) {
            throw new BeanException(context, getTypeName(type) + " must be annotated with @" + Entity.class.getName());
        }
        final String name = config.name();
        if (name.isEmpty()) {
            return type.getSimpleName();
        }
        return name;
    }

    @Override
    public TypeAdapter<EntityList<?, ?>> resolve(Class<EntityList<?, ?>> type, TypeContext ctx) throws BeanException {
        final String name = ctx.getTypeName();
        final String code = this.codeName(type, ctx);
        final String query = String.format("SELECT e FROM %s e WHERE e.%s = ?1", this.entityName(type, ctx), code);
        return new SafeAdapter<>(type, ctx.getLevel(), ctx.getCode()) {
            @Override
            protected EntityList<?, ?> doParse(String value) {
                final List<EntityList<?, ?>> result = entityManager.createQuery(query, type)
                        .setParameter(1, value)
                        .getResultList();
                return CodeListException.unique(result, name, value, level, code);
            }

            @Override
            protected String doFormat(EntityList<?, ?> value) {
                return value.getCode();
            }
        };
    }
}
