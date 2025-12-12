package io.github.up2jakarta.cii.core;

import io.github.up2jakarta.cii.CII;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Named
@Singleton
public class OffsetTimeAdapter extends io.github.up2jakarta.xml.adapters.OffsetTimeAdapter {

    @Inject
    public OffsetTimeAdapter() {
        super(CII.FORMATTER_OFFSET_TIME, "XML-DT04");
    }

}
