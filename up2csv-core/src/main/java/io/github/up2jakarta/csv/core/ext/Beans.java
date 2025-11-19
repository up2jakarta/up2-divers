package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.data.Segment;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Stack;

public abstract class Beans extends io.github.up2jakarta.lov.core.Beans {

    public static Optional<Error> error(Field property, Class<?> type) {
        final Error config = property.getAnnotation(Error.class);
        if (config == null) {
            return Optional.ofNullable(type.getAnnotation(Error.class));
        }
        return Optional.of(config);
    }

    public static Class<? extends Segment> getSegmentType(Stack<Class<? extends Segment>> stack) {
        Class<? extends Segment> segmentType = stack.peek();
        for (Class<? extends Segment> superType : stack) {
            if (segmentType.isAssignableFrom(superType)) {
                segmentType = superType;
                break;
            }
        }
        return segmentType;
    }

    public static Stack<Class<? extends Segment>> cleanStack(Stack<Class<? extends Segment>> stack) {
        final Stack<Class<? extends Segment>> result = new Stack<>();
        result.push(stack.getFirst());
        for (Class<? extends Segment> superType : stack) {
            if (!superType.isAssignableFrom(result.peek())) {
                result.push(superType);
            }
        }
        return result;
    }

}
