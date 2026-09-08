# Up2CSV :: Format Framework

[![Maven Central](https://img.shields.io/maven-central/v/io.github.up2jakarta/up2csv-format?color=green)](https://central.sonatype.com/artifact/io.github.up2jakarta/up2csv-format)
[![Mvn Repository](https://badges.mvnrepository.com/badge/io.github.up2jakarta/up2csv-format/badge.svg?color=green)](https://mvnrepository.com/artifact/io.github.up2jakarta/up2csv-format)

# Dependencies

``` xml
    <dependency>
        <groupId>io.github.up2jakarta</groupId>
        <artifactId>up2csv-format</artifactId>
        <version>1.7.2</version>
    </dependency>
    <!-- Optional JSR-303 Provider -->
    <!-- Optional CDI/IoC Provider -->
```

# Features

This framework is based on [up2csv-core](../up2csv-core/README.md) for CSV files batch processing in two modes:

## 1. Standard mode

Reading and Writing `single-segments` from/to `standard` `CSV` files

- [SingleWriter](src/main/java/io/github/up2jakarta/csv/io/SingleWriter.java)
- [SingleReader](src/main/java/io/github/up2jakarta/csv/io/SingleReader.java)
- See [SingleNoteTests](src/test/java/io/github/up2jakarta/test/SingleNoteTests.java) for more details.
- See [SingleErrorTests](src/test/java/io/github/up2jakarta/test/SingleErrorTests.java) for read only bean.

## 2. Multi-Segments mode

Reading and Writing `business-objects` from/to `multi-segments` `CSV` files

- Support of `ModeType.NEAT` aka three meta-data columns: record-number, segment-type and business-reference
    - [NeatAdapter](src/main/java/io/github/up2jakarta/csv/io/NeatAdapter.java)
    - [NeatFileReader](src/main/java/io/github/up2jakarta/csv/io/NeatFileReader.java)
    - [NeatFileWriter](src/main/java/io/github/up2jakarta/csv/io/NeatFileWriter.java)
    - See [NeatInvoiceTests](src/test/java/io/github/up2jakarta/test/NeatInvoiceTests.java)
      or [BusinessNeatTests](src/test/java/io/github/up2jakarta/test/BusinessNeatTests.java) for more details.
    - See [NeatCopyJobITests](../up2job-core/src/test/java/io/github/up2jakarta/test/NeatCopyJobITests.java)
      for sample import/export batch processing
    - Simple implementation
        - [SimpleNeatReader](src/main/java/io/github/up2jakarta/csv/io/SimpleNeatReader.java)
        - See [SimpleNeatTests](src/test/java/io/github/up2jakarta/test/SimpleNeatTests.java) for more details.

- Support of `ModeType.MESS` aka two meta-data columns: segment-type and business-reference.
    - [MessFileReader](src/main/java/io/github/up2jakarta/csv/io/MessFileReader.java)
    - [MessFileWriter](src/main/java/io/github/up2jakarta/csv/io/MessFileWriter.java)
    - See [BusinessMessTests](src/test/java/io/github/up2jakarta/test/BusinessMessTests.java)
      or [MessInvoiceTests](src/test/java/io/github/up2jakarta/test/MessInvoiceTests.java) for more details.
    - See [MessCopyJobITests](../up2job-core/src/test/java/io/github/up2jakarta/test/MessCopyJobITests.java)
      for sample import/export batch processing
    - Simple implementation
        - [SimpleMessReader](src/main/java/io/github/up2jakarta/csv/io/SimpleMessReader.java)
        - See [SimpleMessTests](src/test/java/io/github/up2jakarta/test/SimpleMessTests.java) for more details.

- Support of `IMode.FULL` aka three meta-data columns: record-number, segment-type and business-reference
    - [FullFileReader](src/main/java/io/github/up2jakarta/csv/io/FullFileReader.java)
    - [FullFileWriter](src/main/java/io/github/up2jakarta/csv/io/FullFileWriter.java)
    - See [FullInvoiceTests](src/test/java/io/github/up2jakarta/test/FullInvoiceTests.java)
      or [BusinessFullTests](src/test/java/io/github/up2jakarta/test/BusinessFullTests.java) for more details.
    - See [FullCopyJobITests](../up2job-core/src/test/java/io/github/up2jakarta/test/FullCopyJobITests.java)
      for sample import/export batch processing
    - Simple implementation
      - [SimpleFullReader](src/main/java/io/github/up2jakarta/csv/io/SimpleFullReader.java)
      - See [SimpleFullTests](src/test/java/io/github/up2jakarta/test/SimpleFullTests.java) for more details.
