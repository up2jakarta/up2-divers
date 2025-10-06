package io.github.up2jakarta.csv.ops.impl;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.core.ops.FastAggregator;
import io.github.up2jakarta.csv.ops.impl.dto.Invoice1;

public class Invoice1Aggregator extends FastAggregator<Invoice1, GroupType, SegmentType, InputRowEntity, InputErrorEntity> {

    private final SimpleCreator creator;

    public Invoice1Aggregator(MapperFactory<GroupType> factory, SimpleCreator creator) throws BeanException {
        super(factory, Invoice1.class, SegmentType.S01, SegmentType.values());
        this.creator = creator;
    }

    @Override
    protected SimpleHandler create(InputRowEntity row) {
        return new SimpleHandler(row, creator);
    }

}

