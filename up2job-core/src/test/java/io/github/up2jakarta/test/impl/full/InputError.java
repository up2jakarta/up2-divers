package io.github.up2jakarta.test.impl.full;

import io.github.up2jakarta.csv.fmt.FullError;
import io.github.up2jakarta.lov.IError;
import io.github.up2jakarta.test.impl.TermType;

public class InputError extends FullError<TermType, InputRecord> {

    public InputError(InputRecord row, int order, TermType type, Integer offset, IError cause, String trace) {
        super(row, order, type, offset, cause, trace);
    }

}
