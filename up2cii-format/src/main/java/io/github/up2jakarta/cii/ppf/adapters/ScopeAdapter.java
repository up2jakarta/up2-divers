package io.github.up2jakarta.cii.ppf.adapters;

import io.github.up2jakarta.cii.ppf.ScopeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link ScopeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class ScopeAdapter extends CodeListAdapter<ScopeType> {

    @Inject
    public ScopeAdapter() {
        super(ScopeType.class, "PPF-G102");
    }

}
