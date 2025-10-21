package io.github.up2jakarta.csv.fmt.hdl;

import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.SeverityType;

public class MiniCollector<B extends DataType<B>, R extends MiniRecord<?>> extends FastCollector<R, B, MiniError<B, R>> {

    public MiniCollector(R row, SeverityType level) {
        super(row, MiniError::new, level);
    }

}
