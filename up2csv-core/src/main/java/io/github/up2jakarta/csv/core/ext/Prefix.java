package io.github.up2jakarta.csv.core.ext;

import java.lang.annotation.*;

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
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.PACKAGE})
public @interface Prefix {

    /**
     * @return the prefix value
     */
    String value();

}

