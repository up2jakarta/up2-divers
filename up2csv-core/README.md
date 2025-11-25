# Up2CSV :: Core Framework

[![Maven Central](https://img.shields.io/maven-central/v/io.github.up2jakarta/up2csv-core?style=for-the-badge&color=green)](https://central.sonatype.com/artifact/io.github.up2jakarta/up2csv-core)

- `Up2CSV` is an open-source, light and modern framework that maps and validates easily flat-data to javaBeans and also
  export java-beans to flat-data.
- `Up2CSV` helps developers to parse `business-object` in the case of data is spread over `several` segments (`CSV`
  records).
- `Up2CSV` comes with pre-build tools that support persist-able objects (`JPA`) or exportable like (`XML` or `JSON`).

Shortly, `Up2CSV` is able to map complex objects from `flat-data` to `ready entities` in single pass within `less-code`
approach.

# Features

- Fault tolerance
- Error management API
- Support of JSR-303 validation
- Support of JPA (Java Persistence API)
- Support of JAXB (Java Architecture for XML Binding)
- Support of IoC container like CDI (Contexts and Dependency Injection) provider or Spring or whatever
- Configuration based on @Annotation
- Support of Java OOP (Object-Oriented Programming)
- Support of `Immutable` java-beans including java `Record`
- Support of Java `Optional` and Up2J `Wrapper`
- Mapping from flat-data to java-bean
- Unmapping from java-bean to flat-data
- Extensions
    - Processor API
    - Conversion Resolver API
    - Conversion Extension API
    - Bean Checker API for java-beans
    - Resolver API for business Data Definition
    - Error API
    - Input API
- Format API for multi-segments import and export
- Stream API for batch processing support

# Dependencies

``` xml
    <dependency>
        <groupId>io.github.up2jakarta</groupId>
        <artifactId>up2csv-core</artifactId>
        <version>1.6.1</version>
    </dependency>
    <!-- Required JSR-303 Validation Provider -->
    <dependency>
        <groupId>org.hibernate.validator</groupId>
        <artifactId>hibernate-validator</artifactId>
    </dependency>
    <!-- Optional JPA Provider -->
    <!-- Optional CDI Provider -->
```

# Mapping of flat-data

- Without error collecting (fail-fast)

```java

@Inject
private Up2Factory<?> factory;

public void test() {
    // GIVEN Singleton
    final Up2Mapper<Up2Segment, ?> mapper = factory.build(Up2Segment.class);
    // WHEN
    final Up2Segment bean = mapper.map("Data 1", "Data 2", "...", "Data n");
    // THEN
    // Here the bean is full-filled automatically 
}
```

- Within error collecting

```java

@Inject
private Up2Factory<DynamicType> factory;

public void test() {
    // GIVEN Singletons
    final Up2Mapper<Up2Segment, DynamicType> mapper = factory.build(Up2Segment.class);
    // GIVEN Prototypes
    final SimpleCollector<DynamicType> handler = new SimpleCollector<>();
    // WHEN
    final Up2Segment bean = mapper.map(handler, "Data 1", "Data 2", "...", "Data n");
    final List<SimpleEvent<DynamicType>> errors = handler.toList();
    // THEN
    // Here the bean is full-filled automatically
    // Here the errors is full-filled automatically
    // ... Persistence or ETL or whatever processing
}
```

# Unmapping of java-bean

During the unmapping of java-bean:

- The `Processor API` are not supported for String properties except the default value without modifying the bean.
- The JSR-303 validation is not enabled automatically because beans maybe be not full-filled with default values,
  but the validation can be invoked manually
- The formatting of properties are done with the same annotations for mapping aka `Resolver API`
- The annotation `@Truncated` is supported
- When the flag `@Fragment.nullable` is enabled then export of default values is disabled when fragment is `null`

```java

@Inject
private Up2Factory<?> factory;

public void test() {
    // GIVEN Singleton
    final Up2Flatter<Up2Segment, ?> mapper = factory.format(Up2Segment.class);
    final Up2Segment bean; // ... full-fill the bean
    // WHEN
    final List<? extends IViolationEvent<?>> violations = mapper.validate(bean); // manual validation
    final String[] data = mapper.unmap(bean);
    // THEN
    // Here the data is full-filled automatically 
}
```

# Annotations

## @Position

```java
public class Up2Fragment implements Segment {

    @Position(0)
    private String firstName;

    @Position(1)
    private String lastName;

    // ...
}
```

## @PositionOverride

Overrides `@Position` of an embeddable property or a super-property defined in super class.

## @Fragment

Reuse of java beans in order to avoid code duplication

```java
public class Up2Segment extends Up2Fragment {

    // Override the positions defined in Up2Fragment 
    @Fragment(2)
    private Up2Fragment mother;

    // Override the positions defined in Up2Fragment 
    @Position(2 + 2)
    private Up2Fragment father;

    @Position(2 + 2 + 2)
    private String other;

    // ...

    public static final class Up2Fragment implements Segment {
        @Position(0)
        private String firstName;

        @Position(1)
        private String lastName;

        // ...
    }
}
```

## @FragmentOverride

Overrides `@Fragment` of an embeddable fragment or a super-fragment defined in super class.

## @Processor API

Up2J Processor API is useful for creating configurable processor activated by annotation on fields.

Up2J Core comes with 3 built-in shortcut annotations:

### @Position.defaultValue

Setting the default value

```java
public class Up2Segment implements Segment {

    @Position(value = 0, defaultValue = "*")
    private String code;

    // ...
}
```

### @Up2Token

```java
public class Up2Segment implements Segment {

    @Position(0)
    @Up2Token
    private String code;

    // ...
}
```

### @Up2Trim

```java
public class Up2Segment implements Segment {

    @Position(0)
    @Up2Trim({"", "-", "null", "undefined"})
    private String code;

    // ...
}
```

### Put all together

```java
public class Up2Segment implements Segment {

    @Position(value = 0, defaultValue = "*") // 1st order
    @Up2Trim({"", "-", "null", "undefined"}) // 2nd order
    @Up2Token // 3rd order
    private String code;

    // ...
}
```

## @Position.converter

Any type different from `String` needs to be converted, so the utility of converters

```java
public class Up2Segment implements Segment {

    @Position(value = 0, converter = CurrencyConverter.class)
    private CurrencyCodeType currency;

    // ...
}
```

## @Resolver API

Up2J @Resolver allows the resolution of the conversion function for one or more type.

Up2J @Resolver is activated by shortcut annotation like @Processor.

Up2J Core comes with 6 built-in shortcut annotations:

### @Up2Boolean

This annotation allows the automatic conversion of `boolean` and its wrapper.

```java
public class Up2Segment implements Segment {

    @Position(0)
    @Up2Boolean(trueValue = "Yes", falseValue = "No")
    private Boolean flag;

    // ...
}
```

### @Up2Number

This annotation allows the automatic conversion of non-decimal `Number` and their wrappers:

- int
- long
- short
- byte
- BigInteger

```java
public class Up2Segment implements Segment {

    @Position(value = 0, defaultValue = "-1")
    @Up2Number
    private int number;

    @Position(1)
    @Up2Number
    private Integer other;

    // ...
}
```

### @Up2Decimal

This annotation allows the automatic conversion of decimal `Number` and their wrappers:

- BigDecimal
- double
- float

```java
public class Up2Segment implements Segment {

    @Position(0)
    @Up2Decimal(value = 4, roundingMode = RoundingMode.HALF_EVEN)
    private BigDecimal amount;

    @Position(1)
    @Up2Decimal(value = 4)
    private double quantity;

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

```java
public class Up2Segment implements Segment {

    @Position(0)
    @Up2Temporal
    private LocalDate date;

    // ...
}
```

### @Up2TemporalAmount

This annotation allows the automatic conversion of `java.time.TemporalAmount`:

- Period
- Duration

```java
public class Up2Segment implements Segment {

    @Position(1)
    @Up2TemporalAmount
    private Period period;

    // ...
}
```

### @Up2CodeList

This annotation allows the automatic conversion of Up2J `CodeList` API

- `CodeList` based on `enum` or `class` constants

```java
public class Up2Segment implements Segment {

    @Position(0)
    @Up2CodeList
    private CurrencyCodeType currency;

    @Position(1)
    @Up2CodeList
    private CountryCodeType country;

    // ...
}
```

## @Extension and @Checker API

Up2J @Extension allows the resolution of the conversion function for one or more type for third-party annotation.

Up2J @Extension is activated by shortcut annotation like @Processor and @Resolver.

Up2J Core comes with 2 built-in shortcut annotations:

### @Up2EnableXML

Automatic detection for `XmlEnum` and `XmlJavaTypeAdapter` XML annotations on segments annotated by `XmlType`.

```java

@XmlType
@XmlEnum
public enum TestXmlEnumValue {
    @XmlEnumValue("1") ONE
}
```

```java

@XmlType
@XmlEnum
public enum TestXmlEnum {
    TWO
}
```

```java

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
```

```java

@XmlType
@Up2EnableXML
public class Up2Segment implements Segment {

    @Position(0)
    private TestXmlEnumValue enum1; // enum within @XmlEnumValue

    @Position(1)
    private TestXmlEnum enum2; // enum without @XmlEnumValue

    @Position(2)
    private CurrencyCodeType currency; // type annotated with XmlJavaTypeAdapter

    @Position(3)
    @XmlJavaTypeAdapter(CountryXmlAdapter.class) // property annotated with XmlJavaTypeAdapter
    private CountryCodeType country;

    // ... setters
}
```

### @Up2EnableJPA

Automatic detection for `Enumerated` and `Convert` JPA annotations on segments annotated by `Entity`.

```java
public enum JpaEnum {
    ONE, TWO
}
```

```java

@Entity
@Up2EnableJPA
public class Up2Segment implements Segment {

    @Position(0)
    @Enumerated(EnumType.STRING) // conversion based on enum constant name
    private JpaEnum string;

    @Position(1)
    @Enumerated(EnumType.ORDINAL) // conversion based on enum constant ordinal
    private JpaEnum ordinal;

    @Position(2)
    @Convert(converter = CurrencyConverter.class)
    // CurrencyConverter implements AttributeConverter<CurrencyCodeType, String> {...}
    private CurrencyCodeType currency;

    // ... setters
}
```

# Validation and Error API

## @Error

Helps the engine to full-fill the right error code and severity.

This annotation is fully integrated with @Processor, @Resolver and JSR-303 Payload.

```java
public class TestSegment implements Segment {

    public static final String TU_P_021 = "TU-P021";

    @Position(0)
    @Error(value = TU_P_021, severity = FATAL) // for any error caused by this property
    private String code;

    // ...

}
```

## Support of JSR-303 Payload

You can also use the JSR-303 validation `Payload` to override the error severity and error code.

Up2J comes with two predefined payloads to override the error severity, by default is `SeverityType.ERROR`.

- [Warning.class](src/main/java/io/github/up2jakarta/csv/api/Warning.java)
- [Fatal.class](src/main/java/io/github/up2jakarta/csv/api/Fatal.java)

You can define your own payload of course:

```java

@Error(value = "UP2-100100", severity = SeverityType.WARNING) // Here is the magic
public interface Up2Payload extends Error.Payload {
}
```

## Support of JSR-303 @Constraint

Also, Up2J supports @Error on JSR-303 constraint annotations:

```java

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

```java

@jakarta.validation.Valid
public class TestSegment implements Segment {

    @Position(0)
    @Size(min = 1, max = 3, payload = SeverityFatal.class)
    private String code;

    // ...

}
```

## @ValidOverride

- Enables JSR-303 validation within specific groups, this annotation works with segments only,
  whatever the main segment or embeddable fragments.
- If `ValidOverride.disable` is equals to `true`, then the validation of embeddable fragments will be disabled too.
- To avoid double validation, `@Valid` must not exist when `@ValidOverride` is used on properties aka fields.
- On segment classes, `@ValidOverride` always takes precedence over JSR-303 `@Valid` annotation.
- On segment classes, the validation can be enabled by super-segment classes, the first super-class annotated by
  `@ValidOverride` or `@Valid` will be considered.

```java

@ValidOverride(groups = Up2Group.class)
public class TestSegment implements Segment {

    @Position(0)
    @Size(min = 1, max = 3, payload = SeverityFatal.class, groups = Up2Group.class)
    @Size(min = 1, max = 2, payload = SeverityError.class) // Default
    private String code;

    // ...

}
```

# Error management API

`Up2CSV` is able to gathering all events (errors or warnings) and continue
processing flat-data within fault-tolerance principle.

1. [ComplianceHandler.java](./src/main/java/io/github/up2jakarta/csv/core/hdl/ComplianceHandler.java) for validation
   only
    - [ComplianceCollector.java](./src/main/java/io/github/up2jakarta/csv/core/hdl/ComplianceCollector.java)
2. [EventHandler.java](./src/main/java/io/github/up2jakarta/csv/core/hdl/EventHandler.java) for both validation and
   mapping modes
    - [FastHandler.java](./src/main/java/io/github/up2jakarta/csv/core/hdl/FastHandler.java)
3. [BusinessHandler.java](./src/main/java/io/github/up2jakarta/csv/core/hdl/BusinessHandler.java) for all processing
   modes include import/aggregation
    - [EventCollector.java](./src/main/java/io/github/up2jakarta/csv/core/hdl/EventCollector.java)
    - [EventModeCollector.java](./src/main/java/io/github/up2jakarta/csv/core/hdl/EventModeCollector.java)
        - [BusinessCollector.java](./src/main/java/io/github/up2jakarta/csv/core/hdl/BusinessCollector.java)
        - [PropertyCollector.java](./src/main/java/io/github/up2jakarta/csv/core/hdl/PropertyCollector.java)
        - [PropertyFailureCollector.java](./src/main/java/io/github/up2jakarta/csv/core/hdl/PropertyFailureCollector.java)

## @Truncated

Tells the engine that the given input data is already truncated, it allows overriding of event offset.

```java

@Truncated(4) // The first 4 columns are truncated
public class MySegment implements Segment {
    // ...
}
```

# Support of JPA AccessMode

## Field based access

```java
public class MyBean implements Segment {

    @Position(0)
    @Access(AccessType.FIELD)
    private String code;

    // No getters neither setters

}
```

## Property based access (Default Mode)

```java

@Access(AccessType.PROPERTY)
public class MyBean implements Segment {

    @Position(1)
    @Up2Boolean
    protected String code;

    // ...

    protected String getCode() {
        return code;
    }

    protected void setCode(String code) {
        this.code = code;
    }
}
```

## Support of Java Record access

```java
public enum Source {
    CSV, WEB, KPI
}
```

```java
public record MyRecord(long id, @Position(0) String code, @Position(1) String label, Source source) implements Segment {
}
```

> :warning: In the case of `MyRecord` beans are created by the framework;
> - The value of `id` will be always `0` because primitive
> - The value of `source` will be always `null`

> :information_source: In the case of immutable beans, the following assertions are true:
> - The writing of properties is done with constructor whatever the access type Property or Field
> - Primitive properties are set with default values when are not mapped or the input data is null
> - When primitive property is managed by the framework, its value is exported by `Up2Flatter` because not `null`

### Constructor selection to set values of non-managed properties

```java
public record MyRecord(@Position(0) String code, @Position(1) String label, Source source) implements Segment {

    @Creator // Correct the value of source
    private MyRecord(String code, String label) {
        this(code, label, Source.CSV);
    }
}
```

> :warning: the constraints for java-records are:
> - The constructor must contain all managed properties as arguments with the same names and types
> - The order is not important

### More, you can secure the source attribute

```java
public final class MySegment implements Segment {

    private final @Position(0) String code;
    private final @Position(1) String label;
    private final Source source;

    @Creator // For mapping only
    private Final1Segment(String code, String label) {
        this.code = code;
        this.label = label;
        this.source = Source.CSV;
    }

    // Public constructor
    public Final1Segment(String code, String label, Source source) {
        if (source == Source.CSV) {
            throw new IllegalArgumentException("trying to hack the source");
        }
        this.code = code;
        this.label = label;
        this.source = source;
    }

    // ... Getters
}
```

> :warning: the constraints for immutables beans are:
> - The constructor must contain all managed properties as arguments with the same names and types
> - :new: Each property must be final
> - The order is not important

### Mix final and non-final properties workarounds

### 1. @Fragment

If some properties are open for modification, you can use segments composition technique
i.e put all non-final properties in separate fragment

```java
public final class MySegment implements Segment {

    private final @Position(0) String code;
    private final @Position(1) String label;
    private final @Fragment(1 /* nullable = false */) MyFragment container;

    @Creator
    private Final1Segment(String code, String label, MyFragment container) {
        this.code = code;
        this.label = label;
        this.container = container;
    }

    // ... Getters

    public static class MyFragment implements Segment {

        private @Position(0) String description; // non-final
        private @Position(1) String another; // non-final
        // ... Other properties

        // ... Getters & Setters

    }
}
```

### 2. Wrapper

Another solution, the use of `Wrapper` to wrap each non-final property,
> :new: The wrapper supports the JSR-303 validation, free to use annotation on Wrapper type-argument.

```java
import io.github.up2jakarta.lov.core.Wrapper;

@Access(FIELD) // avoid conflict of getNote return-type
public final class MySegment implements Segment {

    private final @Position(0) String code;
    private final @Position(1) String label;
    @Position(2)
    private final @NotBlank Wrapper<String> note; // non-final
    @Position(3)
    private final Wrapper<@NotBlank String> another; // non-final
    // ... Other properties

    @Creator
    private Final1Segment(String code, String label, Wrapper<String> note, Wrapper<String> another) {
        this.code = code;
        this.note = note;
        this.label = label;
        this.another = another;
    }

    public String getNote() {
        return this.note.get();
    }

    public void setNote(String note) {
        return this.note.accept(note);
    }

    // ... Accessors
}
```

## Input API

### The specifications are described in [io.gitHub.up2jakarta.csv.api](./src/main/java/io/github/up2jakarta/csv/api)

- `BeanLinker`: Bean linker for aggregation/segregation processing.
- `IEvent`: Input event representation (model) for errors or exceptions.
- `IRecord`: Input record representation (model)
- `IType`: Segment definition for import/export processing.

See [Sample implementations here](./src/test/java/io/github/up2jakarta/csv/impl)

## Data Definition and Resolver API

## Contract

- [DataType.java](./src/main/java/io/github/up2jakarta/csv/data/DataType.java) base interface
- [DataTypeResolver.java](./src/main/java/io/github/up2jakarta/csv/data/DataTypeResolver.java) base resolver

## Simple implementation

- [@Definition](./src/main/java/io/github/up2jakarta/csv/data/Definition.java) annotation based definition
- [DataTypeResolver.dynamic()](./src/main/java/io/github/up2jakarta/csv/data/DataTypeResolver.java) for `@Definition`
- [DataTypeResolver.empty()](./src/main/java/io/github/up2jakarta/csv/data/DataTypeResolver.java) NoOP implementation

# Format API

The final goal of `Up2CSV` is to parse and format `business objects` in case of data is spread over several segments.

## Business Case

For example, it's impossible to present an invoice in standard CSV format because invoice should contain several items
and each item:

- Should reference a product and this product may have several attributes
- Should have several charges or allowances
- Should have several notes
- and more

## Technical Problem

It's impossible to present a `business-property` within `0..n` cardinality

## Solution

- Put any multiple `business-property` (within `0..n` cardinality) in a separate `segment` and `BusinessAggregator` do
  the work.
- Also, it's possible put any optional `business-property` (within `0..1` cardinality) in a separate `segment` to
  simplify the validation.
- And more depending on the `business-logic`

## Business Aggregation & Segregation

- [BusinessExporter.java](src/main/java/io/github/up2jakarta/csv/core/BusinessExporter.java) to segregate and export
  java-bean to flat-data
    1. [FullExporter.java](src/main/java/io/github/up2jakarta/csv/core/FullExporter.java) for `FULL` mode
    2. [FastExporter.java](src/main/java/io/github/up2jakarta/csv/core/FastExporter.java) for `FAST` mode
    3. [UnitExporter.java](src/main/java/io/github/up2jakarta/csv/core/UnitExporter.java) for `UNIT` mode
- [BusinessImporter.java](src/main/java/io/github/up2jakarta/csv/core/BusinessImporter.java) to aggregate and import
  java-bean from flat-data
    1. [FullImporter.java](src/main/java/io/github/up2jakarta/csv/core/FullImporter.java) for `FULL` mode
    2. [FastImporter.java](src/main/java/io/github/up2jakarta/csv/core/FastImporter.java) for `FAST` mode
    3. [UnitImporter.java](src/main/java/io/github/up2jakarta/csv/core/UnitImporter.java) for `UNIT`mode
- Simple Implementations
    1. [SimpleFullImporter.java](src/main/java/io/github/up2jakarta/csv/fmt/SimpleFullImporter.java) for `FULL` mode
    2. [SimpleFastImporter.java](src/main/java/io/github/up2jakarta/csv/fmt/SimpleFastImporter.java) for `FAST` mode
    3. [SimpleUnitImporter.java](src/main/java/io/github/up2jakarta/csv/fmt/SimpleUnitImporter.java) for `UNIT`mode

## Use cases

1. See [BusinessFullTests](src/test/java/io/github/up2jakarta/csv/fmt/BusinessFullTests.java) for `FULL` mode.
2. See [BusinessFastTests](src/test/java/io/github/up2jakarta/csv/fmt/BusinessFastTests.java) for `FAST` mode.
3. See [BusinessUnitTests](src/test/java/io/github/up2jakarta/csv/fmt/BusinessUnitTests.java) for `UNIT` mode.

# Stream API (Batch processing)

- [BusinessReader.java](src/main/java/io/github/up2jakarta/csv/core/BusinessReader.java) for stream inputs
- [BusinessWriter.java](src/main/java/io/github/up2jakarta/csv/core/BusinessWriter.java) for stream outputs

See [up2csv-format](../up2csv-format/README.md) for CSV files implementation.

# Best practices

- Define your mapper as singleton to avoid scanning javaBeans every time.
- If the javaBeans are manipulated by Bytecode-Enhancement you must provide Cache API and sync operations.
- Use of `CodeList` because it is compatible with both JPA `AttributeConverter` and `XmlAdapter`
