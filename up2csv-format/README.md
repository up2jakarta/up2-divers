# Up2CSV :: Format Framework

[![Maven Central](https://img.shields.io/maven-central/v/io.github.up2jakarta/up2csv-format?style=for-the-badge&color=green)](https://central.sonatype.com/artifact/io.github.up2jakarta/up2csv-format)

# Features

- Reading and Writing `single-segments` from/to `standard` `CSV` files
    - SingleWriter
    - SingleReader
    - See [SingleNoteTests](src/test/java/io/github/up2jakarta/csv/io/SingleNoteTests.java) for more details.
    - See [SingleErrorTests](src/test/java/io/github/up2jakarta/csv/io/SingleNoteTests.java) for read only bean.

- Reading and Writing `business-objects` from/to `multi-segments` `CSV` files
    - Support of `ModeType.FAST` aka two meta-data columns: segment-type and business-reference.
        - FastFileReader
        - FastFileWriter
        - See [BusinessFastTests](src/test/java/io/github/up2jakarta/csv/io/BusinessFastTests.java)
          or [FastInvoiceTests](src/test/java/io/github/up2jakarta/csv/io/FastInvoiceTests.java) for more details.
    - Simple implementation of `ModeType.FAST`
        - SimpleFastReader
        - See [SimpleFastTests](src/test/java/io/github/up2jakarta/csv/io/SimpleFastTests.java) for more details.
    - Support of `ModeType.FULL` aka three meta-data columns: record-number, segment-type and business-reference
        - FullFileReader
        - FullFileWriter
        - See [FullInvoiceTests](src/test/java/io/github/up2jakarta/csv/io/FullInvoiceTests.java)
          or [BusinessFullTests](src/test/java/io/github/up2jakarta/csv/io/BusinessFullTests.java) for more details.
    - Simple implementation of `ModeType.FULL`
        - SimpleFullReader
        - See [SimpleFullTests](src/test/java/io/github/up2jakarta/csv/io/SimpleFullTests.java) for more details.

> :warning: `ModeType.UNIT` is not supported for batch processing
>
> It's used only for unitary processing with Publish/Subscribe systems like JMS Queues or Kafka topics
>
> It's developed for best compacted data as an alternative of XML or JSON formats
