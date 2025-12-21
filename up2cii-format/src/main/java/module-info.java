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

    opens io.github.up2jakarta.cii.format.minified to jakarta.xml.bind, org.glassfish.jaxb.runtime;
    opens io.github.up2jakarta.cii.format.standard to jakarta.xml.bind, org.glassfish.jaxb.runtime;
    opens io.github.up2jakarta.cii.format.unmapped to jakarta.xml.bind, org.glassfish.jaxb.runtime;
    opens io.github.up2jakarta.cii.format.minified.qdt to org.glassfish.jaxb.runtime;
    opens io.github.up2jakarta.cii.format.minified.udt to org.glassfish.jaxb.runtime;
    opens io.github.up2jakarta.cii.format.minified.ram to org.glassfish.jaxb.runtime;
    opens io.github.up2jakarta.cii.format.standard.qdt to org.glassfish.jaxb.runtime;
    opens io.github.up2jakarta.cii.format.standard.udt to org.glassfish.jaxb.runtime;
    opens io.github.up2jakarta.cii.format.standard.ram to org.glassfish.jaxb.runtime;
    opens io.github.up2jakarta.cii.format.unmapped.qdt to org.glassfish.jaxb.runtime;
    opens io.github.up2jakarta.cii.format.unmapped.udt to org.glassfish.jaxb.runtime;
    opens io.github.up2jakarta.cii.format.unmapped.ram to org.glassfish.jaxb.runtime;
    opens io.github.up2jakarta.cii.format to org.glassfish.jaxb.runtime;

    opens CII_D16B.uncefact.identifierlist.standard;
    opens CII_D16B.uncefact.codelist.standard;
    opens CII_D16B.uncefact.data.standard;
}