# Up2CSV :: Core Framework

`Up2CSV` is an open-source, light and modern framework that maps and validates easily flat-data to javaBeans.

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.bytebuddy/byte-buddy/badge.svg?style=for-the-badge&version=1.1.0)](https://central.sonatype.com/artifact/io.github.up2jakarta/up2csv-core)

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
    - Converter API (Coming soon ...)
    - Extension API (Coming soon ...)
    - Context API (Coming soon ...)
    - and more ...

# Requirements

- Java 17

# Dependencies

``` xml
    <dependency>
        <groupId>io.github.up2jakarta.divers</groupId>
        <artifactId>up2csv-core</artifactId>
        <version>1.1.0</version>
    </dependency>
    <!-- Optional SLF4J Provider -->
    <!-- Optional JSR-303 Validation Provider -->
    <!-- Optional IoC Provider -->
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

Up2 Processor API is configurable and reusable

``` java
public Up2Segment implements Segment {
    
    @Position(0)
    @Processor(value = TrimTransformer.class, arguments = {"", "-", "null", "undefined"})
    private String code;
}
```

Up2 Core comes with 3 built-in shortcut annotations:

### @Default

Setting the default value

``` java
public Up2Segment implements Segment {

    @Position(0)
    @Default("null")
    private String code;
}
```

### Token

``` java
public Up2Segment implements Segment {

    @Position(0)
    @Token
    private String code;
}
```

### Trim

``` java
public Up2Segment implements Segment {

    @Position(0)
    @Trim({"", "-", "null", "undefined"}) // Order 1
    @Token // Order 2
    @Default("UP2") // Order 3
    private String code;
}
```

# Validation

## @Severity

Helps the engine to consider

``` java
public TestSegment implements Segment {

    @Position(0)
    @Severity(FATAL) // any error caused by this property will be considered as a fatal error
    private String code;

}
```

You can also use the JSR-303 validation `Payload`

- SeverityType.Level.Fatal.class
- SeverityType.Level.Error.class
- SeverityType.Level.Warning.class

## @jakarta.validation.Valid

Enables JSR-303 validation

``` java
@jakarta.validation.Valid
public TestSegment implements Segment {

    @Position(0)
    @Size(min = 1, max = 3, payload = SeverityType.Level.Fatal.class)
    private String code;

}
```

## @Validated

Enables JSR-303 validation within specific groups

``` java
@Validated(groups =  Group.Mapping.class)
public TestSegment implements Segment {

    @Position(0)
    @Size(min = 1, max = 3, payload = Level.Fatal.class, groups = Group.Mapping.class)
    @Size(min = 1, max = 2, payload = Level.Error.class) // Default
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

See the package [persistence](./src/main/java/io/github/up2jakarta/csv/persistence)

- InputError
- InputRepository
- InputRow
- InputType

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
{

    @Bean
    @Scope(SCOPE_SINGLETON)
    public CollapsedStringAdapter tokenAdapter() {
      return new CollapsedStringAdapter(); // required for @Token
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    public TokenTransformer tokenTransformer(CollapsedStringAdapter tokenAdapter) {
        return new TokenTransformer(tokenAdapter); // required for @Token
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    public TrimTransformer trimTransformer() {
        return new TrimTransformer(); // required for @Trim
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    public DefaultValueTransformer defaultValueTransformer() {
        return new DefaultValueTransformer(); // required for @Default
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    public Validator validator() {
        return CSV.validator(messageInterpolator()); // required for MapperFactory
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    public BeanContext beanContext(final ApplicationContext context) {
        return context::getBean; // required for MapperFactory
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    public MapperFactory mapperFactory(final BeanContext context, Validator validator) {
        return new MapperFactory(context, validator); // Here we go
    }
    
    // Custom implementations of Persistence API

    @Bean
    @Scope(SCOPE_SINGLETON)
    public InputRepository<InputRowEntity> inputRepository() {
        return (r) -> 0; // No errors already saved for input rows
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    public EventCreator<InputRowImpl, InputErrorKeyImpl, InputErrorImpl> eventCreator() {
        return new CompositeKeyCreator<>(InputErrorImpl::new, InputErrorKeyImpl::new); // required for EventHandler
    }

    // ... Define custom Transformers
}
```

# Best practices

- Define your mapper as singleton to avoid scanning javaBeans always
- If the javaBeans are manipulated by Bytecode-Enhancement you must provide jakarta.persistence.Cache and sync
  operations.
- By design, transformers (used by @Processor or its shortcuts) must be singleton also.
