module up2jakarta.test {
    requires spring.batch.infrastructure;
    requires spring.boot.autoconfigure;
    requires org.hibernate.validator;
    requires org.junit.jupiter.api;
    requires up2jakarta.csv.format;
    requires up2jakarta.job.core;
    requires up2jakarta.lov.core;
    requires jakarta.validation;
    requires spring.batch.core;
    requires spring.batch.test;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.test;
    requires spring.jdbc;
    requires spring.tx;
    requires org.slf4j;
    requires java.sql;

    exports io.github.up2jakarta.test.dto;
    exports io.github.up2jakarta.test.impl;

    exports io.github.up2jakarta.test.impl.fast to spring.beans;
    exports io.github.up2jakarta.test.impl.full to spring.beans;
    exports io.github.up2jakarta.test.impl.unit to spring.beans;

    opens io.github.up2jakarta.test.impl to up2jakarta.lov.core;
    opens io.github.up2jakarta.test.dto to up2jakarta.csv.core, org.hibernate.validator;
    opens io.github.up2jakarta.test to spring.core, spring.beans, spring.context, org.junit.platform.commons;
}