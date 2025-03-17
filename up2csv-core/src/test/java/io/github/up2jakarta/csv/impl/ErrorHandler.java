package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.core.DefaultHandler;
import io.github.up2jakarta.csv.impl.InputErrorEntity.PKey;
import io.github.up2jakarta.csv.input.InputRepository;

public class ErrorHandler extends DefaultHandler<InputRowEntity, PKey, DataId, InputErrorEntity> {

    public ErrorHandler(InputRowEntity row, ErrorCreator creator, InputRepository<InputRowEntity> repository) {
        super(row, creator, repository);
    }

    public ErrorHandler(InputRowEntity row, ErrorCreator creator) {
        this(row, creator, r -> 0);
    }

}
