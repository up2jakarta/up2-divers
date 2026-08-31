package io.github.up2jakarta.csv.core;

/**
 * Native {@link IMode}, out-of-the-box supported modes.
 */
public enum ModeType implements IMode {

    /**
     * Includes only the segment {@code type} which used as {@code discriminator} to differentiate segments.
     *
     * @see io.github.up2jakarta.csv.api.IRecord#getType()
     * @see NeatExporter
     * @see NeatImporter
     */
    NEAT(0, 1),

    /**
     * Includes the segment {@code type} and the business identifier which used as {@code pivot} to aggregate records.
     * <p>
     * This mode is thread-safe in batch processing since inputs could be aggregated regardless of their writing order.
     *
     * @see io.github.up2jakarta.csv.api.IMessRecord#getType()
     * @see io.github.up2jakarta.csv.api.IMessRecord#getPivot()
     * @see MessExporter
     * @see MessImporter
     */
    MESS(0, 2);

    private final int index, offset, length;

    ModeType(int index, int length) {
        this.offset = index + 1;
        this.length = length;
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public int getLength() {
        return length;
    }

}
