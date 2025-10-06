package io.github.up2jakarta.csv.core.ext;

import java.lang.annotation.*;

/**
 * Up2 Annotation resolver that supports database prefix checking for :
 * <ul>
 *   <li>{@link jakarta.persistence.Table#name()}</li>
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

