module up2jakarta.csv.core {
    requires up2jakarta.lov.core;
    requires jakarta.persistence;
    requires jakarta.validation;
    requires jakarta.xml.bind;
    requires jakarta.inject;

    exports io.github.up2jakarta.csv;
    exports io.github.up2jakarta.csv.api;
    exports io.github.up2jakarta.csv.api.ext;
    exports io.github.up2jakarta.csv.api.fct;
    exports io.github.up2jakarta.csv.api.hdl;
    exports io.github.up2jakarta.csv.cfg;
    exports io.github.up2jakarta.csv.core;
    exports io.github.up2jakarta.csv.core.ext;
    exports io.github.up2jakarta.csv.core.hdl;
    exports io.github.up2jakarta.csv.data;
    exports io.github.up2jakarta.csv.fmt;
    exports io.github.up2jakarta.csv.prc;
    exports io.github.up2jakarta.csv.slv;
}