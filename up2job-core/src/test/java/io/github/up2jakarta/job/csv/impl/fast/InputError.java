package io.github.up2jakarta.job.csv.impl.fast;

import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.fmt.ECause;
import io.github.up2jakarta.job.csv.impl.GroupType;
import io.github.up2jakarta.xml.api.PropertyException;

public class InputError extends ECause<GroupType, InputRecord> implements Segment {

    public InputError(InputRecord row, int offset, GroupType type, PropertyException cause) {
        super(row, offset, type, cause);
    }

}
