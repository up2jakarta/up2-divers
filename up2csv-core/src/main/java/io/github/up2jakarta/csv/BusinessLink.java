package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.api.ILinker;
import io.github.up2jakarta.csv.api.Linker;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static io.github.up2jakarta.csv.api.ILinker.N;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J Business Annotation that indicates parent-child relationships within segment definition.
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Repeatable(BusinessLink.List.class)
public @interface BusinessLink {

    /**
     * @return the code of input type.
     * @see io.github.up2jakarta.csv.api.IType#getCode()
     */
    String value();

    /**
     * This property can be redefined in {@link BusinessObject#overrides()}
     *
     * @return the minimum of cardinality.
     */
    int min() default 0;

    /**
     * This property can be redefined in {@link BusinessObject#overrides()}
     *
     * @return the maximum of cardinality.
     */
    int max() default N;

    /**
     * If set to {@code true}, the engine should automatically append new columns
     * at the beginning of the business data structure of the related underlying segment.
     *
     * <p>This flag can be redefined in {@link BusinessObject#overrides()}</p>
     *
     * @return the flag that allows the engine to append the @{@link ReferenceId} automatically to the child segment.
     * @see BusinessId
     */
    boolean automatic() default false;

    /**
     * This flag can be redefined in {@link BusinessObject#overrides()}
     *
     * @return the implementation class of child-segment, by default is computed from {@link Linker#value()}.
     */
    Class<? extends Segment> target() default Segment.class;

    /**
     * Returns the linker bean definition, by default is undefined since the engine can do
     * automatic linkage only for one-to-one relationships.
     *
     * <p>This definition can be redefined in {@link BusinessObject#overrides()} </p>
     *
     * <ul>
     *     <b>Up2J engine cannot link automatically {@link java.util.Collection} since the following limitations:</b>
     *     <li>There is no standard way to detect if a collection is mutable or not</li>
     *     <li>{@link java.util.Map} and {@link java.util.Set} don't allow duplicated elements</li>
     * </ul>
     *
     * <p>
     *     <b>Here is a business case of all supported one-to-one relationships</b>
     * </p>
     * {@snippet lang = "java":
     *        import java.util.Optional;
     *        import io.github.up2jakarta.csv.cfg.Position;
     *        import io.github.up2jakarta.lov.core.Wrapper;
     *
     *        @BusinessObject("01")
     *        public class Invoice implements Segment {
     *
     *             @Position(0)
     *             @BusinessId
     *             private String reference;
     *
     *             // ... other properties
     *
     *             @BusinessLink(value = "02", min = 1, max = 1)
     *             private ThirdParty buyer;
     *
     *             @BusinessLink(value = "03", max = 1)
     *             private final Optional<ThirdParty> payer = Optional.empty();
     *
     *             @BusinessLink(value = "04", max = 1)
     *             private final Wrapper<ThirdParty> payee = new Wrapper<>();
     *
     *             // ... other business links
     *        }
     *
     *        public class ThirdParty implements Segment {
     *
     *             @BusinessId
     *             @Position(0)
     *             private String reference;
     *
     *             @Position(1)
     *             private String name;
     *
     *             // ... other properties
     *        }
     *}
     *
     * @return the bean definition of the {@link ILinker}
     */
    Linker bean() default @Linker(Void.class);

    /**
     * Up2J Business Annotation that supports {@link Repeatable} {@link BusinessLink}.
     */
    @Documented
    @Target(FIELD)
    @Retention(RUNTIME)
    @interface List {

        BusinessLink[] value();

    }

    /**
     * Internal Undefined Linker
     */
    abstract class Void implements ILinker<Segment, Segment> {
        private Void() {
            throw new UnsupportedOperationException();
        }
    }

}
