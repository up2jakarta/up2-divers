package io.github.up2jakarta.cii.ppf;

import io.github.up2jakarta.cii.api.Agency;
import io.github.up2jakarta.cii.api.Documented;
import io.github.up2jakarta.cii.ppf.adapters.ChargeReasonCodeAdapter;
import io.github.up2jakarta.csv.extension.CodeList;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Specific {@link Documented} for Allowance Charge Reason Code.
 */
@Documented(value = "Allowance Charge Reason Code", agency = Agency.EN_16931, version = "2.3")
@XmlJavaTypeAdapter(ChargeReasonCodeAdapter.class)
public interface ChargeReasonCodeType<T extends ChargeReasonCodeType<T>> extends CodeList<T> {

}
