package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.core.BeanContext;
import io.github.up2jakarta.csv.slv.CodeListChecker;
import io.github.up2jakarta.lov.CodeListResolver;
import io.github.up2jakarta.lov.ConstantProvider;
import io.github.up2jakarta.lov.Support.Argument;
import io.github.up2jakarta.lov.core.TypeContext;

import java.lang.annotation.*;

/**
 * Up2J {@link io.github.up2jakarta.lov.TypeAdapter} resolver that supports {@link io.github.up2jakarta.lov.CodeList} types.
 *
 * @see CodeListResolver
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Resolver(CodeListChecker.class)
public @interface Up2CodeList {

    /**
     * Returns the code-list name, by default Up2J will compute the name from the code-list type.
     *
     * @return the code-list name
     * @see io.github.up2jakarta.lov.core.Beans#getTypeName(Class)
     */
    String value() default "";

    /**
     * The resolver must be managed by {@link BeanContext}.
     *
     * @return the code-list resolver.
     */
    Class<? extends CodeListResolver<?>> type() default ConstantProvider.class;

    /**
     * Returns the qualified name of the resolver, by default is <code>null</code>.
     * <p>
     * Useful when the {@link BeanContext} contains many beans of the specified resolver.
     *
     * @return the qualified name
     * @see jakarta.inject.Named
     */
    String name() default "";

    /**
     * Returns the arguments for the code-list resolver values lookup {@link CodeListResolver#resolve(Class, TypeContext)}.
     * <ul>
     *     The list of parameters are documented with {@link io.github.up2jakarta.lov.Support} on the resolver itself:
     *     <li>
     *         {@link io.github.up2jakarta.lov.ConstantProvider} does not supports any parameters.
     *     </li>
     *     <li>
     *         {@link io.github.up2jakarta.lov.DynamicProvider} supports only one required parameter: <code>file</code>
     *     </li>
     *     <li>
     *         {@link io.github.up2jakarta.lov.EntityResolver} supports only one optional parameter: <code>jpaCode</code>
     *     </li>
     *     <li>
     *         {@link io.github.up2jakarta.lov.TableResolver} supports 3 required parameters:
     *         <ul>
     *             <li><code>sqlTable</code></li>
     *             <li><code>sqlName</code></li>
     *             <li><code>sqlCode</code></li>
     *         </ul>
     *     </li>
     * </ul>
     *
     * @return the resolver arguments
     */
    Argument[] args() default {};

}
