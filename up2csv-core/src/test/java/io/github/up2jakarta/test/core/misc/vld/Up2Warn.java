package io.github.up2jakarta.test.core.misc.vld;

import io.github.up2jakarta.lov.SeverityType;

/**
 * Overriding error payload for JSR-303 validation
 */
@io.github.up2jakarta.csv.cfg.Error(value = Up2Warn.TU_P_011, level = SeverityType.WARNING)
public interface Up2Warn extends io.github.up2jakarta.csv.cfg.Error.Payload {

    String TU_P_011 = "TU-P011";

}
