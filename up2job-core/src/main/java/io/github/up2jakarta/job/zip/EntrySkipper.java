package io.github.up2jakarta.job.zip;

public interface EntrySkipper {

    void skip(String file);

    boolean isSkipped(String file);

    boolean isClean();

}
