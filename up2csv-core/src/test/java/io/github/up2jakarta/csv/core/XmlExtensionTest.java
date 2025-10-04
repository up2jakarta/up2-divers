package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.impl.*;
import io.github.up2jakarta.csv.misc.BeanException;
import io.github.up2jakarta.csv.misc.Errors;
import io.github.up2jakarta.csv.test.Tests;
import io.github.up2jakarta.csv.test.bean.xml.*;
import io.github.up2jakarta.csv.test.codelist.CurrencyCodeType;
import io.github.up2jakarta.csv.test.codelist.CurrencyConverter;
import io.github.up2jakarta.csv.test.codelist.TestCodeList;
import io.github.up2jakarta.csv.test.codelist.TestCodeListConverter;
import io.github.up2jakarta.xml.api.SeverityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class XmlExtensionTest {

    private final SimpleCreator creator;
    private final MapperFactory<BusinessType> factory;

    @Autowired
    XmlExtensionTest(MapperFactory<BusinessType> factory, SimpleCreator creator) {
        this.factory = factory;
        this.creator = creator;
    }

    @Test
    void testValidBean() throws BeanException {
        // Given
        final Mapper<Test1Bean, BusinessType> mapper = factory.build(Test1Bean.class);
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
        final String[] out = mapper.unmap(bean);
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
        final Mapper<Test1Bean, BusinessType> mapper = factory.build(Test1Bean.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "11", "2", "33", "ILS", "ANY");
        // When
        final SimpleHandler handler = new SimpleHandler(row, creator);
        final Test1Bean bean = mapper.map(row, handler);
        final List<SimpleErrorEntity> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(4, errors.size());
        {
            final SimpleErrorEntity error = errors.getFirst();
            assertSame(row, error.getRecord());
            assertEquals(0, error.getOrder());
            assertEquals(0, error.getOffset());
            assertEquals(SeverityType.WARNING, error.getSeverity());
            assertEquals(Test1Bean.XML_XXX, error.getCode());
            assertEquals("Unknown value [11] for @XmlEnum[XML1Enum]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(1);
            assertSame(row, error.getRecord());
            assertEquals(1, error.getOrder());
            assertEquals(2, error.getOffset());
            assertEquals(SeverityType.ERROR, error.getSeverity());
            assertEquals(Errors.ERROR_XML_ENUM, error.getCode());
            assertEquals("Unknown value [33] for @XmlEnum[XML3Enum]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(2);
            assertSame(row, error.getRecord());
            assertEquals(2, error.getOrder());
            assertEquals(3, error.getOffset());
            assertEquals(SeverityType.ERROR, error.getSeverity());
            assertEquals(CurrencyConverter.ISO_4217, error.getCode());
            assertEquals("Unknown value [ILS] for CodeList[CurrencyCodeType]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(3);
            assertSame(row, error.getRecord());
            assertEquals(3, error.getOrder());
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
        final Mapper<Test2Bean, BusinessType> mapper = factory.build(Test2Bean.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "11", "22", "ILS", "ANY");
        // When
        final SimpleHandler handler = new SimpleHandler(row, creator);
        final Test2Bean bean = mapper.map(row, handler);
        final List<SimpleErrorEntity> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(4, errors.size());
        {
            final SimpleErrorEntity error = errors.getFirst();
            assertSame(row, error.getRecord());
            assertEquals(0, error.getOrder());
            assertEquals(0, error.getOffset());
            assertEquals(SeverityType.WARNING, error.getSeverity());
            assertEquals(Test2Bean.XML_001, error.getCode());
            assertEquals("Unknown value [11] for @XmlEnum[XML1Enum]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(1);
            assertSame(row, error.getRecord());
            assertEquals(1, error.getOrder());
            assertEquals(1, error.getOffset());
            assertEquals(SeverityType.WARNING, error.getSeverity());
            assertEquals(Test2Bean.XML_002, error.getCode());
            assertEquals("Unknown value [22] for @XmlEnum[XML2Enum]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(2);
            assertSame(row, error.getRecord());
            assertEquals(2, error.getOrder());
            assertEquals(2, error.getOffset());
            assertEquals(SeverityType.WARNING, error.getSeverity());
            assertEquals(Test2Bean.XML_003, error.getCode());
            assertEquals("Unknown value [ILS] for CodeList[CurrencyCodeType]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(3);
            assertSame(row, error.getRecord());
            assertEquals(3, error.getOrder());
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
        assertEquals("Test3Bean[enum1] - must be annotated with @Up2Converter or one of its shortcuts", thrown.getMessage());
    }

}
