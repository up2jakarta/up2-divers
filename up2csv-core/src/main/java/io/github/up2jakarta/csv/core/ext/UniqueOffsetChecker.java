package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.api.ext.TypeContext;
import io.github.up2jakarta.csv.api.ext.TypeListener;
import io.github.up2jakarta.csv.core.BeanAccess;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * Up2J {@link io.github.up2jakarta.csv.cfg.Position} checker implementation, useful for persist-able output result.
 */
@Named
@Singleton
public final class UniqueOffsetChecker implements TypeListener, TypeContext {
    private final List<Integer> offsets = new LinkedList<>();

    @Override
    public TypeContext beforeSegment(BeanAccess mode, Class<? extends Segment> type) {
        return this;
    }

    @Override
    public void positionProperty(Field property, Class<?> type, int offset) throws BeanException {
        if (!offsets.add(offset)) {
            throw new BeanException(property, "@Position[value] must be unique");
        }
    }
}
