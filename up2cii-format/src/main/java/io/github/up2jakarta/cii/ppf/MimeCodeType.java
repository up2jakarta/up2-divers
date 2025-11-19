package io.github.up2jakarta.cii.ppf;

import io.github.up2jakarta.cii.Documented;
import io.github.up2jakarta.cii.core.Agency;
import io.github.up2jakarta.cii.ppf.adapters.MimeCodeAdapter;
import io.github.up2jakarta.lov.CodeList;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on FR/PPF G4.17 : File mime code.
 */
@Generated(value = "PPF", comments = "by A.ABBESSI")
@Documented(value = "File mime code", agency = Agency.EN_16931, version = "2.3")
@XmlJavaTypeAdapter(MimeCodeAdapter.class)
public enum MimeCodeType implements CodeList<MimeCodeType> {

    PDF("application/pdf", "PDF"),
    ODS("application/vnd.oasis.opendocument.spreadsheet", "ODS"),
    XLS_X("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "XLSX"),
    XML("application/xml", "XML"),
    JPEG_JPG("image/jpeg", "JPEG / JPG"),
    PNG("image/png", "PNG"),
    CSV("text/csv", "CSV"),
    ;

    private final String name;
    private final String code;

    MimeCodeType(String code, String name) {
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
