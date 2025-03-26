package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Up2Decimal;
import io.github.up2jakarta.csv.annotation.Up2Default;
import io.github.up2jakarta.csv.annotation.Up2Number;
import io.github.up2jakarta.csv.extension.CheckerContext;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.misc.BeanException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.lang.reflect.Field;

import static io.github.up2jakarta.csv.core.JpaTableChecker.checkName;

final class JpaColumnContext implements CheckerContext {

    private final String prefix;

    JpaColumnContext(Class<? extends Segment> entityType, String prefix) throws BeanException {
        this.prefix = prefix;
        final PrimaryKeyJoinColumn column = entityType.getAnnotation(PrimaryKeyJoinColumn.class);
        if (column != null) {
            checkName(entityType, column.name(), prefix, "@PrimaryKeyJoinColumn[name]");
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

    private void checkNumberColumn(Field field, Class<?> fieldType, Column column) throws BeanException {
        if (field.isAnnotationPresent(Digits.class)) {
            throw new BeanException(field, "must not be annotated with @Digits");
        }
        if (column.scale() < 0) {
            throw new BeanException(field, "@Column[scale] must be positive");
        }
        if (column.precision() < 0) {
            throw new BeanException(field, "@Column[precision] must be positive");
        }
        if (fieldType == Long.class || fieldType == long.class) {
            checkNumber(field, column, 19);
        }
        if (fieldType == Integer.class || fieldType == int.class) {
            checkNumber(field, column, 10);
        }
        if (fieldType == Short.class || fieldType == short.class) {
            checkNumber(field, column, 5);
        }
        if (fieldType == Byte.class || fieldType == byte.class) {
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
        if (column.length() < size.max()) {
            throw new BeanException(field, "@Size[max] must be less than or equals @Column[length]");
        }
        if (column.nullable() && size.min() > 0) {
            throw new BeanException(field, "@Size[min] does not match with @Column[nullable]");
        }
        if (size.min() == 0 && column.nullable() == field.isAnnotationPresent(NotBlank.class)) {
            final Up2Default def = field.getAnnotation(Up2Default.class);
            if (def != null) {
                if (def.value().trim().isEmpty()) {
                    throw new BeanException(field, "@Up2Default[value] does not match with @Column[nullable]");
                }
            } else {
                throw new BeanException(field, "@NotBlank does not match with @Column[nullable]");
            }
        }
    }

    private void checkColumn(Field field, Class<?> fieldType, Column column) throws BeanException {
        checkName(field, column.name(), prefix, "@Column[name]");
        if (column.length() < 0) {
            throw new BeanException(field, "@Column[length] must be positive");
        }
        //Check length & nullable
        final Size size = field.getAnnotation(Size.class);
        if (String.class.equals(fieldType)) {
            checkStringColumn(field, column, size);
        } else {
            if (column.nullable() == field.isAnnotationPresent(NotNull.class)) {
                throw new BeanException(field, "@NotNull does not match with @Column[nullable]");
            }
            if (size != null && !fieldType.isArray()) {
                throw new BeanException(field, "must not be annotated with @Size");
            }
        }
        // Check Number
        if (Number.class.isAssignableFrom(fieldType)) {
            checkNumberColumn(field, fieldType, column);
        }
    }

    @Override
    public void beforeSuperSegment(Class<? extends Segment> superType) throws BeanException {
        if (superType.getAnnotation(Entity.class) != null) {
            final Inheritance inheritance = superType.getAnnotation(Inheritance.class);
            if (inheritance == null) {
                throw new BeanException(superType, "must be annotated by @Inheritance");
            }
            final DiscriminatorColumn column = superType.getAnnotation(DiscriminatorColumn.class);
            if (column != null) {
                checkName(superType, column.name(), prefix, "@DiscriminatorColumn[name]");
            }
        } else if (superType.getAnnotation(MappedSuperclass.class) == null) {
            throw new BeanException(superType, "must be annotated by @MappedSuperclass");
        }
    }

    @Override
    public void beforePositionProperty(Field property, Class<?> propertyType, int offset) throws BeanException {
        final Column column = property.getAnnotation(Column.class);
        if (column != null) {
            checkColumn(property, propertyType, column);
        }
    }

    @Override
    public void beforeFragmentProperty(Field field, Class<? extends Segment> fieldType) throws BeanException {
        final PrimaryKeyJoinColumn column = fieldType.getAnnotation(PrimaryKeyJoinColumn.class);
        if (column != null) {
            checkName(fieldType, column.name(), prefix, "@PrimaryKeyJoinColumn[name]");
        }
        if (field.getAnnotation(Embedded.class) != null || field.getAnnotation(EmbeddedId.class) != null) {
            if (fieldType.getAnnotation(Embeddable.class) == null) {
                throw new BeanException(fieldType, "must be annotated by @Embeddable");
            }
        } else if (field.getAnnotation(OneToOne.class) != null) {
            if (fieldType.getAnnotation(Entity.class) == null) {
                throw new BeanException(fieldType, "must be annotated by @Entity");
            }
        } else if (field.getAnnotation(ManyToOne.class) != null) {
            if (fieldType.getAnnotation(Entity.class) == null) {
                throw new BeanException(fieldType, "must be annotated by @Entity");
            }
            final JoinColumn join = fieldType.getAnnotation(JoinColumn.class);
            final JoinColumns joins = fieldType.getAnnotation(JoinColumns.class);
            if (join != null) {
                if (joins != null) {
                    throw new BeanException(field, "must be annotated by only one @JoinColumn(s)");
                }
                checkName(field, join.name(), prefix, "@JoinColumn[name]");
            } else if (joins == null) {
                throw new BeanException(fieldType, "must be annotated by @JoinColumn(s)");
            } else {
                for (final JoinColumn jc : joins.value()) {
                    checkName(field, jc.name(), prefix, "@JoinColumn[name]");
                    if (!jc.foreignKey().name().isBlank()) {
                        throw new BeanException(fieldType, "@ForeignKey[name] must be empty");
                    }
                }
            }
        } else if (field.getAnnotation(Transient.class) != null) {
            throw new BeanException(field, "must be annotated by @Transient");
        }
    }

}
