package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.api.fct.*;
import io.github.up2jakarta.csv.core.BeanLinker;
import io.github.up2jakarta.csv.core.misc.Parsable;

/**
 * Base {@link io.github.up2jakarta.csv.api.IType} implementation.
 */
public final class SegmentType extends BeanLinker.Type<GroupType, SegmentType> implements InvoiceType, DummyTypes {

    private final GroupType type;
    private final String code;

    <P extends Parsable, T extends Parsable>
    SegmentType(GroupType dt, String cl, Class<P> pt, Class<T> ct, IJoin<P, T> jn, ILink<P, T> ln) {
        super(pt, ct, jn, ln);
        this.code = cl;
        this.type = dt;
    }

    <P extends Parsable, T extends Parsable>
    SegmentType(GroupType dt, String cl, Class<P> pt, Class<T> ct, IOptional<P, T> jn, ILink<P, T> ln) {
        this(dt, cl, pt, ct, jn.many(), ln);
    }

    <P extends Parsable, T extends Parsable>
    SegmentType(GroupType dt, String cl, Class<P> pt, Class<T> ct, ISegment<P, T> jn, ILink<P, T> ln) {
        this(dt, cl, pt, ct, jn.many(), ln);
    }

    <P extends Parsable, T extends Parsable>
    SegmentType(GroupType dt, String cl, Class<P> pt, Class<T> ct, IMapValue<P, T> jn, ILink<P, T> ln) {
        this(dt, cl, pt, ct, jn.values(), ln);
    }

    <P extends Parsable, T extends Parsable>
    SegmentType(GroupType dt, String cl, Class<P> pt, Class<T> ct, IMapKey<P, T> jn, ILink<P, T> ln) {
        this(dt, cl, pt, ct, jn.keys(), ln);
    }

    <P extends Parsable, T extends Parsable>
    SegmentType(GroupType dt, String cl, Class<P> pt, Class<T> ct, IArray<P, T> jn, ILink<P, T> ln) {
        this(dt, cl, pt, ct, jn.values(), ln);
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public GroupType getDataType() {
        return type;
    }

}
