package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.core.BeanContext;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.Up2Flatter;
import io.github.up2jakarta.csv.core.Up2Mapper;
import io.github.up2jakarta.csv.data.DataResolver;
import io.github.up2jakarta.csv.data.DynamicType;
import io.github.up2jakarta.csv.io.dto.Note;
import io.github.up2jakarta.lov.core.BeanException;
import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;

import static io.github.up2jakarta.csv.io.misc.Tests.TUGenerator.path;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class SingleNoteTests {

    private static final int MIN = 10;
    private static final int MAX = 99;

    private final SingleReader<Note> reader;
    private final File file;

    @Autowired
    SingleNoteTests(BeanContext context, CSVFormat fmt) throws BeanException, IOException {
        final Up2Factory<DynamicType> factory = new Up2Factory<>(context, DataResolver.dynamic());
        final Up2Flatter<Note, DynamicType> format = factory.format(Note.class);
        final SingleWriter<Note, DynamicType> writer = new SingleWriter<>(format, fmt);
        final Up2Mapper<Note, DynamicType> mapper = factory.build(Note.class);
        this.reader = new SingleReader<>(mapper, fmt);
        this.file = path(this.getClass()).resolve("notes.csv").toFile();
        // Generating file
        writer.open(file);
        for (var i = MIN; i <= MAX; i++) {
            writer.write(new Note("K" + i, "Note N°" + i));
            writer.flush();
        }
        writer.close();
    }

    @Test
    void testReader() throws IOException {
        // GIVEN
        reader.open(file);
        // WHEN
        var i = MIN;
        Note note;
        while ((note = reader.next()) != null) {
            assertEquals("K" + i, note.getKey());
            assertEquals("Note N°" + i, note.getContent());
            i++;
        }
        reader.close();
        // THEN
        assertEquals(MAX, i - 1);
    }

    @Test
    void testHeader() throws IOException {
        // WHEN
        final String[] header = reader.open(file);
        reader.close();
        // THEN
        assertNotNull(header);
        assertEquals(2, header.length);
        assertEquals("Key", header[0]);
        assertEquals("Content", header[1]);
    }

}
