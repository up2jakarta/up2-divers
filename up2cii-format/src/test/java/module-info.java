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

    opens io.github.up2jakarta.test.format.minified to spring.core, spring.beans, spring.context, org.junit.platform.commons;
    opens io.github.up2jakarta.test.api to spring.core, spring.beans, org.junit.platform.commons;
    opens io.github.up2jakarta.test.format.standard to spring.core, org.junit.platform.commons;
    opens io.github.up2jakarta.test.format.unmapped to spring.core, org.junit.platform.commons;
    opens io.github.up2jakarta.test.cl to spring.core, org.junit.platform.commons;
    opens io.github.up2jakarta.test to spring.core, spring.beans, spring.context;
    opens io.github.up2jakarta.test.xml to org.junit.platform.commons;
    opens io.github.up2jakarta.test.ppf to org.junit.platform.commons;
    opens io.github.up2jakarta.test.edi to org.junit.platform.commons;

    opens xml.unece;
    opens xml.ppf;
    opens xml;
}