package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.core.BusinessId;
import io.github.up2jakarta.job.flux.FluxSupplier;
import io.github.up2jakarta.xml.adapters.KeyCoder;

import java.io.InputStream;
import java.util.Optional;
import java.util.zip.ZipEntry;

@SuppressWarnings("unused")
public class EntryFastDecoder implements EntryDecoder {

    private static volatile EntryFastDecoder INSTANCE;

    private EntryFastDecoder() {
    }

    public static EntryFastDecoder getInstance() {
        if (INSTANCE == null) {
            synchronized (EntryFastDecoder.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EntryFastDecoder();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Optional<BusinessId> decode(ZipEntry entry, FluxSupplier<InputStream> input) {
        try {
            final String reference = EntryDecoder.decodeEntry(entry);
            final long id = KeyCoder.decode(entry.getComment());
            return Optional.of(new BusinessId(id, reference));
        } catch (Exception ignore) {
            return Optional.empty();
        }
    }

}
