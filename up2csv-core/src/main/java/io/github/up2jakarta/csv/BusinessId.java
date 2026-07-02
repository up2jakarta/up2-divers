package io.github.up2jakarta.csv;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J Business Annotation that indicates the identifier property in order match parent-child relationship.
 * <p>
 * Note that it's supported on {@link io.github.up2jakarta.csv.cfg.Position} properties only and must be unique.
 * <p>
 * Here is a business case of how annotations can be used:
 * {@snippet lang = "java":
 *       import java.util.List;
 *       import java.util.LinkedList;
 *       import io.github.up2jakarta.csv.api.Linker;
 *       import io.github.up2jakarta.csv.cfg.Position;
 *
 *       @BusinessObject("01")
 *       public class Invoice implements Segment {
 *
 *            @Position(0)
 *            @BusinessId
 *            private String reference;
 *
 *            // ... other properties
 *
 *            @BusinessLink(value = "10", bean = @Linker(InvoiceItemLinker.class))
 *            private final List<InvoiceItem> items = new LinkedList<>();
 *
 *            // ... other business links
 *
 *            // ... getters and setters
 *       }
 *
 *       public class InvoiceItem implements Segment {
 *
 *            @BusinessId
 *            @Position(0)
 *            private String itemId;
 *
 *            // ... other properties
 *
 *            @BusinessLink(value = "20", automatic = true, bean = @Linker(ItemAttributeLinker.class))
 *            private final List<ItemAttribute> attributes = new LinkedList<>();
 *
 *            // ... getters and setters
 *       }
 *
 *       public class ItemAttribute implements Segment {
 *
 *            @Position(0)
 *            private String key;
 *
 *            @Position(1)
 *            private String value;
 *
 *            // ... getters and setters
 *       }
 *}
 *
 * @see BusinessLink#automatic()
 * @see ReferenceId
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
public @interface BusinessId {
}
