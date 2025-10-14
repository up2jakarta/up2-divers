# Up2CSV :: Format Framework

[![Maven Central](https://img.shields.io/maven-central/v/io.github.up2jakarta/up2csv-format?style=for-the-badge&color=green)](https://central.sonatype.com/artifact/io.github.up2jakarta/up2csv-format)

# Features

- Reading business-objects from CSV file (multi-segment)
- Writing business-objects to CSV file (multi-segment)
- Support of `ModeType.FAST` aka two meta-data columns: segment-type and business-reference
- Support of `ModeType.FULL` aka three meta-data columns: record-number, segment-type and business-reference

See [Business Tests](src/test/java/io/github/up2jakarta/csv/io/BusinessInvoiceTests.java) for more details.

