package io.github.up2jakarta.csv.ops;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.BusinessEntry;
import io.github.up2jakarta.csv.core.Mapper;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.data.SegmentWriter;
import io.github.up2jakarta.xml.clv.CodeListException;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Supplier;

import static io.github.up2jakarta.csv.slv.CodeListResolver.checkUnique;
import static java.util.Arrays.copyOfRange;

/**
 * Internal base implementation for segregation processing only.
 */
abstract class BusinessProcessor<B extends DataType<B>, I extends IType<B, I>, T extends BusinessObject> extends BusinessMapping<B, I> {

    protected final I root;
    protected final ModeType mode;
    protected final BusinessTyping typing;

    BusinessProcessor(MapperFactory<B> factory, ModeType mode, Class<T> type, I root, I[] nodes) throws BeanException {
        super(factory, root, nodes);
        checkUnique(type, nodes);
        if (!type.equals(root.getClassType())) {
            throw new BeanException(type, "Invalid business typing");
        }
        this.mode = mode;
        this.root = root;
        this.typing = new BusinessTyping(this.getMapper(root));
    }

    private void format(Segment bean, int offset, I type, MDFiller<I> consumer) throws BeanException, IOException {
        final String[] data = this.getMapper(type).unmap(bean, offset);
        consumer.accept(bean, type, data);
        for (final I node : this.getJoins(type)) {
            final Collection<Segment> values = node.joiner().joins(bean);
            if (values == null) {
                continue;
            }
            for (var value : values) {
                this.format(value, typing.defaultOffset, node, consumer);
            }
        }
    }

    void format(T bean, Supplier<String> rowId, SegmentWriter callback) throws BeanException, IOException {
        if (bean == null) {
            return;
        }
        this.format(bean, typing.rootOffset, root, (s, t, d) -> {
            typing.filler.accept(d, rowId, t, bean);
            callback.accept(d);
        });
    }

    @FunctionalInterface
    private interface MDFiller<I extends IType<?, I>> {
        void accept(Segment source, I type, String[] data) throws IOException;
    }

    @FunctionalInterface
    private interface BPFiller {
        void accept(String[] target, Supplier<String> rowId, IType<?, ?> type, BusinessObject source);
    }

    /**
     * Internal business-object typing implementation.
     */
    public final class BusinessTyping {

        private final int rootOffset;
        private final int defaultOffset;
        private final BPFiller filler;

        private BusinessTyping(Mapper<Segment, ?> mapper) {
            this.filler = (mode.typeIdIndex == 0) ? mode::fast : mode::full;
            this.defaultOffset = mode.getLength();
            this.rootOffset = BusinessEntry.offset(mapper, mode);
        }

        /**
         * Parses and returns the segment type aka the discriminator.
         *
         * @param record the input record
         * @return the input segment-type
         * @throws CodeListException if unknown value
         */
        public I type(String... record) throws CodeListException {
            return parser.parse(record[mode.typeIdIndex]);
        }

        /**
         * Truncates and return the data columns aka without meta-data.
         *
         * @param type   the input segment-type
         * @param record the input record
         * @return the truncated data
         */
        public String[] truncate(I type, String... record) {
            final int offset = this.offset(type);
            return copyOfRange(record, offset, record.length, String[].class);
        }

        int offset(I type) {
            if (type == root) {
                return rootOffset;
            }
            return defaultOffset;
        }

    }

}
