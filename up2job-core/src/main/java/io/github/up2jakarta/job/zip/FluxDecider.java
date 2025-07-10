package io.github.up2jakarta.job.zip;

@SuppressWarnings("unused")
@FunctionalInterface
public interface FluxDecider<R extends ArchiveResource> {

    boolean accept(R flux);

}
