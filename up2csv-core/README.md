# Up2CSV :: Core Framework

[![Maven Central](https://img.shields.io/maven-central/v/io.github.up2jakarta/up2csv-core?style=for-the-badge&color=green)](https://central.sonatype.com/artifact/io.github.up2jakarta/up2csv-core)

- `Up2CSV` is an open-source, light and modern framework that maps and validates easily flat-data to javaBeans.
- `Up2CSV` helps developers to parse `business-object` in the case of data is spread over `several` segments (`CSV` records).
- `Up2CSV` comes with pre-build tools that support persist-able objects (`JPA`) or exportable like (`XML` or `JSON`).

Shortly, `Up2CSV` is able to map complex objects from `flat-data` to `ready entities` in single pass within `less-code` approach.

# Features

- Fault tolerance
- Error management API
- Support of JSR-303 validation
- Support of JPA (Java Persistence API)
- Support of JAXB (Java Architecture for XML Binding)
- Support of IoC container like CDI (Contexts and Dependency Injection) provider or Spring or whatever
- Configuration based on @Annotation
- Support of Java OOP (Object-Oriented Programming)
- Business Aggregator
- Extensions
    - Processor API
    - Conversion Resolver API
    - Error API
    - Input API
    - Conversion Extension API
    - Bean Checker API
    - BusinessData Resolver API

# Requirements

- Java 17

# Dependencies

``` xml
    <dependency>
        <groupId>io.github.up2jakarta</groupId>
        <artifactId>up2csv-core</artifactId>
        <version>1.4.7</version>
    </dependency>
    <!-- Optional JSR-303 Validation Provider -->
    <!-- Optional JPA Provider -->
    <!-- Optional CDI Provider -->
```

# Annotations

## @Position

``` java
public Up2Fragment implements Segment {

    @Position(0)
    private String firstName;

    @Position(1)
    private String lastName;
}
```

## @PositionOverride

Overrides `@Position` of an embeddable property or a super-property defined in super class.

## @Fragment

Reuse of java beans in order to avoid code duplication

``` java
public Up2Segment implements Segment {

    @Position(0)
    private String firstName;

    @Position(1)
    private String lastName;

    // Override the positions defined in Up2Fragment 
    @Fragment(2)
    private Up2Fragment mother;

    // Override the positions defined in Up2Fragment 
    @Position(2 + 2)
    private Up2Fragment father;
    
    @Position(2 + 2 + 2)
    private String other;
}
```

## @FragmentOverride

Overrides `@Fragment` of an embeddable fragment or a super-fragment defined in super class.

## @Processor API

Up2 Processor API is useful to create configurable processor activated by annotation on fields.

Up2 Core comes with 3 built-in shortcut annotations:

### @Up2Default

Setting the default value

``` java
public Up2Segment implements Segment {

    @Position(0)
    @Up2Default("null")
    private String code;
}
```

### @Up2Token

``` java
public Up2Segment implements Segment {

    @Position(0)
    @Up2Token
    private String code;
}
```

### @Up2Trim

``` java
public Up2Segment implements Segment {

    @Position(0)
    @Up2Trim({"", "-", "null", "undefined"}) 
    private String code;
}
```

### Put all together

``` java
public Up2Segment implements Segment {

    @Position(0)
    @Up2Trim({"", "-", "null", "undefined"}) // 1st order
    @Up2Token // 2nd order
    @Up2Default("UP2") // 3rd order
    private String code;
}
```

## @Converter

Any type different from `String` needs to be converted, so the utility of @Converter

``` java
public Up2Segment implements Segment {

    @Converter(CurrencyConverter.class)
    private CurrencyCodeType currency;
}
```

## @Resolver API

Up2 @Resolver allows the resolution of the conversion function for one or more type.

Up2 @Resolver is activated by shortcut annotation like @Processor.

Up2 Core comes with 6 built-in shortcut annotations:

### @Up2Boolean

This annotation allows the automatic conversion of `boolean` and its wrapper.

``` java
public Up2Segment implements Segment {

    @Position(0)
    @Up2Boolean("Yes")
    private Boolean valid;
}
```

### @Up2Number

This annotation allows the automatic conversion of non-decimal `Number` and their wrappers:

- int
- long
- short
- byte
- BigInteger

``` java
public Up2Segment implements Segment {

    @Position(0)
    @Up2Default("0")
    @Up2Number
    private int anInt;
    
    @Position(2)
    @Up2Number
    private Integer anInteger;
    
    // ...
}
```

### @Up2Decimal

This annotation allows the automatic conversion of decimal `Number` and their wrappers:

- BigDecimal
- double
- float

``` java
public Up2Segment implements Segment {

    @Position(0)
    @Up2Decimal(value = 4, roundingMode = RoundingMode.HALF_EVEN)
    private BigDecimal aDecimal;
    
    @Position(2)
    @Up2Default("0")
     @Up2Decimal(value = 4)
    private double aDouble;
    
    // ...
}
```

### @Up2Temporal

This annotation allows the automatic conversion of `java.time.Temporal`:

- LocalTime
- LocalDate
- LocalDateTime
- OffsetTime
- OffsetDateTime
- ZonedDateTime
- Year
- YearMonth
- Instant

``` java
public Up2Segment implements Segment {

    @Position(2)
    @Up2Temporal
    private LocalDate date;
    
    // ...
}
```

### @Up2TemporalAmount

This annotation allows the automatic conversion of `java.time.TemporalAmount`:

- Period
- Duration

``` java
public Up2Segment implements Segment {

    @Position(2)
    @Up2TemporalAmount
    private Period perid;
    
    // ...
}
```

### @Up2CodeList

This annotation allows the automatic conversion of Up2 `CodeList` API

- `CodeList` based on `enum`

``` java
public Up2Segment implements Segment {

    @Position(0)
    @Up2CodeList
    private CurrencyCodeType currency;

    @Position(9)
    @Up2CodeList
    private CountryCodeType country;
    
    // ...
}
```

## @Extension API

Up2 @Extension allows the resolution of the conversion function for one or more type for third-party annotation.

Up2 @Extension is activated by shortcut annotation like @Processor and @Resolver.

Up2 Core comes with 2 built-in shortcut annotations:

### @Up2EnableXML

Automatic detection for `XmlEnum` and `XmlJavaTypeAdapter` XML annotations on segments annotated by `XmlType`.

``` java
@XmlType
@XmlEnum
public enum TestXmlEnumValue {
    @XmlEnumValue("1") ONE
}

@XmlType
@XmlEnum
public enum TestXmlEnum {
    TWO
}

@XmlJavaTypeAdapter(CurrencyConverter.class)
public enum CurrencyCodeType implements CodeList<CurrencyCodeType> {

    EUR("EUR", "Euro"),
    TND("TND", "Tunisian Dinar"),
    ;

    private final String name;
    private final String code;

    CurrencyCodeType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCode() {
        return code;
    }

}

@XmlType
@Up2EnableXML
public Up2Segment implements Segment {

    @Position(0)
    private TestXmlEnumValue enum1; // enum within @XmlEnumValue
    
    @Position(1)
    private TestXmlEnum enum2; // enum without @XmlEnumValue

    @Position(3)
    private CurrencyCodeType currency; // type annotated with XmlJavaTypeAdapter
    
    @Position(4)
    @XmlJavaTypeAdapter(CountryXmlAdapter.class) // property annotated with XmlJavaTypeAdapter
    private CountryCodeType country;
    
    // ... setters
}
```

### @Up2EnableJPA

Automatic detection for `Enumerated` and `Convert` JPA annotations on segments annotated by `Entity`.

``` java
public enum JpaEnum {
   ONE, TWO
}

@Entity
@Up2EnableJPA
public Up2Segment implements Segment {

    @Position(0)
    @Enumerated(EnumType.STRING) // conversion based on enum constant name
    private JpaEnum string;

    @Position(1)
    @Enumerated(EnumType.ORDINAL) // conversion based on enum constant ordinal
    private JpaEnum ordinal;

    @Position(2)
    @Convert(converter = CurrencyConverter.class) // CurrencyConverter implements AttributeConverter<CurrencyCodeType, String> {...}
    private CurrencyCodeType currency;
    
     // ... setters
}
```

# Validation and Error API

## @Error

Helps the engine to full-fill the right error code and severity.

This annotation is fully integrated with @Processor, @Converter, @Resolver and JSR-303 Payload.

``` java
public TestSegment implements Segment {

    public static final String TU_P_021 = "TU-P021";

    @Position(0)
    @Error(value = TU_P_021, severity = FATAL) // for any error caused by this property
    private String code;

}
```

## Support of JSR-303 Payload

You can also use the JSR-303 validation `Payload` to override the error severity and error code.

Up2 comes with three predefined payloads to override the error severity.

- Errors.Fatal.class
- Errors.Error.class
- Errors.Warning.class

You can define your own payload of course:

``` java
@Error(value = "UP2-100100", severity = SeverityType.ERROR) // Here the magic
public interface Up2Payload extends Error.Payload {
}
```

## Support of JSR-303 @Constraint

Also, Up2 supports @Error on JSR-303 constraint annotations:

``` java
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {Up2NotEmptyValidator.class})
@Error(value = "UP2-900999", severity = SeverityType.FATAL) // Here the magic
public @interface Up2NotEmpty {

    String message() default "{jakarta.validation.constraints.NotEmpty.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
```

## @jakarta.validation.Valid

Enables JSR-303 validation

``` java
@jakarta.validation.Valid
public TestSegment implements Segment {

    @Position(0)
    @Size(min = 1, max = 3, payload = Errors.Fatal.class)
    private String code;

}
```

## @ValidOverride

Enables JSR-303 validation within specific groups.

``` java
@ValidOverride(groups = Up2Group.class)
public TestSegment implements Segment {

    @Position(0)
    @Size(min = 1, max = 3, payload = Errors.Fatal.class, groups = Up2Group.class)
    @Size(min = 1, max = 2, payload = Errors.Error.class) // Default
    private String code;

}
```

# Error management API

`Up2CSV` is able to gathering all events (errors or warnings) and continue
processing flat-data within fault-tolerance principle.

## @Truncated

Tells the engine that the given input data is already truncated, it allows overriding of error offset.

``` java
@Truncated(4) // The first 4 columns are truncated
public TestSegment implements Segment {
    ...
}
```

## Input API

### The specifications are described in [io.github.up2jakarta.csv.input](./src/main/java/io/github/up2jakarta/csv/input)

- `InputError`: Input error presentation (model)
- `InputRepository`: input error repository (helpful for error id generation)
- `InputSegment`: Input record presentation (model)
- `InputType`: Segment discriminator type

See [Sample implementations here](./src/test/java/io/github/up2jakarta/csv/impl)

# Use of Up2CSV

- Without error collecting (fail-fast)

``` java
@Inject
private MapperFactory factory;

{
    // GIVEN Singleton
    final Mapper<Up2Segment> mapper = factory.build(Up2Segment.class);
    // WHEN
    final Up2Segment bean = mapper.map("Data 1", "Data 2", "Data n");
    // THEN
    // Here the bean is full-filled automatically 
}
```

- Within error collecting

``` java
@Inject
private MapperFactory factory;

@Inject
private InputRepository<InputRowImpl> repository;

@Inject
private EventCreator<InputRowImpl, ?, ?, InputErrorImpl> creator;

{
    // GIVEN Singletons
    final Mapper<Up2Segment> mapper = factory.build(Up2Segment.class);
    // GIVEN Prototypes
    final InputRowImpl row ; // ... retrive it from repository or CSV file
    final EventHandler<InputRowImpl, ?, ?, InputErrorImpl> handler = new EventHandlerImpl<>(row, creator, repository) ;
    // WHEN
    final Up2Segment bean = mapper.map(row, handler);
    final List<InputErrorImpl> errors = handler.toList();
    // THEN
    // Here the bean is full-filled automatically
    // Here the errors is full-filled automatically 
    // ... Persistence or ETL or whateven processing
}
```

# Business Aggregation

The final goal of `Up2CSV` is to parse a `business-object` in case of data is spread over several segments.

## Sample Business Case

It's impossible to present an invoice in standard CSV format because invoice should contain several items and each item:

- Should reference a product and this product may have several attributes
- Should have several charges or allowances
- Should have several notes
- and more

## Problem

It's impossible to present a `business-property` within `0..n` cardinality

## Solution

- Put any multiple `business-property` (within `0..n` cardinality) in a separate `segment` and `BusinessAggregator` do
  the work.
- Also, it's possible put any optional `business-property` (within `0..1` cardinality) in a separate `segment` to
  simplify the validation.
- And more depending on the `business-logic`

See [BusinessAggregatorTest.java](src/test/java/io/github/up2jakarta/csv/BusinessAggregatorTest.java) for more details.

# Best practices

- Define your mapper as singleton to avoid scanning javaBeans every time.
- If the javaBeans are manipulated by Bytecode-Enhancement you must provide Cache API and sync operations.
- Use of `CodeList` because it is compatible with both JPA `AttributeConverter` and `XmlAdapter`
