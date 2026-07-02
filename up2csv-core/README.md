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
        <version>1.7.0</version>
    </dependency>
    <!-- Optional JSR-303 Provider -->
    <!-- Optional CDI/IoC Provider -->
```

# Mapping of flat-data

- Without error collecting (fail-fast)

```java

import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.Up2Mapper;

@Inject
private Up2Factory<?> factory;

public void test() {
    // GIVEN Singleton
    final Up2Mapper<Up2Segment, ?> mapper = factory.mapper(Up2Segment.class);
    // WHEN
    final Up2Segment bean = mapper.map("Data 1", "Data 2", "...", "Data n");
    // THEN
    // Here the bean is full-filled automatically 
}
```

- Within error collecting

```java

import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.Up2Mapper;
import io.github.up2jakarta.csv.core.hdl.SimpleCollector;
import io.github.up2jakarta.csv.core.hdl.SimpleEvent;
import io.github.up2jakarta.csv.data.HeaderType;

@Inject
private Up2Factory<DynamicType> factory;

public void test() {
    // GIVEN Singletons
    final Up2Mapper<Up2Segment, HeaderType> mapper = factory.mapper(Up2Segment.class);
    // GIVEN Prototypes
    final SimpleCollector<HeaderType> handler = new SimpleCollector<>();
    // WHEN
    final Up2Segment bean = mapper.map(handler, "Data 1", "Data 2", "...", "Data n");
    final List<SimpleEvent<HeaderType>> errors = handler.toList();
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

import io.github.up2jakarta.csv.api.hdl.IComplianceEvent;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.Up2Flatter;
import jakarta.inject.Inject;

@Inject
private Up2Factory<?> factory;

public void test() {
    // GIVEN Singleton
    final Up2Flatter<Up2Segment, ?> mapper = factory.flatter(Up2Segment.class);
    final Up2Segment bean; // ... full-fill the bean
    // WHEN
    final List<? extends IComplianceEvent<?>> violations = mapper.validate(bean); // manual validation
    final String[] data = mapper.unmap(bean);
    // THEN
    // Here the data is full-filled automatically 
}
```

# Annotations

## @Position

```java
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;

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
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;

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
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;

public class Up2Segment implements Segment {

    @Position(value = 0, defaultValue = "*")
    private String code;

    // ...
}
```

### @Up2Token

```java
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Token;

public class Up2Segment implements Segment {

    @Position(0)
    @Up2Token({"", "-", "null", "undefined"})
    private String code;

    // ...
}
```

### @Up2Trim

```java
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Trim;

public class Up2Segment implements Segment {

    @Position(0)
    @Up2Trim({"", "-", "null", "undefined"})
    private String code;

    // ...
}
```

### Put all together

```java
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Trim;

public class Up2Segment implements Segment {

    @Position(value = 0, defaultValue = "*") // defaultValue is always in 1st order
    @Up2Trim({"null", "undefined"}) // 2nd order
    @Up2Token({"", "-"}) // 3rd order
    private String code;

    // ...
}
```

## @Position.converter

Any type different from `String` needs to be converted, so the utility of converters

```java
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Converter;

public class Up2Segment implements Segment {

    @Position(value = 0, converter = @Up2Converter(CurrencyConverter.class))
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
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Boolean;

public class Up2Segment implements Segment {

    @Position(0)
    @Up2Boolean(trueValue = "Yes", falseValue = "No")
    private Boolean flag;

    // ...
}
```

### @Up2Character

This annotation allows the automatic conversion of `char` and its wrapper.

```java
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Character;

public class Up2Segment implements Segment {

    @Position(0)
    @Up2Character
    private char char1;

    @Position(0)
    @Up2Character(false)
    private Character char2;

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
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;

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

### @Up2Up2OptionalInt

This annotation allows the automatic conversion of `OptionalInt`.

```java
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2OptionalInt;

import java.util.OptionalInt;

public class Up2Segment implements Segment {

    @Position(0)
    @Up2OptionalInt
    private OptionalInt index;

    // ...
}
```

### @Up2OptionalLong

This annotation allows the automatic conversion of `OptionalLong`.

```java
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2OptionalLong;

import java.util.OptionalLong;

public class Up2Segment implements Segment {

    @Position(0)
    @Up2OptionalLong
    private OptionalLong index;

    // ...
}
```

### @Up2Decimal

This annotation allows the automatic conversion of decimal `Number` and their wrappers:

- BigDecimal
- double
- float

```java
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Decimal;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.RoundingMode.HALF_EVEN;

public class Up2Segment implements Segment {

    @Position(0)
    @Up2Decimal(value = 4, roundingMode = HALF_EVEN)
    private BigDecimal amount;

    @Position(1)
    @Up2Decimal(value = 4)
    private double quantity;

    // ...
}
```

### @Up2OptionalDouble

This annotation allows the automatic conversion of `OptionalDouble`.

```java
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2OptionalDouble;

import java.util.OptionalDouble;

import static java.math.RoundingMode.HALF_EVEN;

public class Up2Segment implements Segment {

    @Position(0)
    @Up2OptionalDouble(value = 4, roundingMode = HALF_EVEN)
    private OptionalDouble amount;

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
import io.github.up2jakarta.csv.Segment;

import java.time.LocalDate;

public class Up2Segment implements Segment {

    @Position(0)
    @Up2Temporal
    private LocalDate date;

    // ...
}
```

### @Up2Date

This annotation allows the automatic conversion of `java.util.Date`:

- java.util.Date
- java.sql.Timestamp
- java.sql.Date
- java.sql.Time

```java
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Date;

public class Up2Segment implements Segment {

    @Up2Date
    @Position(0)
    private java.util.Date date;

    @Up2Date
    @Position(1)
    private java.sql.Date sqlDate;

    @Up2Date
    @Position(2)
    private java.sql.Time sqlTime;

    @Up2Date
    @Position(3)
    private java.sql.Timestamp timestamp;

    // ...
}
```

### @Up2TemporalAmount

This annotation allows the automatic conversion of `java.time.TemporalAmount`:

- Period
- Duration

```java
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2TemporalAmount;

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
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2CodeList;

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

Automatic detection for `XmlEnum` and `XmlJavaTypeAdapter` XML annotations on segments annotated with `@XmlType`.

```java

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum
public enum TestXmlEnumValue {
    @XmlEnumValue("1") ONE
}
```

```java

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum
public enum TestXmlEnum {
    TWO
}
```

```java

import io.github.up2jakarta.lov.CodeList;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2EnableXML;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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

Automatic detection for `Enumerated` and `Convert` JPA annotations on segments annotated with `@Entity`.

```java
public enum JpaEnum {
    ONE, TWO
}
```

```java

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2EnableJPA;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

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
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.lov.SeverityType;

public class TestSegment implements Segment {

    public static final String TU_P_021 = "TU-P021";

    @Position(0)
    @Error(value = TU_P_021, severity = SeverityType.FATAL) // for any error caused by this property
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
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.lov.SeverityType;

@Error(value = "UP2-100100", severity = SeverityType.WARNING) // Here is the magic
public interface Up2Payload extends Error.Payload {
}
```

## Support of JSR-303 @Constraint

Also, Up2J supports @Error on JSR-303 constraint annotations:

```java

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.lov.SeverityType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static io.github.up2jakarta.lov.SeverityType.FATAL;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = Up2NotEmptyValidator.class)
@Error(value = "UP2-900999", severity = FATAL) // Here the magic
public @interface Up2NotEmpty {

    String message() default "{jakarta.validation.constraints.NotEmpty.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
```

## @jakarta.validation.Valid

Enables JSR-303 validation

```java

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@Valid
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
- On segment classes, the validation can be enabled by super-segment classes, the first super-class annotated with
  `@ValidOverride` or `@Valid` will be considered.

```java

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import jakarta.validation.constraints.Size;

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

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Truncated;

@Truncated(4) // The first 4 columns are truncated
public class MySegment implements Segment {
    // ...
}
```

# Support of JPA AccessMode

> :information_source: In the case of multiple `@Access` are presents, the precedence is as follows:
> - The annotation on top segment-level (inherited class) always takes precedence over segment-level (super class).
> - The annotation on property-level always takes precedence over segment-level.
> - The default access is based on fields not getters and setters.

## Field based access

```java

import io.github.up2jakarta.csv.cfg.Position;
import jakarta.persistence.Access;

import static jakarta.persistence.AccessType.FIELD;

@Access(FIELD) // Default access
public class MyBean implements Segment {

    @Position(0)
    private String code;
    // ... other fields

    // No getters neither setters

}
```

## Property based access

```java

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;

import static jakarta.persistence.AccessType.PROPERTY;

@Access(PROPERTY)
public class MyBean implements Segment {

    @Position(1)
    protected String code;
    // ... other properties

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
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;

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
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;

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
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Creator;
import io.github.up2jakarta.csv.cfg.Position;

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
ie put all non-final properties in separate fragment

```java
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Creator;
import io.github.up2jakarta.csv.cfg.Position;

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

Another solution, the use of `Wrapper` to wrap each modifiable property,
> :new: The wrapper supports the JSR-303 validation, free to use annotation on Wrapper type-argument.

```java
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Creator;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.lov.core.Wrapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import static jakarta.persistence.AccessType.FIELD;

@Valid
@Access(FIELD) // avoid conflict of getNote return-type
public final class MySegment implements Segment {

    private final @Position(0) String code;
    private final @Position(1) String label;
    private final @Position(2) Wrapper<@NotBlank String> note; // modifiable in the face of final modifier
    // ... Other properties

    @Creator
    private Final1Segment(String code, String label, String note) {
        this.code = code;
        this.label = label;
        this.note = new Wrapper<>(note);
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

- [IType](./src/main/java/io/github/up2jakarta/csv/api/IType.java): Segment definition for import/export processing.
- [ILinker](./src/main/java/io/github/up2jakarta/csv/api/ILinker.java): Bean linker for aggregation/segregation processing.
- [IEvent](./src/main/java/io/github/up2jakarta/csv/api/IEvent.java): Input event representation (model) for errors or exceptions.
- [IRecord](./src/main/java/io/github/up2jakarta/csv/api/IRecord.java): Input record representation (model)

See [Sample implementations here](./src/test/java/io/github/up2jakarta/test/impl)

## Data Definition and Resolver API

## Contract

- [ITerm](./src/main/java/io/github/up2jakarta/csv/data/ITerm.java) base interface
- [TermResolver](./src/main/java/io/github/up2jakarta/csv/data/TermResolver.java) base resolver

## Simple implementation

- [@Header](./src/main/java/io/github/up2jakarta/csv/data/Header.java) annotation based definition of business terms
- [TermResolver.header()](./src/main/java/io/github/up2jakarta/csv/data/TermResolver.java) for `@Header`
- [TermResolver.empty()](./src/main/java/io/github/up2jakarta/csv/data/TermResolver.java) NoOP implementation

# Format API

- [BusinessObject](./src/main/java/io/github/up2jakarta/csv/BusinessObject.java): Segment definition for business-objects.
- [BusinessLink](./src/main/java/io/github/up2jakarta/csv/BusinessLink.java): Link definition for segment's relationship.
- [BusinessId](./src/main/java/io/github/up2jakarta/csv/BusinessId.java): Identifier definition for segment.
- [ReferenceId](./src/main/java/io/github/up2jakarta/csv/ReferenceId.java): Identifier definition for segment's relationship.

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

1. See [BusinessFullTests](src/test/java/io/github/up2jakarta/test/fmt/BusinessFullTests.java) for `FULL` mode.
2. See [BusinessFastTests](src/test/java/io/github/up2jakarta/test/fmt/BusinessFastTests.java) for `FAST` mode.
3. See [BusinessUnitTests](src/test/java/io/github/up2jakarta/test/fmt/BusinessUnitTests.java) for `UNIT` mode.

# Stream API (Batch processing)

- [BusinessReader.java](src/main/java/io/github/up2jakarta/csv/core/BusinessReader.java) for stream inputs
- [BusinessWriter.java](src/main/java/io/github/up2jakarta/csv/core/BusinessWriter.java) for stream outputs

See [up2csv-format](../up2csv-format/README.md) for CSV files implementation.

# Best practices

- Define your mapper as singleton to avoid scanning javaBeans every time.
- If the javaBeans are manipulated by Bytecode-Enhancement you must provide Cache API and sync operations.
- Use of `CodeList` because it is compatible with both JPA `AttributeConverter` and `XmlAdapter`
