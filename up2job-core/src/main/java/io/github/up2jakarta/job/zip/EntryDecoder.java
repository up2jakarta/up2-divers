package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.core.BusinessId;
import io.github.up2jakarta.job.core.BusinessObject;
import io.github.up2jakarta.job.core.KeyCoder;
import io.github.up2jakarta.job.flux.FluxSupplier;

import java.io.InputStream;
import java.util.Optional;
import java.util.zip.ZipEntry;

import static io.github.up2jakarta.job.core.ResourceAware.XML_EXTENSION;

public interface EntryDecoder {

    static String encodeName(String reference) {
        return reference + XML_EXTENSION;
    }

    static ZipEntry encodeEntry(BusinessObject<?, ?> invoice) {
        final ZipEntry entry = new ZipEntry(encodeName(invoice.getReference()));
        entry.setComment(KeyCoder.encode(invoice.getKey()));
        return entry;
    }

    static String decodeEntry(ZipEntry entry) {
        final String fileName = entry.getName();
        final int index = fileName.lastIndexOf(XML_EXTENSION);
        return fileName.substring(0, index);
    }

    Optional<BusinessId> decode(ZipEntry entry, FluxSupplier<InputStream> input);

}
