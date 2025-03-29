package io.github.up2jakarta.cii.core;

import io.github.up2jakarta.cii.CII;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Named
@Singleton
public class LocalDateAdapter extends io.github.up2jakarta.xml.adapters.LocalDateAdapter {

    protected LocalDateAdapter() {
        super(CII.FORMATTER_LOCAL_DATE, "XML-DT01");
    }

}
