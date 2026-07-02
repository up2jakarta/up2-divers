package io.github.up2jakarta.test.fmt.tree;

import io.github.up2jakarta.csv.BusinessId;
import io.github.up2jakarta.csv.BusinessLink;
import io.github.up2jakarta.csv.BusinessObject;
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.Linker;
import io.github.up2jakarta.csv.api.Overlink;
import io.github.up2jakarta.csv.api.Sublink;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Token;
import io.github.up2jakarta.test.fmt.sln.Tree90Linker;
import io.github.up2jakarta.test.impl.BusinessType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.LinkedList;
import java.util.List;

import static io.github.up2jakarta.lov.SeverityType.WARNING;
import static io.github.up2jakarta.test.impl.TermType.*;

@Valid
@BusinessType(NODE)
@Error(value = "CSV-N0DE", level = WARNING)
@BusinessObject(value = "90", overrides = {
        @Overlink(value = @BusinessLink("91"), replaces = @Sublink(value = "91", with = "92")),
        @Overlink(value = @BusinessLink("92"), replaces = @Sublink(value = "91", with = "93")),
        @Overlink(value = @BusinessLink("93"), replaces = @Sublink(value = "91", with = "94")),
        @Overlink(value = @BusinessLink("94"), excludes = "91") // Leaf node
})
public final class Tree90 implements Segment {

    @NotNull
    @Up2Token
    @BusinessId
    @Position(0)
    @BusinessType(UUID)
    private final String key;

    @NotEmpty
    @Position(1)
    @BusinessType(NONE)
    private final String value;

    @BusinessLink(value = "91", automatic = true, bean = @Linker(Tree90Linker.class))
    private final List<Tree90> nodes = new LinkedList<>();

    public Tree90(String key, String value) {
        this.value = value;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public List<Tree90> getNodes() {
        return nodes;
    }

}
