package io.github.up2jakarta.test.core.misc.lov;

import io.github.up2jakarta.lov.CodeListAdapter;
import org.springframework.stereotype.Component;

@Component
public class TestCodeListConverter extends CodeListAdapter<TestCodeList> {

    public static final String TU_001 = "TU-001";

    public TestCodeListConverter() {
        super(TestCodeList.class, TU_001);
    }

}
