package io.github.up2jakarta.test.core.misc.lov;

import io.github.up2jakarta.lov.CodeList;

@SuppressWarnings("ALL")
public class Test3CodeList implements CodeList, Comparable<Test3CodeList> {

    public String getCode() {
        return "TEST";
    }

    public String getName() {
        return "TEST";
    }

    @Override
    public int compareTo(Test3CodeList o) {
        return 0;
    }
}
