package io.github.up2jakarta.csv.test.clv;

import io.github.up2jakarta.xml.clv.CodeListConverter;
import org.springframework.stereotype.Component;

@Component
public class TestCodeListConverter extends CodeListConverter<TestCodeList> {

    public static final String TU_001 = "TU-001";

    TestCodeListConverter() {
        super(TestCodeList.class, TU_001);
    }

}
