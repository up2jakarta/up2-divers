package io.github.up2jakarta.csv.data;

import java.lang.annotation.*;

/**
 * Up2 Aggregation Annotation that indicates the identifier property in order match parent-child relationship.
 * <p>
 * Note that it's supported on {@link io.github.up2jakarta.csv.cfg.Position} properties only and must be unique.
 *
 * <p>
 * Here is a business case of how annotations can be used:
 * <blockquote><pre>
 *         public class InvoiceItem implements Segment {
 *
 *              &#064;BusinessId
 *              &#064;Position(0)
 *              private String itemId;
 *
 *              // ... other properties
 *
 *              private final Map&#60;String, ItemAttribute&#62; attributes = new LinkedHashMap&#60;&#62;();
 *
 *              // ... getters and setters
 *         }
 *
 *         public class ItemAttribute implements Segment {
 *
 *              &#064;ParentId(InvoiceItem.class)
 *              &#064;Position(0)
 *              private String itemId;
 *
 *              &#064;Position(1)
 *              private String key;
 *
 *              &#064;Position(2)
 *              private String value;
 *
 *              // ... getters and setters
 *         }
 *
 * </pre></blockquote>
 *
 * @see BusinessObject
 * @see ParentId
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface BusinessId {
}
