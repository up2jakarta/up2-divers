package io.github.up2jakarta.cii.format.minified.udt;

import io.github.up2jakarta.cii.ppf.ItemTypeIDCodeType;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CodeType", propOrder = {"value"})
public class ProductClassCodeType {

    // BT-158
    @XmlValue
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    private String value;

    // BT-158-1
    @XmlAttribute(name = "listID")
    private ItemTypeIDCodeType listId;

    // BT-158-2
    @XmlAttribute(name = "listVersionID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    private String listVersionId;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ItemTypeIDCodeType getListId() {
        return this.listId;
    }

    public void setListId(ItemTypeIDCodeType listId) {
        this.listId = listId;
    }

    public String getListVersionId() {
        return this.listVersionId;
    }

    public void setListVersionId(String listVersionId) {
        this.listVersionId = listVersionId;
    }

}
