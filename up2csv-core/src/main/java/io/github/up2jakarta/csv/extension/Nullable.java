package io.github.up2jakarta.csv.extension;

/**
 * Contact reference to check {@link Segment} is like <code>null</code> object.
 */
public interface Nullable extends Segment {

    /**
     * Checks if {@link Segment} is like <code>null</code>.
     * If the segment is nullable, it will be validated and then will be set to <code>nul</code> into the output result.
     *
     * @return if {@link Segment} is not like <code>null</code>
     */
    boolean isNotNull();

}
