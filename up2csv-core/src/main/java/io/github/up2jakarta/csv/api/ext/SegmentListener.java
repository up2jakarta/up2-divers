package io.github.up2jakarta.csv.api.ext;

import io.github.up2jakarta.csv.core.AccessMode;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.data.Segment;

/**
 * Contact interface for checker listener for annotated {@link Segment}.
 *
 * @see io.github.up2jakarta.csv.cfg.Checker
 */
public interface SegmentListener {

    /**
     * Checks if the checker is activated.
     *
     * @param type the segment type
     * @return <code>true</code> if it should activate the checking.
     */
    default boolean isActivated(Class<? extends Segment> type) {
        return true;
    }

    /**
     * Listener callback before scanning the <code>segment</code>.
     *
     * @param type the segment type
     * @param mode the access mode read/write only
     * @return the checker context for the given <code>segment</code>.
     * @throws BeanException for any missing or wrong bean configuration
     */
    CheckerContext beforeSegment(AccessMode mode, Class<? extends Segment> type) throws BeanException;

    /**
     * Listener callback after the completion of scanning the segment.
     *
     * @param context the checker context
     * @throws BeanException for any missing or wrong bean configuration
     */
    default void afterSegment(CheckerContext context) throws BeanException {
        context.close();
    }

}
