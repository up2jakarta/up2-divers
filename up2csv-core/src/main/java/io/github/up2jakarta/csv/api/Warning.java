package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Error.Payload;
import io.github.up2jakarta.lov.SeverityType;

import static io.github.up2jakarta.csv.api.IEvent.EC_COMPLIANCE;

/**
 * JSR-303 {@link jakarta.validation.Payload} marker interface for {@link SeverityType#WARNING}
 */
@Error(value = EC_COMPLIANCE, level = SeverityType.WARNING)
public interface Warning extends Payload {
}
