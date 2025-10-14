package io.github.up2jakarta.csv.io.impl;

import io.github.up2jakarta.csv.io.SimpleWriter;
import io.github.up2jakarta.csv.io.dto.Invoice;
import org.apache.commons.csv.CSVFormat;

public class InvoiceWriter extends SimpleWriter<Invoice> {

    public InvoiceWriter(InvoiceAggregator aggregator, CSVFormat format) {
        super(aggregator, format);
    }

}

