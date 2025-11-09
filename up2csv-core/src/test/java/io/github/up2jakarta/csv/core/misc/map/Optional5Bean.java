package io.github.up2jakarta.csv.core.misc.map;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.Segment;

import java.util.Optional;

public class Optional5Bean implements Segment {

    @Position(0)
    @Up2Number
    private int id;

    @Fragment(1)
    private Optional<Content> content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Optional<Content> getContent() {
        return content;
    }

    public void setContent(Optional<Content> content) {
        this.content = content;
    }

    public static class Content implements Segment {
        @Position(0)
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
