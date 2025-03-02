package io.github.up2jakarta.cii.ppf;

import io.github.up2jakarta.cii.api.Agency;
import io.github.up2jakarta.cii.api.Documented;
import io.github.up2jakarta.cii.api.Duplicated;
import io.github.up2jakarta.cii.ppf.adapters.MimeCodeAdapter;
import io.github.up2jakarta.csv.extension.CodeList;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on FR/PPF G4.17 : File mime code.
 */
@Generated(value = "PPF", comments = "by A.ABBESSI")
@Documented(value = "File mime code", agency = Agency.EN_16931, version = "2.3")
@XmlJavaTypeAdapter(MimeCodeAdapter.class)
public enum MimeCodeType implements CodeList<MimeCodeType> {

    DOC("application/msword", "DOC"),
    PDF("application/pdf", "PDF"),

    @Duplicated("PKCS7")
    P7S("application/pkcs7-mime", "P7S"),
    RTF("application/rtf", "RTF"),

    @Duplicated("XLS")
    XLC("application/vnd.ms-excel", "XLC"),

    @Duplicated("PPT")
    PPS("application/vnd.ms-powerpoint", "PPS"),
    ODP("application/vnd.oasis.opendocument.presentation", "ODP"),
    ODS("application/vnd.oasis.opendocument.spreadsheet", "ODS"),
    ODT("application/vnd.oasis.opendocument.text", "ODT"),
    PPT_X("application/vnd.openxmlformats-officedocument.presentationml.presentation", "PPTX"),
    XLS_X("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "XLSX"),
    DOC_X("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "DOCX"),
    BZ2("application/x-bzip2", "BZ2"),

    @Duplicated("GZIP")
    GZ("application/x-gzip", "GZ"),
    P7B("application/x-pkcs7-certificates", "P7B"),
    TGZ("application/x-tar", "TGZ"),
    X_HTML("application/xhtml+xml", "XHTML"),
    XML("application/xml", "XML"),
    XLM("application/xml ou text/xml", "XLM"),
    ZIP("application/zip", "ZIP"),
    BMP("image/bmp", "BMP"),
    FAX("image/g3fax", "FAX"),
    GIF("image/gif", "GIF"),
    JPEG_JPG("image/jpeg", "JPEG / JPG"),
    PNG("image/png", "PNG"),
    SVG("image/svg+xml", "SVG"),
    TIF_TIFF("image/tiff", "TIF / TIFF"),
    CSV("text/csv", "CSV"),
    HTM_HTML("text/html", "HTM / HTML"),
    TXT("text/plain", "TXT"),
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
