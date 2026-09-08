# Up2JOB :: Core Framework

[![Maven Central](https://img.shields.io/maven-central/v/io.github.up2jakarta/up2job-core?color=green)](https://central.sonatype.com/artifact/io.github.up2jakarta/up2job-core)
[![Mvn Repository](https://badges.mvnrepository.com/badge/io.github.up2jakarta/up2job-core/badge.svg?color=green)](https://mvnrepository.com/artifact/io.github.up2jakarta/up2job-core)

`Up2JOB` is an open-source framework that supports shared `Spring-Batch` flow-context and ZIP processors.

# Dependencies

``` xml
    <dependency>
        <groupId>io.github.up2jakarta</groupId>
        <artifactId>up2job-core</artifactId>
        <version>1.7.2</version>
    </dependency>
```

# Sample use cases of [up2csv-format](../up2csv-format/README.md)

- [FullCopyJobITests](./src/test/java/io/github/up2jakarta/test/FullCopyJobITests.java) for `IMode.FULL`
- [MessCopyJobITests](./src/test/java/io/github/up2jakarta/test/MessCopyJobITests.java) for `ModeType.MESS`
- [NeatCopyJobITests](./src/test/java/io/github/up2jakarta/test/NeatCopyJobITests.java) for `ModeType.NEAT`

