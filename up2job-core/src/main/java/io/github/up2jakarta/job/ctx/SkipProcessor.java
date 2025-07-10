package io.github.up2jakarta.job.ctx;

import io.github.up2jakarta.job.core.BusinessException;
import io.github.up2jakarta.job.core.ReferenceAware;

@SuppressWarnings("unused")
public interface SkipProcessor<C extends ContextAware, I extends ReferenceAware<C>> {

    void onSkip(I item, BusinessException error, boolean failure);

}
