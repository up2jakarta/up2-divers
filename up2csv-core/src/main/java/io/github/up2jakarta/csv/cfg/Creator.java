package io.github.up2jakarta.csv.cfg;

import java.lang.annotation.*;

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
 *
 * <blockquote><pre>
 *         public class ThirdParty implements Segment {
 *
 *              &#064;BusinessId
 *              &#064;Position(0)
 *              private String key;
 *
 *              &#064;Position(1)
 *              private final Wrapper&lt;&#064;NotBlank String&gt; code;
 *
 *              &#064;Position(2)
 *              &#064;NotBlank
 *              private final Wrapper&lt;String&gt; label;
 *
 *              &#064;Fragment(3)
 *              private final Wrapper&lt;&#064;Valid TradeContact&gt; contact;
 *
 *              // ... getters and setters
 *         }
 *
 *         public class TradeContact implements Segment {
 *
 *              &#064;Position(0)
 *              &#064;NotBlank
 *              private String type;
 *
 *              &#064;Position(1)
 *              &#064;NotBlank
 *              private String value;
 *
 *              // ... getters and setters
 *         }
 * </pre></blockquote>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.CONSTRUCTOR)
public @interface Creator {

}
