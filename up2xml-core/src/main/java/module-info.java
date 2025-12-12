module up2jakarta.xml.core {
    requires up2jakarta.lov.core;
    requires jakarta.xml.bind;
    requires java.xml;

    exports io.github.up2jakarta.xml;
    exports io.github.up2jakarta.xml.api;
    exports io.github.up2jakarta.xml.adapters;
}