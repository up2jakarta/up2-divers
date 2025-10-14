package io.github.up2jakarta.csv.core.misc.map.oneshot;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

@SuppressWarnings("unused")
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

    public void setKey(K key) {
        this.key = key;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
