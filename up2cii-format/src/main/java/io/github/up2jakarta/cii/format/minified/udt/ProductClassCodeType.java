package io.github.up2jakarta.cii.format.minified.udt;

import io.github.up2jakarta.cii.ppf.ItemTypeIDCodeType;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "CodeType", propOrder = {"value"})
public class ProductClassCodeType {

    // BT-158
    private String value;

    // BT-158-1
    private ItemTypeIDCodeType listId;

    // BT-158-2
    private String listVersionId;

    @XmlValue
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlAttribute(name = "listID")
    public ItemTypeIDCodeType getListId() {
        return this.listId;
    }

    public void setListId(ItemTypeIDCodeType listId) {
        this.listId = listId;
    }

    @XmlAttribute(name = "listVersionID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    public String getListVersionId() {
        return this.listVersionId;
    }

    public void setListVersionId(String listVersionId) {
        this.listVersionId = listVersionId;
    }

}
