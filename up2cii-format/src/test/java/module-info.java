module up2jakarta.test {
    requires org.junit.jupiter.api;
    requires up2jakarta.cii.core;
    requires up2jakarta.xml.core;
    requires up2jakarta.lov.core;
    requires jakarta.xml.bind;
    requires org.assertj.core;
    requires org.opentest4j;
    requires jakarta.inject;
    requires spring.context;
    requires spring.beans;
    requires spring.test;

    opens io.github.up2jakarta.test.xml to org.junit.platform.commons;
    opens io.github.up2jakarta.test.ppf to org.junit.platform.commons;
    opens io.github.up2jakarta.test.edi to org.junit.platform.commons;
    opens io.github.up2jakarta.test.format.minified;
    opens io.github.up2jakarta.test.format.standard;
    opens io.github.up2jakarta.test.format.unmapped;
    opens io.github.up2jakarta.test.api;
    opens io.github.up2jakarta.test.cl;
    opens io.github.up2jakarta.test;
    opens xml.unece;
    opens xml.ppf;
    opens xml;
}