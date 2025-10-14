package io.github.up2jakarta.csv.io.impl;

import io.github.up2jakarta.csv.io.SimpleReader;
import io.github.up2jakarta.csv.io.dto.Invoice;
import org.apache.commons.csv.CSVFormat;

public class InvoiceReader extends SimpleReader<Invoice, GroupType, SegmentType> {

    public InvoiceReader(InvoiceAggregator aggregator, CSVFormat format) {
        super(aggregator, format, "-");
    }

}

