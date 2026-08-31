package io.github.up2jakarta.test.core;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IMessRecord;
import io.github.up2jakarta.csv.api.hdl.IEventBuilder;
import io.github.up2jakarta.csv.api.hdl.IPropertyCreator;
import io.github.up2jakarta.csv.core.MessImporter;
import io.github.up2jakarta.csv.core.NeatExporter;
import io.github.up2jakarta.csv.core.NeatImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.NeatRecord;
import io.github.up2jakarta.csv.data.SimpleNeatImporter;
import io.github.up2jakarta.csv.data.Up2Result;
import io.github.up2jakarta.csv.hdl.*;
import io.github.up2jakarta.lov.TypeException;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.BuilderTests.BSError;
import io.github.up2jakarta.test.BuilderTests.BSRecord;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.core.bs.*;
import io.github.up2jakarta.test.core.misc.acs.BIdInvalidObject;
import io.github.up2jakarta.test.core.misc.acs.BIdOptionalObject;
import io.github.up2jakarta.test.core.misc.acs.BIdWrapperObject;
import io.github.up2jakarta.test.fmt.sln.DummyAttributeLinker;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static io.github.up2jakarta.csv.api.IEvent.EC_COMPLIANCE;
import static io.github.up2jakarta.csv.api.IEvent.EC_CONVERTER;
import static io.github.up2jakarta.csv.core.MessImporter.DETACHED;
import static io.github.up2jakarta.lov.SeverityType.*;
import static io.github.up2jakarta.test.core.Up2ErrorTests.*;
import static io.github.up2jakarta.test.impl.SegmentType.*;
import static io.github.up2jakarta.test.impl.TermType.*;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class BusinessTests {

    static final String EX_ACCESS = AccessException.class.getName();
    static final String TU_MODULE = DummyReference.class.getModule().getName();

    private final Up2Factory<TermType> factory;

    @Autowired
    BusinessTests(Up2Factory<TermType> factory) {
        this.factory = factory;
    }

    private <I extends VirtualItem, T extends VirtualReference<I>> T assertVirtual(Class<T> type) throws BeanException {
        // GIVEN
        final MessImporter<TermType, SegmentType, T, BSRecord, BSError> importer = factory.builder()
                .mess(type)
                .build(SegmentType.class)
                .build(BSError::new);
        // WHEN
        final BSRecord vr = new BSRecord(S71, null, "$");
        final BSRecord vi = new BSRecord(S73, null, "1");
        final BSRecord va = new BSRecord(S72, null, "I1", "K", "V");
        final Up2Result<T, BSError> result = importer.parse(List.of(vr, vi, va));
        final T bean = result.get();
        //THEN
        assertNotNull(bean);
        assertEquals(0, vr.getEvents().size());
        assertEquals(0, vi.getEvents().size());
        assertEquals(2, va.getEvents().size());
        assertEquals("$", bean.getReference());
        assertEquals(1, bean.getItems().size());
        final I item = bean.getItems().getFirst();
        assertEquals(1, item.getId());
        assertEquals(0, item.getAttributes().size());
        assertEquals(2, result.toList().size());
        //THEN
        {
            final BSError error = va.getEvents().getFirst();
            assertSame(va, error.getKey().getRecord());
            assertEquals(0, error.getKey().getOrder());
            assertEquals(2, error.getOffset());
            assertEquals(ERROR, error.getLevel());
            assertEquals(EC_CONVERTER, error.getCode());
            assertEquals(I006, error.getType());
            assertEquals("For input string: \"I1\"", error.getMessage());
            assertTrace(error.getTrace(),
                    "java.lang.NumberFormatException: For input string: \"I1\"",
                    "\tjava.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:67)",
                    "\tjava.base/java.lang.Integer.parseInt(Integer.java:662)",
                    "\tjava.base/java.lang.Integer.parseInt(Integer.java:778)"
            );
        }
        {
            final BSError error = va.getEvents().getLast();
            assertSame(va, error.getKey().getRecord());
            assertEquals(1, error.getKey().getOrder());
            assertNull(error.getOffset());
            assertEquals(WARNING, error.getLevel());
            assertEquals("CSV-C72", error.getCode());
            assertEquals(D009, error.getType());
            assertEquals(DETACHED, error.getMessage());
            assertNull(error.getTrace());
        }
        return bean;
    }

    @Test
    void testBusinessId() throws BeanException {
        // GIVEN
        final MessImporter<TermType, SegmentType, ComplexBId, BSRecord, BSError> importer = factory.builder()
                .mess(ComplexBId.class)
                .build(SegmentType.class)
                .build(BSError::new);
        final BSRecord record = new BSRecord(S00, "7");
        // WHEN
        final Up2Result<ComplexBId, BSError> result = importer.parse(List.of(record));
        // THEN
        assertEquals(0, result.toList().size());
        assertEquals(0, record.getEvents().size());
        assertEquals(7, result.get().get().get().getId());
    }

    @Test
    void testValidateBusinessId() throws BeanException {
        // GIVEN
        final MessImporter<TermType, SegmentType, ComplexBId, BSRecord, BSError> importer = factory.builder()
                .mess(ComplexBId.class)
                .build(SegmentType.class)
                .build(BSError::new);
        final BSRecord record = new BSRecord(S00, null);
        // WHEN
        final Up2Result<ComplexBId, BSError> result = importer.parse(List.of(record));
        // THEN
        assertEquals(1, result.toList().size());
        assertEquals(1, record.getEvents().size());
        {
            final BSError error = result.toList().getFirst();
            assertNull(error.getOffset());
            assertEquals(ERROR, error.getLevel());
            assertEquals(EC_COMPLIANCE, error.getCode());
            assertEquals(UUID, error.getType());
            assertEquals("must not be null", error.getMessage());
        }
    }

    @Test
    void testRecursiveException() {
        // WHEN
        final BeanException ex = assertThrows(
                BeanException.class,
                () -> factory.builder()
                        .full(CyclicInvoice.class)
                        .build(SegmentType.class)
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
        final NeatImporter<TermType, SegmentType, DummyReference, BSRecord, BSError> importer = factory.builder()
                .neat(DummyReference.class)
                .build(SegmentType.class)
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
        final NeatImporter<TermType, SegmentType, DummyReference, MyRecord, MyError> importer = factory.builder()
                .neat(DummyReference.class)
                .build(SegmentType.class)
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
        assertEquals(ERROR, error.getLevel());
        assertEquals(EC_COMPLIANCE, error.getCode());
        assertNull(error.getType());
        assertEquals("must not be null", error.getMessage());
    }

    @Test
    void testNullData() throws BeanException {
        // GIVEN
        final NeatImporter<TermType, SegmentType, DummyReference, MyRecord, MyError> importer = factory.builder()
                .neat(DummyReference.class)
                .build(SegmentType.class)
                .build(MyError::new);
        final MyRecord record = new MyRecord(S71, (String[]) null);
        // WHEN
        final Up2Result<DummyReference, MyError> result = importer.parse(singletonList(record));
        //THEN
        assertNotNull(result);
        assertNull(result.get());
        assertEquals(1, result.toList().size());
        final MyError error = result.toList().getFirst();
        //THEN
        assertNull(error.getOffset());
        assertEquals(FATAL, error.getLevel());
        assertEquals("CSV-C71", error.getCode());
        assertEquals(NONE, error.getType());
        assertEquals("must not be null", error.getMessage());
    }

    @Test
    void testNullBuilder() throws BeanException {
        // GIVEN
        final NeatImporter<TermType, SegmentType, DummyReference, MyRecord, MyError> importer = new NeatImporter<>(factory, DummyReference.class, SegmentType.class) {
            @Override
            protected PropertyCollector.Builder<TermType, MyRecord, MyError> newBuilder(int size) {
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
        final NeatImporter<TermType, SegmentType, DummyReference, MyRecord, MyError> importer = new NeatImporter<>(factory, DummyReference.class, SegmentType.class) {
            @Override
            protected IEventBuilder<TermType, MyRecord, MyError> newBuilder(int size) {
                return new IEventBuilder<>() {
                    @Override
                    public BusinessHandler<TermType> of(MyRecord record) {
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
        final MessImporter<TermType, SegmentType, DummyReference, MyRecord, SimpleEvent<TermType>> importer = new MessImporter<>(factory, DummyReference.class, SegmentType.class) {
            @Override
            protected MyHandler newBuilder(int size) {
                MyHandler.INSTANCE.toList().clear(); // Not Thread-Safe
                return MyHandler.INSTANCE;
            }
        };
        final MyRecord record1 = new MyRecord(S71, "#1");
        final MyRecord record2 = new MyRecord(S72, "*", "*", "*");
        // WHEN
        final Up2Result<DummyReference, SimpleEvent<TermType>> result = importer.parse(List.of(record1, record2));
        // THEN
        assertEquals(0, result.get().getAttributes().size());
        assertEquals(3, result.toList().size());
        for (final SimpleEvent<TermType> error : result.toList()) {
            assertIn(error.getMessage(),
                    DETACHED, "cannot retrieve the business identifier",
                    "cannot retrieve the @ReferenceId(\"71\") value"
            );
            assertNull(error.getOffset());
            assertEquals(WARNING, error.getLevel());
            assertEquals("CSV-C72", error.getCode());
            assertEquals(D009, error.getType());
        }
    }

    @Test
    void testNullEvent() throws BeanException {
        // GIVEN
        final AtomicInteger counter = new AtomicInteger();
        final MessImporter<TermType, SegmentType, DummyReference, MyRecord, MyError> importer = new MessImporter<>(factory, DummyReference.class, SegmentType.class) {
            @Override
            protected PropertyCollector.Builder<TermType, MyRecord, MyError> newBuilder(int size) {
                final IPropertyCreator<TermType, MyRecord, MyError> creator = (a, b, c, d) -> {
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
    void testDummyNeatImporter() throws BeanException {
        // GIVEN
        final SimpleNeatImporter<DummyReference, TermType, SegmentType> importer = factory.builder()
                .neat(DummyReference.class)
                .build(SegmentType.class)
                .build();
        // WHEN
        final Up2Result<DummyReference, ?> result = importer.parse(List.of(new NeatRecord<>(S71, "#01")));
        //THEN
        assertNotNull(result);
        assertNotNull(result.get());
        assertEquals("#01", result.get().getReference());
        assertEquals(0, result.toList().size());
    }

    @Test
    void testDummyMessImporter() throws BeanException {
        // GIVEN
        final MessImporter<TermType, SegmentType, UnmanagedReference, BSRecord, BSError> importer = factory.builder()
                .mess(UnmanagedReference.class)
                .build(SegmentType.class)
                .build(BSError::new);
        final BSRecord dr = new BSRecord(S71, "$");
        final BSRecord da = new BSRecord(S72, "", "$", "1", "2");
        // WHEN
        final Up2Result<UnmanagedReference, BSError> result = importer.parse(List.of(dr, da));
        assertEquals(1, result.get().getAttributes().size());
        assertEquals(2, result.toList().size());
        assertEquals(2, dr.getEvents().size());
        //THEN
        {
            final BSError error = result.toList().getFirst();
            assertSame(dr, error.getKey().getRecord());
            assertEquals(0, error.getKey().getOrder());
            assertNull(error.getOffset());
            assertEquals(FATAL, error.getLevel());
            assertEquals("CSV-C71", error.getCode());
            assertEquals(NONE, error.getType());
            assertEquals("cannot update the business identifier", error.getMessage());
            assertTrace(error.getTrace(),
                    EX_ACCESS + ": DummyReference[reference] invalid identifier",
                    "\t" + name(TU_MODULE, DummyReference.class) + ".setReference(DummyReference.java:49)"
            );
        }
        {
            final BSError error = result.toList().getLast();
            assertSame(dr, error.getKey().getRecord());
            assertEquals(1, error.getKey().getOrder());
            assertNull(error.getOffset());
            assertEquals(ERROR, error.getLevel());
            assertEquals(EC_COMPLIANCE, error.getCode());
            assertEquals(UUID, error.getType());
            assertEquals("must not be blank", error.getMessage());
        }
    }

    @Test
    void testVirtual1Importer() throws BeanException {
        this.assertVirtual(Virtual1Reference.class);
    }

    @Test
    void testVirtual2Importer() throws BeanException {
        final Virtual2Reference bean = this.assertVirtual(Virtual2Reference.class);
        final Virtual2Item item = bean.getItems().getFirst();
        assertSame(item.getId(), item.getKey().getId());
    }

    @Test
    void testDummy1Importer() throws BeanException {
        // GIVEN
        final NeatImporter<TermType, SegmentType, DummyReference, BSRecord, BSError> importer = factory.builder()
                .neat(DummyReference.class)
                .build(SegmentType.class)
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
        final MessImporter<TermType, SegmentType, DummyReference, BSRecord, BSError> importer = factory.builder()
                .mess(DummyReference.class)
                .build(SegmentType.class)
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
        assertEquals(WARNING, error.getLevel());
        assertEquals("CSV-C72", error.getCode());
        assertEquals(D009, error.getType());
        assertEquals("cannot retrieve the @ReferenceId(\"71\") value", error.getMessage());
        assertTrace(error.getTrace(),
                EX_ACCESS + ": DummyAttribute[parentId] invalid identifier",
                "\t" + name(TU_MODULE, DummyAttribute.class) + ".getParentId(DummyAttribute.java:41)"
        );
    }

    @Test
    void testDummy3Importer() throws BeanException {
        // GIVEN
        final MessImporter<TermType, SegmentType, DummyReference, BSRecord, BSError> importer = factory.builder()
                .mess(DummyReference.class)
                .build(SegmentType.class)
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
        assertEquals(WARNING, error.getLevel());
        assertEquals("CSV-C72", error.getCode());
        assertEquals(D009, error.getType());
        assertEquals("cannot retrieve the business identifier", error.getMessage());
        assertTrace(error.getTrace(),
                EX_ACCESS + ": DummyAttribute[businessId] invalid identifier",
                "\t" + name(TU_MODULE, DummyAttribute.class) + ".getBusinessId(DummyAttribute.java:52)"
        );
    }

    @Test
    void testDummy4Importer() throws BeanException {
        // GIVEN
        final NeatImporter<TermType, SegmentType, DummyReference, BSRecord, BSError> importer = factory.builder()
                .neat(DummyReference.class)
                .build(SegmentType.class)
                .build(BSError::new);
        final BSRecord record1 = new BSRecord(S71, null, "#01");
        final BSRecord record2 = new BSRecord(S72, null, "#01", "B01", "*");
        // WHEN
        final Up2Result<DummyReference, BSError> result = importer.parse(List.of(record1, record2));
        assertEquals(0, result.get().getAttributes().size());
        assertEquals(1, result.toList().size());
        assertEquals(1, record2.getEvents().size());
        assertEquals(0, record1.getEvents().size());
        final BSError error = result.toList().getFirst();
        //THEN
        assertSame(record2, error.getKey().getRecord());
        assertEquals(0, error.getKey().getOrder());
        assertNull(error.getOffset());
        assertEquals(WARNING, error.getLevel());
        assertEquals("CSV-C72", error.getCode());
        assertEquals(D009, error.getType());
        assertEquals("cannot link with Segment#[71]", error.getMessage());
        final String cn = name(TU_MODULE, DummyAttributeLinker.class);
        assertTrace(error.getTrace(),
                EX_ACCESS + ": DummyAttribute[value] invalid value",
                "\t" + cn + ".link(DummyAttributeLinker.java:22)",
                "\t" + cn + ".link(DummyAttributeLinker.java:11)"
        );
    }

    @Test
    void test1Validator() throws BeanException {
        // GIVEN
        final NeatExporter<TermType, SegmentType, DummyReference> importer = factory.builder()
                .neat(DummyReference.class)
                .build(SegmentType.class)
                .export();
        // WHEN
        final SimpleCollector<TermType> collector = new SimpleCollector<>();
        importer.validate(null, collector);
        //THEN
        assertEquals(0, collector.toList().size());
    }

    @Test
    void test2Validator() throws BeanException {
        // GIVEN
        final NeatExporter<TermType, SegmentType, DummyReference> importer = factory.builder()
                .neat(DummyReference.class)
                .build(SegmentType.class)
                .export();
        final DummyReference bean = new DummyReference();
        {
            bean.setReference("TU");
            bean.getAttributes().add(null);
        }
        // WHEN
        final SimpleCollector<TermType> collector = new SimpleCollector<>();
        importer.validate(bean, collector);
        assertEquals(1, collector.toList().size());
        final SimpleEvent<TermType> error = collector.toList().getFirst();
        //THEN
        assertNull(error.getOffset());
        assertEquals(WARNING, error.getLevel());
        assertEquals("CSV-C72", error.getCode());
        assertEquals(D009, error.getType());
        assertEquals("must not be null", error.getMessage());
    }

    @Test
    void test3Validator() throws BeanException {
        // GIVEN
        final NeatExporter<TermType, SegmentType, DummyReference> importer = factory.builder()
                .neat(DummyReference.class)
                .build(SegmentType.class)
                .export();
        final DummyReference bean = new DummyReference();
        {
            bean.setReference("TU");
            final DummyAttribute attribute = new DummyAttribute();
            bean.getAttributes().add(attribute);
            attribute.setBusinessId("*");
            attribute.setParentId("*");
        }
        // WHEN
        final List<? extends IEvent<TermType>> events = importer.validate(bean);
        assertEquals(1, events.size());
        final IEvent<TermType> error = events.getFirst();
        //THEN
        assertEquals(3, error.getOffset());
        assertEquals(ERROR, error.getLevel());
        assertEquals(EC_COMPLIANCE, error.getCode());
        assertEquals(A002, error.getType());
        assertEquals("must not be blank", error.getMessage());
    }

    @Test
    void testBIdWrapper() throws BeanException {
        // WHEN
        final NeatExporter<TermType, SegmentType, BIdWrapperObject> importer = factory.builder()
                .neat(BIdWrapperObject.class)
                .build(SegmentType.class)
                .export();
        // THEN
        assertNotNull(importer);
    }

    @Test
    void testBIdOptional() throws BeanException {
        // WHEN
        final NeatExporter<TermType, SegmentType, BIdOptionalObject> importer = factory.builder()
                .neat(BIdOptionalObject.class)
                .build(SegmentType.class)
                .export();
        // THEN
        assertNotNull(importer);
    }

    @Test
    void testBIdInvalid() {
        // WHEN
        final BeanException ex = assertThrows(
                BeanException.class,
                () -> factory.builder()
                        .neat(BIdInvalidObject.class)
                        .build(SegmentType.class)
                        .build()
        );
        // THEN
        assertEquals(BIdInvalidObject.class, ex.getSource());
        assertEquals("key", ex.getLocator());
        assertEquals("should be annotated with @NotBlank because @BusinessId", ex.getMessage());
    }

    private static final class MyRecord implements IMessRecord<SegmentType> {

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

        @Override
        public String getPivot() {
            return null;
        }
    }

    private static final class MyError extends PropertyEvent<TermType, MyRecord> {
        public MyError(MyRecord row, Integer offset, TermType type, TypeException cause) {
            super(row, offset, type, cause);
        }
    }

    static final class MyHandler extends SimpleCollector<TermType> implements IEventBuilder<TermType, MyRecord, SimpleEvent<TermType>> {
        static final MyHandler INSTANCE = new MyHandler();

        private MyHandler() {
        }

        @Override
        public BusinessHandler<TermType> of(MyRecord record) {
            return INSTANCE;
        }

    }

}
