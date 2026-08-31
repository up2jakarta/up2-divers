package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.lov.CodeList;

/**
 * Contact interface for segment type definition, used to segregate business-objects to multiple segments in one-shot.
 *
 * @param <I> self-type implementation
 * @see io.github.up2jakarta.csv.core.BusinessExporter
 * @see io.github.up2jakarta.csv.data.BusinessWriter
 * @see io.github.up2jakarta.csv.BusinessObject
 * @see io.github.up2jakarta.csv.BusinessLink
 * @see io.github.up2jakarta.csv.ReferenceId
 */
public interface IType<I extends Enum<I> & IType<I>> extends CodeList<I> {

}
