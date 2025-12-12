package io.github.up2jakarta.test.core.misc.map.oneshot;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

public abstract class PersonSegment<K extends Comparable<K>> implements Segment {

    @Position(0)
    private K key;

    @Position(1)
    private String firstName;

    @Position(2)
    private String lastName;

    public K getKey() {
        return key;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
