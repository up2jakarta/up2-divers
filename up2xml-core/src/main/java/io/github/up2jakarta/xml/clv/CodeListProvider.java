package io.github.up2jakarta.xml.clv;

/**
 * List of values provider that's supplies the all possibles values for the specified code-list.
 *
 * @param <T> the code-list values
 */
@FunctionalInterface
public interface CodeListProvider<T extends CodeList<?>> {

    /**
     * Returns all possibles values for the specified code-list <code>type</code>.
     *
     * @param type the code-list type
     * @return the array of values
     */
    T[] values(Class<T> type);

}
