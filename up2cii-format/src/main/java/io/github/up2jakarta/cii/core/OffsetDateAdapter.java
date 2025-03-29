package io.github.up2jakarta.cii.core;

import io.github.up2jakarta.cii.CII;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Named
@Singleton
public class OffsetDateAdapter extends io.github.up2jakarta.xml.adapters.OffsetDateAdapter {

    protected OffsetDateAdapter() {
        super(CII.FORMATTER_OFFSET_DATE, "XML-DT02");
    }

}
