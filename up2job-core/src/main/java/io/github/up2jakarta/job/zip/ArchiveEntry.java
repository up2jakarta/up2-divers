package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.core.BusinessContext;
import io.github.up2jakarta.job.core.BusinessId;
import io.github.up2jakarta.job.core.BusinessType;
import io.github.up2jakarta.job.flux.FluxIndex;

import java.util.List;
import java.util.zip.ZipEntry;

public final class ArchiveEntry<B extends BusinessType<B>, C extends BusinessContext> implements Entry<B, C> {

    private final BusinessId bid;
    private final String name;
    private final ArchiveWalker<B, C> manager;

    private ArchiveEntry(BusinessId bid, String name, ArchiveWalker<B, C> manager) {
        this.name = name;
        this.manager = manager;
        this.bid = bid;
    }

    public ArchiveEntry(BusinessId bid, ZipEntry entry, ArchiveWalker<B, C> manager) {
        this(bid, entry.getName(), manager);
    }

    public ArchiveEntry(Entry<?, ?> entry, ArchiveWalker<B, C> manager) {
        this(entry.getId(), entry.getName(), manager);
    }

    @Override
    public BusinessId getId() {
        return bid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<FluxIndex<B>> getIndexes() {
        return manager.getIndexes();
    }

    @Override
    public void clean() {
        manager.skip(name);
    }

    @Override
    public boolean isCleaned() {
        return manager.isSkipped(name);
    }

}
