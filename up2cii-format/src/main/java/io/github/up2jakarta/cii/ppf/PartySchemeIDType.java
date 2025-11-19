package io.github.up2jakarta.cii.ppf;

import io.github.up2jakarta.cii.Documented;
import io.github.up2jakarta.cii.core.Agency;
import io.github.up2jakarta.lov.CodeList;

/**
 * Specific {@link Documented} for URI Scheme Identification Code.
 */
@Documented(value = "URI Scheme Identification", agency = Agency.EN_16931, version = "2.3")
public interface PartySchemeIDType<T extends PartySchemeIDType<T>> extends CodeList<T> {

}
