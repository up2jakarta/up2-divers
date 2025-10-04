package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.test.ext.Dummy1;
import io.github.up2jakarta.xml.api.SeverityType;

@SuppressWarnings("unused")
public class Test2Exception implements Segment {

    @Position(0)
    @Error(value = "W001", severity = SeverityType.WARNING)
    @Dummy1
    private String id1;

    @Position(1)
    @Error(value = "E002", severity = SeverityType.ERROR)
    @Dummy1
    private String id2;

    @Position(2)
    @Dummy1
    @Error(value = "F003", severity = SeverityType.FATAL)
    private String dummy;

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public String getDummy() {
        return dummy;
    }

    public void setDummy(String dummy) {
        this.dummy = dummy;
    }

}
