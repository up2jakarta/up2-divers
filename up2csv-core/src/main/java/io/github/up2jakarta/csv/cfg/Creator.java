package io.github.up2jakarta.csv.cfg;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J Annotation that permits to select the right constructor for immutable segments like java-records,
 * it should be unique by segment definition.
 * <p>
 * It is optional, if there is one and only one constructor by segment definition.
 * <ul>
 *     If set, the following assertions must be <code>true</code>:
 *     <li>The constructor must contain all the managed properties as arguments</li>
 *     <li>Each property must be final</li>
 *     <li>The argument name must match the property name</li>
 *     <li>The argument type must match the property type</li>
 * </ul>
 * <p>
 * If some properties are open for modification, you can use segments composition technique
 * aka migrate all non-final properties in an embeddable fragments.
 * <p>
 * Another solution, the use of {@link io.github.up2jakarta.lov.core.Wrapper} to wrap each non-final property,
 * note that the wrapper supports the JSR-303 validation.
 * {@snippet lang = "java":
 *       import jakarta.validation.Valid;
 *       import io.github.up2jakarta.csv.Segment;
 *       import io.github.up2jakarta.csv.BusinessId;
 *       import io.github.up2jakarta.csv.cfg.Position;
 *       import io.github.up2jakarta.csv.cfg.Fragment;
 *       import io.github.up2jakarta.lov.core.Wrapper;
 *       import jakarta.validation.constraints.NotBlank;
 *
 *       public class ThirdParty implements Segment {
 *
 *            @BusinessId
 *            @Position(0)
 *            private String key;
 *
 *            @Position(1)
 *            private final Wrapper<@NotBlank String> code;
 *
 *            @Position(2)
 *            @NotBlank
 *            private final Wrapper<String> label;
 *
 *            @Fragment(3)
 *            private final Wrapper<@Valid TradeContact> contact;
 *
 *            // ... getters and setters
 *       }
 *
 *       public class TradeContact implements Segment {
 *
 *            @Position(0)
 *            @NotBlank
 *            private String type;
 *
 *            @Position(1)
 *            @NotBlank
 *            private String value;
 *
 *            // ... getters and setters
 *       }
 *}
 */
@Documented
@Retention(RUNTIME)
@Target(CONSTRUCTOR)
public @interface Creator {

}
