package io.github.up2jakarta.csv.core.misc.lov;

import io.github.up2jakarta.lov.CodeListAdapter;
import org.springframework.stereotype.Component;

@Component
public class TestCodeListConverter extends CodeListAdapter<TestCodeList> {

    public static final String TU_001 = "TU-001";

    TestCodeListConverter() {
        super(TestCodeList.class, TU_001);
    }

}
