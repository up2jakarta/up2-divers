package io.github.up2jakarta.csv.cfg;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J Annotation that supports {@link io.github.up2jakarta.csv.api.IEvent#getOffset()}, helpful to truncate meta-data.
 * <p>
 * In mono-segment CSV format, this annotation is always considered by segment processors
 * {@link io.github.up2jakarta.csv.core.Up2Mapper} and {@link io.github.up2jakarta.csv.core.Up2Flatter}.
 * <p>
 * In multi-segment CSV format, this annotation is considered only on the root business-object by business processors
 * {@link io.github.up2jakarta.csv.core.BusinessImporter} and {@link io.github.up2jakarta.csv.core.BusinessExporter},
 * but on the underlying segments, the information is taken from related {@link io.github.up2jakarta.csv.core.ModeType}.
 * <ul>
 *     <li> <code>1</code> for {@link io.github.up2jakarta.csv.core.ModeType#NEAT}</li>
 *     <li> <code>1</code> for {@link io.github.up2jakarta.csv.core.ModeType#MESS}</li>
 *     <li> {@link io.github.up2jakarta.csv.core.IMode#getOffset()} for any mode</li>
 * </ul>
 *
 * @see io.github.up2jakarta.csv.api.IRecord
 */
@Inherited
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface Truncated {

    /**
     * The first column offset in the {@link io.github.up2jakarta.csv.api.IRecord#getData()}
     * that is being mapped automatically.
     *
     * @return the first column offset
     */
    int value();

}
