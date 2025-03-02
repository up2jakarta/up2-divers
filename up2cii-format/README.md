# Up2CII :: Format Framework

`Up2CII` is an open-source framework for validating, reading and writing CII e-invoices.

[![Maven Central](https://img.shields.io/maven-central/v/io.github.up2jakarta/up2cii-format?style=for-the-badge&color=green)](https://central.sonatype.com/artifact/io.github.up2jakarta/up2cii-format)

# Features

- CII `Cross Industry Invoice` : https://unece.org/trade/uncefact/xml-schemas-2018-2012 :

| Issued | Document Title                | Download                                                                                                   |
|--------|-------------------------------|------------------------------------------------------------------------------------------------------------|
| 2016   | XML Schemas 16B (SCRDM - CII) | [ZIP](https://unece.org/DAM/cefact/xml_schemas/D16B_SCRDM__Subset__CII.zip)                                |
|        | XML Schemas version 16B       | [ZIP](https://unece.org/DAM/cefact/xml_schemas/D16B.zip)                                                   |
|        | Validation Report             | [PDF](https://unece.org/DAM/cefact/xml_schemas/160929-1-Schema_VALIDATION-REPORT-D16B_schemas_28SEP16.pdf) |
|        | Release notes                 | [PDF](https://unece.org/DAM/cefact/xml_schemas/D16B_Schema_Production_Notes.pdf)                           |

- France e-invoicing [Specifications B2B v3.0](https://www.impots.gouv.fr/specifications-externes-b2b)

## Minified Format

Only France supported data within `CodeList` mapping

## Standard Format

All data within `CodeList` mapping

## Unmapped Format

All data without `CodeList` mapping