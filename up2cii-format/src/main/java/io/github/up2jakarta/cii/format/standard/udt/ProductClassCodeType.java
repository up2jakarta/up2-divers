package io.github.up2jakarta.cii.format.standard.udt;

import io.github.up2jakarta.cii.ppf.ItemTypeIDCodeType;
import jakarta.xml.bind.annotation.XmlAttribute;

public class ProductClassCodeType extends AbstractCodeType<ItemTypeIDCodeType> {

    @XmlAttribute(name = "listID")
    protected ItemTypeIDCodeType listID;

    /**
     * {@inheritDoc}
     **/
    @Override
    public ItemTypeIDCodeType getListID() {
        return listID;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void setListID(ItemTypeIDCodeType listID) {
        this.listID = listID;
    }

}
