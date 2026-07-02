package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.flux.FluxSupplier;

import java.io.InputStream;
import java.util.zip.ZipEntry;

public interface EntryDecoder {

    String encode(String reference);

    String decode(ZipEntry entry, FluxSupplier<InputStream> input) throws Exception;

}
