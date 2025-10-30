package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.ext.PropertyConverter;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.data.DataType;
import jakarta.validation.constraints.NotNull;

import static io.github.up2jakarta.csv.core.hdl.FastHandler.of;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;

/**
 * Property Processor
 */
@FunctionalInterface
public interface PProcessor<D extends DataType<D>> {

    default <V> V defaultValue(PProperty<V, D> pp, PropertyConverter<V> pc) throws BeanException {
        try {
            var v = this.process(null, 0, pp, of(ERROR));
            if (v != null) {
                return pc.apply(v);
            }
            return null;
        } catch (Exception ex) {
            throw BeanException.of(pp.getSource(), "@Position[defaultValue] cannot be parsed");
        }
    }

    String process(@NotNull String value, int offset, PProperty<?, D> property, EventHandler<?, D, ?> handler);

}
