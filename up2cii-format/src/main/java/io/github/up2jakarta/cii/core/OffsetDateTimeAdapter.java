package io.github.up2jakarta.cii.core;

import io.github.up2jakarta.cii.CII;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Named
@Singleton
public class OffsetDateTimeAdapter extends io.github.up2jakarta.xml.adapters.OffsetDateTimeAdapter {

    @Inject
    public OffsetDateTimeAdapter() {
        super(CII.FORMATTER_OFFSET_DATE_TIME, "XML-DT03");
    }

}
