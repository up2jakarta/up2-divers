package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.api.ext.TypeContext;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Decimal;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.core.AccessMode;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import static io.github.up2jakarta.csv.core.ext.JpaTableChecker.checkName;
import static io.github.up2jakarta.csv.prc.DefaultProcessor.undefined;

final class JpaColumnContext implements TypeContext {

    private final String prefix;

    JpaColumnContext(Class<? extends Segment> type, String prefix) throws BeanException {
        this.prefix = prefix;
        final PrimaryKeyJoinColumn column = type.getAnnotation(PrimaryKeyJoinColumn.class);
        if (column != null) {
            checkName(type, column.name(), prefix, "@PrimaryKeyJoinColumn[name]");
        }
    }

    private void checkNumber(Field field, Column column, int precision) throws BeanException {
        if (column.precision() < precision) {
            throw new BeanException(field, "@Column[precision] must be greater than or equals to " + precision);
        }
        if (column.scale() != 0) {
            throw new BeanException(field, "@Column[scale] must not be specified");
        }
        if (field.isAnnotationPresent(Up2Decimal.class)) {
            throw new BeanException(field, "must not be annotated with @Up2Decimal, use @Up2Number instead");
        }
    }

    private void checkNumberColumn(Field field, Class<?> type, Column column) throws BeanException {
        if (field.isAnnotationPresent(Digits.class)) {
            throw new BeanException(field, "must not be annotated with @Digits");
        }
        if (column.scale() < 0) {
            throw new BeanException(field, "@Column[scale] must be positive");
        }
        if (column.precision() < 0) {
            throw new BeanException(field, "@Column[precision] must be positive");
        }
        if (type == Long.class || type == long.class) {
            checkNumber(field, column, 19);
        }
        if (type == Integer.class || type == int.class) {
            checkNumber(field, column, 10);
        }
        if (type == Short.class || type == short.class) {
            checkNumber(field, column, 5);
        }
        if (type == Byte.class || type == byte.class) {
            checkNumber(field, column, 3);
        }
        if (column.scale() == 0 && !field.isAnnotationPresent(Up2Number.class)) {
            throw new BeanException(field, "must be annotated with @Up2Number");
        }
        if (column.scale() != 0) {
            final Up2Decimal decimal = field.getAnnotation(Up2Decimal.class);
            if (decimal != null && column.scale() < decimal.value()) {
                throw new BeanException(field, "@Up2Decimal[value] must be be less than or equals to @Column[scale]");
            }
            if (field.isAnnotationPresent(Up2Number.class)) {
                throw new BeanException(field, "must not be annotated with @Up2Number, use @Up2Decimal instead");
            }
        }
    }

    private void checkStringColumn(Field field, Column column, Size size) throws BeanException {
        if (size == null) {
            throw new BeanException(field, "must be annotated with @Size");
        }
        if (size.max() < 0) {
            throw new BeanException(field, "@Size[max] must be positive");
        }
        if (size.min() < 0) {
            throw new BeanException(field, "@Size[min] must be positive");
        }
        if (column.length() < size.max()) {
            throw new BeanException(field, "@Size[max] must be less than or equals @Column[length]");
        }
        final Position position = field.getAnnotation(Position.class);
        if (!column.nullable() && (position == null || undefined(position))) {
            if (field.isAnnotationPresent(NotEmpty.class)) {
                if (field.isAnnotationPresent(NotBlank.class)) {
                    throw new BeanException(field, "must not be annotated by @NotBlank in favor of @NotEmpty");
                }
            } else if (!field.isAnnotationPresent(NotBlank.class)) {
                throw new BeanException(field, "must be annotated @NotEmpty or @NotBlank when @Column[nullable] is false");
            }
        }
    }

    private void checkColumn(Field field, Class<?> type, Column column) throws BeanException {
        checkName(field, column.name(), prefix, "@Column[name]");
        if (column.length() < 0) {
            throw new BeanException(field, "@Column[length] must be positive");
        }
        //Check length & nullable
        final Size size = field.getAnnotation(Size.class);
        if (String.class.equals(type)) {
            checkStringColumn(field, column, size);
        } else {
            if (column.nullable() == field.isAnnotationPresent(NotNull.class)) {
                throw new BeanException(field, "@NotNull does not match with @Column[nullable]");
            }
            if (size != null && !(type.isArray() || Collection.class.isAssignableFrom(type) || Map.class.isAssignableFrom(type))) {
                throw new BeanException(field, "must not be annotated with @Size");
            }
        }
        // Check Number
        if (Number.class.isAssignableFrom(type)) {
            checkNumberColumn(field, type, column);
        }
    }

    @Override
    public void beforeSuperSegment(Class<? extends Segment> type) throws BeanException {
        if (type.getAnnotation(Entity.class) != null) {
            final Inheritance inheritance = type.getAnnotation(Inheritance.class);
            if (inheritance == null) {
                throw new BeanException(type, "must be annotated by @Inheritance");
            }
            final DiscriminatorColumn column = type.getAnnotation(DiscriminatorColumn.class);
            if (column != null) {
                checkName(type, column.name(), prefix, "@DiscriminatorColumn[name]");
            }
        } else if (type.getAnnotation(MappedSuperclass.class) == null) {
            throw new BeanException(type, "must be annotated by @MappedSuperclass");
        }
    }

    @Override
    public void positionProperty(Field property, Class<?> type, int offset) throws BeanException {
        final Column column = property.getAnnotation(Column.class);
        if (column != null) {
            checkColumn(property, type, column);
        }
    }

    @Override
    public void beforeFragmentProperty(AccessMode mode, Field fragment, Class<? extends Segment> type) throws BeanException {
        final PrimaryKeyJoinColumn column = type.getAnnotation(PrimaryKeyJoinColumn.class);
        if (column != null) {
            checkName(type, column.name(), prefix, "@PrimaryKeyJoinColumn[name]");
        }
        if (fragment.getAnnotation(Embedded.class) != null || fragment.getAnnotation(EmbeddedId.class) != null) {
            if (type.getAnnotation(Embeddable.class) == null) {
                throw new BeanException(type, "must be annotated by @Embeddable");
            }
        } else if (fragment.getAnnotation(OneToOne.class) != null) {
            if (type.getAnnotation(Entity.class) == null) {
                throw new BeanException(type, "must be annotated by @Entity");
            }
        } else if (fragment.getAnnotation(ManyToOne.class) != null) {
            if (type.getAnnotation(Entity.class) == null) {
                throw new BeanException(type, "must be annotated by @Entity");
            }
            final JoinColumn join = type.getAnnotation(JoinColumn.class);
            final JoinColumns joins = type.getAnnotation(JoinColumns.class);
            if (join != null) {
                if (joins != null) {
                    throw new BeanException(fragment, "must be annotated by only one @JoinColumn(s)");
                }
                checkName(fragment, join.name(), prefix, "@JoinColumn[name]");
            } else if (joins == null) {
                throw new BeanException(type, "must be annotated by @JoinColumn(s)");
            } else {
                for (final JoinColumn jc : joins.value()) {
                    checkName(fragment, jc.name(), prefix, "@JoinColumn[name]");
                    if (!jc.foreignKey().name().isBlank()) {
                        throw new BeanException(type, "@ForeignKey[name] must be empty");
                    }
                }
            }
        } else if (fragment.getAnnotation(Transient.class) != null) {
            throw new BeanException(fragment, "must be annotated by @Transient");
        }
    }

}
