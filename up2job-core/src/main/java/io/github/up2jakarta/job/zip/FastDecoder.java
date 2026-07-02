package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.flux.FluxSupplier;

import java.io.InputStream;
import java.util.zip.ZipEntry;

@SuppressWarnings("unused")
public class FastDecoder implements EntryDecoder {

    private final String extension;

    public FastDecoder(String extension) {
        this.extension = extension;
    }

    @Override
    public String encode(String reference) {
        return reference + extension;
    }

    @Override
    public String decode(ZipEntry entry, FluxSupplier<InputStream> input) {
        final String fileName = entry.getName();
        final int index = fileName.lastIndexOf(extension);
        return fileName.substring(0, index);
    }

}
