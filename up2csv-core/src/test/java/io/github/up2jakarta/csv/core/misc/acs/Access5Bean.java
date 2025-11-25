package io.github.up2jakarta.csv.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Boolean;

@SuppressWarnings("unused")
public class Access5Bean extends Access5Super<String> {

    @Position(1)
    @Up2Boolean
    protected Boolean flag;

    protected Boolean getFlag() {
        return flag;
    }

    protected void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
