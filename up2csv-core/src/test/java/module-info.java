module up2jakarta.test {
    requires org.hibernate.validator;
    requires org.junit.jupiter.api;
    requires up2jakarta.lov.core;
    requires up2jakarta.csv.core;
    requires jakarta.persistence;
    requires jakarta.validation;
    requires jakarta.xml.bind;
    requires org.opentest4j;
    requires spring.context;
    requires spring.beans;
    requires spring.test;
    /* Spring */
    exports io.github.up2jakarta.test.impl to spring.beans;
    exports io.github.up2jakarta.test.fmt.sln to spring.beans;
    exports io.github.up2jakarta.test.impl.sln to spring.beans;
    exports io.github.up2jakarta.test.core.misc.ext to spring.beans;
    exports io.github.up2jakarta.test.core.misc.cvr to spring.beans;
    exports io.github.up2jakarta.test.core.misc.lov to spring.beans;
    /* LOV */
    opens io.github.up2jakarta.test.impl to up2jakarta.lov.core;
    opens io.github.up2jakarta.test.core.misc.lov to up2jakarta.lov.core;
    /* CSV */
    opens io.github.up2jakarta.test.core.misc to up2jakarta.csv.core;
    opens io.github.up2jakarta.test.core.misc.ext to up2jakarta.csv.core;
    opens io.github.up2jakarta.test.core.misc.map to up2jakarta.csv.core;
    opens io.github.up2jakarta.test.core.misc.xml to up2jakarta.csv.core;
    opens io.github.up2jakarta.test.core.misc.prc to up2jakarta.csv.core;
    opens io.github.up2jakarta.test.core.misc.map.oneshot to up2jakarta.csv.core;
    opens io.github.up2jakarta.test.core.misc.jpa.checker to up2jakarta.csv.core;
    opens io.github.up2jakarta.test.core.misc.jpa.checker.base to up2jakarta.csv.core;
    /* HV */
    opens io.github.up2jakarta.test.core.bs to up2jakarta.csv.core, org.hibernate.validator;
    opens io.github.up2jakarta.test.impl.dto to up2jakarta.csv.core, org.hibernate.validator;
    opens io.github.up2jakarta.test.fmt.tree to up2jakarta.csv.core, org.hibernate.validator;
    opens io.github.up2jakarta.test.core.misc.acs to up2jakarta.csv.core, org.hibernate.validator;
    opens io.github.up2jakarta.test.core.misc.jpa to up2jakarta.csv.core, org.hibernate.validator;
    opens io.github.up2jakarta.test.core.misc.vld to up2jakarta.csv.core, org.hibernate.validator;
    opens io.github.up2jakarta.test.core.misc.cvr to up2jakarta.csv.core, org.hibernate.validator;
    /* TU */
    opens io.github.up2jakarta.test;
    opens io.github.up2jakarta.test.fmt.misc to up2jakarta.csv.core, org.junit.platform.commons;
    opens io.github.up2jakarta.test.core to up2jakarta.csv.core, org.junit.platform.commons;
    opens io.github.up2jakarta.test.fmt to org.junit.platform.commons;
}