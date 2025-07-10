package io.github.up2jakarta.job.ctx;

import io.github.up2jakarta.job.core.BusinessError;
import io.github.up2jakarta.job.core.BusinessException;
import io.github.up2jakarta.job.core.Cleanable;
import io.github.up2jakarta.job.core.ReferenceAware;
import org.slf4j.Logger;
import org.springframework.batch.core.SkipListener;

import static io.github.up2jakarta.job.core.BusinessException.getCause;
import static io.github.up2jakarta.job.core.BusinessException.getError;

@SuppressWarnings("unused")
public abstract class StepSkipper<C extends ContextAware, W extends ReferenceAware<C>, P extends ReferenceAware<C>>
        implements SkipListener<P, W>, SkipProcessor<C, P> {

    private final C context;
    private final Logger logger;
    private final BusinessError<?> defaultWriteError;
    private final BusinessError<?> defaultProcessError;

    protected StepSkipper(C context, BusinessError<?> defaultWriteError, BusinessError<?> defaultProcessError, Logger logger) {
        this.context = context;
        this.logger = logger;
        this.defaultWriteError = defaultWriteError;
        this.defaultProcessError = defaultProcessError;
    }

    protected BusinessError<?> logWarning(BusinessError<?> defaultError, ReferenceAware<C> item, Throwable cause) {
        final BusinessError<?> error = getError(cause, defaultError);
        logger.warn("{} : {} {}", context, error, item.reference(context));
        return error;
    }

    @Override
    public final void onSkipInWrite(W item, Throwable cause) {
        final BusinessError<?> error = this.logWarning(defaultWriteError, item, cause);
        try {
            onSkipInWrite(context, item, error, getCause(cause));
        } catch (Exception ex) {
            logger.error("{} : failed to skip write error: {} #[{}]", context, error, item.reference(context));
        }
    }

    @Override
    public final void onSkipInProcess(P item, Throwable cause) {
        final BusinessError<?> error = this.logWarning(defaultProcessError, item, cause);
        try {
            onSkipInProcess(context, item, error, getCause(cause));
        } catch (Exception ex) {
            logger.error("{} : failed to skip process error: {} #[{}]", context, error, item.reference(context), ex);
        }
    }

    @Override
    public final void onSkip(P item, BusinessException error, boolean failure) {
        if (!(item instanceof Cleanable c && c.isCleaned())) {
            this.onSkipInProcess(item, error);
            if (failure && context instanceof FaultAware collector) {
                collector.addFailures(1);
            }
        }
    }

    protected abstract void onSkipInWrite(C context, W item, BusinessError<?> error, Throwable cause);

    protected abstract void onSkipInProcess(C context, P item, BusinessError<?> error, Throwable cause);

}
