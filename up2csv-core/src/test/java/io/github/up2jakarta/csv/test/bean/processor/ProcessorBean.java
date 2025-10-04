package io.github.up2jakarta.csv.test.bean.processor;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Token;
import io.github.up2jakarta.csv.cfg.Up2Trim;
import io.github.up2jakarta.csv.data.Segment;

@SuppressWarnings("unused")
public class ProcessorBean implements Segment {

    @Position(0)
    @Up2Trim({"", "-"})
    @Up2Token
    private String currency;

    @Position(1)
    @Up2Trim({"", "-"})
    @Up2Token
    private String code;

    @Position(2)
    @Up2Trim
    @Up2Token
    private String name;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
