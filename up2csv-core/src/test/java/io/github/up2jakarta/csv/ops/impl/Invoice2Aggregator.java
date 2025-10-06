package io.github.up2jakarta.csv.ops.impl;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.core.ops.FastAggregator;
import io.github.up2jakarta.csv.ops.impl.dto.Invoice2;

public class Invoice2Aggregator extends FastAggregator<Invoice2, GroupType, SegmentType, InputRowEntity, InputErrorEntity> {

    private final SimpleCreator creator;

    public Invoice2Aggregator(MapperFactory<GroupType> factory, SimpleCreator creator) throws BeanException {
        super(factory, Invoice2.class, SegmentType.S11, SegmentType.values());
        this.creator = creator;
    }

    @Override
    protected SimpleHandler create(InputRowEntity row) {
        return new SimpleHandler(row, creator);
    }

}

