package io.github.up2jakarta.cii.ppf;

import io.github.up2jakarta.cii.Documented;
import io.github.up2jakarta.cii.core.Agency;
import io.github.up2jakarta.cii.ppf.adapters.ScopeAdapter;
import io.github.up2jakarta.lov.CodeList;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on FR/PPF G1.02 : Scope Type.
 */
@Generated(value = "PPF", comments = "by A.ABBESSI")
@Documented(value = "Scope type", agency = Agency.EN_16931, version = "2.3")
@XmlJavaTypeAdapter(ScopeAdapter.class)
public enum ScopeType implements CodeList<ScopeType> {

    B1("B1", "Dépôt d'une facture de bien"),
    S1("S1", "Dépôt d'une facture de service"),
    M1("M1", "Dépôt d'une facture double (livraison de bien et services qui ne sont pas accessoires l'une de l'autre)"),
    B2("B2", "Dépôt d'une facture de bien déjà payée"),
    S2("S2", "Dépôt d'une facture de service déjà payée"),
    M2("M2", "Dépôt d'une facture double déjà payée"),
    S3("S3", "Dépôt d'une facture de service de sous-traitance avec paiement direct"),
    B4("B4", "Dépôt d'une facture définitive (après acompte) de bien"),
    S4("S4", "Dépôt d'une facture définitive (après acompte) de service"),
    M4("M4", "Dépôt d'une facture définitive (après acompte) double"),
    S5("S5", "Dépôt par un sous-traitant d’une facture de service"),
    S6("S6", "Dépôt par un cotraitant d’une facture de service"),
    B7("B7", "Dépôt d'une facture de bien ayant fait l'objet d'un e-reporting (TVA déjà collectée)"),
    S7("S7", "Dépôt d'une facture de service ayant fait l'objet d'un e-reporting (TVA déjà collectée)"),
    ;

    private final String name;
    private final String code;

    ScopeType(String code, String name) {
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
