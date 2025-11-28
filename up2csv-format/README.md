# Up2CSV :: Format Framework

[![Maven Central](https://img.shields.io/maven-central/v/io.github.up2jakarta/up2csv-format?style=for-the-badge&color=green)](https://central.sonatype.com/artifact/io.github.up2jakarta/up2csv-format)

# Dependencies

``` xml
    <dependency>
        <groupId>io.github.up2jakarta</groupId>
        <artifactId>up2csv-format</artifactId>
        <version>1.6.2</version>
    </dependency>
    <!-- Optional JSR-303 Provider -->
    <!-- Optional CDI/IoC Provider -->
```

# Features

This framework is based on [up2csv-core](../up2csv-core/README.md) for CSV files batch processing in two modes:

## 1. Standard mode

Reading and Writing `single-segments` from/to `standard` `CSV` files

- SingleWriter
- SingleReader
- See [SingleNoteTests](src/test/java/io/github/up2jakarta/csv/io/SingleNoteTests.java) for more details.
- See [SingleErrorTests](src/test/java/io/github/up2jakarta/csv/io/SingleNoteTests.java) for read only bean.

## 2. Multi-Segments mode

Reading and Writing `business-objects` from/to `multi-segments` `CSV` files

- Support of `ModeType.FAST` aka two meta-data columns: segment-type and business-reference.
    - FastFileReader
    - FastFileWriter
    - See [BusinessFastTests](src/test/java/io/github/up2jakarta/csv/io/BusinessFastTests.java)
      or [FastInvoiceTests](src/test/java/io/github/up2jakarta/csv/io/FastInvoiceTests.java) for more details.
    - See [FastCopyJobITests](../up2job-core/src/test/java/io/github/up2jakarta/job/csv/FastCopyJobITests.java)
      for sample import/export batch processing
    - Simple implementation of `ModeType.FAST`
        - SimpleFastReader
        - See [SimpleFastTests](src/test/java/io/github/up2jakarta/csv/io/SimpleFastTests.java) for more details.
- Support of `ModeType.FULL` aka three meta-data columns: record-number, segment-type and business-reference
    - FullFileReader
    - FullFileWriter
    - See [FullInvoiceTests](src/test/java/io/github/up2jakarta/csv/io/FullInvoiceTests.java)
      or [BusinessFullTests](src/test/java/io/github/up2jakarta/csv/io/BusinessFullTests.java) for more details.
    - See [FullCopyJobITests](../up2job-core/src/test/java/io/github/up2jakarta/job/csv/FullCopyJobITests.java)
      for sample import/export batch processing
    - Simple implementation of `ModeType.FULL`
        - SimpleFullReader
        - See [SimpleFullTests](src/test/java/io/github/up2jakarta/csv/io/SimpleFullTests.java) for more details.
- Support of `ModeType.UNIT` aka three meta-data columns: record-number, segment-type and business-reference
    - UnitFileReader
    - UnitFileWriter
    - See [UnitInvoiceTests](src/test/java/io/github/up2jakarta/csv/io/UnitInvoiceTests.java)
      or [BusinessUnitTests](src/test/java/io/github/up2jakarta/csv/io/BusinessUnitTests.java) for more details.
    - See [UnitCopyJobITests](../up2job-core/src/test/java/io/github/up2jakarta/job/csv/UnitCopyJobITests.java)
      for sample import/export batch processing
    - Simple implementation of `ModeType.FULL`
        - SimpleUnitReader
        - See [SimpleUnitTests](src/test/java/io/github/up2jakarta/csv/io/SimpleUnitTests.java) for more details.
