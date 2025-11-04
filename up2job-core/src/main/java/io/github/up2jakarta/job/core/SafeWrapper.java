package io.github.up2jakarta.job.core;

import java.util.List;

public class SafeWrapper<T extends RuntimeException> {

    protected T value;

    public final void propagate() {
        if (value != null) {
            throw value;
        }
    }

    public final void closeAndPropagate(List<?> resources) {
        if (value != null) {
            SafeUtil.close(resources);
            throw value;
        }
    }

    public final void accept(T value) {
        if (this.value == null) {
            this.value = value;
        }
    }

}
