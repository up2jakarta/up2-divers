package io.github.up2jakarta.cii.ppf;

import io.github.up2jakarta.cii.api.Agency;
import io.github.up2jakarta.cii.api.Documented;
import io.github.up2jakarta.cii.ppf.adapters.ProfileAdapter;
import io.github.up2jakarta.csv.extension.CodeList;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on FR/PPF S1.06 : Profile Type.
 */
@Generated(value = "PPF", comments = "by Abderrazek ABBESSI")
@Documented(value = "Profile type", agency = Agency.EN_16931, version = "2.3")
@XmlJavaTypeAdapter(ProfileAdapter.class)
public enum ProfileType implements CodeList<ProfileType> {

    EN_16931("urn:cen.eu:en16931:2017"),

    @Deprecated(since = "2.3", forRemoval = true)
    MINIMUM("urn:factur-x.eu:1p0:minimum"),

    @Deprecated(since = "2.3", forRemoval = true)
    BASIC_WL("urn:factur-x.eu:1p0:basicwl"),

    @Deprecated(since = "2.3", forRemoval = true)
    BASIC("urn:cen.eu:en16931:2017:compliant:factur-x.eu:1p0:basic"),

    @Deprecated(since = "2.3", forRemoval = true)
    EXTENDED("urn:cen.eu:en16931:2017:conformant:factur-x.eu:1p0:extended"),
    ;

    private final String uri;

    ProfileType(String uri) {
        this.uri = uri;
    }

    @Override
    public String getCode() {
        return uri;
    }

    @Override
    public String getName() {
        return uri;
    }
}
