# Up2CII :: Format Framework

[![Maven Central](https://img.shields.io/maven-central/v/io.github.up2jakarta/up2cii-format?color=green)](https://central.sonatype.com/artifact/io.github.up2jakarta/up2cii-format)
[![Mvn Repository](https://badges.mvnrepository.com/badge/io.github.up2jakarta/up2cii-format/badge.svg?color=green)](https://mvnrepository.com/artifact/io.github.up2jakarta/up2cii-format)

`Up2CII` is an open-source framework for validating, reading and writing CII e-invoices.

# Dependencies

``` xml
    <dependency>
        <groupId>io.github.up2jakarta</groupId>
        <artifactId>up2cii-format</artifactId>
        <version>1.7.1</version>
    </dependency>
```

# Features

- CII D22B [Cross Industry Invoice](https://unece.org/trade/uncefact/xml-schemas)

- France e-invoicing [Specifications B2B v3.0](https://www.impots.gouv.fr/specifications-externes-b2b)

## Minified Format

Only France supported data within `CodeList` mapping

## Standard Format

All data within `CodeList` mapping

## Unmapped Format

All data without `CodeList` mapping