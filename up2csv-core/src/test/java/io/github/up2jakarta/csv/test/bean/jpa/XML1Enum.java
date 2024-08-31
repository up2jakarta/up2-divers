package io.github.up2jakarta.csv.test.bean.jpa;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.extension.SeverityType;

@Error(value = Test1Bean.JPA_XXX, severity = SeverityType.WARNING)
public enum XML1Enum {

    ONE

}
