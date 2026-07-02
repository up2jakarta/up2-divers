package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.prc.DefaultProcessor;
import io.github.up2jakarta.lov.core.StringAdapter;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J Annotation that supports the index of data in {@link io.github.up2jakarta.csv.api.IRecord#getData()}.
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Processor(DefaultProcessor.class)
public @interface Position {

    /**
     * The column offset in the input record that is being mapped automatically.
     *
     * @return the column offset
     */
    int value();

    /**
     * This configuration always takes precedence over the annotation on the property level because
     * {@link PositionOverride}, by default is <code>undefined</code>.
     *
     * @return the configuration of property converter
     */
    Up2Converter converter() default @Up2Converter(StringAdapter.class);

    /**
     * @return the default value, by default is <code>null</code>
     * @see DefaultProcessor
     */
    String defaultValue() default "";

    /**
     * Flag indicates that this property is mandatory for declaring fragment.
     * <p>
     * If set to <code>true</code> and the property value is <code>null</code> and the declaring
     * {@link Fragment#nullable()} is set to <code>true</code> then the fragment value will be <code>null</code>
     * <p>
     * Here is a business case of how annotations can be used:
     * {@snippet lang = "java":
     *       import io.github.up2jakarta.csv.Segment;
     *       import io.github.up2jakarta.csv.BusinessId;
     *       import io.github.up2jakarta.csv.cfg.Position;
     *       import io.github.up2jakarta.csv.cfg.Fragment;
     *
     *       public class ThirdParty implements Segment {
     *
     *            @BusinessId
     *            @Position(0)
     *            private String key;
     *
     *            // ... other properties
     *
     *            @Fragment(value = 1, nullable = true)
     *            private final Contact contact;
     *
     *            // ... getters and setters
     *       }
     *
     *       public class Contact implements Segment {
     *
     *              @Position(0)
     *              private String type;
     *
     *              @Position(value = 1, required = true) // if null, contact should be null
     *              private String value;
     *
     *              // ... getters and setters
     *       }
     *}
     *
     * @return the required flag
     */
    boolean required() default false;

}
