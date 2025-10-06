package io.github.up2jakarta.job.ctx;

import io.github.up2jakarta.xml.clv.CodeList;

public interface ContextParameter<T extends Enum<T> & CodeList<T>> extends CodeList<T> {

    int getFromStepId();

    int getToStepId();

    boolean isOutput();

    boolean isSave();

}
