package io.github.up2jakarta.cii.ppf;

import io.github.up2jakarta.cii.api.Agency;
import io.github.up2jakarta.cii.api.Documented;
import io.github.up2jakarta.cii.ppf.adapters.ContractTypeAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on FR/PPF G1.03 : Contract Type.
 */
@Generated(value = "PPF", comments = "by Abderrazek ABBESSI")
@Documented(value = "Contract type", agency = Agency.EN_16931, version = "2.3")
@XmlJavaTypeAdapter(ContractTypeAdapter.class)
public enum ContractType implements ReferenceType<ContractType> {

    MARKET("MARCHE", "Marché"),
    CONTRACT("CONTRAT", "Contrat");

    private final String name;
    private final String code;

    ContractType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCode() {
        return code;
    }

}
