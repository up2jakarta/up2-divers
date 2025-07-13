package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.core.BusinessArchive;
import io.github.up2jakarta.job.core.BusinessContext;
import io.github.up2jakarta.job.core.BusinessType;
import io.github.up2jakarta.job.core.Cleanable;

import java.util.Iterator;

public interface ArchiveWalker<B extends BusinessType<B>, C extends BusinessContext>
        extends Iterator<Entry<B, C>>, ArchiveResource, BusinessArchive<B>, EntrySkipper, Cleanable {

}
