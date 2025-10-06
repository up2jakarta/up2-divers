package io.github.up2jakarta.job.core;

import io.github.up2jakarta.xml.clv.CodeList;

@SuppressWarnings("unused")
public interface BusinessError<T extends BusinessError<T>> extends CodeList<T> {

    String getMessage();

}
