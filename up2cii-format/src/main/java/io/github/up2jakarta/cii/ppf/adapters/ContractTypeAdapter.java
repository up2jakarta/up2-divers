package io.github.up2jakarta.cii.ppf.adapters;

import io.github.up2jakarta.cii.ppf.ContractType;
import io.github.up2jakarta.csv.misc.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link ContractType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class ContractTypeAdapter extends CodeListConverter<ContractType> {

    ContractTypeAdapter() {
        super(ContractType.class, "PPF-G103");
    }

}


