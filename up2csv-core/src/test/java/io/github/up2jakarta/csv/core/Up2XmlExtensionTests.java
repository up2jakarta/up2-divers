package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.misc.clv.CurrencyCodeType;
import io.github.up2jakarta.csv.core.misc.clv.CurrencyConverter;
import io.github.up2jakarta.csv.core.misc.clv.TestCodeList;
import io.github.up2jakarta.csv.core.misc.clv.TestCodeListConverter;
import io.github.up2jakarta.csv.core.misc.xml.*;
import io.github.up2jakarta.csv.impl.*;
import io.github.up2jakarta.xml.api.SeverityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static io.github.up2jakarta.csv.api.IEvent.ERROR_XML_ENUM;
import static io.github.up2jakarta.csv.fmt.misc.Tests.record;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class Up2XmlExtensionTests {

    private final Up2Factory<GroupType> factory;

    @Autowired
    Up2XmlExtensionTests(Up2Factory<GroupType> factory) {
        this.factory = factory;
    }

    @Test
    void testValidBean() throws BeanException {
        // Given
        final Up2Mapper<Test1Bean, GroupType> mapper = factory.build(Test1Bean.class);
        final String[] data = {"ALL", "2", "THREE", "TND", "*"};
        // When
        final Test1Bean bean = mapper.map(data);
        // Then
        assertNotNull(bean);
        assertEquals(XML1Enum.ALL, bean.getEnum1());
        assertEquals(XML2Enum.TWO, bean.getEnum2());
        assertEquals(XML3Enum.THREE, bean.getEnum3());
        assertEquals(CurrencyCodeType.TND, bean.getAdapter1());
        assertEquals(TestCodeList.ANY, bean.getAdapter2());
        // When Unmapping
        final Up2Format<Test1Bean, GroupType> format = factory.format(Test1Bean.class);
        final String[] out = format.unmap(bean);
        // Then
        assertNotNull(out);
        assertEquals(data.length, out.length);
        for (var i = 0; i < data.length; i++) {
            assertEquals(data[i], out[i]);
        }
    }

    @Test
    void testDefaultErrors() throws BeanException {
        // Given
        final Up2Mapper<Test1Bean, GroupType> mapper = factory.build(Test1Bean.class);
        final InputRecord row = record(SegmentType.S00, "11", "2", "33", "ILS", "ANY");
        // When
        final InputCollector handler = new InputCollector(row);
        final Test1Bean bean = mapper.map(row, handler);
        final List<InputError> errors = new ArrayList<>(handler.toCollection());
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(4, errors.size());
        {
            final InputError error = errors.getFirst();
            assertSame(row, error.getKey().getRecord());
            assertEquals(0, error.getKey().getOrder());
            assertEquals(0, error.getOffset());
            assertEquals(SeverityType.WARNING, error.getSeverity());
            assertEquals(Test1Bean.XML_XXX, error.getCode());
            assertEquals("Unknown value [11] for @XmlEnum[XML1Enum]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(1);
            assertSame(row, error.getKey().getRecord());
            assertEquals(1, error.getKey().getOrder());
            assertEquals(2, error.getOffset());
            assertEquals(SeverityType.ERROR, error.getSeverity());
            assertEquals(ERROR_XML_ENUM, error.getCode());
            assertEquals("Unknown value [33] for @XmlEnum[XML3Enum]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(2);
            assertSame(row, error.getKey().getRecord());
            assertEquals(2, error.getKey().getOrder());
            assertEquals(3, error.getOffset());
            assertEquals(SeverityType.ERROR, error.getSeverity());
            assertEquals(CurrencyConverter.ISO_4217, error.getCode());
            assertEquals("Unknown value [ILS] for CodeList[CurrencyCodeType]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(3);
            assertSame(row, error.getKey().getRecord());
            assertEquals(3, error.getKey().getOrder());
            assertEquals(4, error.getOffset());
            assertEquals(SeverityType.ERROR, error.getSeverity());
            assertEquals(TestCodeListConverter.TU_001, error.getCode());
            assertEquals("Unknown value [ANY] for CodeList[TestCodeList]", error.getMessage());
            assertNull(error.getTrace());
        }
    }

    @Test
    void testOverrideErrors() throws BeanException {
        // Given
        final Up2Mapper<Test2Bean, GroupType> mapper = factory.build(Test2Bean.class);
        final InputRecord row = record(SegmentType.S00, "11", "22", "ILS", "ANY");
        // When
        final InputCollector handler = new InputCollector(row);
        final Test2Bean bean = mapper.map(row, handler);
        final List<InputError> errors = new ArrayList<>(handler.toCollection());
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(4, errors.size());
        {
            final InputError error = errors.getFirst();
            assertSame(row, error.getKey().getRecord());
            assertEquals(0, error.getKey().getOrder());
            assertEquals(0, error.getOffset());
            assertEquals(SeverityType.WARNING, error.getSeverity());
            assertEquals(Test2Bean.XML_001, error.getCode());
            assertEquals("Unknown value [11] for @XmlEnum[XML1Enum]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(1);
            assertSame(row, error.getKey().getRecord());
            assertEquals(1, error.getKey().getOrder());
            assertEquals(1, error.getOffset());
            assertEquals(SeverityType.WARNING, error.getSeverity());
            assertEquals(Test2Bean.XML_002, error.getCode());
            assertEquals("Unknown value [22] for @XmlEnum[XML2Enum]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(2);
            assertSame(row, error.getKey().getRecord());
            assertEquals(2, error.getKey().getOrder());
            assertEquals(2, error.getOffset());
            assertEquals(SeverityType.WARNING, error.getSeverity());
            assertEquals(Test2Bean.XML_003, error.getCode());
            assertEquals("Unknown value [ILS] for CodeList[CurrencyCodeType]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(3);
            assertSame(row, error.getKey().getRecord());
            assertEquals(3, error.getKey().getOrder());
            assertEquals(3, error.getOffset());
            assertEquals(SeverityType.WARNING, error.getSeverity());
            assertEquals(Test2Bean.XML_004, error.getCode());
            assertEquals("Unknown value [ANY] for CodeList[TestCodeList]", error.getMessage());
            assertNull(error.getTrace());
        }
    }

    @Test
    void testActivation() {
        // Given
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test3Bean.class));
        // THEN
        assertEquals(Test3Bean.class, thrown.getSource());
        assertEquals("enum1", thrown.getLocator());
        assertEquals("Test3Bean[enum1] - @Position[converter] must not be undefined", thrown.getMessage());
    }

}
