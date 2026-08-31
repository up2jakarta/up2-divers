package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.TermResolver;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

/**
 * Dynamic Resolver that's able to resolve {@link Up2Header} annotations.
 */
public class HeaderResolver extends TermResolver<HeaderType> {

    private static final HeaderResolver INSTANCE = new HeaderResolver();

    private HeaderResolver() {
        super(HeaderType.class);
    }

    /**
     * @return the singleton instance
     */
    public static HeaderResolver getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<HeaderType> get(List<Class<? extends Segment>> stack, Field[] path, Field field) {
        final Up2Header config = field.getAnnotation(Up2Header.class);
        if (config != null) {
            return Optional.of(new HeaderType(config.code(), config.name()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<HeaderType> get(Optional<Field> field, Class<? extends Segment> type) {
        if (field.isPresent()) {
            final Up2Header config = field.get().getAnnotation(Up2Header.class);
            if (config != null) {
                return Optional.of(new HeaderType(config.code(), config.name()));
            }
        }
        return Optional.empty();
    }

}
