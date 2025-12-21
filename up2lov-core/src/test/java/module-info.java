module up2jakarta.test {
    requires org.hibernate.orm.core;
    requires org.junit.jupiter.api;
    requires up2jakarta.lov.core;
    requires jakarta.persistence;
    requires jakarta.xml.bind;
    requires spring.context;
    requires spring.test;
    requires spring.jdbc;
    requires spring.beans;
    requires java.sql;

    opens io.github.up2jakarta.test to spring.core, spring.beans, spring.context, org.junit.platform.commons;
    opens io.github.up2jakarta.test.core to up2jakarta.lov.core, org.junit.platform.commons;
    opens io.github.up2jakarta.test.lov to up2jakarta.lov.core, org.hibernate.orm.core;
}