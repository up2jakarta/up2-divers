package io.github.up2jakarta.csv.test.bean.mapper.oneshot;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.extension.Segment;

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
