package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class JpaFragmentCheckerTest {

    private final MapperFactory factory;

    @Autowired
    JpaFragmentCheckerTest(MapperFactory factory) {
        this.factory = factory;
    }


}

