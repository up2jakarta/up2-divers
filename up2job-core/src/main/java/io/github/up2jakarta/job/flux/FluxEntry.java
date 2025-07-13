package io.github.up2jakarta.job.flux;

import io.github.up2jakarta.job.core.LocalFile;

import java.io.Serializable;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class FluxEntry implements Serializable {

    private final FluxPart firstPart;
    private final FluxPart otherPart;

    public FluxEntry(FluxPart firstPart, FluxPart otherPart) {
        this.firstPart = firstPart;
        this.otherPart = otherPart;
    }

    public FluxPart getFirstPart() {
        return firstPart;
    }

    public FluxPart getOtherPart() {
        return otherPart;
    }

    public void update(LocalFile firstFile, LocalFile otherFile, Consumer<FluxEntry> updater) {
        final FluxPart firstCopy = this.firstPart.clone(firstFile);
        final FluxPart otherCopy = this.otherPart.clone(otherFile);
        if (this.firstPart != firstCopy || this.otherPart != otherCopy) {
            updater.accept(new FluxEntry(firstCopy, otherCopy));
        }
    }

    @Override
    public String toString() {
        return "<" + firstPart + ", " + otherPart + ">";
    }

}
