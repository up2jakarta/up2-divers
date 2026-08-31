package io.github.up2jakarta.test.impl.sln;

import io.github.up2jakarta.csv.api.ILinker;
import io.github.up2jakarta.test.impl.dto.Invoice;
import io.github.up2jakarta.test.impl.dto.Note;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.github.up2jakarta.csv.ext.Beans.concat;
import static java.util.Arrays.asList;

@Component
public class InvoiceNoteLinker implements ILinker<Invoice, Note> {

    @Override
    public List<Note> from(Invoice parent) {
        final Note[] notes = parent.getNotes();
        if (notes.length != 0) {
            return asList(notes);
        }
        return List.of();
    }

    @Override
    public void link(Invoice parent, Note child) {
        final Note[] notes = parent.getNotes();
        parent.setNotes(concat(notes, child));
    }
}
