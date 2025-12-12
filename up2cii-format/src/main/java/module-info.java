module up2jakarta.cii.core {
    requires org.glassfish.jaxb.runtime;
    requires up2jakarta.lov.core;
    requires up2jakarta.xml.core;
    requires jakarta.inject;
    requires java.compiler;
    requires java.xml;

    exports io.github.up2jakarta.cii;
    exports io.github.up2jakarta.cii.core;
    exports io.github.up2jakarta.cii.edi;
    exports io.github.up2jakarta.cii.ppf;
    exports io.github.up2jakarta.cii.edi.adapters;
    exports io.github.up2jakarta.cii.ppf.adapters;
    exports io.github.up2jakarta.cii.format.minified;
    exports io.github.up2jakarta.cii.format.minified.qdt;
    exports io.github.up2jakarta.cii.format.minified.udt;
    exports io.github.up2jakarta.cii.format.minified.ram;
    exports io.github.up2jakarta.cii.format.standard;
    exports io.github.up2jakarta.cii.format.standard.qdt;
    exports io.github.up2jakarta.cii.format.standard.udt;
    exports io.github.up2jakarta.cii.format.standard.ram;
    exports io.github.up2jakarta.cii.format.unmapped;
    exports io.github.up2jakarta.cii.format.unmapped.qdt;
    exports io.github.up2jakarta.cii.format.unmapped.udt;
    exports io.github.up2jakarta.cii.format.unmapped.ram;

    opens io.github.up2jakarta.cii.format;
    opens io.github.up2jakarta.cii.format.minified;
    opens io.github.up2jakarta.cii.format.minified.qdt;
    opens io.github.up2jakarta.cii.format.minified.udt;
    opens io.github.up2jakarta.cii.format.minified.ram;
    opens io.github.up2jakarta.cii.format.standard;
    opens io.github.up2jakarta.cii.format.standard.qdt;
    opens io.github.up2jakarta.cii.format.standard.udt;
    opens io.github.up2jakarta.cii.format.standard.ram;
    opens io.github.up2jakarta.cii.format.unmapped;
    opens io.github.up2jakarta.cii.format.unmapped.qdt;
    opens io.github.up2jakarta.cii.format.unmapped.udt;
    opens io.github.up2jakarta.cii.format.unmapped.ram;

    opens CII_D16B.uncefact.identifierlist.standard;
    opens CII_D16B.uncefact.codelist.standard;
    opens CII_D16B.uncefact.data.standard;
}