module up2jakarta.test {
    requires up2jakarta.xml.core;
    requires up2jakarta.lov.core;
    requires org.junit.jupiter.api;
    requires jakarta.xml.bind;
    requires org.opentest4j;

    opens io.github.up2jakarta.test to org.junit.platform.commons;
    opens io.github.up2jakarta.test.adapters to org.junit.platform.commons;
}