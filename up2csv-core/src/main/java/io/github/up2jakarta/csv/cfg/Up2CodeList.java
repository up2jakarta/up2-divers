package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.api.ext.Argument;
import io.github.up2jakarta.csv.slv.CodeListChecker;
import io.github.up2jakarta.lov.CodeListResolver;
import io.github.up2jakarta.lov.ConstantProvider;
import io.github.up2jakarta.lov.core.TypeContext;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J {@link io.github.up2jakarta.lov.TypeAdapter} resolver that supports {@link io.github.up2jakarta.lov.CodeList} types.
 *
 * @see CodeListResolver
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
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
     * The resolver must be managed by {@link io.github.up2jakarta.csv.api.Container}.
     *
     * @return the code-list resolver.
     */
    Class<? extends CodeListResolver<?>> type() default ConstantProvider.class;

    /**
     * Returns the qualified name of the resolver, by default is <code>null</code>.
     * <p>
     * Useful when the {@link io.github.up2jakarta.csv.api.Container} contains many beans of the specified resolver.
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
