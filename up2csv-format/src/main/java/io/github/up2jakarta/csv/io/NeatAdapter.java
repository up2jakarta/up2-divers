package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.api.hdl.IPropertyCreator;
import io.github.up2jakarta.csv.core.NeatExporter;
import io.github.up2jakarta.csv.core.NeatImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.NeatRecord;
import io.github.up2jakarta.csv.hdl.PropertyEvent;
import io.github.up2jakarta.csv.hdl.PropertyFailureCollector.Builder;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.TypeException;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static org.apache.commons.csv.CSVFormat.RFC4180;
import static org.apache.commons.csv.QuoteMode.MINIMAL;

/**
 * Business Object Adapter can serialize and deserialize business objects based on {@code CSV} format.
 * <p>
 * This {@link TypeAdapter} could be used as an alternative of {@code XML}, {@code JSON} and java serialization,
 * it's more compact than other formats also it is type-safe (based on declaration not on runtime discovery).
 *
 * @param <T> the business object type
 */
public class NeatAdapter<T extends Segment> implements TypeAdapter<T> {

    /**
     * Constant holding the record separator
     */
    public static final char SEP = '\n';
    /**
     * Constant holding the default delimiter
     */
    public static final char DEL = '\t';

    private static final CSVFormat TSV = RFC4180.builder()
            .setQuote('"')
            .setTrim(false)
            .setNullString("")
            .setDelimiter(DEL)
            .setQuoteMode(MINIMAL)
            .setRecordSeparator(SEP)
            .setIgnoreEmptyLines(true)
            .setTrailingDelimiter(false)
            .setIgnoreSurroundingSpaces(false)
            .get();

    private final Serializer<?, ?, T> holder;
    private final Class<T> type;

    /**
     * Constructor for {@link io.github.up2jakarta.csv.core.ModeType#NEAT} adapter.
     *
     * @param factory the segment factory
     * @param type    the business object type
     * @param it      the business input type
     * @param <I>     the input segment type
     * @throws BeanException for any missing or wrong bean configuration
     */
    public <I extends Enum<I> & IType<I>> NeatAdapter(Up2Factory<?> factory, Class<T> type, Class<I> it) throws BeanException {
        this.holder = new Serializer<>(factory, TSV, type, it, ERROR);
        this.type = type;
    }

    /**
     * Constructor for {@link io.github.up2jakarta.csv.core.ModeType#NEAT} adapter.
     *
     * @param factory   the segment factory
     * @param delimiter the delimiter character
     * @param type      the business object type
     * @param it        the business input type
     * @param <I>       the input segment type
     * @throws BeanException for any missing or wrong bean configuration
     */
    public <I extends Enum<I> & IType<I>> NeatAdapter(Up2Factory<?> factory, char delimiter, Class<T> type, Class<I> it) throws BeanException {
        this.holder = new Serializer<>(factory, TSV.builder().setDelimiter(delimiter).get(), type, it, ERROR);
        this.type = type;
    }

    @Override
    public final Class<T> getType() {
        return type;
    }

    @Override
    public final T parse(String value) throws TypeException {
        if (value != null) {
            try {
                return holder.serialize(value);
            } catch (IOException cause) {
                throw new RuntimeException(cause);
            }
        }
        return null;
    }

    @Override
    public final String format(T value) throws TypeException {
        if (value != null) {
            try {
                return holder.deserialize(value);
            } catch (IOException cause) {
                throw new RuntimeException(cause);
            }
        }
        return null;
    }

    /**
     * Internal {@link io.github.up2jakarta.csv.core.ModeType#NEAT} Serializer with fault-tolerance principle.
     */
    private static class Serializer<B extends ITerm<B>, I extends Enum<I> & IType<I>, T extends Segment> extends NeatImporter<B, I, T, NeatRecord<I>, PropertyEvent<B, NeatRecord<I>>> {
        private final NeatExporter<B, I, T> exporter;
        private final SeverityType severityLevel;
        private final CSVFormat format;

        private Serializer(Up2Factory<B> factory, CSVFormat format, Class<T> type, Class<I> it, SeverityType level) throws BeanException {
            super(factory, type, it);
            this.severityLevel = level;
            this.exporter = this.toExporter();
            this.format = AccessException.notNull(format, NeatAdapter.class, "format");
        }

        @Override
        protected Builder<B, NeatRecord<I>, PropertyEvent<B, NeatRecord<I>>> newBuilder(int length) {
            final IPropertyCreator<B, NeatRecord<I>, PropertyEvent<B, NeatRecord<I>>> creator = PropertyEvent::new;
            return new Builder<>(length, creator, severityLevel);
        }

        private T serialize(String value) throws IOException {
            final List<NeatRecord<I>> records = new LinkedList<>();
            try (final CSVParser parser = format.parse(new StringReader(value))) {
                for (final CSVRecord record : parser) {
                    records.addLast(super.transform(NeatRecord::new, record.values()));
                }
            }
            return super.parse(records, (o, l) -> o);
        }

        private String deserialize(T value) throws IOException {
            final StringBuilder builder = new StringBuilder();
            this.exporter.format(value, d -> {
                format.println(builder);
                format.print(d[0], builder, true);
                for (var i = 1; i < d.length; i++) {
                    format.print(d[i], builder, false);
                }
            });
            return builder.substring(1);
        }
    }
}
