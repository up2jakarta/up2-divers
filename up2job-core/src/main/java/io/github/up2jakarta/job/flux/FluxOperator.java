package io.github.up2jakarta.job.flux;

import io.github.up2jakarta.job.core.BusinessContext;
import io.github.up2jakarta.job.core.BusinessType;

public interface FluxOperator<B extends BusinessType<B>, C extends BusinessContext> extends FluxSaver<B, C> {

    boolean accept(FluxResource<?> flux);

    B getFirstType();

    B getOtherType();

}
