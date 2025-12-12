module up2jakarta.test {
    requires up2jakarta.csv.format;
    requires org.junit.jupiter.api;
    requires up2jakarta.lov.core;
    requires jakarta.validation;
    requires org.opentest4j;
    requires spring.context;
    requires spring.beans;
    requires spring.test;
    requires spring.core;

    exports io.github.up2jakarta.test.impl to spring.beans;
    opens io.github.up2jakarta.test.dto to up2jakarta.csv.core;
    opens io.github.up2jakarta.test.impl to up2jakarta.lov.core;
    opens io.github.up2jakarta.test;
}