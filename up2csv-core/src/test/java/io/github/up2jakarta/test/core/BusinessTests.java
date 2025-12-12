package io.github.up2jakarta.test.core;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.IEventBuilder;
import io.github.up2jakarta.csv.api.hdl.IPropertyCreator;
import io.github.up2jakarta.csv.core.FastImporter;
import io.github.up2jakarta.csv.core.UnitExporter;
import io.github.up2jakarta.csv.core.UnitImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.hdl.*;
import io.github.up2jakarta.csv.data.Up2Result;
import io.github.up2jakarta.csv.fmt.SimpleUnitImporter;
import io.github.up2jakarta.csv.fmt.UnitRecord;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.TypeException;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.BuilderTests.BSError;
import io.github.up2jakarta.test.BuilderTests.BSRecord;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.core.bs.CyclicInvoice;
import io.github.up2jakarta.test.core.bs.DummyAttribute;
import io.github.up2jakarta.test.core.bs.DummyReference;
import io.github.up2jakarta.test.impl.GroupType;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.dto.Invoice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static io.github.up2jakarta.lov.core.Localizable.CLASS;
import static io.github.up2jakarta.test.core.Up2ErrorTests.assertTrace;
import static io.github.up2jakarta.test.impl.SegmentType.*;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class BusinessTests {

    static final String EX_ACCESS = AccessException.class.getName();
    static final String CSV_MODULE = DummyReference.class.getModule().getName();
    static final String CSV_DUMMY_REFERENCE = CSV_MODULE + "/" + DummyReference.class.getName();

    private final Up2Factory<GroupType> factory;

    @Autowired
    BusinessTests(Up2Factory<GroupType> factory) {
        this.factory = factory;
    }

    @Test
    void testTypingException() {
        // WHEN
        final BeanException ex = assertThrows(
                BeanException.class,
                () -> factory.builder().full(Invoice.class).build(S11).build((r) -> 0)
        );
        assertNotNull(ex);
        // THEN
        assertEquals(Invoice.class, ex.getSource());
        assertEquals(CLASS, ex.getLocator());
        assertEquals("invalid business typing", ex.getMessage());
        assertEquals("Invoice[class] invalid business typing", ex.getLocalizedMessage());
    }

    @Test
    void testRecursiveException() {
        // WHEN
        final BeanException ex = assertThrows(
                BeanException.class,
                () -> factory.builder()
                        .full(CyclicInvoice.class)
                        .build(S61)
                        .build()
        );
        assertNotNull(ex);
        // THEN
        assertEquals(SegmentType.class, ex.getSource());
        assertEquals("63", ex.getLocator());
        assertEquals("cyclic segment is not allowed: CyclicInvoice > CyclicItem > CyclicInvoice", ex.getMessage());
    }

    @Test
    void testNullRecord() throws BeanException {
        // GIVEN
        final UnitImporter<GroupType, SegmentType, DummyReference, BSRecord, BSError> importer = factory.builder()
                .unit(DummyReference.class)
                .build(S71)
                .build(BSError::new);
        // WHEN
        final Up2Result<DummyReference, ?> result = importer.parse(singletonList(null));
        //THEN
        assertNotNull(result);
        assertNull(result.get());
        assertEquals(0, result.toList().size());
    }

    @Test
    void testNullType() throws BeanException {
        // GIVEN
        final UnitImporter<GroupType, SegmentType, DummyReference, MyRecord, MyError> importer = factory.builder()
                .unit(DummyReference.class)
                .build(S71)
                .build(MyError::new);
        final MyRecord record = new MyRecord(null, "*");
        // WHEN
        final Up2Result<DummyReference, MyError> result = importer.parse(singletonList(record));
        //THEN
        assertNotNull(result);
        assertNull(result.get());
        assertEquals(1, result.toList().size());
        final MyError error = result.toList().getFirst();
        assertSame(record, error.getRecord());
        assertEquals(0, error.getOffset());
        assertEquals(SeverityType.ERROR, error.getLevel());
        assertEquals(IEvent.EC_COMPLIANCE, error.getCode());
        assertNull(error.getType());
        assertEquals("must not be null", error.getMessage());
    }

    @Test
    void testNullData() throws BeanException {
        // GIVEN
        final UnitImporter<GroupType, SegmentType, DummyReference, MyRecord, MyError> importer = factory.builder()
                .unit(DummyReference.class)
                .build(S71)
                .build(MyError::new);
        final MyRecord record = new MyRecord(S71, (String[]) null);
        // WHEN
        final Up2Result<DummyReference, MyError> result = importer.parse(singletonList(record));
        //THEN
        assertNotNull(result);
        assertNotNull(result.get());
        assertEquals(0, result.toList().size());
        assertNull(result.get().getReference());
        assertEquals(0, result.get().getAttributes().size());
    }

    @Test
    void testNullBuilder() throws BeanException {
        // GIVEN
        final UnitImporter<GroupType, SegmentType, DummyReference, MyRecord, MyError> importer = new UnitImporter<>(factory, DummyReference.class, S71) {
            @Override
            protected PropertyCollector.Builder<GroupType, MyRecord, MyError> newBuilder(int size) {
                return null;
            }
        };
        final MyRecord record = new MyRecord(S71, "#");
        // WHEN
        final AccessException error = assertThrows(AccessException.class, () -> importer.parse(singletonList(record)));
        //THEN
        assertNotNull(error);
        assertEquals("BusinessTests.1[builder] must not be null", error.getLocalizedMessage());
        assertNull(error.getCause());
    }

    @Test
    void testNullHandler() throws BeanException {
        // GIVEN
        final UnitImporter<GroupType, SegmentType, DummyReference, MyRecord, MyError> importer = new UnitImporter<>(factory, DummyReference.class, S71) {
            @Override
            protected IEventBuilder<GroupType, MyRecord, MyError> newBuilder(int size) {
                return new IEventBuilder<>() {
                    @Override
                    public BusinessHandler<GroupType> of(MyRecord record) {
                        return null;
                    }

                    @Override
                    public List<MyError> toList() {
                        return List.of();
                    }
                };
            }
        };
        final MyRecord record = new MyRecord(S71, "#");
        // WHEN
        final AccessException error = assertThrows(AccessException.class, () -> importer.parse(singletonList(record)));
        //THEN
        assertNotNull(error);
        assertEquals("BusinessTests.2.1[handler] must not be null", error.getLocalizedMessage());
        assertNull(error.getCause());
    }

    @Test
    void testNullSource() throws BeanException {
        // GIVEN
        final UnitImporter<GroupType, SegmentType, DummyReference, MyRecord, SimpleEvent<GroupType>> importer = new UnitImporter<>(factory, DummyReference.class, S71) {
            @Override
            protected MyHandler newBuilder(int size) {
                MyHandler.INSTANCE.toList().clear(); // Not Thread-Safe
                return MyHandler.INSTANCE;
            }
        };
        final MyRecord record1 = new MyRecord(S71, "#1");
        final MyRecord record2 = new MyRecord(S72, "*", "*", "*");
        // WHEN
        final Up2Result<DummyReference, SimpleEvent<GroupType>> result = importer.parse(List.of(record1, record2));
        // THEN
        assertEquals(0, result.get().getAttributes().size());
        assertEquals(3, result.toList().size());
    }

    @Test
    void testNullEvent() throws BeanException {
        // GIVEN
        final AtomicInteger counter = new AtomicInteger();
        final UnitImporter<GroupType, SegmentType, DummyReference, MyRecord, MyError> importer = new UnitImporter<>(factory, DummyReference.class, S71) {
            @Override
            protected PropertyCollector.Builder<GroupType, MyRecord, MyError> newBuilder(int size) {
                final IPropertyCreator<GroupType, MyRecord, MyError> creator = (a, b, c, d) -> {
                    counter.incrementAndGet();
                    return null;
                };
                return new PropertyCollector.Builder<>(size, creator);
            }
        };
        final MyRecord record1 = new MyRecord(S71, "#1");
        final MyRecord record2 = new MyRecord(S72, "*", "*", "*");
        // WHEN
        final Up2Result<DummyReference, MyError> result = importer.parse(List.of(record1, record2));
        // THEN
        assertEquals(0, result.get().getAttributes().size());
        assertEquals(0, result.toList().size());
        assertEquals(3, counter.get());
    }

    @Test
    void testDummyUnitImporter() throws BeanException {
        // GIVEN
        final SimpleUnitImporter<DummyReference, GroupType, SegmentType> importer = factory.builder()
                .unit(DummyReference.class)
                .build(S71)
                .build();
        // WHEN
        final Up2Result<DummyReference, ?> result = importer.parse(List.of(new UnitRecord<>(S71, "#01")));
        //THEN
        assertNotNull(result);
        assertNotNull(result.get());
        assertEquals("#01", result.get().getReference());
        assertEquals(0, result.toList().size());
    }

    @Test
    void testDummyFastImporter() throws BeanException {
        // GIVEN
        final FastImporter<GroupType, SegmentType, DummyReference, BSRecord, BSError> importer = factory.builder()
                .fast(DummyReference.class)
                .build(S71)
                .build(BSError::new);
        final BSRecord record = new BSRecord(S71, "*");
        // WHEN
        final Up2Result<DummyReference, BSError> result = importer.parse(List.of(record));
        assertEquals(1, result.toList().size());
        assertEquals(1, record.getEvents().size());
        final BSError error = result.toList().getFirst();
        //THEN
        assertSame(record, error.getKey().getRecord());
        assertEquals(0, error.getKey().getOrder());
        assertNull(error.getOffset());
        assertEquals(S71.getEventLevel(), error.getLevel());
        assertEquals(S71.getEventCode(), error.getCode());
        assertEquals(S71.getDataType(), error.getType());
        assertEquals("cannot update the business identifier", error.getMessage());
        assertTrace(error.getTrace(),
                EX_ACCESS + ": DummyReference[businessId] invalid identifier",
                "\t" + CSV_DUMMY_REFERENCE + ".setReference(DummyReference.java:36)",
                "\t" + CSV_DUMMY_REFERENCE + ".setReference(DummyReference.java:19)"
        );
    }

    @Test
    void testDummy1Importer() throws BeanException {
        // GIVEN
        final UnitImporter<GroupType, SegmentType, DummyReference, BSRecord, BSError> importer = factory.builder()
                .unit(DummyReference.class)
                .build(S71)
                .build(BSError::new);
        final BSRecord record1 = new BSRecord(S71, null, "#01");
        final BSRecord record2 = new BSRecord(S72, null, "#01", "B01", "V01");
        // WHEN
        final Up2Result<DummyReference, BSError> result = importer.parse(List.of(record1, record2));
        //THEN
        assertNotNull(result);
        assertNotNull(result.get());
        assertEquals("#01", result.get().getReference());
        assertEquals(1, result.get().getAttributes().size());
        assertEquals(0, result.toList().size());
    }

    @Test
    void testDummy2Importer() throws BeanException {
        // GIVEN
        final UnitImporter<GroupType, SegmentType, DummyReference, BSRecord, BSError> importer = factory.builder()
                .unit(DummyReference.class)
                .build(S71)
                .build(BSError::new);
        final BSRecord record1 = new BSRecord(S71, null, "#01");
        final BSRecord record2 = new BSRecord(S72, null, "*", "B01", "V01");
        // WHEN
        final Up2Result<DummyReference, BSError> result = importer.parse(List.of(record1, record2));
        assertEquals(0, result.get().getAttributes().size());
        assertEquals(2, result.toList().size());
        assertEquals(2, record2.getEvents().size());
        assertEquals(0, record1.getEvents().size());
        final BSError error = result.toList().getFirst();
        //THEN
        assertSame(record2, error.getKey().getRecord());
        assertEquals(0, error.getKey().getOrder());
        assertNull(error.getOffset());
        assertEquals(S72.getEventLevel(), error.getLevel());
        assertEquals(S72.getEventCode(), error.getCode());
        assertEquals(S72.getDataType(), error.getType());
        assertEquals("cannot retrieve the parent identifier", error.getMessage());
        assertTrace(error.getTrace(),
                EX_ACCESS + ": DummyAttribute[getParentId] invalid identifier"
        );
    }

    @Test
    void testDummy3Importer() throws BeanException {
        // GIVEN
        final UnitImporter<GroupType, SegmentType, DummyReference, BSRecord, BSError> importer = factory.builder()
                .unit(DummyReference.class)
                .build(S71)
                .build(BSError::new);
        final BSRecord record1 = new BSRecord(S71, null, "#01");
        final BSRecord record2 = new BSRecord(S72, null, "#01", "*", "V01");
        // WHEN
        final Up2Result<DummyReference, BSError> result = importer.parse(List.of(record1, record2));
        assertEquals(1, result.get().getAttributes().size());
        assertEquals(1, result.toList().size());
        assertEquals(1, record2.getEvents().size());
        assertEquals(0, record1.getEvents().size());
        final BSError error = result.toList().getFirst();
        //THEN
        assertSame(record2, error.getKey().getRecord());
        assertEquals(0, error.getKey().getOrder());
        assertNull(error.getOffset());
        assertEquals(S72.getEventLevel(), error.getLevel());
        assertEquals(S72.getEventCode(), error.getCode());
        assertEquals(S72.getDataType(), error.getType());
        assertEquals("cannot retrieve the business identifier", error.getMessage());
        assertTrace(error.getTrace(),
                EX_ACCESS + ": DummyAttribute[getBusinessId] invalid identifier"
        );
    }

    @Test
    void testDummy4Importer() throws BeanException {
        // GIVEN
        final UnitImporter<GroupType, SegmentType, DummyReference, BSRecord, BSError> importer = factory.builder()
                .unit(DummyReference.class)
                .build(S71)
                .build(BSError::new);
        final BSRecord record1 = new BSRecord(S71, null, "#01");
        final BSRecord record2 = new BSRecord(S72, null, "#01", "B01", "*");
        // WHEN
        final Up2Result<DummyReference, BSError> result = importer.parse(List.of(record1, record2));
        assertEquals(0, result.get().getAttributes().size());
        assertEquals(2, result.toList().size());
        assertEquals(2, record2.getEvents().size());
        assertEquals(0, record1.getEvents().size());
        final BSError error = result.toList().getFirst();
        //THEN
        assertSame(record2, error.getKey().getRecord());
        assertEquals(0, error.getKey().getOrder());
        assertNull(error.getOffset());
        assertEquals(S72.getEventLevel(), error.getLevel());
        assertEquals(S72.getEventCode(), error.getCode());
        assertEquals(S72.getDataType(), error.getType());
        assertEquals("cannot link with Segment#[71]", error.getMessage());
        assertTrace(error.getTrace(),
                EX_ACCESS + ": DummyAttribute[value] invalid value",
                "\t" + CSV_MODULE + "/io.github.up2jakarta.test.impl.DummyTypes.lambda$dummyAttributes$0(DummyTypes.java:98)"
        );
    }

    @Test
    void test1Validator() throws BeanException {
        // GIVEN
        final UnitExporter<GroupType, SegmentType, DummyReference> importer = factory.builder()
                .unit(DummyReference.class)
                .build(S71)
                .export();
        // WHEN
        final SimpleCollector<GroupType> collector = new SimpleCollector<>();
        importer.validate(null, collector);
        //THEN
        assertEquals(0, collector.toList().size());
    }

    @Test
    void test2Validator() throws BeanException {
        // GIVEN
        final UnitExporter<GroupType, SegmentType, DummyReference> importer = factory.builder()
                .unit(DummyReference.class)
                .build(S71)
                .export();
        final DummyReference bean = new DummyReference();
        {
            bean.getAttributes().add(null);
        }
        // WHEN
        final SimpleCollector<GroupType> collector = new SimpleCollector<>();
        importer.validate(bean, collector);
        assertEquals(1, collector.toList().size());
        final SimpleEvent<GroupType> error = collector.toList().getFirst();
        //THEN
        assertNull(error.getOffset());
        assertEquals(S72.getEventLevel(), error.getLevel());
        assertEquals(S72.getEventCode(), error.getCode());
        assertEquals(S72.getDataType(), error.getType());
        assertEquals("must not be null", error.getMessage());
    }

    @Test
    void test3Validator() throws BeanException {
        // GIVEN
        final UnitExporter<GroupType, SegmentType, DummyReference> importer = factory.builder()
                .unit(DummyReference.class)
                .build(S71)
                .export();
        final DummyReference bean = new DummyReference();
        {
            bean.getAttributes().add(new DummyAttribute());
        }
        // WHEN
        final List<? extends IEvent<GroupType>> events = importer.validate(bean);
        assertEquals(1, events.size());
        final IEvent<GroupType> error = events.getFirst();
        //THEN
        assertEquals(3, error.getOffset());
        assertEquals(SeverityType.ERROR, error.getLevel());
        assertEquals(IEvent.EC_COMPLIANCE, error.getCode());
        assertEquals(GroupType.D005, error.getType());
        assertEquals("must not be blank", error.getMessage());
    }

    static final class MyRecord implements IRecord<SegmentType> {

        private final SegmentType type;
        private final String[] data;

        public MyRecord(SegmentType type, String... data) {
            this.type = type;
            this.data = data;
        }

        @Override
        public SegmentType getType() {
            return type;
        }

        @Override
        public String[] getData() {
            return data;
        }
    }

    static final class MyError extends PropertyEvent<GroupType, MyRecord> {
        public MyError(MyRecord row, Integer offset, GroupType type, TypeException cause) {
            super(row, offset, type, cause);
        }
    }

    static final class MyHandler extends SimpleCollector<GroupType> implements IEventBuilder<GroupType, MyRecord, SimpleEvent<GroupType>> {
        static final MyHandler INSTANCE = new MyHandler();

        private MyHandler() {
        }

        @Override
        public BusinessHandler<GroupType> of(MyRecord record) {
            return INSTANCE;
        }

    }

}
