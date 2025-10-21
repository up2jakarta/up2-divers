package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.xml.api.SeverityType;

import static io.github.up2jakarta.csv.core.EventHandler.ERROR_VALIDATOR;

/**
 * JSR-303 {@link jakarta.validation.Payload} base marker interface for {@link SeverityType#WARNING}
 */
@io.github.up2jakarta.csv.cfg.Error(value = ERROR_VALIDATOR, severity = SeverityType.WARNING)
public interface Warning extends Error.Payload {
}
