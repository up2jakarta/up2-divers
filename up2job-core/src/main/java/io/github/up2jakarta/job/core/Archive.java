package io.github.up2jakarta.job.core;

/**
 * {@see https://ibm.github.io/ibm-cos-sdk-java/com/ibm/cloud/objectstorage/services/s3/model/ObjectMetadata.html}
 */
@SuppressWarnings("unused")
public interface Archive {

    Long getId();

    String getArchiveBucket();

    String getArchivePath();

    Long getContentLength();

    String getContentMD5();

    String getArchiveVersion();

}
