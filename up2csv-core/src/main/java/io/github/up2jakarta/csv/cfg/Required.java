package io.github.up2jakarta.csv.cfg;

import java.lang.annotation.*;

/**
 * Up2 Annotation that indicates the required properties in order to consider an embedded fragment as <code>null</code> like.
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
 * @see Fragment
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Required {
}
