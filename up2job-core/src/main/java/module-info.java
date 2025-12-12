module up2jakarta.job.core {
    requires spring.batch.infrastructure;
    requires up2jakarta.lov.core;
    requires up2jakarta.xml.core;
    requires jakarta.persistence;
    requires spring.batch.core;
    requires spring.tx;
    requires org.slf4j;
    requires java.xml;

    exports io.github.up2jakarta.job;
    exports io.github.up2jakarta.job.ctx;
    exports io.github.up2jakarta.job.zip;
    exports io.github.up2jakarta.job.core;
    exports io.github.up2jakarta.job.flux;
}