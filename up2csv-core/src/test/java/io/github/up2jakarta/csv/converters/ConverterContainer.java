package io.github.up2jakarta.csv.converters;

import jakarta.persistence.AttributeConverter;
import org.hibernate.resource.beans.container.spi.BeanContainer;
import org.hibernate.resource.beans.container.spi.ContainedBean;
import org.hibernate.resource.beans.spi.BeanInstanceProducer;

import java.util.HashMap;
import java.util.Map;

public class ConverterContainer implements BeanContainer {

    private final Map<Class<?>, ContainedBean<?>> cache;

    public ConverterContainer(AttributeConverter<?, ?>[] converters) {
        this.cache = new HashMap<>(converters.length);
        for (final AttributeConverter<?, ?> adapter : converters) {
            final ContainedBean<?> bean = new ContainedConverter<>(adapter);
            cache.put(adapter.getClass(), bean);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <B> ContainedBean<B> getBean(Class<B> type, LifecycleOptions options, BeanInstanceProducer fallback) {
        if (AttributeConverter.class.isAssignableFrom(type)) {
            return (ContainedBean<B>) cache.get(type);
        }
        return () -> fallback.produceBeanInstance(type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <B> ContainedBean<B> getBean(String name, Class<B> type, LifecycleOptions options, BeanInstanceProducer fallback) {
        if (AttributeConverter.class.isAssignableFrom(type)) {
            return (ContainedBean<B>) cache.get(type);
        }
        return () -> fallback.produceBeanInstance(name, type);
    }

    @Override
    public void stop() {
        this.cache.clear();
    }

    private record ContainedConverter<C extends AttributeConverter<?, ?>>(C converter) implements ContainedBean<C> {

        public C getBeanInstance() {
            return converter;
        }

    }

}

