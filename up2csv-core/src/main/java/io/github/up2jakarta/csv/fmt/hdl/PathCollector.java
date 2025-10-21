package io.github.up2jakarta.csv.fmt.hdl;

import io.github.up2jakarta.csv.data.DataType;

public class PathCollector<B extends DataType<B>, R extends PathRecord<?>> extends FullCollector<R, B, PathError<B, R>> {

    public PathCollector(R row) {
        super(row, PathError::new, (r) -> 0);
    }

}
