module up2jakarta.csv.format {
    requires transitive org.apache.commons.csv;
    requires transitive up2jakarta.csv.core;
    requires up2jakarta.lov.core;

    exports io.github.up2jakarta.csv.io;
}