package io.github.up2jakarta.csv.test.validation;

import static io.github.up2jakarta.csv.extension.SeverityType.WARNING;

/**
 * Overriding error payload for JSR-303 validation
 */
@io.github.up2jakarta.csv.annotation.Error(value = Up2Warn.TU_P_011, severity = WARNING)
public interface Up2Warn extends io.github.up2jakarta.csv.annotation.Error.Payload {

    String TU_P_011 = "TU-P011";

}
