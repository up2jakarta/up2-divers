package io.github.up2jakarta.lov.core;

import io.github.up2jakarta.lov.IMessage;

import java.lang.reflect.AnnotatedElement;

/**
 * Internal Contract Interface for localizable exceptions.
 */
public sealed interface Localizable extends IMessage permits BeanException, AccessException {

    String CLASS = "class";
    String PACKAGE = "info";
    String CONSTRUCTOR = "new";
    String FORMAT = "%s[%s] %s";

    /**
     * @return the source of problem
     */
    AnnotatedElement getSource();

    /**
     * @return the source name
     */
    CharSequence getName();

    /**
     * @return the locator of problem in the source, il maye be property name or whatever.
     */
    String getLocator();

}
