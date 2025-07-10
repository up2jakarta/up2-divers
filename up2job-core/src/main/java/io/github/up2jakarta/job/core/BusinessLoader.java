package io.github.up2jakarta.job.core;

import io.github.up2jakarta.job.ctx.ContextAware;
import jakarta.persistence.NoResultException;

@FunctionalInterface
@SuppressWarnings("unused")
public interface BusinessLoader<C extends ContextAware> {

    long get(C context, String reference) throws NoResultException;

}
