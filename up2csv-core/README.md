# Up2CSV :: Core Framework

`Up2CSV` is an open-source, light and modern framework that maps and validates easily flat-data to javaBeans.

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.bytebuddy/byte-buddy/badge.svg?style=for-the-badge&version=1.3.0)](https://central.sonatype.com/artifact/io.github.up2jakarta/up2csv-core)

# Features

- Fault tolerance
- Error management API
- Support of JSR-303 validation
- Support of JPA (Java Persistence API)
- Support of JAXB (Java Architecture for XML Binding)
- Support of IoC container like CDI (Contexts and Dependency Injection) provider or Spring or whatever
- Configuration based on @Annotation
- Support of Java OOP (Object-Oriented Programming)
- Extensions
    - Processor API
    - Conversion Resolver API
    - Error API
    - Input API
    - Conversion Extension API
    - Bean Checker API (Coming soon)

# Requirements

- Java 17

# Dependencies

``` xml
    <dependency>
        <groupId>io.github.up2jakarta</groupId>
        <artifactId>up2csv-core</artifactId>
        <version>1.3.0</version>
    </dependency>
    <!-- Optional SLF4J Provider -->
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
@Error(value = "UP2-000100", severity = SeverityType.ERROR)
public interface CustomPayload extends Error.Payload {
}
```

## Support of JSR-303 @Constraint

Also, Up2 supports @Error on JSR-303 constraint annotations:

``` java
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CustomValidator.class})
@Error(value = "UP2-000100", severity = SeverityType.FATAL)
@SuppressWarnings("unused")
public @interface CustomConstraint {

    String message() default "{jakarta.validation.constraints.CustomConstraint.message}";

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

## @Validated

Enables JSR-303 validation within specific groups

``` java
@Validated(groups =  CustomGroup.class)
public TestSegment implements Segment {

    @Position(0)
    @Size(min = 1, max = 3, payload = Errors.Fatal.class, groups = CustomGroup.class)
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

- InputError
- InputRepository
- InputRow
- InputType

### Sample implementations in [io.github.up2jakarta.csv.test.input](./src/test/java/io/github/up2jakarta/csv/test/input)

- InputRowEntity implements InputRow
- InputErrorEntity implements InputError<InputRowEntity, InputErrorEntity.PKey>
- SimpleErrorEntity implements InputError<InputRowEntity, SimpleErrorEntity>, InputError.Key<InputRowEntity>

# Use of Up2CSV

- Without Validation

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

- Within Validation

``` java
@Inject
private MapperFactory factory;

@Inject
private InputRepository<InputRowImpl> repository;

@Inject
private EventCreator<InputRowImpl, InputErrorKeyImpl, InputErrorImpl> creator;

{
    // GIVEN Singletons
    final Mapper<Up2Segment> mapper = factory.build(Up2Segment.class);
    // GIVEN Prototypes
    final InputRowImpl row ; // ... retrive it from repository or create new one
    final EventHandler<InputRowImpl, InputErrorKeyImpl, InputErrorImpl> handler = new EventHandler<>(row, creator, repository) ;
    // WHEN
    final Up2Segment bean = mapper.map(row, handler);
    final List<InputErrorImpl> errors = handler.toList();
    // THEN
    // Here the bean is full-filled automatically
    // Here the errors is full-filled automatically 
    // ... Persistence or ETL or whateven processing
}
```

# Configuration

``` java
@Configuration
@ComponentScan(basePackageClasses = {MapperFactory.class, TokenProcessor.class, DecimalResolver.class}) // mandatory scans
public class TUConfiguration {

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public CollapsedStringAdapter tokenAdapter() {
        return new CollapsedStringAdapter(); // for @Up2Token
    }

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public Validator validator() {
        return CSV.validator(messageInterpolator());
    }

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public BeanContext beanContext(final ApplicationContext context) {
        return context::getBean;
    }

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public InputRepository<InputRowEntity> inputRepository() {
        return r -> 0; // Simplified
    }

    @Bean
    @Scope(value = SCOPE_SINGLETON)
    public CompositeKeyCreator<InputRowImpl, InputErrorKeyImpl, InputErrorImpl> compositeKeyCreator() {
        return new CompositeKeyCreator<>(InputErrorImpl::new, InputErrorKeyImpl::new);
    }

    // ... Define custom processors
}
```

# Best practices

- Define your mapper as singleton to avoid scanning javaBeans always
- If the javaBeans are manipulated by Bytecode-Enhancement you must provide jakarta.persistence.Cache and sync
  operations.
- Use of `CodeList` because it is compatible with both JPA `AttributeConverter` and `XmlAdapter`
