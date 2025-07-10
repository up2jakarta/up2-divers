package io.github.up2jakarta.job.ctx;

import org.slf4j.Logger;

import static io.github.up2jakarta.job.ctx.ContextProvider.debug;

@SuppressWarnings("unused")
public abstract class LogProvider {

    protected abstract Logger getLogger();

    protected void logContext(String reference, String key, Object value, boolean output) {
        debug(getLogger(), "Context", reference, key, value, output);
    }

}
