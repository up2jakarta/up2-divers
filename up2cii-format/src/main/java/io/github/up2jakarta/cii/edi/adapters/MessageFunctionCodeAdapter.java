package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.MessageFunctionCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link MessageFunctionCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class MessageFunctionCodeAdapter extends CodeListAdapter<MessageFunctionCodeType> {

    @Inject
    public MessageFunctionCodeAdapter() {
        super(MessageFunctionCodeType.class, "ECE-1225");
    }

}
