package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.api.IErrorRepository;
import io.github.up2jakarta.csv.core.DefaultHandler;
import io.github.up2jakarta.csv.impl.InputErrorEntity.PKey;

public class ErrorHandler extends DefaultHandler<InputRowEntity, PKey, BusinessType, InputErrorEntity> {

    public ErrorHandler(InputRowEntity row, ErrorCreator creator, IErrorRepository<InputRowEntity> repository) {
        super(row, creator, repository);
    }

    public ErrorHandler(InputRowEntity row, ErrorCreator creator) {
        this(row, creator, r -> 0);
    }

}
