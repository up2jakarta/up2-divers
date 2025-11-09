# Up2JOB :: Core Framework

[![Maven Central](https://img.shields.io/maven-central/v/io.github.up2jakarta/up2job-core?style=for-the-badge&color=green)](https://central.sonatype.com/artifact/io.github.up2jakarta/up2job-core)

`Up2 JOB` is an open-source framework that supports shared `Spring-Batch` flow-context and ZIP processors.

# Dependencies

``` xml
    <dependency>
        <groupId>io.github.up2jakarta</groupId>
        <artifactId>up2job-core</artifactId>
        <version>1.5.7</version>
    </dependency>
```

# Sample use cases of [up2csv-format](../up2csv-format/README.md)

- [FastCopyJobITests](./src/test/java/io/github/up2jakarta/job/csv/FastCopyJobITests.java) for `ModeType.FAST`
- [FullCopyJobITests](./src/test/java/io/github/up2jakarta/job/csv/FullCopyJobITests.java) for `ModeType.FULL`
- [UnitCopyJobITests](./src/test/java/io/github/up2jakarta/job/csv/UnitCopyJobITests.java) for `ModeType.UNIT`

