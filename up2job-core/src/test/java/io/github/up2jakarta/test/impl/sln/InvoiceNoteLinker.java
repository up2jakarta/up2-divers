package io.github.up2jakarta.test.impl.sln;

import io.github.up2jakarta.csv.api.ILinker;
import io.github.up2jakarta.test.dto.Invoice;
import io.github.up2jakarta.test.dto.Note;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InvoiceNoteLinker implements ILinker<Invoice, Note> {

    @Override
    public List<Note> from(Invoice parent) {
        return parent.getNotes();
    }

    @Override
    public void link(Invoice parent, Note child) {
        parent.getNotes().add(child);
    }
}
