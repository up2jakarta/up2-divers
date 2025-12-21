# Up2LOV :: Core Framework

[![Maven Central](https://img.shields.io/maven-central/v/io.github.up2jakarta/up2lov-core?style=for-the-badge&color=green)](https://central.sonatype.com/artifact/io.github.up2jakarta/up2lov-core)

`Up2LOV` is an open-source API for list of values (LOV) manipulation.

# Dependencies

``` xml
    <dependency>
        <groupId>io.github.up2jakarta</groupId>
        <artifactId>up2lov-core</artifactId>
        <version>1.6.4</version>
    </dependency>
```

# Features

## CodeList

`API` that represents the base model for List of values (`LOV`)

## CodeListConverter

Converter based on binary search that maps CodeList from/to flat-data, it's compatible with XML and JPA.

## CodeListResolver

Contract interface to solve the right converter/adapter for code-list types.

### EntityResolver

Generic Lazy resolver that retrieves code-list stored in database based on JPA queries.

### EntityResolver

Generic Lazy resolver that retrieves code-list stored in database based on native SQL queries.

### CodeListProvider

Generic Eager resolver that fetches the list of values at bootstrap time.

#### 1. ConstantProvider

Simple provider that's able to scan java classes to solve the list of values of a specific code-list type, it supports:

- Compile time constants for enum based types.
- Runtime constants for class based types.

#### 2.DynamicProvider

Simple provider that's able to retrieve the list of values from `.properties` or `.yml` files. 

