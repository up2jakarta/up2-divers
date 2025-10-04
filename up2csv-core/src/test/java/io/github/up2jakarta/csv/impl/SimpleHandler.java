package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.api.IErrorRepository;

public class SimpleHandler extends io.github.up2jakarta.csv.misc.SimpleHandler<InputRowEntity, BusinessType, SimpleErrorEntity> {

    public SimpleHandler(InputRowEntity row, SimpleCreator creator, IErrorRepository<InputRowEntity> repository) {
        super(row, creator, repository);
    }

    public SimpleHandler(InputRowEntity row, SimpleCreator creator) {
        this(row, creator, r -> 0);
    }

}
