package io.github.up2jakarta.job.zip;

@SuppressWarnings("unused")
public interface EntrySkipper {

    void skip(String file);

    boolean isSkipped(String file);

    boolean isClean();

}
