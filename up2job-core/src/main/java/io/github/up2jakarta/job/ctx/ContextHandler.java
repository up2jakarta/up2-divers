package io.github.up2jakarta.job.ctx;

import org.slf4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;

import java.lang.reflect.InvocationHandler;
import java.util.*;

import static io.github.up2jakarta.job.ctx.ContextProvider.*;
import static java.util.Optional.ofNullable;

class ContextHandler<B extends ContextAware, T extends SupportAware> extends FaultHandler<T> implements InvocationHandler {

    private final int flowId;
    private final Class<B> contract;
    private final Map<ContextParameter<?>, Object> memory = new HashMap<>();

    private ExecutionContext stepContext;

    ContextHandler(Class<B> baseInterface, T baseInstance, ContextProvider<?, ?> provider, int flowId) {
        super(baseInstance, provider);
        this.flowId = flowId;
        this.contract = baseInterface;
    }

    @Override
    protected Class<? extends ContextAware> getContract() {
        return contract;
    }

    @Override
    protected void setValue(ContextParameter<?> parameter, Object value) {
        final String key = stepKey(parameter);
        if (parameter.isSave()) {
            if (value == null) {
                stepContext.remove(key);
            } else {
                stepContext.put(key, value);
            }
        } else {
            memory.put(parameter, value);
        }
    }

    @Override
    protected Object getValue(ContextParameter<?> parameter, Class<?> type, boolean notNull) {
        Object value;
        if (parameter.isSave()) {
            value = stepContext.get(stepKey(parameter));
        } else {
            value = memory.get(parameter);
        }
        if (value == null && notNull) {
            if (type == Map.class) {
                value = new LinkedHashMap<>();
            } else if (type == List.class) {
                value = new LinkedList<>();
            } else if (type == Set.class) {
                value = new LinkedHashSet<>();
            }
            if (value != null) {
                this.setValue(parameter, value);
            }
        }
        return value;
    }

    @Override
    Map<String, Object> initialize(StepExecution execution, Logger logger, int order) {
        context.setOrder(order);
        memory.entrySet().removeIf(e -> canRemove(e.getKey(), order));
        final Map<String, Object> data = ofNullable(stepContext).map(ExecutionContext::toMap)
                .map(LinkedHashMap::new)
                .orElse(new LinkedHashMap<>());
        data.put(stepKey(provider.getStepOrderParameter()), order);
        data.put(stepKey(provider.getFlowOrderParameter()), flowId);
        data.put(stepKey(provider.getInputParameter()), context.getInput());
        if (logger.isDebugEnabled()) {
            data.forEach((k, v) -> debug(logger, "Context", context.toString(), k, v, false));
            memory.forEach((k, v) -> debug(logger, "#Memory", context.toString(), stepKey(k), v, false));
        }
        this.stepContext = execution.getExecutionContext();
        return data;
    }

    @Override
    void finalize(StepExecution execution, Logger logger, int order) {
        this.stepContext = execution.getExecutionContext();
        if (logger.isDebugEnabled()) {
            for (final ContextParameter<?> fp : provider.getOutputs(order)) {
                final String key = stepKey(fp);
                debug(logger, "Context", context.toString(), key, stepContext.get(key), true);
            }
            memory.entrySet().stream().filter(e -> canLog(e.getKey(), order)).forEach(e -> {
                final String key = stepKey(e.getKey());
                debug(logger, "#Memory", context.toString(), key, e.getValue(), true);
            });
        }
    }

    @Override
    void initialize(JobExecution execution, Logger logger) {
        final ExecutionContext jobContext = execution.getExecutionContext();
        final String inputKey = flowKey(provider.getInputParameter(), flowId);
        syncParameter(logger, context, jobContext, inputKey, context.getInput());
    }

}
