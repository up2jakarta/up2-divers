package io.github.up2jakarta.csv.test.codelist;

import io.github.up2jakarta.csv.misc.CodeListConverter;
import org.springframework.stereotype.Component;

@Component
public class TestCodeListConverter extends CodeListConverter<TestCodeList> {

    public static final String TU_001 = "TU-001";

    TestCodeListConverter() {
        super(TestCodeList.class, TU_001);
    }

}
