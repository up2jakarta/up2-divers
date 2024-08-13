package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Processor;
import io.github.up2jakarta.csv.annotation.Token;
import io.github.up2jakarta.csv.annotation.Trim;
import io.github.up2jakarta.csv.core.Segment;
import io.github.up2jakarta.csv.processor.TokenTransformer;
import io.github.up2jakarta.csv.processor.TrimTransformer;

@SuppressWarnings("unused")
public class ProcessorBean implements Segment {

    @Position(0)
    @Trim({"", "-"})
    @Token
    private String currency;

    @Position(1)
    @Trim({"", "-"})
    @Processor(TokenTransformer.class)
    private String code;

    @Position(2)
    @Processor(TrimTransformer.class)
    @Processor(TokenTransformer.class)
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
