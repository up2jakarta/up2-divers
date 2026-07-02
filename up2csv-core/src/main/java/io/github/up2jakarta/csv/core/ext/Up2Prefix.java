package io.github.up2jakarta.csv.core.ext;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J Annotation resolver that supports database prefix checking
 *
 * <ul>
 *     If the package is annotated with, the checker verify the table name prefix:
 *     <li>{@link jakarta.persistence.Table#name()}</li>
 *     <li>{@link jakarta.persistence.SecondaryTable#name()}</li>
 *     <li>{@link jakarta.persistence.JoinTable#name()}</li>
 *     <li>{@link jakarta.persistence.OneToOne}</li>
 *     <li>{@link jakarta.persistence.ManyToOne}</li>
 *     <li>{@link jakarta.persistence.OneToMany}</li>
 *     <li>{@link jakarta.persistence.ManyToMany}</li>
 * </ul>
 * <ul>
 *     If the entity class is annotated with, the checker verify the columns name prefix:
 *   <li>{@link jakarta.persistence.Column#name()}</li>
 *   <li>{@link jakarta.persistence.JoinColumn#name()}</li>
 *   <li>{@link jakarta.persistence.DiscriminatorColumn#name()}</li>
 *   <li>{@link jakarta.persistence.PrimaryKeyJoinColumn#name()}</li>
 * </ul>
 */
@Inherited
@Documented
@Retention(RUNTIME)
@Target({TYPE, PACKAGE})
public @interface Up2Prefix {

    /**
     * @return the prefix value
     */
    String value();

}

