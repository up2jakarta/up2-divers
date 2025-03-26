package io.github.up2jakarta.xml.codelist;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
@SuppressWarnings("unused")
public @interface Source {

    Class<?> value();

}
