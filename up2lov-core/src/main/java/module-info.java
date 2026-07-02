module up2jakarta.lov.core {
    requires jakarta.persistence;
    requires jakarta.xml.bind;
    requires java.sql;

    exports io.github.up2jakarta.lov;
    exports io.github.up2jakarta.lov.core;
    exports io.github.up2jakarta.lov.bst;
}