package io.github.up2jakarta.test.core;

import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.Up2Mapper;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.core.misc.jpa.checker.base.*;
import io.github.up2jakarta.test.impl.GroupType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.github.up2jakarta.lov.core.Localizable.CLASS;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class Up2JpaCheckerTests {

    private final Up2Factory<GroupType> factory;

    @Autowired
    Up2JpaCheckerTests(Up2Factory<GroupType> factory) {
        this.factory = factory;
    }

    @Test
    void testActivation() throws BeanException {
        // WHEN
        final Up2Mapper<Test1Entity, GroupType> mapper = factory.build(Test1Entity.class);
        // THEN
        assertNotNull(mapper);
    }

    @Test
    void testColumnNullable1() throws BeanException {
        // WHEN
        final Up2Mapper<Test17Entity, GroupType> mapper = factory.build(Test17Entity.class);
        // THEN
        assertNotNull(mapper);
    }

    @Test
    void testColumnNullable2() throws BeanException {
        // WHEN
        final Up2Mapper<Test18Entity, GroupType> mapper = factory.build(Test18Entity.class);
        // THEN
        assertNotNull(mapper);
    }

    // Checking

    @Test
    void testNoTableEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test2Entity.class));
        // THEN
        assertEquals(Test2Entity.class, error.getSource());
        assertEquals(CLASS, error.getLocator());
        assertEquals("must be annotated with @Table", error.getMessage());
    }

    @Test
    void testNoTableNameEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test3Entity.class));
        // THEN
        assertEquals(Test3Entity.class, error.getSource());
        assertEquals(CLASS, error.getLocator());
        assertEquals("@Table[name] must not be empty", error.getMessage());
    }

    @Test
    void testNoTableNameUpperEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test4Entity.class));
        // THEN
        assertEquals(Test4Entity.class, error.getSource());
        assertEquals(CLASS, error.getLocator());
        assertEquals("@Table[name] must be uppercase", error.getMessage());
    }

    @Test
    void testNoTableNamePrefixEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test5Entity.class));
        // THEN
        assertEquals(Test5Entity.class, error.getSource());
        assertEquals(CLASS, error.getLocator());
        assertEquals("@Table[name] must starts with \"TB_\"", error.getMessage());
    }

    @Test
    void testInvalidTableNameEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test6Entity.class));
        // THEN
        assertEquals(Test6Entity.class, error.getSource());
        assertEquals(CLASS, error.getLocator());
        assertEquals("@Table[name] must starts with alphabetic", error.getMessage());
    }

    @Test
    void testPrefixUppercaseEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test7Entity.class));
        // THEN
        assertEquals(Test7Entity.class, error.getSource());
        assertEquals(CLASS, error.getLocator());
        assertEquals("@Prefix[value] must be uppercase", error.getMessage());
    }

    @Test
    void testPrefixUnderscoreEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test8Entity.class));
        // THEN
        assertEquals(Test8Entity.class, error.getSource());
        assertEquals(CLASS, error.getLocator());
        assertEquals("@Prefix[value] must ends with underscore (_)", error.getMessage());
    }

    @Test
    void testMissingColumnNameEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test10Entity.class));
        // THEN
        assertEquals(Test10Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("@Column[name] must not be empty", error.getMessage());
    }

    @Test
    void testColumnNamePrefixEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test11Entity.class));
        // THEN
        assertEquals(Test11Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("@Column[name] must starts with \"TU_\"", error.getMessage());
    }

    @Test
    void testColumnNameUpperEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test12Entity.class));
        // THEN
        assertEquals(Test12Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("@Column[name] must be uppercase", error.getMessage());
    }

    @Test
    void testColumnNameSpaceEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test13Entity.class));
        // THEN
        assertEquals(Test13Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("@Column[name] must contains only alphanumeric or underscore", error.getMessage());
    }

    @Test
    void testNoSizeBean() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test15Entity.class));
        // THEN
        assertEquals(Test15Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("must be annotated with @Size", error.getMessage());
    }

    @Test
    void testColumnLengthEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test14Entity.class));
        // THEN
        assertEquals(Test14Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("@Size[max] must be less than or equals @Column[length]", error.getMessage());
    }

    @Test
    void testColumnLengthPositive() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test38Entity.class));
        // THEN
        assertEquals(Test38Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("@Column[length] must be positive", error.getMessage());
    }

    @Test
    void testColumnNullableEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test16Entity.class));
        // THEN
        assertEquals(Test16Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("must be annotated @NotEmpty or @NotBlank when @Column[nullable] is false", error.getMessage());
    }

    @Test
    void testColumnManyNullableEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test19Entity.class));
        // THEN
        assertEquals(Test19Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("must not be annotated with @NotBlank in favor of @NotEmpty", error.getMessage());
    }

    @Test
    void testSizeMaxPositive() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test39Entity.class));
        // THEN
        assertEquals(Test39Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("@Size[max] must be positive", error.getMessage());
    }

    @Test
    void testSizeMinBean() throws BeanException {
        // GIVEN
        final Up2Mapper<Test20Entity, GroupType> mapper = factory.build(Test20Entity.class);
        // THEN
        assertNotNull(mapper);
    }

    @Test
    void testInvalidNotNullEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test21Entity.class));
        // THEN
        assertEquals(Test21Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("@NotNull does not match with @Column[nullable]", error.getMessage());
    }

    @Test
    void testInvalidSizeEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test22Entity.class));
        // THEN
        assertEquals(Test22Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("must not be annotated with @Size", error.getMessage());
    }

    @Test
    void testIntegerPrecisionEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test23Entity.class));
        // THEN
        assertEquals(Test23Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("@Column[precision] must be greater than or equals to 10", error.getMessage());
    }

    @Test
    void testIntegerScaleEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test24Entity.class));
        // THEN
        assertEquals(Test24Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("@Column[scale] must not be specified", error.getMessage());
    }

    @Test
    void testIntegerScaleConverter() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test28Entity.class));
        // THEN
        assertEquals(Test28Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("must not be annotated with @Up2Decimal, use @Up2Number instead", error.getMessage());
    }

    @Test
    void testLongPrecisionEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test25Entity.class));
        // THEN
        assertEquals(Test25Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("@Column[precision] must be greater than or equals to 19", error.getMessage());
    }

    @Test
    void testLongScaleEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test26Entity.class));
        // THEN
        assertEquals(Test26Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("@Column[scale] must not be specified", error.getMessage());
    }

    @Test
    void testLongScaleConverter() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test27Entity.class));
        // THEN
        assertEquals(Test27Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("must not be annotated with @Up2Decimal, use @Up2Number instead", error.getMessage());
    }

    @Test
    void testShortPrecisionEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test29Entity.class));
        // THEN
        assertEquals(Test29Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("@Column[precision] must be greater than or equals to 5", error.getMessage());
    }

    @Test
    void testShortScaleEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test30Entity.class));
        // THEN
        assertEquals(Test30Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("@Column[scale] must not be specified", error.getMessage());
    }

    @Test
    void testShortScaleConverter() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test31Entity.class));
        // THEN
        assertEquals(Test31Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("must not be annotated with @Up2Decimal, use @Up2Number instead", error.getMessage());
    }

    @Test
    void testBytePrecisionEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test32Entity.class));
        // THEN
        assertEquals(Test32Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("@Column[precision] must be greater than or equals to 3", error.getMessage());
    }

    @Test
    void testByteScaleEntity() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test33Entity.class));
        // THEN
        assertEquals(Test33Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("@Column[scale] must not be specified", error.getMessage());
    }

    @Test
    void testByteScaleConverter() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test34Entity.class));
        // THEN
        assertEquals(Test34Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("must not be annotated with @Up2Decimal, use @Up2Number instead", error.getMessage());
    }

    @Test
    void testPrecisionPositive() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test35Entity.class));
        // THEN
        assertEquals(Test35Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("@Column[precision] must be positive", error.getMessage());
    }

    @Test
    void testScalePositive() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test36Entity.class));
        // THEN
        assertEquals(Test36Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("@Column[scale] must be positive", error.getMessage());
    }

    @Test
    void testDigits() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test37Entity.class));
        // THEN
        assertEquals(Test37Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("must not be annotated with @Digits", error.getMessage());
    }

    @Test
    void testBigIntegerScale() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test41Entity.class));
        // THEN
        assertEquals(Test41Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("must be annotated with @Up2Number", error.getMessage());
    }

    @Test
    void testDecimalScale() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test40Entity.class));
        // THEN
        assertEquals(Test40Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("@Up2Decimal[value] must be be less than or equals to @Column[scale]", error.getMessage());
    }

    @Test
    void testDecimalConverter() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.build(Test42Entity.class));
        // THEN
        assertEquals(Test42Entity.class, error.getSource());
        assertEquals("key", error.getLocator());
        assertEquals("must not be annotated with @Up2Number, use @Up2Decimal instead", error.getMessage());
    }

}

