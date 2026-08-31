package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.core.BSOperator.XMode;

/**
 * Multi-Segment Input Mode for meta-data columns.
 */
public sealed interface IMode permits ModeType, XMode {

    /**
     * Extended mode for {@link ModeType#MESS} with an extra-data
     * {@link io.github.up2jakarta.csv.api.IFullRecord#getReference()} at offset {@code 0}.
     * <p>
     * The record-key could be used to tracks the input source, useful for debugging purpose.
     *
     * @see io.github.up2jakarta.csv.api.Recordable
     * @see io.github.up2jakarta.csv.data.FullExporter
     * @see io.github.up2jakarta.csv.data.FullImporter
     */
    IMode FULL = XMode.of(1);

    /**
     * @return the index of discriminator
     * @see io.github.up2jakarta.csv.api.IRecord#getType()
     */
    int getIndex();

    /**
     * Returns the minimum of {@link io.github.up2jakarta.csv.cfg.Truncated#value()} offset
     * because it is reserved as the index of {@link io.github.up2jakarta.csv.api.IMessRecord#getPivot()}
     * for {@link ModeType#MESS} mode and these derivatives
     *
     * @return the minimum of offset.
     */
    int getOffset();

    /**
     * @return the length of meta-data
     */
    int getLength();

}
