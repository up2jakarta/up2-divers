package io.github.up2jakarta.csv.annotation;

import io.github.up2jakarta.csv.processor.TokenTransformer;

import java.lang.annotation.*;

/**
 * Up2 Shortcut Annotation for {@link Processor} that clean up XML <code>xs:token</code>.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Processor(TokenTransformer.class)
public @interface Token {
}
