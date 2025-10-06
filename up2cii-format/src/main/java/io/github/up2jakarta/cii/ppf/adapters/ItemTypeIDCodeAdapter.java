package io.github.up2jakarta.cii.ppf.adapters;

import io.github.up2jakarta.cii.ppf.ItemTypeIDCodeType;
import io.github.up2jakarta.xml.clv.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link ItemTypeIDCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class ItemTypeIDCodeAdapter extends CodeListConverter<ItemTypeIDCodeType> {

    ItemTypeIDCodeAdapter() {
        super(ItemTypeIDCodeType.class, "ECE-7143");
    }

}
