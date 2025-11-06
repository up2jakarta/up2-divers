package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.api.ext.BeanContext;
import io.github.up2jakarta.csv.prc.DefaultProcessor;
import io.github.up2jakarta.xml.api.TypeConverter;

import java.lang.annotation.*;

/**
 * Up2 Annotation that supports the index of data in {@link io.github.up2jakarta.csv.api.IRecord#getColumns()}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Processor(DefaultProcessor.class)
public @interface Position {

    /**
     * The column offset in the input row that is being mapped automatically.
     *
     * @return the column offset
     */
    int value();

    /**
     * The converter must be managed by {@link BeanContext}
     *
     * @return the class of the processor
     */
    Class<? extends TypeConverter<?>> converter() default NaN.class;

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
     * <blockquote><pre>
     *         public class ThirdParty implements Segment {
     *
     *              &#064;BusinessId
     *              &#064;Position(0)
     *              private String key;
     *
     *              // ... other properties
     *
     *              &#064;Fragment(value = 1, nullable = true)
     *              private final Contact contact;
     *
     *              // ... getters and setters
     *         }
     *
     *         public class TradeContact implements Segment {
     *
     *              &#064;Position(0)
     *              private String type;
     *
     *              &#064;Position(1)
     *              &#064;Required // if null, contact should be null
     *              private String value;
     *
     *              // ... getters and setters
     *         }
     * </pre></blockquote>
     *
     * @return the nullable flag
     */
    boolean required() default false;

    /**
     * Undefined implementation used only as default value for Position.
     */
    final class NaN extends TypeConverter<Void> {

        private NaN() {
            super(void.class, null, null);
        }

        @Override
        public Void parse(String value) {
            return null;
        }

        @Override
        public String format(Void value) {
            return null;
        }
    }

}
